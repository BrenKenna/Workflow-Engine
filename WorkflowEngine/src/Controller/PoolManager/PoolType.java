/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.PoolManager;

/**
 *
 * @author kenna
 */
public enum PoolType {
    
    ENGINE {
        @Override
        public boolean isType(String query) {
            return "engine".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "Engine";
        }
    },
    
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
            return "task".equals(query.toLowerCase());
        }

        @Override
        public String toString() {
            return "task";
        }
    };
    
    
    /**
     * Compare query string to enum value
     * 
     * @param query
     * @return 
     */
    public abstract boolean isType(String query);
    
    
    /**
     * Represent enum value as string
     * 
     * @return String 
     */
    @Override
    public abstract String toString();
    
    
    /**
     * Check if query is a valid enum value
     * 
     * @param query
     * @return True/False
     */
    public static boolean hasType(String query) {
        return indexOf(query) > -1;
    }
    
    
    /**
     * Fetch index of query
     * 
     * @param query
     * @return -1/Index
     */
    public static int indexOf(String query) {
        
        // Initialize search params
        int poolIndex = -1;
        int counter = 0;
        boolean found = false;
        
        // Search while valid
        while ( counter < PoolType.values().length && !found ) {
        
            // Get active
            PoolType poolType = PoolType.values()[counter];
            
            // Terminate loop if found
            if ( poolType.isType(query) ) {
                found = true;
                poolIndex = counter;
            }
            
            // Otherwise proceed
            else {
                counter++;
            }
        }
        
        
        // Return result
        return poolIndex;
    }
    
    
    /**
     * Return pool type matching query
     * 
     * @param query
     * @return PoolType/Null
     */
    public static PoolType getType(String query) {
        
        // Initialize required variables
        PoolType poolType = null;
        int poolIndex = PoolType.indexOf(query);
        
        // Handle lookup
        if ( poolIndex > -1 ) {
            poolType = PoolType.values()[poolIndex];
        }
        
        // Return result
        return poolType;
    }
}
