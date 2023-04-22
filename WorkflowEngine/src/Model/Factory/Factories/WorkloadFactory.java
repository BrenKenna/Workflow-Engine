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
import Model.Factory.ModelRequest.WorkloadRequest;
import Model.WorkItem.Task.ItemTask;
//import Model.Factory.ModelRequest.WorkloadRequest;
import Model.WorkItem.Workload;
import java.util.List;

/**
 *
 * @author kenna
 */
public class WorkloadFactory extends ModelFactory {

    
    /**
     * Construct factory
     */
    public WorkloadFactory() {
        super();
        this.level = EngineConstants.WORKLOAD_LEVEL;
        this.modelType = ModelType.WORKLOAD;
    }
    
    
    
    /**
     * Create workload object or delegate to the next factory
     * 
     * @param modelType
     * @param objectRequest
     * @return Object-Worklaod
     */
    @Override
    public Object makeObject(ModelType modelType, ModelRequest objectRequest) {

        // Create object
        System.out.println("\n\nActive factory = " + this.activeFactory());
        System.out.println("WorkLoad Level = " + EngineConstants.WORKLOAD_LEVEL + ", Active Level = " + this.level );
        if ( this.level == modelType.getLevel() ) {
            System.out.println("\n\nACreating Workload");
            return parseWorkloadRequest( (WorkloadRequest) objectRequest );
        }
        
        // Otherwise delegate
        else {
            System.out.println("\n\nDelegating creation of " + modelType.toString() + " to " + this.nextFactory.toString());
            return this.nextFactory.makeObject(modelType, objectRequest);
        }
    }
    
    
    /**
     * Parse request workload request for constructing workload object
     * 
     * @param objectRequest
     * @return Workload
     */
    private Workload parseWorkloadRequest(WorkloadRequest objectRequest) {
        
        // Initalize
        Workload output = new Workload();
        
        // Parse fields
        for ( KeyType key : objectRequest.getRequest().keySet() ) {
            ModelAttribute attribute = objectRequest.getAttribute(key);
            Object data = attribute.getAttribute();
            
            // Handle setters
            if ( key == KeyType.TASKS ) {
                output.setWorkload( (List<ItemTask>) data );
            }
        }
        
        // Return results
        return output;
    }
    
    
    /**
     * Return Model Types created by factory
     * 
     * @return String
     */
    @Override
    public String toString() {
        return ModelType.WORKLOAD.toString() + ", Level = " + level;
    }
}
