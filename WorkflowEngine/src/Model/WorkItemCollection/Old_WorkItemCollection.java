/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItemCollection;

import Model.WorkItemCollection.Index.WorkItemIndex_Adapter;
import Model.WorkItemCollection.Index.WorkItem_IndexType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Model.WorkItem.OldWorkItem;

/**
 * Model class for querying and updating a list of work items
 * 
 * 
 * 
 * @author kenna
 */
public class Old_WorkItemCollection {
    
    // Attributes
    private List<OldWorkItem> data;
    private Map<WorkItem_IndexType, WorkItemIndex_Adapter> indexes;

    
    /**
     * Construct null collection
     */
    public Old_WorkItemCollection() {
        this.data = new ArrayList<>();
        this.indexes = new HashMap<>();
    }
    
    
    /**
     * Construct from dataset
     * 
     * @param data 
     * @param indexes 
     */
    public Old_WorkItemCollection(List<OldWorkItem> data, Map<WorkItem_IndexType, WorkItemIndex_Adapter> indexes) {
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
    public List<OldWorkItem> getData() {
        return data;
    }
    
    
    /**
     * Set data
     * 
     * @param data 
     */
    public void setData(List<OldWorkItem> data) {
        this.data = data;
    }

    
    /**
     * Extend OldWorkItem list
     * 
     * @param data 
     */
    public void extendData(List<OldWorkItem> data) {
        for(OldWorkItem item : data) {
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
    
}
