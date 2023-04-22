/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.Factories;

import Controller.EngineConstants;
import Model.Factory.ModelRequest.*;
import Model.WorkItem.Task.*;

/**
 *
 * @author kenna
 */
public class ItemTaskFactory extends ModelFactory {
    
    /**
     * Default constructor
     */
    public ItemTaskFactory() {
        super();
        this.level = EngineConstants.ITEM_TASK_LEVEL;
        this.modelType = ModelType.ITEM_TASK;
    }

    
    /**
     * Return ItemTask object
     * 
     * @param modelType
     * @param objectRequest
     * @return 
     */
    @Override
    public Object makeObject(ModelType modelType, ModelRequest objectRequest) {
        
        // Create object
        System.out.println("\n\nActive factory = " + this.activeFactory());
        System.out.println("ItemTask Level = " + EngineConstants.ITEM_TASK_LEVEL + ", Active Level = " + this.level );
        if ( this.level == modelType.getLevel() ) {
            System.out.println("\n\nCreating Item Task");
            ItemTask output = parseRequest((ItemTaskRequest) objectRequest);
            return output;
        }
        
        // Otherwise delegate
        else {
            System.out.println("\n\nDelegating creation of " + modelType.toString() + " to " + this.nextFactory.toString());
            return this.nextFactory.makeObject(modelType, objectRequest);
        }
    }
    
    
    /**
     * 
     * @param request
     * @return 
     */
    private ItemTask parseRequest(ItemTaskRequest request) {
        
        // Initalize output
        ItemTask output = new ItemTask();
        
        // Parse
        for ( KeyType keyType : request.getRequest().keySet() ) {
            
            // Fetch attribute value
            ModelAttribute attribute = request.getAttribute(keyType);
            Object data = attribute.getAttribute();
            
            // Set Task field
            if ( keyType.isType( KeyType.TASK ) ) {
                output.setTask(data.toString());
            }
            
            // Otherwise set task name field
            else if ( keyType.isType( KeyType.TASK_NAME ) ) {
                output.setTaskName(data.toString());
            }
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
        return ModelType.ITEM_TASK.toString() + ", Level = " + level;
    }
}
