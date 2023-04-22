/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

import Controller.PoolManager.PoolType;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author kenna
 */
public class PoolManager {
    
    // Attributes
    private static PoolManager instance;
    private final Map<PoolType, ForkJoinPool> workFlowPools;
    
    
    /**
     * Construct with uniform pool sizes
     * 
     * @param poolSize
     */
    private PoolManager(int poolSize) {
        
        // Create pools
        ForkJoinPool enginePool = new ForkJoinPool(poolSize);
        ForkJoinPool itemPool = new ForkJoinPool(poolSize);
        ForkJoinPool taskPool = new ForkJoinPool(poolSize);
        
        // Put into map
        this.workFlowPools = new HashMap<>();
        this.workFlowPools.put(PoolType.ENGINE, enginePool);
        this.workFlowPools.put(PoolType.TASK, taskPool);
        this.workFlowPools.put(PoolType.ITEM, itemPool);
    }
    
    
    /**
     * Construct pool manager with specific pool sizes
     * 
     * @param itemPoolSize
     * @param taskPoolSize
     * @param enginePoolSize 
     */
    private PoolManager(int itemPoolSize, int taskPoolSize, int enginePoolSize) {
        
        // Create pools
        ForkJoinPool enginePool = new ForkJoinPool(enginePoolSize);
        ForkJoinPool itemPool = new ForkJoinPool(itemPoolSize);
        ForkJoinPool taskPool = new ForkJoinPool(taskPoolSize);
        
        // Put into map
        this.workFlowPools = new HashMap<>();
        this.workFlowPools.put(PoolType.ENGINE, enginePool);
        this.workFlowPools.put(PoolType.TASK, taskPool);
        this.workFlowPools.put(PoolType.ITEM, itemPool);
    }
    
    
    /**
     * Return instance of PoolManager with uniform pool sizes
     * 
     * @param poolSize
     * @return PoolManager
     */
    public static PoolManager getInstance(int poolSize) {
        if( instance == null ) {
            instance = new PoolManager(poolSize);
        }
        return instance;
    }
    
    
    /**
     * Get current instance of PoolManager
     * 
     * @return PoolManager
     */
    public static PoolManager getCurrent() {
        if (instance == null) {
            System.out.println("PoolManager must be instantiated first");
            return null;
        }
        else {
            return instance;
        }
    }
    
    /**
     * Return instance of PoolManager with specific pool sizes
     * 
     * @param itemPoolSize
     * @param taskPoolSize
     * @param enginePoolSize
     * @return PoolManager
     */
    public static PoolManager getInstance(int itemPoolSize, int taskPoolSize, int enginePoolSize) {
        if( instance == null ) {
            instance = new PoolManager(itemPoolSize, taskPoolSize, enginePoolSize);
        }
        return instance;
    }

    
    /**
     * Fetch all pools
     * 
     * @return Map-PoolType,ForkJoinPool
     */
    public Map<PoolType, ForkJoinPool> getWorkFlowPools() {
        return workFlowPools;
    }
    
    
    /**
     * Fetch specific pool type
     * 
     * @param pool
     * @return PoolType-Item, Task, Engine
     */
    public ForkJoinPool getWorkFlowPool(PoolType pool) {
        ForkJoinPool output = null;
        output = workFlowPools.get(pool);
        return output;
    }
    
    
    /**
     * Fetch pool from queried string
     * 
     * @param pool
     * @return ForkJoinPool
     */
    public ForkJoinPool getWorkFlowPool(String pool) {
        
        // Query pool type
        ForkJoinPool output = null;
        PoolType poolType = PoolType.getType(pool);
        
        // Fetch valid ForkJoinPool
        if ( poolType != null ) {
            output = workFlowPools.get(poolType);  
        }
        
        // Otherwise log error
        else {
            System.out.println("Queried pool not found");
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Close all pools
     */
    public void closePools() {
        for( PoolType poolType : workFlowPools.keySet() ) {
            ForkJoinPool pool = workFlowPools.get(poolType);
            pool.shutdown();
            workFlowPools.replace(poolType, null);
        }
    }
    
    
    /**
     * Close requested pool
     * 
     * @param poolType 
     */
    public void closePool(PoolType poolType) {
        ForkJoinPool pool = workFlowPools.get(poolType);
        pool.shutdown();
        workFlowPools.replace(poolType, null);
    }
    
    
    /**
     * Close requested pool
     * 
     * @param query 
     */
    public void closePool(String query) {
        ForkJoinPool pool = this.getWorkFlowPool(query);
        pool.shutdown();
    }
    
    
    /**
     * Check if all pools are open
     * 
     * @return True/False
     */
    public boolean areOpen() {
        
        // Initialize
        boolean output = true;
        
        // Scan types
        int counter = 0;
        while ( output && counter < workFlowPools.keySet().size() ) {
            
            // Fetch type and compare
            PoolType poolType = (PoolType) workFlowPools.keySet().toArray()[counter];
            if ( workFlowPools.get(poolType) == null ) {
                output = false;
            }
            else {
                counter++;
            }
        }

        // Return results
        return output;
    }
    
    
    /**
     * Check if pool is open
     * 
     * @param poolType
     * @return True/False
     */
    public boolean isOpen(PoolType poolType) {
        
        // Initialize vars
        boolean output = false;
        ForkJoinPool pool = workFlowPools.get(poolType);
        
        // Check
        if ( pool != null ) {
            output = true;
        }
        
        // Return result
        return output;
    }
    
    
    /**
     * Check if pool is open
     * 
     * @param pool
     * @return True/False
     */
    public boolean isOpen(String pool) {
        
        // Initialize vars
        boolean output = false;
        ForkJoinPool poolType = this.getWorkFlowPool(pool);
        
        // Check
        if ( poolType != null ) {
            output = true;
        }
        
        // Return result
        return output;
    }
    
    
    /**
     * Open specific pool of required size if closed
     * 
     * @param poolType
     * @param poolSize 
     */
    public void openPool(PoolType poolType, int poolSize) {
        
        // Handle opening pool
        if ( !isOpen(poolType) ) {
            ForkJoinPool pool = new ForkJoinPool(poolSize);
            workFlowPools.put(poolType, pool);
        }
    }
    
    
    /**
     * Open specific pool of required size if closed
     * 
     * @param query
     * @param poolSize 
     */
    public void openPool(String query, int poolSize) {
        
        // Handle opening pool
        if ( !isOpen(query) ) {
            
            // Open pool if valid
            PoolType poolType = PoolType.getType(query);
            if ( poolType != null ) {
                ForkJoinPool pool = new ForkJoinPool(poolSize);
                workFlowPools.put(poolType, pool);
            }
        }
    }
}
