/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

import Model.WorkItem.Task.*;
import Utils.DateUtils;
import Utils.TokenGenerator;
import java.util.List;
import java.util.Map;


/**
 *
 * @author kenna
 */
public class OldSingleTask implements OldWorkItem {

    // Item identifier
    private static int itemId = -1;
    private int autoId;
    private String itemName;


    // Item level
    private final TokenGenerator tokenGenerator = new TokenGenerator();
    private final DateUtils dateUtils = new DateUtils();
    private ItemType itemType = ItemType.SINGLE;
    private ItemState itemState;
    private String lockId;
    private String lockDate;
    private String doneDate;


    // Task level
    private int taskCount;
    private int tasksDone;
    private OldItemTask task;


    /**
     * Null constructor
     */
    public OldSingleTask() {
        this.autoId = setAutoId();
    }

    
    /**
     * Create work item from task
     * 
     * @param task 
     */
    public OldSingleTask(OldItemTask task) {
        this.autoId = setAutoId();
        this.task = task;
        this.itemState = ItemState.TODO;
    }

    
    
    /**
     * Unlock item
     */
    @Override
    public void unlockItem() {
        task.refreshTask();
        this.lockDate = null;
        this.lockId = null;
        this.doneDate = null;
        this.tasksDone = 0;
    }

    
    /**
     * Unlock task
     */
    @Override
    public void unlockTask() {
        task.refreshTask();
    }

    
    /**
     * Not implemented in single items
     * 
     * @param taskName 
     */
    @Override
    public void unlockTask(String taskName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     */
    @Override
    public void unlockTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     */
    @Override
    public void refreshNestedItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Handle running task
     */
    @Override
    public void handleTask() {
        if ( this.task.getTaskState().isState("pending") ) {
            this.runTask();
        }
        
        // Pass
        else {
            System.out.println("Skipping __" + ", task is not pending");
        }
    }
    
    /**
     * Run tasks
     */
    @Override
    public void runTask() {
        
        // Lock item
        this.setLockDate();
        this.lockItem();
        
        // Process task
        this.task.processTask();
        
        // Mark as done
        this.setDoneDate();
        this.itemState = ItemState.DONE;
    }

    
    /**
     * Not implemented in single items
     * 
     */
    @Override
    public void runTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     * 
     * @param task 
     */
    @Override
    public void runTask(OldItemTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
     * Auto-increment
     * 
     * @return 
     */
    private static int setAutoId() {
        OldSingleTask.itemId++;
        return OldSingleTask.itemId;
    }
    

    /**
     * Return task Id
     * 
     * @return 
     */
    @Override
    public int getItemId() {
        return this.autoId;
    }
    

    /**
     * Return task name
     * 
     * @return String
     */
    @Override
    public String getItemName() {
        return this.itemName;
    }

    
    /**
     * Return item type
     * 
     * @return ItemType
     */
    @Override
    public ItemType getType() {
        return this.itemType;
    }

    
    /**
     * Return task
     * 
     * @return Task
     */
    @Override
    public OldItemTask getTask() {
        return this.task;
    }

    
    /**
     * Not implemented in single items
     * 
     * @return Error
     */
    @Override
    public List<OldItemTask> getTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     * 
     * @param taskName
     * @return 
     */
    @Override
    public OldItemTask getTask(String taskName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Return current lockID
     * 
     * @return String-UUID
     */
    @Override
    public String getLockId() {
        return this.lockId;
    }

    
    /**
     * Return lock date
     * 
     * @return String- Lock Date
     */
    @Override
    public String getLockDate() {
        return this.lockDate;
    }

    
    /**
     * Return done date
     * 
     * @return String
     */
    @Override
    public String getDoneDate() {
        return this.doneDate;
    }

    
    /**
     * Return Task Log
     * 
     * @return ProcessLog
     */
    @Override
    public TaskLogging getTaskLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented for single items
     * 
     * @return 
     */
    @Override
    public Map<String, TaskLogging> getTaskLogs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented for single items
     * 
     * @param taskName
     * @return 
     */
    @Override
    public TaskLogging getTaskLog(String taskName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Get current task state
     * 
     * @return 
     */
    @Override
    public TaskState getTaskState() {
        return this.task.getTaskState();
    }

    
    /**
     * Not implemented for single items
     * 
     * @param taskName
     * @return 
     */
    @Override
    public TaskState getTaskState(String taskName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented for single items
     * 
     * @return 
     */
    @Override
    public Map<String, TaskState> getTaskStates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<TaskState, Integer> summarizeTaskStates() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * 
     * Setters
     * 
     */
    
    
    /**
     * Set item name
     * 
     * @param itemName 
     */
    @Override
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    
    /**
     * Set task
     * 
     * @param task 
     */
    @Override
    public void setTask(OldItemTask task) {
        this.task = task;
    }

    
    /**
     * Not implemented in single items
     * 
     * @param tasks 
     */
    @Override
    public void setTasks(List<OldItemTask> tasks) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     * 
     * @param taskName
     * @param task 
     */
    @Override
    public void setTask(String taskName, OldItemTask task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    }

    
    /**
     * Set task state
     * 
     * @param taskState 
     */
    @Override
    public void setTaskState(TaskState taskState) {
        switch (taskState) {
            case ACTIVE:
                this.task.markAsActive();
                break;
            case COMPLETE:
                this.task.markAsComplete();
                break;
            case PENDING:
                this.task.markAsPending();
                break;
            default:
                this.task.markAsError();
                break;
        }
    }

    
    /**
     * Not implemented in single items
     * 
     * @param taskState
     * @param taskName 
     */
    @Override
    public void setTaskState(TaskState taskState, String taskName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     * 
     * @param taskStates 
     */
    @Override
    public void setTaskStates(Map<String, TaskState> taskStates) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * 
     * @return 
     */
    @Override
    public int getTaskCount() {
        return this.taskCount;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getTasksDone() {
        return this.tasksDone;
    }

    
    /**
     * Not implemented in single items
     * 
     */
    @Override
    public void setTaskCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     */
    @Override
    public void setTasksDone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Return item as string
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "SingleTask{"
                + "autoId=" + autoId
                + ", itemName=" + itemName
                + ", itemType=" + itemType.toString()
                + ", itemState=" + itemState.toString()
                + ", lockId=" + lockId
                + ", lockDate=" + lockDate
                + ", doneDate=" + doneDate
                + ", taskCount=" + taskCount
                + ", tasksDone=" + tasksDone
                + ", task=" + task
        + '}';
    }    

    
    /**
     * Not implemented in single items
     * 
     * @param task 
     */
    @Override
    public void handleTask(String task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    /**
     * Not implemented in single items
     * 
     */
    @Override
    public void handleTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ItemState getState() {
        return this.itemState;
    }
}
