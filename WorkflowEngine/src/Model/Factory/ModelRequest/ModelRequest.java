/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.ModelRequest;

import Model.Factory.Factories.ModelType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kenna
 */
public abstract class ModelRequest {

    // Attributes accesible by concrete classes
    protected Map<KeyType, ModelAttribute> request;
    protected ModelType type;
    
    
    /**
     * Null constructor
     * 
     * @param type 
     */
    public ModelRequest(ModelType type) {
        this.request = new HashMap<>();
        this.type = type;
    }
    
    
    /**
     * Construct with request
     * 
     * @param type
     * @param request 
     */
    public ModelRequest(ModelType type, Map<KeyType, ModelAttribute> request) {
        this.type = type;
        this.request = request;
    }

    
    /**
     * Get request
     * 
     * @return 
     */
    public Map<KeyType, ModelAttribute> getRequest() {
        return this.request;
    }
    
    
    
    /**
     * Get valid keys for concrete request
     * 
     * @return 
     */
    public List<KeyType> getValidKeys() {
        return this.type.getKeyTypes();
    }

    
    
    /**
     * Get attribute for key if valid
     * 
     * @param key
     * @return 
     */
    public ModelAttribute getAttribute(KeyType key) {
       ModelAttribute output = null;
       if ( this.hasKey(key) ) {
           return this.request.get(key);
       }
       return output;
    }


    /**
     * Get attribute if valid
     * 
     * @param key
     * @return 
     */
    public ModelAttribute getAttribute(String key) {
       ModelAttribute output = null;
       if ( this.hasKey(key) ) {
           int keyInd = KeyType.indexOfKey(key);
           KeyType keyType = KeyType.values()[keyInd];
           return this.request.get(keyType);
       }
       return output;
    }
    
    
    /**
     * Set attribute if valid
     * 
     * @param key
     * @param attribute 
     */
    public void setAttributeValue(KeyType key, ModelAttribute attribute) {
        if ( this.isValidKey(key) ) {
            this.request.put(key, attribute);
        }
    }


    /**
     * 
     * @param query
     * @return 
     */
    public boolean isValidKey(KeyType query) {
        return ModelType.isValidKey(type, query);
    }
    
    
    /**
     * Check if query string is valid for concrete type
     * 
     * @param query
     * @return True/False
     */
    public boolean isValidKey(String query) {
        KeyType keyType = KeyType.getKeyType(query);
        return ModelType.isValidKey(type, keyType);
    }
    
    
    /**
     * 
     * @param query
     * @return 
     */
    public boolean hasKey(KeyType query) {
        return this.request.keySet().contains(query);
    }

    
    /**
     * 
     * @param query
     * @return 
     */
    public boolean hasKey(String query) {
        KeyType key = KeyType.getKeyType(query);
        return this.request.keySet().contains(key);
    }
}
