/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem.Task;

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
public class OldItemTask {

    // Task identifier
    private static int taskId = -1;
    private final int autoId;
    private String taskName;

    
    // Task stuff
    private TaskState taskState;
    private String task;
    private TaskLogging taskLog;

    
    /**
     * Null constructor
     */
    public OldItemTask() {
        this.autoId = setAutoId();
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }

    
    /**
     * Create task from input
     * 
     * @param task 
     */
    public OldItemTask(String task) {
        this.autoId = setAutoId();
        this.task = task;
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }
    
    
    
    /**
     * Generate item task from name & task
     * 
     * @param task 
     */
    public OldItemTask(String[] task) {
        this.autoId = setAutoId();
        this.task = task[0];
        this.taskName = task[1];
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }
    

    /**
     * Auto-increment
     * 
     * @return 
     */
    private static int setAutoId() {
        OldItemTask.taskId++;
        return OldItemTask.taskId;
    }
    

    /**
     * 
     * @return 
     */
    public int getTaskId() {
        return this.autoId;
    }
    
    
    /**
     * Return task name
     * 
     * @return 
     */
    public String getTaskName() {
        return this.taskName;
    }
    
    
    /**
     * Return task
     * 
     * @return Task
     */
    public String getTask() {
        return this.task;
    }
    
    
    /**
     * Fetch task log
     * 
     * @return TaskLogging
     */
    public TaskLogging getTaskLog() {
        return this.taskLog;
    }
    
    
    /**
     * Check if task name matches query
     * 
     * @param query
     * @return True/False
     */
    public boolean isTask(String query) {
        String holder = this.taskName.toLowerCase();
        return holder.equals(query.toLowerCase());
    }

    
    /**
     * Fetch task state
     * 
     * @return TaskState
     */
    public TaskState getTaskState() {
        return this.taskState;
    }
    
    
    /**
     * Set required state
     * 
     * @param newState 
     */
    public void setTaskState(TaskState newState) {
        this.taskState = newState;
    }
    
    
    /**
     * Mark task as pending
     * 
     */
    public void markAsPending() {
        this.taskState = TaskState.PENDING;
    }
    
    
    /**
     * Mark task as complete
     * 
     */
    public void markAsComplete() {
        this.taskState = TaskState.COMPLETE;
    }
    
    
    /**
     * Mark task active
     * 
     */
    public void markAsActive() {
        this.taskState = TaskState.ACTIVE;
    }
    
    
    /**
     * Mark task as error
     * 
     */
    public void markAsError() {
        this.taskState = TaskState.ERROR;
    }

    
    /**
     * Refresh task
     */
    public void refreshTask() {
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }
    
    /**
     * Run task and return logging
     * 
     * @return Map-Stdout & Stderr
     */
    public Map<String, String[]> runTask() {
        
        // Initialize output
        Map<String, String[]> output = new HashMap<>();
        String[] stdout, stderr;
        Process proc;
        
        // Configure running task
        String startTime = getCurrentTimeStamp();
        String threadName = Thread.currentThread().getName();
        taskLog.setStartTime(startTime);
        taskLog.setThreadName(threadName);
        
        // Try run task
        try {
            
            // Run and parse logging
            proc = Runtime.getRuntime().exec(this.task);
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
     * Fetch stdout log from reader
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
     */
    public void processTask() {
        
        // Update task state
        this.markAsActive();
        
        // Run task & Create log
        Map<String, String[]> procLogging = runTask();
        ProcessLog procLog = new ProcessLog(procLogging);
        this.taskLog.setProcLog(procLog);
        
        // Handle task state
        if ( procLogging.get("stdout") == null ) {
            this.markAsError();
        }
        else {
            this.markAsComplete();
        }
    }
    
    
    /**
     * 
     * @return 
     */
    public String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
    
    
    /**
     * Return task as string
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "ItemTask{" 
                + "autoId=" + autoId
                + ", taskName=" + taskName
                + ", taskState=" + taskState.toString()
                + ", task=" + task
                + ", taskLog=" + taskLog
         + '}';
    }

}
