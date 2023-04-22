/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItemCollection.Index;

import Utils.Index.*;
import java.util.List;
import Model.WorkItem.OldWorkItem;

/**
 *
 * @author kenna
 */
public class WorkItemIndex_Adapter {

    // Attributes
    private final WorkItem_IndexType workItemIndexType;
    private final IndexType indexType;
    private final boolean isInt;
    private final boolean isString;
    private final Index index;

    
    /**
     * Construct from valid type
     * 
     * @param workItemIndexType
     * @param indexType 
     */
    public WorkItemIndex_Adapter(WorkItem_IndexType workItemIndexType, IndexType indexType) {
        this.workItemIndexType = workItemIndexType;
        this.indexType = indexType;
        this.isInt = (indexType == IndexType.INTEGER_IND);
        this.isString = (indexType == IndexType.STRING_IND);
        
        if ( this.isInt) {
            index = new IntIndex(indexType);
        }
        
        else {
            index = new StringIndex(indexType);
        }
    }
    
    
    /**
     * Get the type of index
     * 
     * @return IndexType
     */
    public IndexType getIndexType() {
        return this.indexType;
    }
    
    
    /**
     * Get the work item index type (ie Primary Key etc)
     * 
     * @return WorkItem_IndexType
     */
    public WorkItem_IndexType getWorkItemType() {
        return this.workItemIndexType;
    }

    
    /**
     * Add work items to index
     * 
     * @param items 
     */
    public void indexFromItems(List<OldWorkItem> items) {
        
        // Update index with list
        for (int counter = 0; counter < items.size(); counter++) {
            OldWorkItem item = items.get(counter);
            IndexEntry indexEntry = workItemIndexType.makeIndexEntry(item, counter);
            index.insert(indexEntry);
        }
        
        // Sort index ascendingly
        index.sort(true);
    }
}
