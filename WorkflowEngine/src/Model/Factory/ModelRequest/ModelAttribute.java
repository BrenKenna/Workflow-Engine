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
public class ModelAttribute {
    
    private Object attribute;
    private final KeyType keyType;
    private final ModelType modelType;
    
    public ModelAttribute(Object attribute, KeyType keyType, ModelType modelType) {
        this.attribute = attribute;
        this.keyType = keyType;
        this.modelType = modelType;
    }

    public Object getAttribute() {
        return attribute;
    }

    public KeyType getKeyType() {
        return keyType;
    }
    
    public ModelType getModelType() {
        return modelType;
    }
    
    public void setAttrbute(Object attribute) {
        this.attribute = attribute;
    }
}
