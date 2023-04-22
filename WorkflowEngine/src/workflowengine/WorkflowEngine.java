/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workflowengine;

import Controller.Engine;
import Controller.EngineConstants;
import Controller.EngineState;
import Controller.PoolManager.Old_ParallelWorkItems;
import Controller.PoolManager.PoolManager;
import Controller.PoolManager.PoolType;
import Model.Factory.Factories.FactoryChain;
import Model.Factory.Factories.ModelFactory;
import Model.Factory.Factories.ModelType;
import Model.Supporting.TaskGenerator;
import Model.Factory.Factories.WorkItemFactory;
import Model.Factory.ModelRequest.*;
import Model.WorkItem.ItemType;
import Model.WorkItem.Task.OldItemTask;
import Model.WorkItemCollection.Old_WorkItemCollection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.WorkItem.OldWorkItem;
import Model.WorkItem.Task.ItemTask;
import Model.WorkItem.Task.TaskLogging;
import Model.WorkItem.WorkItem;
import Model.WorkItem.Workload;
import Model.WorkItemCollection.WorkItemCollection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kenna
 */
public class WorkflowEngine {
    
    
    /**
     * Entry point for program
 
 (X)- Moved on from taskParallelization for now
 (X)- Testing OldWorkItem-Factory
 - Working on PoolManager & Task Execution from Collection
     * 
     * @param args 
     */
    public static void main(String[] args) {
        
        // Test initial task parallelization
        // testParaTasks()
        
        // Run OldWorkItem Interface sanity check
        // testWorkItem();
        
        // Test parallel processing of WorkItems
        //  through the PoolManager
        // testPoolManager();
        
        // Create workload under Abstract Factory Chain
        //  & Model Request Interface
        // testRequestInterface();
        
        // Test WorkFlow Engine, with WorkItemCollection
        testEngine();
    }
    
    
    /**
     * Do things while task is running
     * 
     * @param task
     * @param engine 
     */
    public static void waitOnTask(ForkJoinTask task, Engine engine) {
        
        // Inspection complete
        try {
            System.out.println("Engine-Controller-Engine: Inspection complete waiting on next iteration");
            TimeUnit.SECONDS.sleep(2);
        }
        catch (InterruptedException ex) {
            System.out.println("Engine-Controller-Engine: Cannot sleep 10s");
            System.out.println(ex);
        }

        // Initiate loop
        int counter = 0;
        do {
            
            // Log engine state
            counter++;
            System.out.println("Engine-Controller-Engine: ForkJoinTask monitor iteration = " + counter);
            System.out.println("Engine-Controller-Engine: ForkJoinTask still active");
            System.out.println("Engine-Controll-Engine: Engine State is " + engine.getEngineState());
            
            // Fetch work item data
            List<WorkItem> active, workload;
            active = engine.getActiveTasks();
            workload = engine.getWorkload();
            
            // Inspect state
            System.out.println("Engine-Controller-Engine: Active tasks = " + active.size());
            System.out.println("Engine-Controller-Engine: Queued tasks = " + workload.size());
            if ( active.size() >= 1 ) {
                System.out.println("Engine-Controller-Engine: Example Task");
                System.out.println( "\n" + active.get(0) + "\n");
            }
            
            
            // Inspection complete
            try {
                System.out.println("Engine-Controller-Engine: Inspection complete waiting on next iteration");
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Engine-Controller-Engine: Cannot sleep 10s");
                System.out.println(ex);
            }
            
        } while( !task.isDone() );
        System.out.println("Engine-Controller-Engine: ForkJoinTask has completed" + task.isDone());
    }
    
    
    
