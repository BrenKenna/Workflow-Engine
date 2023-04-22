/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

import Controller.EngineConstants;
import Model.WorkItem.ItemType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import Model.WorkItem.OldWorkItem;

/**
 *
 * @author kenna
 */
public class Old_ParallelWorkItems extends RecursiveAction {

    // Attributes
    private final List<OldWorkItem> workload;
    private int threshold = 2;
    
    // Can be used for allocating nested tasks
    private final PoolManager poolManager = PoolManager.getCurrent();
    
    
    /**
     * Constructor with workload
     * 
     * @param workload 
     */
    public Old_ParallelWorkItems(List<OldWorkItem> workload) {
        this.workload = workload;
    }
    
    
    /**
     * Constructor with workload & threshold
     * @param workload
     * @param threshold 
     */
    public Old_ParallelWorkItems(List<OldWorkItem> workload, int threshold) {
        this.workload = workload;
        this.threshold = threshold;
    }
    
    
    /**
     * Generate sub-tasks from WorkItems
     * 
     * @return List-Parallel OldWorkItem
     */
    public List<Old_ParallelWorkItems> subTasks() {
        List<Old_ParallelWorkItems> output = new ArrayList<>();
        for(OldWorkItem i : this.workload) {
            List<OldWorkItem> task = new ArrayList<>();
            task.add(i);
            output.add(new Old_ParallelWorkItems(task));
        }
        // System.out.println(this.hashCode() + " array size = " + output.size());
        return output;
    }
    
    
    /**
     * Handle processing item based on type
     * 
     * @param workItem 
     */
    public void handleTask(OldWorkItem workItem) {
        
        // Handle as single
        if ( workItem.getType() == ItemType.SINGLE ) {
            System.out.println("Processing SingleTask-WorkItem:\t" + workItem.getItemId());
            workItem.handleTask();
        }
        
        // Otherwise handle as nested
        else {
            
            // Run tasks in parallel
            System.out.println("Retrieving TaskPool for all tasks from NestedTask-WorkItem:\t" + workItem.getItemId());
            workItem.lockItem();
            ForkJoinPool taskPool = poolManager.getWorkFlowPool(PoolType.TASK);
            Old_ParallelItemTasks paraTasks = new Old_ParallelItemTasks(workItem.getTasks());
            
            // Process tasks
            System.out.println("Invoking tasks");
            taskPool.invoke(paraTasks);
            workItem.setDoneDate();
        }
    }
    
    
    /**
     * Verify lock on Item
     * 
     * => Overtime this will be a call to a remote server.
     * 
     * @param workItem
     * @return True/False
     */
    public boolean verifyItem(OldWorkItem workItem) {
        
        // Attempt to lock item
        String lockId = workItem.lockItem();
        
        // Wait, and check item state
        // Code to wait
        String currentLock = workItem.getLockId();
        return lockId.equals(currentLock);
    }

    
    /**
     * Descend workload, proceeding with processing if valid
     * 
     * @param taskIndex 
     * @param accumConflicts 
     */
    public void recursiveCompute(int taskIndex, int accumConflicts) {
        
        // Recursive descend task list
        // System.out.println("Accumlated conflicts:\t" + accumConflicts);
        if ( taskIndex > -1 && accumConflicts < EngineConstants.CONFLICT_LIMIT) {
            
            // Fetch active task
            OldWorkItem task = this.workload.get(taskIndex);
            
            // Proceed if inactive
            if ( verifyItem(task) ) {
                
                // Process item
                System.out.println("Processing item:\t" + task.getItemId());
                handleTask(task);
                accumConflicts = 0;
            }
            
            // Otherwise note conflict
            else {
                System.out.println("Skipping item, conflict raised on:\t" + task.getItemId());
                accumConflicts++;
            }
            
            // Run next
            recursiveCompute( taskIndex - 1, accumConflicts );
        }
        
        // Log no tasks, breaching conflicts
        else {
            
            // Log no more tasks
            if ( taskIndex < 0 ) {
                System.out.println("Stopping, no more tasks");
            }
            
            // Otherwise log conflict breach
            if ( accumConflicts >= EngineConstants.CONFLICT_LIMIT ) {
                System.out.println("Stopping, allowed number on conflicts streak has been breached");
            }
        }
    }
    
    
    /**
     * Recursively add WorkItems to pool
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
            recursiveCompute( this.workload.size() - 1, 0);
        }
    }
}
