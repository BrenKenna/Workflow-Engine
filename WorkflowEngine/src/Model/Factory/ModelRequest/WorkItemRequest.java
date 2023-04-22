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
public class WorkItemRequest extends ModelRequest {
    
    
    /**
     * Null constructor
     */
    public WorkItemRequest() {
        super(ModelType.WORK_ITEM);
    }
    
    
    /**
     * Construct with request object
     * 
     * @param request 
     */
    public WorkItemRequest(Map<KeyType, ModelAttribute> request) {
        super(ModelType.WORK_ITEM, request);
    }
    
}
