/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

import Model.WorkItem.Task.*;
import java.util.List;
import java.util.Map;


/**
 *
 * @author kenna
 */
public interface OldWorkItem {
    
    /**
     * Unlock item
     */
    public void unlockItem();
    
    
    /**
     * Unlock task
     */
    public void unlockTask();
    
    
    /**
     * Unlock specific task
     * 
     * @param taskName 
     */
    public void unlockTask(String taskName);
    
    
    /**
     * Unlock all tasks in nested item
     */
    public void unlockTasks();
    
    
    /**
     * Refresh nested item
     */
    public void refreshNestedItem();
    
    /**
     * Run task
     */
    public void runTask();
    
    
    /**
     * Run tasks
     */
    public void runTasks();
    
    
    /**
     * Run specific task
     * 
     * @param task 
     */
    public void runTask(OldItemTask task);
    
    
    /**
     * Handle running task
     */
    public void handleTask();
    
    
    /**
     * Handle running a specific task
     * @param taskName
     */
    public void handleTask(String taskName);
    
    
    /**
     * Handle running all tasks
     */
    public void handleTasks();

    
    /**
     * 
     * Getters 
     * 
    */
    
    /**
     * 
     * @return 
     */
    public int getItemId();

    
    /**
     * 
     * @return 
     */
    public ItemState getState();
    
    
    /**
     * 
     * @return 
     */
    public String getItemName();


    /**
     * 
     * @return 
     */
    public ItemType getType();


    /**
     * 
     * @return 
     */
    public OldItemTask getTask();


    /**
     * 
     * @return 
     */
    public int getTaskCount();
    
    
    /**
     * 
     * @return 
     */
    public int getTasksDone();


    /**
     * 
     * @return 
     */
    public List<OldItemTask> getTasks();


    /**
     * 
     * @param taskName
     * @return 
     */
    public OldItemTask getTask(String taskName);
    

    /**
     * 
     * @return 
     */
    public String getLockId();


    /**
     * 
     * @return 
     */
    public String getLockDate();

    
    /**
     * 
     * @return 
     */
    public String getDoneDate();
    

    /**
     * 
     * @return 
     */
    public TaskLogging getTaskLog();

    
    /**
     * 
     * @return 
     */
    public Map<String, TaskLogging> getTaskLogs();

    
    /**
     * 
     * @param taskName
     * @return 
     */
    public TaskLogging getTaskLog(String taskName);
    
    
    /**
     * 
     * @return 
     */
    public TaskState getTaskState();


    /**
     * 
     * @param taskName
     * @return 
     */
    public TaskState getTaskState(String taskName);


    /**
     * 
     * @return 
     */
    public Map<String, TaskState> getTaskStates();

    
    /**
     * 
     * @return 
     */
    public Map<TaskState, Integer> summarizeTaskStates();
    
    
    /**
     * 
     * Setters 
     * 
    */
    
    /**
     * 
     * @param itemName 
     */
    public void setItemName(String itemName);
    

    /**
     * 
     * @param task 
     */
    public void setTask(OldItemTask task);

    
    /**
     * 
     */
    public void setTaskCount();

    
    /**
     * 
     */
    public void setTasksDone();
    

    /**
     * 
     * @param tasks 
     */
    public void setTasks(List<OldItemTask> tasks);


    /**
     * 
     * @param taskName
     * @param task 
     */
    public void setTask(String taskName, OldItemTask task);
    

    /**
     * 
     * @return lockId token
     */
    public String lockItem();


    /**
     * 
     */
    public void setLockDate();


    /**
     *  
     */
    public void setDoneDate();
    

    /**
     * 
     * @param taskState 
     */
    public void setTaskState(TaskState taskState);


    /**
     * 
     * @param taskState
     * @param taskName 
     */
    public void setTaskState(TaskState taskState, String taskName);


    /**
     * 
     * @param taskStates 
     */
    public void setTaskStates(Map<String, TaskState> taskStates);
    
    
    /**
     * Return Item as string
     * 
     * @return 
     */
    @Override
    public String toString();
}
