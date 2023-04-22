/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

import Model.WorkItem.Task.*;
import Utils.DateUtils;
import Utils.TokenGenerator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenna
 */
public class OldNestedTask implements OldWorkItem {
    
    // Item identifier
    private static int itemId = -1;
    private int autoId;
    private String itemName;


    // Item level
    private final TokenGenerator tokenGenerator = new TokenGenerator();
    private final DateUtils dateUtils = new DateUtils();
    private final ItemType itemType = ItemType.NESTED;
    private ItemState itemState;
    private String lockId;
    private String lockDate;
    private String doneDate;


    // Task level
    private int taskCount;
    private int tasksDone;
    private List<OldItemTask> tasks;


    /**
     * Null constructor
     */
    public OldNestedTask() {
        this.autoId = setAutoId();
    }

    
    /**
     * Construct work item with a collection of sub-tasks
     * 
     * @param tasks 
     */
    public OldNestedTask(List<OldItemTask> tasks) {
        this.autoId = setAutoId();
        this.tasks = tasks;
        this.itemState = ItemState.TODO;
    }

    
    /**
     * Unlock item
     */
    @Override
    public void unlockItem() {
        this.lockDate = null;
        this.lockId = null;
        this.doneDate = null;
        this.tasksDone = 0;
    }

    
    /**
     * Not implemented in nested items
     */
    @Override
    public void unlockTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Unlock specific task
     * 
     * @param taskName 
     */
    @Override
    public void unlockTask(String taskName) {
        
        // Handle running task
        OldItemTask task = getTask(taskName);
        if ( task != null ) {
            this.unlockItem();
            task.refreshTask();
        }
        
        //
        else {
            System.out.println("Task not found, cannot unlock queried '" + taskName + "'");
        }
    }

    
    /**
     * Unlock nested tasks
     */
    @Override
    public void unlockTasks() {
        for (OldItemTask task : this.tasks) {
            task.refreshTask();
        }
    }

    
    /**
     * Refresh nested item
     */
    @Override
    public void refreshNestedItem() {
        this.unlockItem();
        this.unlockTasks();
    }
    
    
    /**
     * Run input task if not done
     * @param taskName
     */
    @Override
    public void handleTask(String taskName) {
        
        // Proceed if unlocked
        if ( this.itemState.isState("todo") ) {
            
            // Handle running task
            OldItemTask task = getTask(taskName);
            if ( task != null ) {

                // Lock item & process task
                this.setLockDate();
                this.lockItem();
                task.processTask();
            }

            //
            else {
                System.out.println("Task not found, cannot unlock queried '" + taskName + "'");
            }
        }
        
        // Otherwise pass
        else {
            System.out.println("Skipping item '" + this.itemName + "'");
        }
    }

    
    /**
     * Handle running all tasks
     */
    @Override
    public void handleTasks() {
        
        // Process tasks if unlocked
        if ( this.itemState.isState("todo") ) {
            
            // Process tasks
            this.setLockDate();
            this.lockItem();
            this.runTasks();
            
            // Set done state
            this.itemState = ItemState.DONE;
            this.setDoneDate();
            this.tasksDone++;
        }
        
        // Otherwise pass
        else {
            System.out.println("Skipping item '" + this.itemName + "'");
        }
    }

    
    /**
     * Not implemented in single items
     * 
     */
    @Override
    public void runTasks() {
        
        // Pass over all items
        for (OldItemTask task : this.tasks) {
            
            // Run task if pending
            if ( task.getTaskState().isState("pending") ) {
                task.processTask();
            }
            
            // Otherwise pass
            else {
                System.out.println("Skipping task '" + task.getTaskId() + "', in '" + this.itemName + "'");
            }
            
        }
    }

    
    /**
     * Not implemented in single items
     * 
     * @param task
     */
    @Override
    public void runTask(OldItemTask task) {
        
        // Run if pending
        if ( task.getTaskState().isState("pending") ) {
            task.processTask();
        }
            
        // Otherwise pass
        else {
            System.out.println("Skipping task '" + task.getTaskId() + "', in '" + this.itemName + "'");
        }
    }
    
    
    /**
     * Auto-increment
     * 
     * @return 
     */
    private static int setAutoId() {
        OldNestedTask.itemId++;
        return OldNestedTask.itemId;
    }
    

