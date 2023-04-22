/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;


/**
 * Enum to hold valid item states: Todo, Locked, Done
 * 
 * @author kenna
 */
public enum ItemState {
    
    
    TODO {
        
        /**
         * Compare query to state
         */
        @Override
        public boolean isState(String query) {
            return "todo".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "ToDo";
        }
    },
    
    
    LOCKED {
        
        /**
         * Compare query to state
         */
        @Override
        public boolean isState(String query) {
            return "locked".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Locked";
        }
    },
    
    
    DONE {
        
        /**
         * Compare query to state
         */
        @Override
        public boolean isState(String query) {
            return "done".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Done";
        }
    }
    
    ;

    
    /**
     * Search states for query
     * 
     * @param query
     * @return True/False
     */
    public static boolean hasState(String query) {
        
        // Initialize values
        boolean found = false;
        int limit = ItemState.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && !found ) {
            ItemState itemState = ItemState.values()[counter];
            if ( itemState.isState(query) ) {
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
        int limit = ItemState.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && stateInd < 0 ) {
            ItemState itemState = ItemState.values()[counter];
            if ( itemState.isState(query) ) {
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
    public abstract boolean isState(String query);

    
    /**
     * Abstract method to enum as string
     * 
     * @return ToDo/Done/Locked 
     */
    @Override
    public abstract String toString();
}
