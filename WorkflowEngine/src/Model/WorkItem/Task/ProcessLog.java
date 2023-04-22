/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.WorkItem.Task;

import java.util.Map;

/**
 *
 * @author kenna
 */
public class ProcessLog {
    
    // Attributes
    private String[] stdout, stderr;
    
    
    /**
     * Null constructor
     */
    public ProcessLog() {
        this.stdout = null;
        this.stderr = null;
    }

    
    /**
     * Construct process log from map
     * 
     * @param procLog 
     */
    public ProcessLog(Map<String, String[]> procLog) {
        this.stdout = procLog.get("stdout");
        this.stderr = procLog.get("stderr");
    }
    
    /**
     * Constructor with log
     * 
     * @param stdout
     * @param stderr 
     */
    public ProcessLog(String[] stdout, String[] stderr) {
        this.stdout = stdout;
        this.stderr = stderr;
    }

    
    /**
     * Get stdout
     * 
     * @return stdout
     */
    public String[] getStdout() {
        return stdout;
    }

    
    /**
     * Get stderr
     * 
     * @return stdout
     */
    public String[] getStderr() {
        return stderr;
    }

    
    /**
     * Set stdout
     * 
     * @param stdout 
     */
    public void setStdout(String[] stdout) {
        this.stdout = stdout;
    }

    
    /**
     * Set stderr
     * 
     * @param stderr 
     */
    public void setStderr(String[] stderr) {
        this.stderr = stderr;
    }

    
    /**
     * Represent ProcessLog as string
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "ProcessLog{"
                + "stdout=" + stdout
                + ", stderr=" + stderr
        + '}';
    }
}
