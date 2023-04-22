/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workflowengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kenna
 */
public class ParallelTask extends RecursiveAction {

    // Attributes
    private final List<String> workload;
    private int threshold = 2;
    private static List<Map<String, String[]>> logs = new ArrayList<>();
    // private static int nExecs = 0;

    
    /**
     * Constructor with workload
     * 
     * @param workload 
     */
    public ParallelTask(List<String> workload) {
        this.workload = workload;
    }

    
    /**
     * Constructor with workload & threshold
     * @param workload
     * @param threshold 
     */
    public ParallelTask(List<String> workload, int threshold) {
        this.workload = workload;
        this.threshold = threshold;
    }

    
    /**
     * 
     */
    @Override
    protected void compute() {
        
        // Add all tasks to pool
        if( this.workload.size() >= this.threshold ) {
            // System.out.println("I am adding tasks");
            ForkJoinTask.invokeAll( subTasks() );
        }
        
        // Otherwise process
        else {
            // nExecs++;
            // System.out.println("I am executing task:\t" + nExecs);
            recursiveCompute( this.workload.size() - 1 );
        }
    }

    
    /**
     * Generate sub-tasks from tasks
     * 
     * @return List-Parallel Task
     */
    public List<ParallelTask> subTasks() {
        List<ParallelTask> output = new ArrayList<>();
        for(String i : this.workload) {
            List<String> task = new ArrayList<>();
            task.add(i);
            output.add(new ParallelTask(task));
        }
        return output;
    }

    
    /**
     * Descend down index of task list
     * 
     * @param taskIndex 
     */
    public void recursiveCompute(int taskIndex) {
        
        // Recursive descend task list
        if ( taskIndex > -1 ) {
            
            // Fetch active task
            String task = this.workload.get(taskIndex);
            
            // Run active
            Map<String, String[]> taskLog = this.runTask(task);
            logs.add(taskLog);
            
            // Run next
            recursiveCompute( taskIndex - 1 );
        }
        
        // Log no tasks
        else {
            System.out.println("No more tasks");
        }
    }
    
    
    /**
     * Run a task
     * 
     * @param task
     * @return Log
     */
    public Map<String, String[]> runTask(String task) {
        
        // Initialize output
        Map<String, String[]> output = new HashMap<>();
        
        
        // Run task
        try {
            
            // Run process and fetch log
            String startTime = getCurrentTimeStamp();
            String threadName = Thread.currentThread().getName();
            System.out.println("\n\nExecuting tasks on " + threadName + ":\t" + startTime);
            Process proc = Runtime.getRuntime().exec(task);
            String[] stdout = getTaskLog(proc.getInputStream());
            String[] stderr = getTaskLog(proc.getErrorStream());
            
            
            // Append info
            System.out.println("Process ID:\t" + proc.pid());
            System.out.println("Exit code:\t" + proc.exitValue());
            System.out.println("Cpu duration:\t" + proc.info().totalCpuDuration().get().toMillis());
            System.out.println("User:\t" + proc.info().user().get());
            
            // Add to log
            output.put("stdout", stdout);
            output.put("stderr", stderr);
            
            
        } catch (IOException ex) {
            Logger.getLogger(WorkflowEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
    
    
    /**
     * Fetch stdout log from reader
     * 
     * @param inputStream
     * @return String[]
     */
    public String[] getTaskLog(InputStream inputStream) {
        
        // Set needed variables
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> log = reader.lines().toList();
        String[] output = new String[ log.size() ];
        
        // Append each to output
        for(int i = 0; i < log.size(); i++ ) {
            output[i] = log.get(i);
        }
        
        // Return
        return output;
    }
    
    
    /**
     * Log Map
     * 
     * @return 
     */
    public List<Map<String, String[]>> getTaskLogs() {
        return logs;
    }

    
    /**
     * Return tasks
     * 
     * @return List-Task
     */
    public List<String> getTasks() {
        return this.workload;
    }
    
    /**
     * 
     * @return 
     */
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