    /**
     * Get item ID
     * 
     * @return 
     */
    @Override
    public int getItemId() {
        return this.autoId;
    }

    
    /**
     * Get item state
     * 
     * @return ItemState 
     */
    public ItemState getState() {
        return this.itemState;
    }
    
    
    /**
     * Get item name
     * 
     * @return 
     */
    @Override
    public String getItemName() {
        return this.itemName;
    }

    
    /**
     * Return item type
     * 
     * @return 
     */
    @Override
    public ItemType getType() {
        return this.itemType;
    }

    
    /**
     * Not implemented in nested items
     * 
     * @return 
     */
    @Override
    public OldItemTask getTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Return tasks
     * 
     * @return 
     */
    @Override
    public List<OldItemTask> getTasks() {
        return this.tasks;
    }

    
    /**
     * Return index of task 
     * 
     * @param taskNam
     * @return Index
     */
    public int indexOf(String taskNam) {
        
        // Initialize serarch params
        int counter = 0;
        int taskIndex = -1;
        boolean found = false;
        
        // Search until found
        while( counter < tasks.size() && !found ) {
            OldItemTask query = tasks.get(counter);
            if ( query.isTask(lockId) ) {
                found = true;
                taskIndex = counter;
            }
            
            else {
                counter++;
            }
        }
        
        // Return index
        return taskIndex;
    }
    
    
    /**
     * Get task
     * 
     * @param taskName
     * @return 
     */
    @Override
    public OldItemTask getTask(String taskName) {
        
        // Initialize search params
        OldItemTask output = null;
        int taskIndex = indexOf(taskName);
        
        // Handle result
        if ( taskIndex > -1 ) {
            output = tasks.get(taskIndex);
        }
        
        // Return output
        return output;
    }

    
    /**
     * Return lockId
     * 
     * @return 
     */
    @Override
    public String getLockId() {
        return this.lockId;
    }

    
    /**
     * Return lock date
     * 
     * @return 
     */
    @Override
    public String getLockDate() {
        return this.lockDate;
    }

    
    /**
     * Return done date
     * 
     * @return 
     */
    @Override
    public String getDoneDate() {
        return this.doneDate;
    }

    
    /**
     * Not implemented in nested items
     * 
     * @return 
     */
    @Override
    public TaskLogging getTaskLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Return all task logs as hash map
     * 
     * @return 
     */
    @Override
    public Map<String, TaskLogging> getTaskLogs() {
        
        // Initialize output
        Map<String, TaskLogging> output = new HashMap<>();
        
        // Fetch logs
        for( OldItemTask task : this.tasks ) {
            output.put(task.getTaskName(), task.getTaskLog());
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Fetch queried task log
     * 
     * @param taskName
     * 
     * @return TaskLogging/null
     */
    @Override
    public TaskLogging getTaskLog(String taskName) {
        
        // Handle running task
        TaskLogging output = null;
        OldItemTask task = getTask(taskName);
        if ( task != null ) {
            output = task.getTaskLog();
        }
        
        // Otherwise pass
        else {
            System.out.println("Task not found, cannot fetch TaskLogging for queried '" + taskName + "'");
        }
        
        // Return output/null
        return output;
    }

    
    /**
     * Not implemented in nested tasks
     * 
     * @return 
     */
    @Override
    public TaskState getTaskState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Return state for task
     * 
     * @param taskName
     * @return 
     */
    @Override
    public TaskState getTaskState(String taskName) {
        
        // Initialize and fetch task
        TaskState output = null;
        OldItemTask task = getTask(taskName);
        
        // Proceed if not null
        if ( task != null ) {
            output = task.getTaskState();
        }
        
        // Otherwise pass
        else {
            System.out.println("Task not found, cannot fetch TaskState for queried '" + taskName + "'");
        }
        
        // Return results
        return output;
    }

    
    /**
     * Fetch states for all tasks
     * 
     * @return 
     */
    @Override
    public Map<String, TaskState> getTaskStates() {
        
        // Initialize output
        Map<String, TaskState> output = new HashMap<>();
        
        // Fetch logs
        for( OldItemTask task : this.tasks ) {
            output.put(task.getTaskName(), task.getTaskState());
        }
        
        // Return output
        return output;
    }

    
    /**
     * Fetch list of tasks by state
     * 
     * @param query
     * @return 
     */
    private List<OldItemTask> getByState(TaskState query) {
    
        // Initialize output
        List<OldItemTask> output = new ArrayList<>();
        
        // Query 
        for(OldItemTask task : this.tasks) {
            if ( task.isTask(query.toString()) ) {
                output.add(task);
            }
        }
        
        // Return list
        return output;
    }
    
    
    /**
     * Summarize nested item progress
     * 
     * @return Task count per state
     */
    @Override
    public Map<TaskState, Integer> summarizeTaskStates() {
        
        // Initialize output
        Map<TaskState, Integer> output = new HashMap<>();
        
        // Get by state
        for ( TaskState taskState : TaskState.values() ) {
            output.put( taskState, getByState(taskState).size() );
        }
        
        // Return results
        return output;
    }

    
    /**
     * Set item name
     * 
     * @param itemName 
     */
    @Override
    public void setItemName(String itemName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in nested items
     * 
     * @param task 
     */
    @Override
    public void setTask(OldItemTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Set nested tasks
     * 
     * @param tasks 
     */
    @Override
    public void setTasks(List<OldItemTask> tasks) {
        this.tasks = tasks;
        this.taskCount = tasks.size();
    }

    
    /**
     * Update task by name
     * 
     * @param taskName
     * @param task 
     */
    @Override
    public void setTask(String taskName, OldItemTask task) {
        
        // Initialize and fetch task
        OldItemTask queryTask = getTask(taskName);
        int taskIndex = indexOf(taskName);
        
        // Proceed if not null
        if ( task != null ) {
            tasks.remove(taskIndex);
            tasks.add(task);
        }
        
        // Otherwise pass
        else {
            System.out.println("Task not found, cannot update Task for queried '" + taskName + "'");
        }
    }

    
    /**
     * 
     * Lock Item
     * 
     */
    @Override
    public String lockItem() {
        String output = tokenGenerator.generateSalt();
        this.lockId = output;
        this.setLockDate();
        return output;
    }

    
    /**
     * Set lock date
     *  
     */
    @Override
    public void setLockDate() {
        this.lockDate = dateUtils.getNowString();
    }

    
    /**
     * Set done date
     *  
     */
    @Override
    public void setDoneDate() {
        this.doneDate = dateUtils.getNowString();
        this.itemState = ItemState.DONE;
    }

    
    /**
     * Not implemented in nested items
     * 
     * @param taskState 
     */
    @Override
    public void setTaskState(TaskState taskState) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Set task state for query
     * 
     * @param taskState
     * @param taskName 
     */
    @Override
    public void setTaskState(TaskState taskState, String taskName) {
        
        // Initialize and fetch task
        OldItemTask task = getTask(taskName);
        
        // Proceed if not null
        if ( task != null ) {
            task.setTaskState(taskState);
        }
        
        // Otherwise pass
        else {
            System.out.println("Task not found, cannot update TaskState for queried '" + taskName + "'");
        }
    }

    
    /**
     * Set specific task state for queries
     * 
     * @param taskStates 
     */
    @Override
    public void setTaskStates(Map<String, TaskState> taskStates) {
        
        // Update task states
        for ( String taskName : taskStates.keySet() ) {
            TaskState taskState = taskStates.get(taskName);
            setTaskState(taskState, taskName);
        }
    }

    
    /**
     * Return task count
     * 
     * @return 
     */
    @Override
    public int getTaskCount() {
        return this.taskCount;
    }

    
    /**
     * Return tasks done
     * 
     * @return 
     */
    @Override
    public int getTasksDone() {
        return this.tasksDone;
    }

    
    /**
     * Update task counts
     */
    @Override
    public void setTaskCount() {
        this.taskCount = this.tasks.size();
    }

    
    /**
     * Update number of tasks done
     */
    @Override
    public void setTasksDone() {
        this.tasksDone = summarizeTaskStates().get(TaskState.COMPLETE);
    }

    
    /**
     * Return item as string
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "NestedTask{"
                + "autoId=" + autoId
                + ", itemName=" + itemName
                + ", itemType=" + itemType.toString()
                + ", itemState=" + itemState.toString()
                + ", lockId=" + lockId
                + ", lockDate=" + lockDate
                + ", doneDate=" + doneDate
                + ", taskCount=" + taskCount
                + ", tasksDone=" + tasksDone
                + ", tasks=" + tasks
        + '}';
    }

    
    /**
     * Not implemented in nested tasks
     */
    @Override
    public void handleTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Not implemented in single items
     */
    @Override
    public void runTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
