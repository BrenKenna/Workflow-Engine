/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.Index;

import java.util.List;


/**
 * Integer specific index
 * 
 * @author kenna
 */
public class IntIndex extends Index{
    
    public IntIndex(List<IndexEntry> index) {
        super(index);
    }
    
    public IntIndex(IndexType indexType) {
        super(indexType);
    }
}