    /**
     * Test engine, showcasing the core component interfaces
     */
    public static void testEngine() {
    
        // Initialize broad variables
        WorkItemCollection workload;
        ModelFactory modelFactory = FactoryChain.getFactoryChain();
        TaskGenerator taskGenerator = new TaskGenerator();
        Engine engine = new Engine();
        
        // Fetch random workload
        System.out.println("\n\nFetching Workload for Processing");
        workload = fetchWorkload();
        System.out.println("\nWorkload retrived");
        
        // Feed workload to engine
        System.out.println("\n\nFeeding workload to Engine");
        engine.fetchWorkload(workload);
        System.out.println("\nFeed Complete");
        
        // Consume workload
        System.out.println("\n\nInitiating engine");
        ForkJoinPool pool = engine.getPoolManager().getWorkFlowPool(PoolType.ENGINE);
        ForkJoinTask forkJoinTask = pool.submit(engine);
        waitOnTask(forkJoinTask, engine);
        System.out.println("\n\nProcessing complete. Terminating Engine");
        System.out.println("Engine termination completed = " + engine.terminateEngine());
        
        
        // Display workload
        System.out.println("\n\nDisplaying workload:");
        for( WorkItem task : workload.getData() ) {
            System.out.println(task.toString());
        }
        
        // Diaplaying logs for completed exampled
        showExampleDone(workload.getData());
    }
    
    
    /**
     * Print example done
     * 
     * @param workload 
     */
    public static void showExampleDone(List<WorkItem> workload) {
     
        // Fetch log
        WorkItem item = workload.get(0);
        ItemTask task = item.getWorkload().getWorkload().get(0);
        TaskLogging taskLog = task.getTaskLog();
        
        // Display stdout, and stderr
        System.out.println("\n\nPrinting: stdout");
        for(String i : taskLog.getStdout() ) {
            System.out.println(i);
        }
            
        // Print stderr
        System.out.println("\n\nPrinting: stderr");
        for(String i : taskLog.getStderr() ) {
            System.out.println(i);
        }
    }
    
    
    /**
     * View workload
     */
    public static void testRequestInterface() {
        
        // Fetch
        System.out.println("Creating workload:");
        WorkItemCollection workload = fetchWorkload();
        
        // Display
        System.out.println("\n\nDisplaying workload:");
        for( WorkItem task : workload.getData() ) {
            System.out.println(task.toString());
        }
    }
    
    
    /**
     * Test processing workload from request interface & factory chain
     * @return 
     */
    public static WorkItemCollection fetchWorkload() {
    
        // Make random items
        List<WorkItem> workItems = new ArrayList<>();
        for ( int i = 0; i < 10; i++ ) {
            workItems.add(fetchWorkItem());
        }
        
        // Create worklflow collection
        WorkItemCollection workload = new WorkItemCollection();
        workload.extendData(workItems);
        
        // Return collection
        return workload;
    }
    
    
    /**
     * Test abstract model factory chain
     * 
     * @return WorkItem
     */
    public static WorkItem fetchWorkItem() {
    
        // Fetch factory chain & task generator
        System.out.println("Fetching Factory Chain");
        ModelFactory modelFactory = FactoryChain.getFactoryChain();
        
        // Make ItemTask
        System.out.println("Initializing ItemTask Request");
        ItemTaskRequest itemTaskReq = getItemRequest();
        ItemTask task = (ItemTask) modelFactory.makeObject(ModelType.ITEM_TASK, itemTaskReq);
        System.out.println("\n\nCreation complete. Creating Remainder");
        
        // Make tasks for workload
        List<ItemTask> tasks = new ArrayList<>();
        tasks.add(task);
        int counter = 0;
        while ( counter <= 5 ) {
            System.out.println("Creating ItemTask:\t" + counter);
            itemTaskReq = getItemRequest();
            task = (ItemTask) modelFactory.makeObject(ModelType.ITEM_TASK, itemTaskReq);
            tasks.add(task);
            counter++;
        }
        
        // Make Workload from Request
        System.out.println("Creating Workload from ItemTask-List, Level = " + EngineConstants.WORKLOAD_LEVEL);
        WorkloadRequest workloadRequest = getWorkloadRequest(tasks);
        Workload workload = (Workload) modelFactory.makeObject(ModelType.WORKLOAD, workloadRequest);
        
        // Make work item from workload
        System.out.println("Creating WorkItem from Workload, Level = " + EngineConstants.WORK_ITEM_LEVEL);
        String itemName = "Ping Tests";
        WorkItemRequest workItemRequest = getWorkItemRequest(itemName, workload);
        WorkItem workItem = (WorkItem) modelFactory.makeObject(ModelType.WORK_ITEM, workItemRequest);
        
        
        // Return work item
        return workItem;
    }
    
    
    
    /**
     * Get work item request from item name and workload
     * 
     * @param itemName
     * @param workload
     * @return 
     */
    public static WorkItemRequest getWorkItemRequest(String itemName, Workload workload) {
        
        // Initalize with workload
        WorkItemRequest workItemRequest = new WorkItemRequest();
        ModelAttribute workloadAttribute = new ModelAttribute(workload, KeyType.WORKLOAD, ModelType.WORK_ITEM);
        workItemRequest.setAttributeValue(KeyType.WORKLOAD, workloadAttribute);
        
        // Add item name
        ModelAttribute itemNameAttribute = new ModelAttribute(itemName, KeyType.ITEM_NAME, ModelType.WORK_ITEM);
        workItemRequest.setAttributeValue(KeyType.ITEM_NAME, itemNameAttribute);
        
        // Return request
        return workItemRequest;
    }
    
    
    
