/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Supporting;

import Model.WorkItem.*;
import Model.WorkItem.Task.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 *
 * @author kenna
 */
public class TaskGenerator {
    
    // To generate sites to ping
    private final Random rand = new Random();
    protected String[] pings = {
        "amazon.com", "google.com", "facebook.com", "rte.ie", "github.com", "twitter.com", "netflix.com",
        "youtube.com", "spotify.com"
    };
    

    /**
     * Return random site to ping
     * 
     * @return String
     */
    public String[] getCmd() {
        String[] output = new String[2];
        int randNum = rand.nextInt( this.pings.length );
        String cmd = pings[randNum];
        output[0] = "ping " + cmd;
        output[1] = "Ping " + cmd;
        return output;
    }
    
    
    /**
     * Fetch required number of commands to run
     * 
     * @param nCmds
     * @return List-String
     */
    public List<String[]> getCmds(int nCmds) {
        List<String[]> output = new ArrayList<>();
        for ( int i = 0; i <= nCmds; i++) {
            output.add(getCmd());
        }
        return output;
    }
    
    
    /**
     * Randomly generate random number of tasks
     * 
     * @return List-String
     */
    public List<String[]> getCmds() {
        int nCmds = rand.nextInt(10);
        return getCmds(nCmds);
    }
    
    
    /**
     * Fetch random ping task
     * 
     * @return OldItemTask
     */
    public OldItemTask makeRandTask() {
        return new OldItemTask(getCmd());
    }
    
    
    /**
     * Fetch random number of ping commands
     * 
     * @return OldItemTask
     */
    public List<OldItemTask> makeRandTasks() {
        List<OldItemTask> itemTasks = new ArrayList<>();
        for( String[] task : getCmds() ) {
            itemTasks.add(new OldItemTask(task) );
        }
        return itemTasks;
    }
    
    
    /**
     * Fetch requred number of ping commands
     * 
     * @return OldItemTask
     */
    public List<OldItemTask> makeRandTasks(int nTasks) {
        List<OldItemTask> itemTasks = new ArrayList<>();
        for( String[] task : getCmds(nTasks) ) {
            itemTasks.add(new OldItemTask(task) );
        }
        return itemTasks;
    }
    
    
    
    /**
     * Make single task work item
     * 
     * @return OldWorkItem
     */
    public OldWorkItem makeSingleTaskItem() {
        OldItemTask task = this.makeRandTask();
        return new OldSingleTask(task);
    }
    
    
    /**
     * Make a random list of single task work items
     * 
     * @param nItems
     * @return List-WorkItems
     */
    public List<OldWorkItem> makeSingleTaskItems(int nItems) {
        List<OldWorkItem> workItems = new ArrayList<>();
        for (OldItemTask task : makeRandTasks(nItems)) {
            OldWorkItem item = new OldSingleTask(task);
            workItems.add(item);
        }
        return workItems;
    }
    
    
    /**
     * Make a nested task work item
     * 
     * @param nTasks
     * @return OldWorkItem
     */
    public OldWorkItem makeNestedTaskItem(int nTasks) {
        List<OldItemTask> tasks = this.makeRandTasks(nTasks);
        return new OldNestedTask(tasks);
    }
    
    
    /**
     * Make required number of nested work items
     * 
     * @param nTasks
     * @param nItems
     * @return List-OldWorkItem 
     */
    public List<OldWorkItem> makeNestedTaskItem(int nTasks, int nItems) {
        List<OldWorkItem> items = new ArrayList<>();
        for (int i = 0; i <= nItems; i++) {
            List<OldItemTask> tasks = this.makeRandTasks(nTasks);
            items.add( new OldNestedTask(tasks) );
        }
        return items;
    }
}
