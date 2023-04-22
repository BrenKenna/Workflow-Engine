/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem.Task;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author kenna
 */
public class TaskLogging {
    
    // Attributes
    private ProcessLog procLog;
    private String startTime, endTime, threadName;
    private long procID;
    private Optional<Duration> cpuDuration;
    
    
    /**
     * Null constructor
     */
    public TaskLogging() {
        this.procLog = new ProcessLog();
    }
    
    
    /**
     * Construct task logger from process log
     * 
     * @param procLog 
     */
    public TaskLogging(Map<String, String[]> procLog) {
        this.procLog = new ProcessLog(procLog);
    }
    
    
    /**
     * Construct task logger from process logs
     * 
     * @param stdout
     * @param stderr 
     */
    public TaskLogging(String[] stdout, String[] stderr) {
        this.procLog = new ProcessLog(stdout, stderr);
    }

    
    /**
     * Get process log
     * 
     * @return 
     */
    public ProcessLog getProcLog() {
        return procLog;
    }

    
    /**
     * Get stdout
     * 
     * @return 
     */
    public String[] getStdout() {
        return this.procLog.getStdout();
    }
    
    
    /**
     * Get stderr
     * 
     * @return 
     */
    public String[] getStderr() {
        return this.procLog.getStderr();
    }
    
    
    /**
     * Get start time
     * 
     * @return 
     */
    public String getStartTime() {
        return this.startTime;
    }
    
    
    /**
     * Get end time
     * 
     * @return 
     */
    public String getEndTime() {
        return this.endTime;
    }
    
    
    /**
     * Get thread name
     * 
     * @return 
     */
    public String getThreadName() {
        return this.threadName;
    }

    
    /**
     * Get Process ID
     * 
     * @return 
     */
    public long getProcID() {
        return procID;
    }

    
    /**
     * Get CPU duration
     * 
     * @return 
     */
    public Optional<Duration> getCpuDuration() {
        return cpuDuration;
    }

    
    /**
     * Set process log
     * 
     * @param procLog 
     */
    public void setProcLog(ProcessLog procLog) {
        this.procLog = procLog;
    }

    
    /**
     * Set stdout
     * 
     * @param stdout 
     */
    public void setStdout(String[] stdout) {
        this.procLog.setStdout(stdout);
    }
    
    
    /**
     * Set stderr
     * 
     * @param stderr 
     */
    public void setStderr(String[] stderr) {
        this.procLog.setStderr(stderr);
    }
    
    
    /**
     * Set end time
     * 
     * @param startTime 
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    
    /**
     * Set start time
     * 
     * @param endTime 
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    
    /**
     * Set thread name
     * 
     * @param threadName 
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    
    /**
     * Set process ID
     * 
     * @param procID 
     */
    public void setProcID(long procID) {
        this.procID = procID;
    }

    
    /**
     * Set CPU duration
     * 
     * @param cpuDuration 
     */
    public void setCpuDuration(Optional<Duration> cpuDuration) {
        this.cpuDuration = cpuDuration;
    }

    
    /**
     * Represent logger as string
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "TaskLogging{"
                + "procLog=" + procLog
                + ", startTime=" + startTime
                + ", endTime=" + endTime
                + ", threadName=" + threadName
                + ", procID=" + procID
                + ", cpuDuration=" + cpuDuration
        + '}';
    }
}
