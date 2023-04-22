/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

import Model.WorkItem.Task.ItemTask;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenna
 */
public class Workload {
    
    // Attributes
    private List<ItemTask> workload;
    private ItemType workloadType = ItemType.SINGLE;
    
    
    /**
     * Null constructor
     */
    public Workload() {
        this.workload = new ArrayList<>();
        this.workloadType = ItemType.SINGLE;
    }
    
    
    /**
     * 
     * 
     * @param workload 
     */
    public Workload(List<ItemTask> workload) {
        this.workload = workload;
        this.workloadType = ItemType.NESTED;
    }
    
    
    /**
     * 
     * @param work
     */
    public Workload(ItemTask work) {
        this.workload = new ArrayList<>();
        this.workload.add(work);
        this.workloadType = ItemType.SINGLE;
    }
    
    
    /**
     * Add task to workload
     * 
     * @param work 
     * @return  
     */
    public boolean addTask(ItemTask work) {
        
        // Handle whether to add task
        int workIndex = indexOf(work);
        if ( workIndex > -1) {
            return false;
        }
        
        // Add task
        this.workload.add(work);
        if ( this.workload.size() >= 2 ) {
            this.workloadType = ItemType.NESTED;
        }
        else {
            this.workloadType = ItemType.SINGLE;
        }
        
        // Return flag
        return true;
    }
    
    
    /**
     * Drop task if present
     * 
     * @param task
     * @return 
     */
    public boolean dropTask(ItemTask task) {
    
        // Handle whether to add task
        int workIndex = indexOf(task);
        if ( workIndex < 0) {
            return false;
        }
        
        // Add task
        this.workload.remove(workIndex);
        if ( this.workload.size() >= 2 ) {
            this.workloadType = ItemType.NESTED;
        }
        else {
            this.workloadType = ItemType.NESTED;
        }
        
        // Return flag
        return true;
    }
    
    
    /**
     * Drop task
     * 
     * @param task
     * @return True/False
     */
    public boolean dropTask(String task) {
    
        // Handle whether to add task
        int workIndex = indexOf(task);
        if ( workIndex < 0) {
            return false;
        }
        
        // Add task
        this.workload.remove(workIndex);
        if ( this.workload.size() >= 2 ) {
            this.workloadType = ItemType.NESTED;
        }
        else {
            this.workloadType = ItemType.NESTED;
        }
        
        // Return flag
        return true;
    }
    
    
    /**
     * Fetch index of item
     * 
     * @param query
     * @return 
     */
    public int indexOf(ItemTask query) {
        return this.workload.indexOf(query);
    }
    
    
    /**
     * Fetch index query
     * 
     * @param query
     * @return Index of query 
     */
    public int indexOf(String query) {
        
        // Initialize vars
        int workIndex = -1;
        int counter = 0;
        boolean found = false;
        
        // Search until found
        while ( !found && counter < this.workload.size() ) {
            ItemTask task = this.workload.get(counter);
            if ( task.isTask(query) ) {
                found = true;
                workIndex = counter;
            }
            else {
                counter++;
            }
        }
        
        // Return result
        return workIndex;
    }

    
    /**
     * Fetch task if present
     * 
     * @param task
     * @return 
     */
    public ItemTask getTask(ItemTask task) {
        int index = this.indexOf(task);
        if ( index > -1 ) {
            return this.workload.get(index);
        }
        else {
            return null;
        }
    }
    
    
    /**
     * Fetch task if present
     * 
     * @param task
     * @return 
     */
    public ItemTask getTask(String task) {
        int index = this.indexOf(task);
        if ( index > -1 ) {
            return this.workload.get(index);
        }
        else {
            return null;
        }
    }

    
    /**
     * Get workload
     * 
     * @return 
     */
    public List<ItemTask> getWorkload() {
        return workload;
    }

    
    /**
     * Get state
     * 
     * @return 
     */
    public ItemType getWorkloadType() {
        return workloadType;
    }

    
    /**
     * Set tasks
     * 
     * @param tasks 
     */
    public void setWorkload(List<ItemTask> tasks) {
        this.workload = tasks;
        if( this.workload.size() >= 2 ) {
            this.workloadType = ItemType.NESTED;
        }
        else {
            this.workloadType = ItemType.SINGLE;
        }
    }
    
    /**
     * 
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "Workload{"
                + "workload=" + workload
                + ", workloadType=" + workloadType
        + '}';
    }
}
