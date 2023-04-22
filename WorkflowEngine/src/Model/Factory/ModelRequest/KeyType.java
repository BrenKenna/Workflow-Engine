/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.ModelRequest;

import Model.Factory.Factories.ModelType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kenna
 */
public enum KeyType {
    
    TASK {
        @Override
        public boolean isModel(String query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "task".equals(query.toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return TASK == query;
        }

        @Override
        public String toString() {
            return "Task";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.ITEM_TASK;
        }
    },
    
    
    TASK_NAME {
        @Override
        public boolean isModel(String query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "task name".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return TASK_NAME == query;
        }

        @Override
        public String toString() {
            return "Task Name";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.ITEM_TASK;
        }
    },
    
    TASK_STATE {
        @Override
        public boolean isModel(String query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "task state".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return TASK_STATE == query;
        }

        @Override
        public String toString() {
            return "Task State";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.ITEM_TASK;
        }
        
    },
    
    
    TASK_LOGGING {
        @Override
        public boolean isModel(String query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.ITEM_TASK.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "task logging".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return TASK_LOGGING == query;
        }

        @Override
        public String toString() {
            return "Task Logging";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.ITEM_TASK;
        }
    },
    
    
    ITEM_ID {
        @Override
        public boolean isModel(String query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "item id".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return ITEM_ID == query;
        }

        @Override
        public String toString() {
            return "Item ID";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.WORK_ITEM;
        }
    },
    
    
    ITEM_NAME {
        @Override
        public boolean isModel(String query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "item name".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return ITEM_NAME == query;
        }

        @Override
        public String toString() {
            return "Item Name";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.WORK_ITEM;
        }
        
    },
    
    WORKLOAD {
        @Override
        public boolean isModel(String query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.WORK_ITEM.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "workload".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return WORKLOAD == query;
        }

        @Override
        public String toString() {
            return "Workload";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.WORK_ITEM;
        }
    },
    
    
    TASKS {
        @Override
        public boolean isModel(String query) {
            return ModelType.WORKLOAD.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.WORKLOAD.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "tasks".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return TASKS == query;
        }

        @Override
        public String toString() {
            return "Tasks";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.WORKLOAD;
        }
        
    },
    
    WORKLOAD_STATE {
        @Override
        public boolean isModel(String query) {
            return ModelType.WORKLOAD.isType(query);
        }

        @Override
        public boolean isModel(ModelType query) {
            return ModelType.WORKLOAD.isType(query);
        }

        @Override
        public boolean isType(String query) {
            return "workload state".equals(query.replace("_", "").toLowerCase());
        }

        @Override
        public boolean isType(KeyType query) {
            return WORKLOAD_STATE == query;
        }

        @Override
        public String toString() {
            return "Workload State";
        }

        @Override
        public ModelType geModelType() {
            return ModelType.WORKLOAD;
        }
    };
    
    
    /**
     * Return model type of key
     * 
     * @return 
     */
    public abstract ModelType geModelType();
    
    
    /**
     * Check if key type is part of queried model string
     * 
     * @param query
     * @return True/False
     */
    public abstract boolean isModel(String query);
    
    
    /**
     * Check if key type is part of queried model type
     * 
     * @param query
     * @return True/False
     */
    public abstract boolean isModel(ModelType query);
    
    
    /**
     * Check if key type is part of queried key type
     * 
     * @param query
     * @return True/False
     */
    public abstract boolean isType(String query);
    
    
    /**
     * Check if key type is part of queried key type
     * 
     * @param query
     * @return True/False
     */
    public abstract boolean isType(KeyType query);
    
    
    /**
     * Return as string
     * 
     * @return 
     */
    @Override
    public abstract String toString();
    
    
    /**
     * Get keys by their root type
     * 
     * @param modelType
     * @return List-Key Types
     */
    public static List<KeyType> getKeysByRoot(ModelType modelType) {
        List<KeyType> output = new ArrayList<>();
        for ( KeyType key : KeyType.values() ) {
            if ( key.isModel(modelType) ) {
                output.add(key);
            }
        }
        return output;
    }
    
    
    /**
     * Fetch index of query
     * 
     * @param query
     * @return index
     */
    public static int indexOfKey(String query) {
        
        // Initialize search params
        int index = -1;
        int counter = 0;
        boolean found = false;
        
        // Search until found
        while (!found & counter < KeyType.values().length) {
            KeyType current = KeyType.values()[counter];
            if ( current.isType(query) ) {
                found = true;
                index = counter;
            }
            else {
                counter++;
            }
        }
        
        // Return result
        return index;
    }
    
    
    /**
     * Query key type
     * 
     * @param query
     * @return 
     */
    public static KeyType getKeyType(String query) {
        int index = KeyType.indexOfKey(query);
        if ( index > -1 ) {
            return KeyType.values()[index];
        }
        else {
            return null;
        }
    }
}
