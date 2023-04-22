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
public class ItemTask {
    
    // Task identifier
    private static int taskId = -1;
    private final int autoId;
    private String taskName;

    
    // Task stuff
    private TaskState taskState;
    private String task;
    private TaskLogging taskLog;

    
    /**
     * Null constructor
     */
    public ItemTask() {
        this.autoId = setAutoId();
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }

    
    /**
     * Create task from input
     * 
     * @param task 
     */
    public ItemTask(String task) {
        this.autoId = setAutoId();
        this.task = task;
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }
    
    
    
    /**
     * Generate item task from name & task
     * 
     * @param task 
     */
    public ItemTask(String[] task) {
        this.autoId = setAutoId();
        this.task = task[0];
        this.taskName = task[1];
        this.taskState = TaskState.PENDING;
        this.taskLog = new TaskLogging();
    }
    

    /**
     * Auto-increment
     * 
     * @return 
     */
    private static int setAutoId() {
        ItemTask.taskId++;
        return ItemTask.taskId;
    }
    

    /**
     * 
     * @return 
     */
    public int getTaskId() {
        return this.autoId;
    }
    
    
    /**
     * Return the name of the task
     * 
     * @return 
     */
    public String getTaskName() {
        return this.taskName;
    }
    
    
    /**
     * See if task names match
     * 
     * @param query
     * @return 
     */
    public boolean isTask(String query) {
        return this.taskName.equals(query);
    }
    
    
    /**
     * Set task
     * 
     * @param task 
     */
    public void setTask(String task) {
        this.task = task;
    }
    
    
    /**
     * Set task name
     * 
     * @param taskName 
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    
    /**
     * Return task log
     * 
     * @return 
     */
    public TaskLogging getTaskLog() {
        return this.taskLog;
    }
    
    
    /**
     * Return the task
     * 
     * @return 
     */
    public String getTask() {
        return this.task;
    }
    
    
    /**
     * Return task state
     * 
     * @return 
     */
    public TaskState getTaskState() {
        return this.taskState;
    }
    
    
    /**
     * 
     * @param state 
     */
    public void setTaskState(TaskState state) {
        this.taskState = state;
    }
    
    /**
     * Return ItemTask as string
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "ItemTask{"
                + "autoId=" + autoId
                + ", taskName=" + taskName
                + ", taskState=" + taskState
                + ", task=" + task
                + ", taskLog=" + taskLog
        + '}';
    }
    
    
    
    
}
