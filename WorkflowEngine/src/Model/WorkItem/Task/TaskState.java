/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem.Task;

/**
 *
 * @author kenna
 */
public enum TaskState {
    
    PENDING {
        @Override
        public String toString() {
            return "Pending";
        }

        @Override
        public boolean isState(String query) {
            return "pending".equals( query.toLowerCase() );
        }
    },
    
    
    ACTIVE {
        @Override
        public String toString() {
            return "Active";
        }

        @Override
        public boolean isState(String query) {
            return "active".equals( query.toLowerCase() );
        }
    },

    
    ERROR {
        @Override
        public String toString() {
            return "Error";
        }

        @Override
        public boolean isState(String query) {
            return "error".equals( query.toLowerCase() );
        }
    },
    
    
    COMPLETE {
        @Override
        public String toString() {
            return "Complete";
        }

        @Override
        public boolean isState(String query) {
            return "complete".equals( query.toLowerCase() );
        }
    }
    ;
    
    
    @Override
    public abstract String toString();
    public abstract boolean isState(String query);
    
    
    /**
     * Search states for query
     * 
     * @param query
     * @return True/False
     */
    public static boolean hasType(String query) {
        
        // Initialize values
        boolean found = false;
        int limit = TaskState.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && !found ) {
            TaskState itemState = TaskState.values()[counter];
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
        int limit = TaskState.values().length;
        int counter = 0;
        
        // Search until found is valid
        while ( counter < limit && stateInd < 0 ) {
            TaskState itemState = TaskState.values()[counter];
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
}
