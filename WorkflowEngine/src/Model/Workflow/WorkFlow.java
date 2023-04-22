/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Workflow;

import Model.WorkItem.*;
import Model.WorkItemCollection.Old_WorkItemCollection;
import java.util.HashMap;
import java.util.Map;


/**
 * WorkFlow is a collection of WorkItems grouped by their state
 * 
 * @author kenna
 */
public class WorkFlow {

    // Attributes
    private Map<ItemState, Old_WorkItemCollection> workflowData;
    
    
    /**
     * Null constructor
     */
    public WorkFlow() { }


    /**
     * 
     * @param workflowData 
     */
    public WorkFlow(Map<ItemState, Old_WorkItemCollection> workflowData) {
        this.workflowData = workflowData;
    }
    
    
    /**
     * Return the workflow
     * 
     * @return Map<ItemState, WorkItem[]>
     */
    public Map<ItemState, Old_WorkItemCollection> fetchWorkFlow() {
        return workflowData;
    }
    
    /**
     * Fetch work times by state
     * 
     * @param itemState
     * @return WorkItem[]
     */
    public Old_WorkItemCollection getItemsByState(ItemState itemState) {
        return workflowData.get(itemState);
    }
    
    
    /**
     * Summarize the item states of the workflow
     * 
     * @return Map<ItemState, Integer>
     */
    public Map<ItemState, Integer> summarizeItemStates() {
        
        // Initialize output
        Map<ItemState, Integer> stateSummary = new HashMap<>();
        
        // Count the elements of itemState in workflow
        //  and append to output
        for(ItemState itemState: workflowData.keySet()) {
            int count = workflowData.get(itemState).getSize();
            stateSummary.put(itemState, count);
        }
        
        // Return results
        return stateSummary;
    }
}
