/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.ModelRequest;

import Model.Factory.Factories.ModelType;

/**
 *
 * @author kenna
 */
public class ModelKey {
    
    private KeyType key;
    private ModelType type;
    
    public ModelKey(KeyType key, ModelType type) {
        this.key = key;
        this.type = type;
    }

    public KeyType getKey() {
        return key;
    }

    public ModelType getType() {
        return type;
    }
    
    
}
