/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

/**
 *
 * @author kenna
 */
public enum ItemType {
    
    SINGLE {
        @Override
        public boolean isType(String query) {
            return "single".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Single";
        }
    },

    NESTED {
        @Override
        public boolean isType(String query) {
            return "nested".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Nested";
        }
    }
    ;
    
    
    /**
     * Search states for query
     * 
     * @param query
     * @return True/False
     */
    public static boolean hasType(String query) {
        
        // Initialize values
        boolean found = false;
        int limit = ItemType.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && !found ) {
            ItemType itemState = ItemType.values()[counter];
            if ( itemState.isType(query) ) {
                found = true;
            }
            else {
                counter++;
            }
        }
        
        // Return search result
        return found;
    }
    
    
    /**
     * Fetch index of queried item state
     * 
     * @param query
     * @return -1/Index in array
     */
    public static int indexOf(String query) {
        
        // Initialize values
        int stateInd = -1;
        int limit = ItemType.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && stateInd < 0 ) {
            ItemType itemState = ItemType.values()[counter];
            if ( itemState.isType(query) ) {
                stateInd = counter;
            }
            else {
                counter++;
            }
        }
        
        // Return search result
        return stateInd;
    }
    
    
    /**
     * Abstract method to compare query to active type
     * 
     * @param query
     * @return True/False
     */
    public abstract boolean isType(String query);

    
    /**
     * Abstract method to enum as string
     * 
     * @return ToDo/Done/Locked 
     */
    @Override
    public abstract String toString();
}
