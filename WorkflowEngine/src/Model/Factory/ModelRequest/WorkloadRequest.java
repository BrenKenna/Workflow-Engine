/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.ModelRequest;

import Model.Factory.Factories.ModelType;
import java.util.Map;

/**
 *
 * @author kenna
 */
public class WorkloadRequest extends ModelRequest {
    
    
    /**
     * Null constructor
     */
    public WorkloadRequest() {
        super(ModelType.WORKLOAD);
    }
    
    
    /**
     * Constructor with request
     * 
     * @param request 
     */
    public WorkloadRequest(Map<KeyType, ModelAttribute> request) {
        super(ModelType.WORKLOAD);
    }
}
