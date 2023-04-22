/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

import Model.WorkItem.Task.OldItemTask;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author kenna
 */
public class Old_ParallelItemTasks extends RecursiveAction {
    
    // Attributes
    private final List<OldItemTask> workload;
    private int threshold = 2;
    
    // Can be used for allocating nested tasks
    private final PoolManager poolManager = PoolManager.getCurrent();
    
    
    /**
     * Constructor with workload
     * 
     * @param workload 
     */
    public Old_ParallelItemTasks(List<OldItemTask> workload) {
        this.workload = workload;
    }
    
    
    /**
     * Constructor with workload & threshold
     * @param workload
     * @param threshold 
     */
    public Old_ParallelItemTasks(List<OldItemTask> workload, int threshold) {
        this.workload = workload;
        this.threshold = threshold;
    }
    
    
    /**
     * Generate sub-tasks from WorkItems
     * 
     * @return List-Parallel WorkItem
     */
    public List<Old_ParallelItemTasks> subTasks() {
        List<Old_ParallelItemTasks> output = new ArrayList<>();
        for(OldItemTask i : this.workload) {
            List<OldItemTask> task = new ArrayList<>();
            task.add(i);
            output.add(new Old_ParallelItemTasks(task));
        }
        // System.out.println(this.hashCode() + " array size = " + output.size());
        return output;
    }
    
    
    /**
     * Descend workload, proceeding with processing if valid
     * 
     * @param taskIndex 
     */
    public void recursiveCompute(int taskIndex) {
        
        // Recursive descend task list
        if ( taskIndex > -1) {
            
            // Fetch active task
            OldItemTask task = this.workload.get(taskIndex);
            
            // Process task
            task.processTask();

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
            recursiveCompute( this.workload.size() - 1);
        }
    }
}
