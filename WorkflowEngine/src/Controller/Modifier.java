/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.WorkItem.ItemState;
import Model.WorkItem.Task.ItemTask;
import Model.WorkItem.Task.TaskState;
import Model.WorkItem.WorkItem;
import Utils.DateUtils;
import Utils.TokenGenerator;

/**
 *
 * Class for modifiying WorkItems:
 * 
 * a). Item States
 *      => Unlocking WorkItem
 *      => Refreshing WorkItem
 *      => Locking WorkItem
 *      => Marking as Done
 * 
 * b). ItemTask:
 *      => Adding.
 *      => Dropping.
 * 
 * @author kenna
 */
public class Modifier {
    
    // 
    private final TokenGenerator tokenGenerator;
    private final DateUtils dateUtils;
    
    
    /**
     * Constructor
     */
    public Modifier() {
        this.tokenGenerator = new TokenGenerator();
        this.dateUtils = new DateUtils();
    }
    
    
    /**
     * Lock item
     * 
     * @param item 
     * @return  
     */
    public String lockItem(WorkItem item) {
        String lockId = tokenGenerator.generateSalt();
        String lockDate = dateUtils.getNowString();
        
        // Set fields
        item.setLockId(lockId);
        item.setItemState(ItemState.LOCKED);
        item.setLockDate(lockDate);
        
        // Return assigned lock ID
        return lockId;
    }
    
    
    
    /**
     * Unlock item
     * 
     * @param item 
     */
    public void markItemAsDone(WorkItem item) {
        String doneDate = dateUtils.getNowString();
        item.setItemState(ItemState.DONE);
        item.setDoneDate(doneDate);
    }
    
    
    /**
     * Unlock item
     * 
     * @param item 
     */
    public void unlockItem(WorkItem item) {
        item.setItemState(ItemState.TODO);
        item.setDoneDate(null);
        item.setLockDate(null);
        item.setTasksDone(0);
    }
    
    
    /**
     * Unlock item and clear all tasks
     * @param item 
     */
    public void refreshItem(WorkItem item) {
        unlockItem(item);
        
        // Manage tasks
    }
    
    
    /**
     * Increment tasks done
     * 
     * @param item 
     */
    public void increaseTasksDone(WorkItem item) {
        int tasksDone = item.getTasksDone();
        tasksDone++;
        item.setTasksDone(tasksDone);
    }
    
    
    /**
     * Add new task
     * 
     * @param item
     * @param task 
     */
    public void addTask(WorkItem item, ItemTask task) {
        if ( item.getWorkload().addTask(task) ) {
            item.setTaskCount( item.getWorkload().getWorkload().size() );
        }
    }
    
    
    /**
     * Drop task
     * 
     * @param item
     * @param task 
     */
    public void dropTask(WorkItem item, ItemTask task) {
        if ( item.getWorkload().dropTask(task) ) {
            item.setTaskCount( item.getWorkload().getWorkload().size() );
        }
    }
    
    
    /**
     * Drop task
     * 
     * @param item
     * @param task 
     */
    public void dropTask(WorkItem item, String task) {
        if ( item.getWorkload().dropTask(task) ) {
            item.setTaskCount( item.getWorkload().getWorkload().size() );
        }
    }
    
    
    /**
     * Set task as done
     * 
     * @param task 
     */
    public void markTaskAsDone(ItemTask task) {
        task.setTaskState(TaskState.COMPLETE);
    }
    
    
    /**
     * Mark task as active
     * 
     * @param task 
     */
    public void markTaskAsActive(ItemTask task) {
        task.setTaskState(TaskState.ACTIVE);
    }
    
    
    /**
     * Mark task as pending
     * @param task
     */
    public void markTaskAsPending(ItemTask task) {
        task.setTaskState(TaskState.PENDING);
    }
    
    
    /**
     * Mark task as in Error state
     * @param task 
     */
    public void markTaskAsError(ItemTask task) {
        task.setTaskState(TaskState.ERROR);
    }
}
