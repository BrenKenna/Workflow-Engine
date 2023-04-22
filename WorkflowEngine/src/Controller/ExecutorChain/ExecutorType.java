/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.ExecutorChain;

/**
 *
 * @author kenna
 */
public enum ExecutorType {
    
    ITEM {
        @Override
        public boolean isType(String query) {
            return "item".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Item";
        }
    },
    
    TASK {
        @Override
        public boolean isType(String query) {
            return "item".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Task";
        }
    };
    
    
    public abstract boolean isType(String query);
    
    @Override
    public abstract String toString();
    
    
    /**
     * Fetch index of query
     * 
     * @param query
     * @return 
     */
    public static int indexOf(String query) {
        
        // Initialize search params
        int typeIndex = -1;
        int counter = 0;
        boolean found = false;
        
        // Search until found or still valid
        while ( !found && counter < ExecutorType.values().length ) {
            ExecutorType activeType = ExecutorType.values()[counter];
            if ( activeType.isType(query) ) {
                found = true;
                typeIndex = counter;
            }
            else {
                counter++;
            }
        }
        
        // Return result
        return typeIndex;
    }
    
    
    /**
     * Fetch type
     * 
     * @param query
     * @return 
     */
    public static ExecutorType getType(String query) {
        ExecutorType output = null;
        int indexType = ExecutorType.indexOf(query);
        if ( indexType > -1 ) {
            output = ExecutorType.values()[indexType];
        }
        return output;
    }
}
