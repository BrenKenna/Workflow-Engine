/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItemCollection;

import Model.WorkItem.WorkItem;
import Model.WorkItemCollection.Index.WorkItemIndex_Adapter;
import Model.WorkItemCollection.Index.WorkItem_IndexType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenna
 */
public class WorkItemCollection {
    
     
    // Attributes
    private List<WorkItem> data;
    private Map<WorkItem_IndexType, WorkItemIndex_Adapter> indexes;

    
    /**
     * Construct null collection
     */
    public WorkItemCollection() {
        this.data = new ArrayList<>();
        this.indexes = new HashMap<>();
    }
    
    
    /**
     * Construct from dataset
     * 
     * @param data 
     * @param indexes 
     */
    public WorkItemCollection(List<WorkItem> data, Map<WorkItem_IndexType, WorkItemIndex_Adapter> indexes) {
        this.data = data;
        this.indexes = indexes;
    }
    
    
    /**
     * Item count
     * 
     * @return int 
     */
    public int getSize() {
        return data.size();
    }

    @Override
    public String toString() {
        return "WorkItemCollection{" + "data=" + data + ", indexes=" + indexes + '}';
    }

    
    /**
     * Get data
     * 
     * @return 
     */
    public List<WorkItem> getData() {
        return data;
    }
    
    
    /**
     * Set data
     * 
     * @param data 
     */
    public void setData(List<WorkItem> data) {
        this.data = data;
    }

    
    /**
     * Extend WorkItem list
     * 
     * @param data 
     */
    public void extendData(List<WorkItem> data) {
        for(WorkItem item : data) {
            this.data.add(item);
        }
    }
    
    /**
     * Get indexes
     * 
     * @return 
     */
    public Map<WorkItem_IndexType, WorkItemIndex_Adapter> getIndexes() {
        return indexes;
    }
    
    /**
     * Set indexes
     * 
     * @param indexes 
     */
    public void setIndexes(Map<WorkItem_IndexType, WorkItemIndex_Adapter> indexes) {
        this.indexes = indexes;
    }
    
    
    /**
     * Add a new work item
     * 
     * @param workItem 
     */
    public void addNew(WorkItem workItem) {
        this.data.add(workItem);
    }
}
