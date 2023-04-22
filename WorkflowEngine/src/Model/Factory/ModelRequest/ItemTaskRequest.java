/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.ModelRequest;

import Model.Factory.Factories.ModelType;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author kenna
 */
public class ItemTaskRequest extends ModelRequest {
    
    
    /**
     * Null constructor
     * 
     */
    public ItemTaskRequest() {
        super(ModelType.ITEM_TASK);
    }
    
    
    /**
     * Constructor with data
     * 
     * @param request 
     */
    public ItemTaskRequest(Map<KeyType, ModelAttribute> request) {
        super(ModelType.ITEM_TASK, request);
    }


}
