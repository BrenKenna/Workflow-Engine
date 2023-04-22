/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

import Controller.EngineConstants;
import Controller.ExecutorChain.ItemExecutor;
import Model.WorkItem.WorkItem;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author kenna
 */
public class Parallel_WorkItems extends RecursiveAction {

    // Attributes
    private final List<WorkItem> workload;
    private int threshold;
    
    
    // Can be used for allocating nested tasks
    private final ItemExecutor itemExecutor;
    private final PoolManager poolManager;
    
    
    /**
     * Construct parallel work items
     * 
     * @param workload 
     */
    public Parallel_WorkItems(List<WorkItem> workload) {
        this.workload = workload;
        this.itemExecutor = new ItemExecutor();
        this.poolManager = PoolManager.getCurrent();
        this.threshold = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * Construct with a threshold above which parallelization is implemented
     * 
     * @param workload
     * @param threshold 
     */
    public Parallel_WorkItems(List<WorkItem> workload, int threshold) {
        this.workload = workload;
        this.itemExecutor = new ItemExecutor();
        this.poolManager = PoolManager.getCurrent();
        this.threshold = threshold;
    }
    
    
    
    /**
     * Split current workload into sub-tasks
     * 
     * @return 
     */
    public List<Parallel_WorkItems> subTasks() {
        List<Parallel_WorkItems> output = new ArrayList<>();
        for(WorkItem i : this.workload) {
            List<WorkItem> task = new ArrayList<>();
            task.add(i);
            output.add(new Parallel_WorkItems(task));
        }
        // System.out.println(this.hashCode() + " array size = " + output.size());
        return output;
    }
    
    
    /**
     * Delegate execution of work item to ItemExecutor
     * 
     * @param taskIndex 
     */
    public void recursiveCompute(int taskIndex) {
        
        // Recursive descend task list
        // System.out.println("Accumlated conflicts:\t" + accumConflicts);
        if ( taskIndex > -1) {
            
            // Fetch active task
            WorkItem task = this.workload.get(taskIndex);
            System.out.println("Delegating WorkItem executing to ItemExecutor for:\t" + task.getItemName());
            itemExecutor.handleRunningTaskType(task);
            System.out.println("ItemExecutor has completed:\t" + task.getItemName());
            
            // Run next
            recursiveCompute( taskIndex - 1);
        }
        
        // Log no tasks
        else {
            System.out.println("Stopping, no more tasks");
        }
    }
    
    
    /**
     * Recursively add tasks to pool
     * 
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
            recursiveCompute( this.workload.size() - 1);
        }
    }
}
