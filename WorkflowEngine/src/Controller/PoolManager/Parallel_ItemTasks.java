/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

import Controller.EngineConstants;
import Controller.ExecutorChain.TaskExecutor;
import Model.WorkItem.Task.ItemTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author kenna
 */
public class Parallel_ItemTasks extends RecursiveAction {
    
    
    // Attributes
    private final List<ItemTask> workload;
    private int threshold;
    
    
    // Can be used for allocating nested tasks
    private final TaskExecutor taskExecutor;
    private final PoolManager poolManager;
    
    
    /**
     * Construct parallel work items
     * 
     * @param workload 
     */
    public Parallel_ItemTasks(List<ItemTask> workload) {
        this.workload = workload;
        this.taskExecutor = new TaskExecutor();
        this.poolManager = PoolManager.getCurrent();
        this.threshold = EngineConstants.POOL_SIZES;
    }
    
    
    /**
     * Construct with a threshold above which parallelization is implemented
     * 
     * @param workload
     * @param threshold 
     */
    public Parallel_ItemTasks(List<ItemTask> workload, int threshold) {
        this.workload = workload;
        this.taskExecutor = new TaskExecutor();
        this.poolManager = PoolManager.getCurrent();
        this.threshold = threshold;
    }
    
    
    
    /**
     * Split current workload into sub-tasks
     * 
     * @return 
     */
    public List<Parallel_ItemTasks> subTasks() {
        List<Parallel_ItemTasks> output = new ArrayList<>();
        for(ItemTask i : this.workload) {
            List<ItemTask> task = new ArrayList<>();
            task.add(i);
            output.add(new Parallel_ItemTasks(task));
        }
        // System.out.println(this.hashCode() + " array size = " + output.size());
        return output;
    }

    
    /**
     * Delegate execution of ItemTasks to the TaskExecutor
     * 
     * @param taskIndex 
     */
    public void recursiveCompute(int taskIndex) {
        
        // Recursive descend task list
        if ( taskIndex > -1) {
            
            // Fetch active task
            ItemTask task = this.workload.get(taskIndex);
            
            // Process task
            System.out.println("Delegating ItemTask executing to the TaskExecutor for:\t" + task.getTaskName());
            taskExecutor.processTask(task);
            System.out.println("TaskExecutor has completed:\t" + task.getTaskName());
            
            // Run next
            recursiveCompute( taskIndex - 1);
        }
        
        // Log no tasks, breaching conflicts
        else {
            
            // Log no more tasks
            if ( taskIndex < 0 ) {
                System.out.println("Stopping, no more nested tasks");
            }
        }
    }
    
    
    /**
     * Recursively process tasks
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
