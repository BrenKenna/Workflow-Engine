/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.Factories;

import Controller.EngineConstants;
import Model.Factory.ModelRequest.KeyType;
import java.util.List;

/**
 *
 * @author kenna
 */
public enum ModelType {
    ITEM_TASK {
        @Override
        public List<KeyType> getKeyTypes() {
            return KeyType.getKeysByRoot(ITEM_TASK);
        }

        @Override
        public boolean isType(String query) {
            return "item task".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(ModelType query) {
            return ITEM_TASK == query;
        }

        @Override
        public String toString() {
            return "Item Task";
        }
        
        @Override
        public int getLevel() {
            return EngineConstants.ITEM_TASK_LEVEL;
        }
    },
    WORKLOAD {
        @Override
        public List<KeyType> getKeyTypes() {
            return KeyType.getKeysByRoot(WORKLOAD);
        }

        @Override
        public boolean isType(String query) {
            return "workload".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(ModelType query) {
            return WORKLOAD == query;
        }

        @Override
        public String toString() {
            return "Workload";
        }
        
        @Override
        public int getLevel() {
            return EngineConstants.WORKLOAD_LEVEL;
        }
    },
    WORK_ITEM {
        @Override
        public List<KeyType> getKeyTypes() {
            return KeyType.getKeysByRoot(WORK_ITEM);
        }

        @Override
        public boolean isType(String query) {
            return "work item".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(ModelType query) {
            return WORK_ITEM == query;
        }

        @Override
        public String toString() {
            return "Work Item";
        }
        
        @Override
        public int getLevel() {
            return EngineConstants.WORK_ITEM_LEVEL;
        }
    }
    ;
    
    
    public abstract List<KeyType> getKeyTypes();
    
    public abstract boolean isType(String query);
    public abstract boolean isType(ModelType query);
    
    public abstract int getLevel();
    
    
    @Override
    public abstract String toString();
    
    
    /**
     * Return whether key is valid for model type
     * 
     * @param type
     * @param key
     * @return True/False
     */
    public static boolean isValidKey(ModelType type, KeyType key) {
        List<KeyType> validKeys = type.getKeyTypes();
        return validKeys.indexOf(key) > -1;
    }
}
