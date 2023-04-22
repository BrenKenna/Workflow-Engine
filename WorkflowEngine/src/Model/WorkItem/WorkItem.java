/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem;

/**
 * Model class for WorkItem
 * 
 * @author kenna
 */
public class WorkItem {
    
    // Item identifier
    private static int itemId = -1;
    private int autoId;
    private String itemName;


    // Item level
    private ItemType itemType = ItemType.SINGLE;
    private ItemState itemState;
    private String lockId;
    private String lockDate;
    private String doneDate;


    // Task level
    private int taskCount;
    private int tasksDone;
    private Workload workload;
    
    
    /**
     * Null constructor
     */
    public WorkItem() {
        this.autoId = setAutoId();
        this.taskCount = 0;
        this.tasksDone = 0;
    }
    
    
    /**
     * Construct with name
     * 
     * @param itemName 
     */
    public WorkItem(String itemName) {
        this.autoId = setAutoId();
        this.taskCount = 0;
        this.tasksDone = 0;
        this.itemName = itemName;
    }
    
    
    /**
     * Construct with name and workload
     * 
     * @param itemName 
     * @param workload 
     */
    public WorkItem(String itemName, Workload workload) {
        this.autoId = setAutoId();
        this.taskCount = 0;
        this.tasksDone = 0;
        this.itemName = itemName;
        this.workload = workload;
    }

    
    /**
     * Auto-increment
     * 
     * @return 
     */
    private static int setAutoId() {
        WorkItem.itemId++;
        return WorkItem.itemId;
    }
    

    /**
     * Return task Id
     * 
     * @return 
     */
    public int getItemId() {
        return this.autoId;
    }

    
    /**
     * 
     * Getters
     * 
     */
    
    
    /**
     * Get itemID
     * 
     * @return 
     */
    public int getAutoId() {
        return autoId;
    }

    
    /**
     * Get item name
     * 
     * @return 
     */
    public String getItemName() {
        return itemName;
    }

    
    /**
     * Get item type
     * @return 
     */
    public ItemType getItemType() {
        return itemType;
    }

    
    /**
     * Get item state
     * 
     * @return 
     */
    public ItemState getItemState() {
        return itemState;
    }

    
    /**
     * Get lockId
     * @return 
     */
    public String getLockId() {
        return lockId;
    }

    
    /**
     * Get lock date
     * 
     * @return 
     */
    public String getLockDate() {
        return lockDate;
    }

    
    /**
     * Get done date
     * 
     * @return 
     */
    public String getDoneDate() {
        return doneDate;
    }

    
    /**
     * Get task count
     * 
     * @return 
     */
    public int getTaskCount() {
        return taskCount;
    }

    
    /**
     * Get tasks done
     * 
     * @return 
     */
    public int getTasksDone() {
        return tasksDone;
    }

    
    /**
     * Get workload
     * 
     * @return 
     */
    public Workload getWorkload() {
        return workload;
    }

    
    /**
     * Set new name for item
     * @param itemName 
     */
    public void setItemName(String itemName) {
        this.itemName = itemName + "-" + this.autoId;
    }

    
    /**
     * Set new type
     * 
     * @param itemType 
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    
    /**
     * Set new item state
     * 
     * @param itemState 
     */
    public void setItemState(ItemState itemState) {
        this.itemState = itemState;
    }

    
    /**
     * Set new lockId
     * 
     * @param lockId 
     */
    public void setLockId(String lockId) {
        this.lockId = lockId;
    }

    
    /**
     * Set new lock date
     * 
     * @param lockDate 
     */
    public void setLockDate(String lockDate) {
        this.lockDate = lockDate;
    }

    
    /**
     * Set new done date
     * 
     * @param doneDate 
     */
    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    
    /**
     * Set new task count
     * 
     * @param taskCount 
     */
    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    
    /**
     * Set new tasks done
     * @param tasksDone 
     */
    public void setTasksDone(int tasksDone) {
        this.tasksDone = tasksDone;
    }

    
    /**
     * Set new workload
     * 
     * @param workload 
     */
    public void setWorkload(Workload workload) {
        this.workload = workload;
    }

    
    /**
     * Represent WorkItem as string
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "WorkItem{"
                + "autoId=" + autoId
                + ", itemName=" + itemName
                + ", itemType=" + itemType
                + ", itemState=" + itemState
                + ", lockId=" + lockId
                + ", lockDate=" + lockDate
                + ", doneDate=" + doneDate
                + ", taskCount=" + taskCount
                + ", tasksDone=" + tasksDone
                + ", workload=" + workload
        + '}';
    }
}
