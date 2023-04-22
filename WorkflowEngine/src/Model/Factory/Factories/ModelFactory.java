/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.Factories;

import Controller.EngineConstants;
import Model.Factory.ModelRequest.ModelRequest;

/**
 *
 * @author kenna
 */
public abstract class ModelFactory {
    
    
    // Attributes for concrete factory level
    public static final int WORK_ITEM_LEVEL = EngineConstants.WORK_ITEM_LEVEL;
    public static final int ITEM_TASK_LEVEL = EngineConstants.ITEM_TASK_LEVEL;
    public static final int WORKLOAD_LEVEL = EngineConstants.WORKLOAD_LEVEL;
    
    
    // Current and next element in chain
    protected int level = 0;
    protected ModelType modelType;
    protected ModelFactory nextFactory;
    protected ModelType nextType;
    
    
    /**
     * Set next factory and type of model
     * 
     * @param nextFactory
     * @param nextType
     */
    protected void setNextFactory(ModelFactory nextFactory, ModelType nextType) {
        this.nextFactory = nextFactory;
        this.nextType = nextType;
    }
    
    
    /**
     * Return active factory
     * 
     * @return 
     */
    protected String activeFactory() {
        return this.toString();
    }
    
    
    /**
     * Check next factory 
     * 
     * @return 
     */
    protected String nextFactory() {
        return toString() + ", Next Factory-" + this.nextFactory.toString();
    }
    
    /**
     * Abstract method to construct specific model object from request
     * 
     * @param modelType
     * @param objectRequest
     * @return 
     */
    public abstract Object makeObject(ModelType modelType, ModelRequest objectRequest);
    
    
    /**
     * Represent factory as string
     * 
     * @return String
     */
    @Override
    public abstract String toString();
}
