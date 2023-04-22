/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Manager.IOHandler.InputHandler;

import Model.Factory.Factories.FactoryChain;
import Model.Factory.Factories.ModelFactory;
import Model.WorkItem.WorkItem;
import Model.WorkItemCollection.WorkItemCollection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/**
 * Class to support reading/streaming input tasks as WorkItems
 * 
 * @author kenna
 */
public class LocalInputHandler {
    
    // Attributes
    private String fieldDelim, subTaskDelim,
            inFile
    ;
    
    // Headers
    private static final String
      shortHeader = "ItemName,Task";
    private static final String
      taskHeader = "TaskName,Task";
    
    private static final String
      singleLongHeader = "";
    
    
    // WorkItem Factory
    ModelFactory modelFactory = FactoryChain.getFactoryChain();
    
    
    /**
     * Null constructor
     */
    public LocalInputHandler() {
        this.fieldDelim = "";
        this.subTaskDelim = "";
        this.inFile = "";
    }

    
    /**
     * Constructor with single task work items only
     * 
     * @param inFile
     * @param fieldDelim 
     */
    public LocalInputHandler(String inFile, String fieldDelim) {
        this.inFile = inFile;
        this.fieldDelim = fieldDelim;
    }
    
    
    /**
     * Constructor for sub-tasks, and mixed workitem set
     * 
     * @param inFile
     * @param fieldDelim
     * @param subTaskDelim 
     */
    public LocalInputHandler(String inFile, String fieldDelim, String subTaskDelim) {
        this.inFile = inFile;
        this.fieldDelim = fieldDelim;
        this.subTaskDelim = subTaskDelim;
    }
    
    
    /**
     * Return workitem collection from input, single tasks
     * 
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public WorkItemCollection importTasks() throws FileNotFoundException, IOException {
        
        // Set params
        WorkItemCollection output = new WorkItemCollection();
        
        // Read data
        BufferedReader reader = 
        new BufferedReader(new FileReader(inFile));
        
        
        // Process lines
        String dataLine;
        while ( (dataLine = reader.readLine()) != null ) {
            WorkItem workItem = processNestedLine(dataLine);
            output.addNew(workItem);
        }
        
        // Return output
        return output;
    }
    
    
    
    /**
     * Validate first line
     * @param firstLine
     * @return 
     */
    public String handleFirstLine(String firstLine) {
        return null;
    }
    
    
    /**
     * 
     * @param dataLine
     * @return 
     */
    public WorkItem processNestedLine(String dataLine) {
        
        // Initialize output
        WorkItem output = new WorkItem();
        
        // Split fields
        String itemName = dataLine.split(fieldDelim)[0];
        String[] tasks = dataLine.split(fieldDelim)[1].split(this.subTaskDelim);
        
        
        
        // Return output
        return output;
    }
    
    
    /**
     * Getters & Setters
    */
    
    
    
    /**
     * Get field delimiter
     * 
     * @return 
     */
    public String getFieldDelim() {
        return fieldDelim;
    }

    
    /**
     * Set new field delimiter
     * 
     * @param fieldDelim 
     */
    public void setFieldDelim(String fieldDelim) {
        this.fieldDelim = fieldDelim;
    }

    
    /**
     * Get sub tasks delimiter
     * 
     * @return 
     */
    public String getSubTaskDelim() {
        return subTaskDelim;
    }

    
    /**
     * Set sub task delimiter
     * 
     * @param subTaskDelim 
     */
    public void setSubTaskDelim(String subTaskDelim) {
        this.subTaskDelim = subTaskDelim;
    }

    
    /**
     * Get file path of input tasks
     * 
     * @return 
     */
    public String getInFile() {
        return inFile;
    }

    
    /**
     * Update tasks to import
     * 
     * @param inFile 
     */
    public void setInFile(String inFile) {
        this.inFile = inFile;
    }

    
    /**
     * Set delimiters
     * 
     * @param fieldDelim
     * @param subTaskDelim 
     */
    public void setDelims(String fieldDelim, String subTaskDelim) {
        this.setFieldDelim(fieldDelim);
        this.setSubTaskDelim(subTaskDelim);
    }
    
    
    /**
     * Represent handler as string
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "LocalInputHandler{" +
                "fieldDelim=" + fieldDelim +
                ", subTaskDelim=" + subTaskDelim +
                ", inFile=" + inFile +
        '}';
    }
}
