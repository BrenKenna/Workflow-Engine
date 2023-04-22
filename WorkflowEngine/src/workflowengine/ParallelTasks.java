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
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * List of tasks has to be external?
 *  Take apart example to see what exactly is going on
 *   same for recursive tasks
 *
 * 
 * @author kenna
 */
public class ParallelTasks extends RecursiveAction {

    // 
    private final String[] tasks;
    private final List<Map<String, String[]>> logs;
    
    
    /**
     * Constructor
     * 
     * @param tasks 
     */
    public ParallelTasks(String [] tasks) {
        this.tasks = tasks;
        this.logs = new ArrayList<>() ;
    }

    
    /**
     * 
     * Needs to be recursive?
     * 
     */
    @Override
    protected void compute() {
        for(String task : tasks) {
            Map<String, String[]> taskLog = this.runTask(task);
            this.logs.add(taskLog);
        }
    }

    
    /**
     * List of tasks has to be external
     * 
     * @param taskIndex 
     */
    public void recursiveCompute(int taskIndex) {
        
        // Fetch active task
        String task = this.tasks[taskIndex];

        // Recursive logic
        if ( taskIndex > 0 ) {
            
            // Run active
            Map<String, String[]> taskLog = this.runTask(task);
            this.logs.add(taskLog);
            
            // Run next
            recursiveCompute( taskIndex - 1 );
        }
        
        // Run last
        else {
            Map<String, String[]> taskLog = this.runTask(task);
            this.logs.add(taskLog);
        }
    }
    
    
    /**
     * Run task and return output
     * 
     * proc.info():
     *  - totalCpuDuration() Optional<Duration>
     *  - arguments()
     *  - commandLine()
     * 
     * @param task
     * @return Map of Stdout and Stderr logs
     */
    public Map<String, String[]> runTask(String task) {
        
        // Initialize output
        Map<String, String[]> output = new HashMap<>();
        
        
        // Run task
        try {
            
            // Run process and fetch log
            System.out.println("\n\nExecuting tasks from pool: " + getCurrentTimeStamp());
            Process proc = Runtime.getRuntime().exec(task);
            String[] stdout = getTaskLog(proc.getInputStream());
            String[] stderr = getTaskLog(proc.getErrorStream());
            System.out.println("Process ID = " + proc.pid());
            System.out.println("Exit code = " + proc.exitValue());
            System.out.println("Cpu duration = " + proc.info().totalCpuDuration().get().toMillis());
            System.out.println("User = " + proc.info().user().get());
            
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
    
    
    public List<Map<String, String[]>> getTaskLogs() {
        return this.logs;
    }
    
    public String[] getTasks() {
        return this.tasks;
    }
    
    
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
