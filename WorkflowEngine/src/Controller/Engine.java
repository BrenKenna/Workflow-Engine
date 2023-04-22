/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Controller.ExecutorChain.ItemExecutor;
import Controller.PoolManager.PoolManager;
import Controller.PoolManager.PoolType;
import Model.WorkItem.WorkItem;
import Model.WorkItemCollection.WorkItemCollection;
import java.util.List;
import java.util.concurrent.RecursiveAction;


/**
 *
 * @author kenna
 */
public class Engine extends RecursiveAction {
    
    // Attributes
    private static Engine instance;
    private final PoolManager poolManager;
    private final ItemExecutor executor;
    private final Modifier modifier;
    private final Runner runner;
    private WorkItemCollection workload;
    private EngineState engineState;
      
    
    /**
     * Default engine constructor
     */
    public Engine() {
        this.poolManager = PoolManager.getInstance(EngineConstants.POOL_SIZES);
        this.executor = new ItemExecutor();
        this.modifier = new Modifier();
        this.runner = new Runner();
        this.workload = new WorkItemCollection();
        this.engineState = EngineState.IDLE;
    }
    
    
    /**
     * Construct engine with desired pool size
     * 
     * @param poolSize 
     */
    public Engine(int poolSize) {
        this.poolManager = PoolManager.getInstance(poolSize);
        this.executor = new ItemExecutor();
        this.modifier = new Modifier();
        this.runner = new Runner();
        this.workload = new WorkItemCollection();
        this.engineState = EngineState.IDLE;
    }

    
    /**
     * Fetch engine state
     * 
     * @return 
     */
    public EngineState getEngineState() {
        return this.engineState;
    }
    
    
    /**
     * Set engine state
     * @param state 
     */
    public void setEngineState(EngineState state) {
        this.engineState = state;
    }
    
    
    
    /**
     * Extend workload with a prototype of provided workload
     * 
     * @param workItems 
     */
    public void fetchWorkload(WorkItemCollection workItems) {
        this.workload.extendData(workItems.getData());
    }
    
    
    /**
     * Extend workload with provided list
     * 
     * @param workItems 
     */
    public void fetchWorkload(List<WorkItem> workItems) {
        this.workload.extendData(workItems);
    }
    
    
    /**
     * Consume the engines active workload
     */
    public void cosumeWorkload() {
    
        // Supply data to runner
        runner.setWorkload(this.workload.getData());
        
        // Iteratively batches of work items
        runner.loopThroughItems();
    }
    
    
    /**
     * Termainte engine
     * 
     * @return State of termination: True/False 
     */
    public boolean terminateEngine() {
        this.setEngineState(EngineState.SUSPENDED);
        this.poolManager.closePools();
        return true;
    }
    
    
    /**
     * Fetch pool manager
     * 
     * @return 
     */
    public PoolManager getPoolManager() {
        return this.poolManager;
    }
    
    
    /**
     * Run engine
     */
    @Override
    protected void compute() {
        this.setEngineState(EngineState.ACTIVE);
        cosumeWorkload();
        this.setEngineState(EngineState.IDLE);
    }
    
    
    /**
     * Get active tasks
     * 
     * @return 
     */
    public List<WorkItem> getActiveTasks() {
        return this.runner.getActive();
    }
    
    
    /**
     * Get workload
     * 
     * @return 
     */
    public List<WorkItem> getWorkload() {
        return this.runner.getWorkload();
    }
}
