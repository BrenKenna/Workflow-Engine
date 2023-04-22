/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItemCollection.Index;

import Utils.Index.IndexEntry;
import Utils.Index.IndexType;
import Model.WorkItem.OldWorkItem;

/**
 *
 * @author kenna
 */
public enum WorkItem_IndexType {
    
    PRIMARY_KEY {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.INTEGER_IND.makeIndexEntry(output, pos);
            return output;
        }
    },

    ITEM_NAME {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.STRING_IND.makeIndexEntry(output, pos);
            return output;
        }
    },
    
    ITEM_STATE {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.STRING_IND.makeIndexEntry(output, pos);
            return output;
        }
    },

    ITEM_TYPE {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.STRING_IND.makeIndexEntry(output, pos);
            return output;
        }
    },
    
    LOCK_DATE {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.STRING_IND.makeIndexEntry(output, pos);
            return output;
        }
    },

    DONE_DATE {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.STRING_IND.makeIndexEntry(output, pos);
            return output;
        }
    },
    
    TASK_COUNT {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.INTEGER_IND.makeIndexEntry(output, pos);
            return output;
        }
    },

    TASKS_DONE {
        @Override
        public IndexEntry makeIndexEntry(OldWorkItem workItem, int pos) {
            IndexEntry output = null;
            output = IndexType.INTEGER_IND.makeIndexEntry(output, pos);
            return output;
        }
    };
    
    
    
    /**
     * Abstract method to create an index entry from OldWorkItem property,
  and position of item in array
     * 
     * @param workItem
     * @param pos
     * @return IndexEntry
     */
    public abstract IndexEntry makeIndexEntry(OldWorkItem workItem, int pos);
}
