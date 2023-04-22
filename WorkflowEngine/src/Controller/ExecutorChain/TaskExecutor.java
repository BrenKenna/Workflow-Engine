/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ExecutorChain;


import Controller.Modifier;
import Model.WorkItem.Task.ItemTask;
import Model.WorkItem.Task.ProcessLog;
import Model.WorkItem.Task.TaskLogging;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author kenna
 */
public class TaskExecutor  {
    
    // Attributes
    private final Modifier modifier;

    
    /**
     * Default constructor
     */
    public TaskExecutor() {
        this.modifier = new Modifier();
    }
   
    
    /**
     * Run task and update logging
     * 
     * @param task
     * @return Stdout, Stderr
     */
    public Map<String, String[]> runTask(ItemTask task) {
        
        // Initialize output
        Map<String, String[]> output = new HashMap<>();
        String[] stdout, stderr;
        Process proc;
        TaskLogging taskLog = task.getTaskLog();
        
        // Configure running task
        String startTime = getCurrentTimeStamp();
        String threadName = Thread.currentThread().getName();
        taskLog.setStartTime(startTime);
        taskLog.setThreadName(threadName);
        
        // Try run task
        try {
            
            // Run and parse logging
            proc = Runtime.getRuntime().exec(task.getTask());
            stdout = getProcesskLog(proc.getInputStream());
            stderr = getProcesskLog(proc.getErrorStream());
            taskLog.setProcID(proc.pid());
            taskLog.setCpuDuration( proc.info().totalCpuDuration());
            
            // Add to log map
            output.put("stdout", stdout);
            output.put("stderr", stderr);
        }
        
        // Otherwise return expection
        catch (IOException ex) {
            stderr = new String[1];
            stderr[0] = ex.toString();
            output.put("stdout", null);
            output.put("stderr", stderr);
        }
        
        // Return output
        String endTime = getCurrentTimeStamp();
        taskLog.setEndTime(endTime);
        return output;
    }
    
    
    /**
     * Convert InputStream to String[] to form stdout/stderr
     * 
     * @param inputStream
     * @return String[]
     */
    public String[] getProcesskLog(InputStream inputStream) {
        
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
     * Process task
     * 
     * @param task
     */
    public void processTask(ItemTask task) {
        
        // Update task state
        modifier.markTaskAsActive(task);
        
        // Run task & Create log
        Map<String, String[]> procLogging = runTask(task);
        ProcessLog procLog = new ProcessLog(procLogging);
        task.getTaskLog().setProcLog(procLog);
        
        // Set task in error state if stdout is null
        if ( procLogging.get("stdout") == null ) {
            modifier.markTaskAsError(task);
        }
        
        // Otherwise mark as complete
        else {
            modifier.markTaskAsDone(task);
        }
    }

    
    /**
     * Get current time dd/MM/yyyy hh:mm:ss
     * 
     * @return 
     */
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
