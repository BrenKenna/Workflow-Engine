/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.PoolManager.Parallel_WorkItems;
import Controller.PoolManager.PoolManager;
import Controller.PoolManager.PoolType;
import java.util.ArrayList;
import java.util.List;
import Model.WorkItem.WorkItem;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author kenna
 */
public class Runner {
    
    
    // Attributes
    private List<WorkItem> workItems;
    private final List<WorkItem> activeTasks;
    private final int poolSize;
    private final int conflictLimit;
    private int conflictStreak;
    
    // Supporting
    private final PoolManager poolManager = PoolManager.getCurrent();
    private final Modifier modifier = new Modifier();
    
    
    /**
     * Null constructor
     */
    public Runner() {
        this.workItems = new ArrayList<>();
        this.activeTasks = new ArrayList<>();
        this.conflictLimit = EngineConstants.CONFLICT_LIMIT;
        this.conflictStreak = 0;
        this.poolSize = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * Construct with updated conflict streak
     * 
     * @param conflictLimit 
     */
    public Runner(int conflictLimit) {
        this.workItems = new ArrayList<>();
        this.activeTasks = new ArrayList<>();
        this.conflictLimit = conflictLimit;
        this.conflictStreak = 0;
        this.poolSize = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * Construct runner with work item 
     * 
     * @param workItems 
     */
    public Runner(List<WorkItem> workItems) {
        this.workItems = workItems;
        this.activeTasks = new ArrayList<>();
        this.conflictStreak = EngineConstants.CONFLICT_LIMIT;
        this.conflictLimit = EngineConstants.CONFLICT_LIMIT;
        this.conflictStreak = 0;
        this.poolSize = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * 
     * 
     * @param workItems
     * @param conflictLimit 
     */
    public Runner(List<WorkItem> workItems, int conflictLimit) {
        this.workItems = workItems;
        this.activeTasks = new ArrayList<>();
        this.conflictLimit = EngineConstants.CONFLICT_LIMIT;
        this.conflictStreak = 0;
        this.poolSize = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * Samples nTasks
     * 
     * @param nTasks 
     */
    public void sampleTasks(int nTasks) {
        
        // Try lock two items
        int counter = 0;
        while (
                this.conflictStreak < this.conflictLimit
                && counter < this.workItems.size()
                && this.activeTasks.size() < nTasks
        ) {
            
            // Fetch from main
            WorkItem current = this.workItems.get(counter);
            
            // Add to active set if available
            if ( this.verifyItem(current) ) {
                this.activeTasks.add(current);
                this.workItems.remove(counter);
                this.resetStreak();
            }
            
            // Otherwise increment
            else {
                this.raiseConflict();
            }
        }
    }
    
    
    /**
     * Sample number tasks of pool size 
     * @return 
     */
    public RunnerState sampleTasks() {
        
        // Try lock two items
        int counter = 0;
        boolean state = false;
        while (
                this.conflictStreak < this.conflictLimit
                && counter < this.workItems.size()
                && this.activeTasks.size() < this.poolSize
        ) {
            
            // Fetch from main
            WorkItem current = this.workItems.remove(counter);
            
            // Add to active set if available
            if ( this.verifyItem(current) ) {
                System.out.println("Engine-Controller: Successfully locked " + current.getItemName());
                this.activeTasks.add(current);
                this.resetStreak();
            }
            
            // Otherwise increment
            else {
                System.out.println("Engine-Controller: Conflict raised on item " + current.getItemName());
                this.raiseConflict();
            }
        }
        
        // Handle state
        if ( this.conflictStreak < EngineConstants.CONFLICT_LIMIT ) {
            System.out.println("Engine-Controller: Runner conflict streak breached");
            return RunnerState.CONFLICT;
        }
        
        // Check task size
        else if ( this.activeTasks.size() < 1 ) {
            System.out.println("Engine-Controller: Runner no tasks could be locked for run");
            return RunnerState.NO_TASKS;
        }
        
        // Otherwise fine
        else {
            return RunnerState.SUCCESSFUL;
        }
    }
    
    
    /**
     * Pop task
     * 
     * @param items
     * @return 
     */
    public WorkItem popTask(List<WorkItem> items) {
        WorkItem output = null;
        if ( items.size() > 0 ) {
            output = items.get(0);
            items.remove(0);
        }
        return output;
    }
    
    
    /**
     * Pop required number of, or available items
     * 
     * @param items
     * @param nItems
     * @return List-WorkItems
     */
    public List<WorkItem> popTasks(List<WorkItem> items, int nItems) {
        
        // Initialize output
        List<WorkItem> output = new ArrayList<>();
        
        // Fetched required number if available
        if ( nItems < items.size() ) {
            for( int i = 0; i < nItems; i++ ) {
                WorkItem active = items.remove(i);
                output.add(active);
            }
        }
        
        // Otherwis fetch what is available
        else {
            for(int i = 0; i < items.size(); i++) {
                WorkItem active = items.remove(i);
                output.add(active);
            }
        }
        
        // Return result
        return output;
    }
    
    
    /**
     * Consume active set of tasks
     */
    public void consumeActive() {
        
        // Pass active tasks to the item pool
        System.out.println("\nEngine-Controller: Adding WorkItems to Pool:\t" + this.activeTasks.size());
        ForkJoinPool itemPool = this.poolManager.getWorkFlowPool(PoolType.ITEM);
        Parallel_WorkItems paraTasks = new Parallel_WorkItems(this.activeTasks);
        
        
        // Process data
        System.out.println("\nEngine-Controller: Processing active pool, " + itemPool.isQuiescent());
        itemPool.invoke(paraTasks);
        itemPool.isQuiescent();
        System.out.println("\nEngine-Controller: Processing active pool, complete, " + itemPool.isQuiescent());
        this.activeTasks.clear();
    }
    
    
    /**
     * Fetch and process active set
     * 
     * @return 
     */
    public boolean fetchRunSet() {
    
        // Proceed if tasks are available
        boolean state;
        if ( this.hasNext(this.workItems) ) {
        
            // Sample next set and consume
            System.out.println("Engine-Controller: Detected N Tasks = " + this.workItems.size());
            RunnerState runnerState = this.sampleTasks();
            if ( runnerState == RunnerState.SUCCESSFUL || runnerState == RunnerState.CONFLICT ) {
                System.out.println("Engine-Controller: Sampled N Tasks = " + this.activeTasks.size());
                this.consumeActive();
                state = true;
            }
            
            // Do not proceed if cannot allocae workload
            else {
                System.out.println("Engine-Controller: Runner could not lock tasks");
                System.out.println("Engine-Controller: Sampled N Tasks = " + this.activeTasks.size());
                state = false;
            }
        }
        
        // Otherwise log no queued tasks
        else {
            System.out.println("Engine-Controller: Runner could not detect tasks");
            state = false;
        }

        // Return state
        return state;
    }
    
    
    /**
     * Update workload
     * @param workload
     * 
     */
    public void setWorkload(List<WorkItem> workload) {
        this.workItems = workload;
    }
    
    
    /**
     * Loop through items until all are consumed
     * 
     */
    public void loopThroughItems() {
        
        // Configure run // && counter < 3
        int counter = 0;
        boolean canRun = true;
        while ( canRun  ) {
            System.out.println("\nRunner: Begining Runner iteration:\t" + counter);
            System.out.println("\nRunner: Queued Tasks:\t" + this.workItems.size());
            System.out.println("\nRunner: hasNext = " + this.hasNext(this.workItems));
            canRun = fetchRunSet();
            System.out.println("\nCompleted Runner iteration:\t" + counter);
            counter++;
        }
        System.out.println("\nRunner completed after iterations:\t" + counter);
    }
    
    
    /**
     * Return whether item is locked
     * 
     * @param workItem
     * @return 
     */
    public boolean verifyItem(WorkItem workItem) {
        
        // Lock item
        String lockId = modifier.lockItem(workItem);

        // Wait, and check item state
        // Code to wait
        String currentLock = workItem.getLockId();
        return lockId.equals(currentLock);
    }
    
    
    /**
     * Return whether list has another item
     * 
     * @param items
     * @return True/False
     */
    public boolean hasNext(List<WorkItem> items) {
        return items.size() > 0;
    }
    
    
    /**
     * Return whether list has another N items
     * 
     * @param items
     * @param nItems
     * @return True/False
     */
    public boolean hasNext(List<WorkItem> items, int nItems) {
        return items.size() > nItems;
    }
    
    
    /**
     * 
     */
    public void raiseConflict() {
        this.conflictStreak++;
    }
    
    
    /**
     * 
     */
    public void resetStreak() {
        this.conflictStreak = 0;
    }
    
    
    /**
     * Return active tasks
     * 
     * @return 
     */
    public List<WorkItem> getActive() {
        return this.activeTasks;
    }
    
    
    /**
     * Get configured workload
     * 
     * @return 
     */
    public List<WorkItem> getWorkload() {
        return this.workItems;
    }
    
    
    /**
     * Add WorkItem
     * 
     * @param workItem 
     */
    public void enqueueItem(WorkItem workItem) {
        this.workItems.add(workItem);
    }
}