    /**
     * Construct workload request from task list
     * 
     * @param tasks
     * @return WorkloadReuest
     */
    public static WorkloadRequest getWorkloadRequest(List<ItemTask> tasks) {
        WorkloadRequest workloadRequest = new WorkloadRequest();
        ModelAttribute workload = new ModelAttribute(tasks, KeyType.TASKS, ModelType.WORKLOAD);
        workloadRequest.setAttributeValue(KeyType.TASKS, workload);
        return workloadRequest;
    }
    
    
    /**
     * Fetch random item task request
     * 
     * @return 
     */
    public static ItemTaskRequest getItemRequest() {
        
        // Get TaskGenerator
        TaskGenerator taskGenerator = new TaskGenerator();
        
        // Create item task request
        ItemTaskRequest itemTaskRequest = new ItemTaskRequest();
        String[] task = taskGenerator.getCmd();
        
        // Set task
        ModelAttribute taskAttribute = new ModelAttribute(task[0], KeyType.TASK, ModelType.ITEM_TASK);
        itemTaskRequest.setAttributeValue(KeyType.TASK, taskAttribute);
        
        // Set task name
        ModelAttribute taskNameAttribute = new ModelAttribute(task[1], KeyType.TASK_NAME, ModelType.ITEM_TASK);
        itemTaskRequest.setAttributeValue(KeyType.TASK_NAME, taskNameAttribute);
        
        // Return result
        return itemTaskRequest;
    }
    
    
    /**
     * Test the pool manager
     * 
     */
    public static void testPoolManager() {
    
        // Configure pool manager
        System.out.println("Configuring the Pool Manager:");
        PoolManager poolManager = PoolManager.getInstance(3);
        System.out.println(poolManager);
        
        // Fetch Single and Nested WorkItems
        System.out.println("\nConfiguring the workload:");
        List<OldWorkItem> singleItems, nestedItems;
        singleItems = fetchWorkItems(true);
        nestedItems = fetchWorkItems(false);
        
        // Configure workflow collection
        System.out.println("\nConsolidating workload into WorkItemCollection:");
        Old_WorkItemCollection workItemCollection = fetchCollection(singleItems);
        workItemCollection.extendData(nestedItems);
        
        // Display current state
        System.out.println("\n\nDisplaying the WorkItemCollection Pre Processing:");
        for(OldWorkItem item: workItemCollection.getData() ) {
            System.out.println(item.toString());
        }
        
        // Pass workload to Item Pool
        System.out.println("\n\nProcessing the WorkItemCollection:");
        ForkJoinPool itemPool = poolManager.getWorkFlowPool(PoolType.ITEM);
        Old_ParallelWorkItems paraTasks = new Old_ParallelWorkItems(workItemCollection.getData());
        itemPool.invoke(paraTasks);
        
        // Display current state
        System.out.println("\n\nDisplaying the WorkItemCollection Post Processing:");
        for(OldWorkItem item: workItemCollection.getData() ) {
            System.out.println(item.toString());
        }
        
        // Close all pools
        System.out.println("\n\nProcessing complete, closing all pools");
        poolManager.closePools();
        System.out.println("\n\nPools close, terminating program... Goodbye :)");
    }
    
    
    /**
     * Fetch a random list of 5 WorkItems for required type
     * 
     * @param areSingle
     * @return List-OldWorkItem
     */
    public static List<OldWorkItem> fetchWorkItems(boolean areSingle) {
    
        // Initialize work item factories
        WorkItemFactory workItemFactory = new WorkItemFactory();
        TaskGenerator taskGenerator = new TaskGenerator();
        List<OldWorkItem> output;
        
        // Create Single Task WorkItems
        if ( areSingle ) {
            
            // Setup single item tasks
            List<OldItemTask> singleTasks = taskGenerator.makeRandTasks(10);
            output = workItemFactory.makeSingeTaskItems(singleTasks);
        }
        
        // Otherwise Nested
        else {
            // Setup nested work items
            Map<Integer, List<OldItemTask> > nestedTasks = new HashMap<>();
            for (Integer i = 0; i < 5; i++) {
                List<OldItemTask> tasks = taskGenerator.makeRandTasks(10);
                nestedTasks.put(i, tasks);
            }
            output = workItemFactory.makeNestedTaskItems(nestedTasks);
        }
        
        // Return output
        return output;
    }
    
    
    /**
     * Print list of work items
     * 
     * @param workItems
     * @param label 
     */
    public static void printWorkItems(List<OldWorkItem> workItems, String label) {
        
        // Display work items
        System.out.println("\nDisplaying '" + label + "' WorkItems:\t");
        for(OldWorkItem workItem: workItems ) {
            System.out.println(workItem);
        }
    }
    
    
    /**
     * Fetch workflow collection
     * 
     * @param workItems
     * @return WorkCollection
     */
    public static Old_WorkItemCollection fetchCollection(List<OldWorkItem> workItems) {
        Old_WorkItemCollection output = new Old_WorkItemCollection();
        output.extendData(workItems);
        return output;
    }
    
    
    /**
     * Process tasks in Old_WorkItemCollection
     * 
     * @param workItemCollection 
     */
    public static void processItems(Old_WorkItemCollection workItemCollection) {
    
        // Process items
        System.out.println("\n\nProcessing Workload:");
        for( OldWorkItem item : workItemCollection.getData() ) {
            if ( item.getType() == ItemType.SINGLE ) {
                System.out.println("Processing Single Item");
                item.handleTask();
            }
            else {
                System.out.println("Processing Nested Item");
                item.handleTasks();
            }
        }
        System.out.println("\n\nWorkItem Data:");
        for(OldWorkItem item: workItemCollection.getData() ) {
            System.out.println(item.toString());
        }
    }
    
    
    /**
     * 
     * Test OldWorkItem interface & collection
     * 
     */
    public static void testWorkItem() {
        
        // Fetch Single and Nested WorkItems
        List<OldWorkItem> singleItems, nestedItems;
        singleItems = fetchWorkItems(true);
        nestedItems = fetchWorkItems(false);
        
        // Print lists
        printWorkItems(singleItems, "Single Items");
        printWorkItems(singleItems, "Nested Items");
        
        // Configure workflow collection
        Old_WorkItemCollection workItemCollection = fetchCollection(singleItems);
        workItemCollection.extendData(nestedItems);
        
        // Process collection
        processItems(workItemCollection);
    }

    
    /**
     * Run task and return output
     * 
     * proc.info():
     *  - totalCpuDuration() Optional<Duration>
     *  - arguments()
     *  - commandLine()
     * 
     * @param task
     * @return Map of Stdout and Stderr logs
     */
    public static Map<String, String[]> runTask(String task) {
        
        // Initialize output
        Map<String, String[]> output = new HashMap<>();
        
        
        // Run task
        try {
            
            // Run process and fetch log
            Process proc = Runtime.getRuntime().exec(task);
            String[] stdout = getTaskLog(proc.getInputStream());
            String[] stderr = getTaskLog(proc.getErrorStream());
            System.out.println("Process ID = " + proc.pid());
            System.out.println("Exit code = " + proc.exitValue());
            System.out.println("Cpu duration = " + proc.info().totalCpuDuration().get().toMillis());
            System.out.println("User = " + proc.info().user().get());
            
            // Add to log
            output.put("stdout", stdout);
            output.put("stderr", stderr);
            
        } catch (IOException ex) {
            Logger.getLogger(WorkflowEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }


    /**
     * Fetch stdout log from reader
     * 
     * @param inputStream
     * @return String[]
     */
    public static String[] getTaskLog(InputStream inputStream) {
        
        // Set needed variables
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> log = reader.lines().toList();
        String[] output = new String[ log.size() ];
        
        // Append each to output
        for(int i = 0; i < log.size(); i++ ) {
            output[i] = log.get(i);
        }
        
        // Return
        return output;
    }
    
    
    /**
     * 
     * Test out task parallelization
     * 
     */
    public static void testParaTasks() {
        
        // Run two in parallel
        System.out.println("\n\nExecuting Parallel Tasks");
        List<String> tasks = new ArrayList<>();
        tasks.add("ping google.com");
        tasks.add("ping amazon.com");
        for(String paraTask : tasks) {
            System.out.println(paraTask);
        }
        
        // Configure pool
        ForkJoinPool pool = new ForkJoinPool(2);
        ParallelTask paraTasks = new ParallelTask(tasks);
        pool.invoke(paraTasks);
        System.out.println("\n\nProcessing Tasks Complete\nTask Log Count: " + paraTasks.getTaskLogs().size());
        for(Map<String, String[]> paraTaskLog: paraTasks.getTaskLogs() ) {
            
            // Print stdout
            System.out.println("\n\nPrinting: stdout");
            for(String i : paraTaskLog.get("stdout")) {
                System.out.println(i);
            }
            
            // Print stdout
            System.out.println("\n\nPrinting: stderr");
            for(String i : paraTaskLog.get("stderr")) {
                System.out.println(i);
            }
        }

        // Shutdown pool
        System.out.println("\n\nShutdown pool");
        pool.shutdown();
        System.out.println("Pool has shutdown = " + pool.isShutdown());
    }
}
