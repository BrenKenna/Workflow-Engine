/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ExecutorChain;


import Controller.Modifier;
import Model.WorkItem.ItemType;
import Model.WorkItem.Task.ItemTask;
import Model.WorkItem.WorkItem;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author kenna
 */
public class ItemExecutor {
    
    // Exectuor holds both + modifier
    private final TaskExecutor taskExecutor;
    private final Modifier modifier;

    
    /**
     * Construct Executor
     */
    public ItemExecutor() {
        this.taskExecutor = new TaskExecutor();
        this.modifier = new Modifier();
    }
    
    
    /**
     * Handle execution of single task item
     * 
     * @param workItem 
     */
    public void handleSingleTaskItem(WorkItem workItem) {
        ItemTask task = workItem.getWorkload().getWorkload().get(0);
        if ( task.getTaskState().isState("pending") ) {
            System.out.println("ItemExecutor: Processing task " + task.getTaskName() + ", in WorkItem " + workItem.getAutoId());
            taskExecutor.runTask(task);
            modifier.markItemAsDone(workItem);
        }
        
        // Pass
        else {
            System.out.println("Skipping __" + ", task is not pending");
        }
    }
    
    
    /**
     * Handle execution of nested task item
     * 
     * @param workItem 
     */
    public void handleNestedTaskItem(WorkItem workItem) {
    
        // Pass over all items
        for (ItemTask task : workItem.getWorkload().getWorkload()) {
            runTask(workItem, task);
        }
    }
    
    
    /**
     * Handle task of nested work item
     * 
     * @param workItem
     * @param taskName 
     */
    public void handleTask(WorkItem workItem, String taskName) {
        
        // Proceed if unlocked
        if ( workItem.getItemState().isState("todo") ) {
            
            // Handle running task
            ItemTask task = workItem.getWorkload().getTask(taskName);
            if ( task != null ) {

                // Lock item & process task
                runTask(workItem, task);
            }

            //
            else {
                System.out.println("Task not found, cannot unlock queried '" + taskName + "'");
            }
        }
        
        // Otherwise pass
        else {
            System.out.println("Skipping item '" + workItem.getItemName() + "'");
        }
    }
    
    
    
    /**
     * Run task from work item
     * 
     * @param workItem
     * @param task 
     */
    public void runTask(WorkItem workItem, ItemTask task) {
    
        // Run task if pending
        if ( task.getTaskState().isState("pending") ) {
            System.out.println("ItemExecutor: Processing task " + task.getTaskName() + ", in WorkItem " + workItem.getAutoId());
            taskExecutor.processTask(task);
            modifier.increaseTasksDone(workItem);
            modifier.markItemAsDone(workItem);
        }
            
        // Otherwise pass
        else {
            System.out.println("Skipping task '" + task.getTaskId() + "', in '" + workItem.getItemId() + "'");
        }
    }
    
    
    /**
     * Handle running work item by type
     * 
     * @param workItem 
     */
    public void handleRunningTaskType(WorkItem workItem) {
    
        // Run as single task
        if ( workItem.getItemType() == ItemType.SINGLE ) {
            this.handleSingleTaskItem(workItem);
        }
        
        // Otherwise, run as nested task
        else {
            this.handleNestedTaskItem(workItem);
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
