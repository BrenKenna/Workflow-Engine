/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.Factories;

import Controller.EngineConstants;
import Model.Factory.ModelRequest.KeyType;
import Model.Factory.ModelRequest.ModelAttribute;
import Model.Factory.ModelRequest.ModelRequest;
import Model.Factory.ModelRequest.WorkItemRequest;
import Model.WorkItem.*;
import Model.WorkItem.Task.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * OldWorkItem Factory with internal methods for OldItemTask
 * 
 * @author kenna
 */
public class WorkItemFactory extends ModelFactory {
    
    
    /**
     * Construct factory
     */
    public WorkItemFactory() {
        super();
        this.level = EngineConstants.WORK_ITEM_LEVEL;
        this.modelType = ModelType.WORK_ITEM;
    }
    
    
    /**
     * Create a single task work item from a task
     * 
     * @param itemTask
     * @return OldWorkItem 
     */
    public OldWorkItem makeSingleTaskItem(OldItemTask itemTask) {
        return new OldSingleTask(itemTask);
    }
    
    
    /**
     * Create a list of single task WorkItems from list of tasks
     * 
     * @param itemTasks
     * @return List-OldWorkItem
     */
    public List<OldWorkItem> makeSingeTaskItems(List<OldItemTask> itemTasks) {
        List<OldWorkItem> output = new ArrayList<>();
        for(OldItemTask task : itemTasks) {
            output.add( new OldSingleTask(task) );
        }
        return output;
    }
    
    
    /**
     * Create a nested task item from list of tasks
     * 
     * @param itemTasks
     * @return OldWorkItem 
     */
    public OldWorkItem makeNestedTaskItem(List<OldItemTask> itemTasks) {
        return new OldNestedTask(itemTasks);
    }
    
    
    /**
     * Create a list of nested task items
     * 
     * @param nestedItemTasks
     * @return List-OldWorkItem
     */
    public List<OldWorkItem> makeNestedTaskItems( Map<Integer, List<OldItemTask> > nestedItemTasks) {
        List<OldWorkItem> output = new ArrayList<>();
        for( Integer i : nestedItemTasks.keySet() ) {
            List<OldItemTask> itemTasks = nestedItemTasks.get(i);
            output.add( makeNestedTaskItem(itemTasks) );
        }
        return output;
    }
    
    
    /**
     * Create OldItemTask from task string
     * 
     * @param task
     * @return OldItemTask
     */
    public OldItemTask makeItemTask(String task) {
        return new OldItemTask(task);
    }
    
    
    /**
     * Make item task from input
     * 
     * @param task
     * @return 
     */
    public OldItemTask makeItemTask(String[] task) {
        return new OldItemTask(task);
    }
    
    
    /**
     * Create list of ItemTasks from list of task strings
     * 
     * @param tasks
     * @return List-OldItemTask
     */
    public List<OldItemTask> makeItemTasks(List<String> tasks) {
        List<OldItemTask> output = new ArrayList<>();
        for(String task : tasks) {
            output.add( makeItemTask(task) );
        }
        return output;
    }

    
    
    /**
     * Construct WorkItem from request or delegate to next factory
     * 
     * @param modelType
     * @param objectRequest
     * @return WorkItem
     */
    @Override
    public Object makeObject(ModelType modelType, ModelRequest objectRequest) {
        
        // Create object
        System.out.println("\n\nActive factory = " + this.activeFactory());
        System.out.println("WorkItem Level = " + EngineConstants.WORK_ITEM_LEVEL + ", Active Level = " + this.level );
        if ( this.level == modelType.getLevel() ) {
            System.out.println("\n\nCreating WorkItem");
            WorkItem output = parseWorkItemRequest((WorkItemRequest) objectRequest);
            return output;
        }
        
        // Otherwise delegate
        else {
            System.out.println("\n\nDelegating creation of " + modelType.toString() + " to " + this.nextFactory.toString());
            return this.nextFactory.makeObject(modelType, objectRequest);
        }
    }
    
    
    /**
     * Construct WorkItem from parsed request
     * 
     * @param request
     * @return 
     */
    private WorkItem parseWorkItemRequest(WorkItemRequest request) {
    
        // Initalize output
        WorkItem output = new WorkItem();
        
        // Handle fields
        for ( KeyType keyType : request.getRequest().keySet() ) {
            
            // Fetch attribute value
            ModelAttribute attribute = request.getAttribute(keyType);
            Object data = attribute.getAttribute();
            
            // Set Task field
            if ( keyType.isType( KeyType.ITEM_NAME ) ) {
                output.setItemName( (String) data);
            }
            
            // Otherwise set task name field
            else if ( keyType.isType( KeyType.WORKLOAD ) ) {
                output.setWorkload( (Workload) data);
            }
            
            // Set state
            if ( output.getWorkload().getWorkload().size() > 1 ) {
                output.setItemType(ItemType.NESTED);
            }
            
            // Set task size
            output.setTaskCount( output.getWorkload().getWorkload().size() );
        }
        
        // Return result
        return output;
    }
    
    
    /**
     * Return Model Types created by factory
     * 
     * @return String
     */
    @Override
    public String toString() {
        return ModelType.WORK_ITEM.toString() + ", Level = " + level;
    }
}
