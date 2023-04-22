/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Factory.Factories;

/**
 *
 * @author kenna
 */
public class FactoryChain {
    
    public static ModelFactory getFactoryChain(){
        
        // Instantiate factories
        System.out.println("\nSanity Checking Factory Chain Construction:");
        ModelFactory workItemFactory = new WorkItemFactory();
        System.out.println("WorkItem Factory: " + workItemFactory.activeFactory());
        ModelFactory itemTaskFactory = new ItemTaskFactory();
        System.out.println("ItemTask Factory: " + itemTaskFactory.activeFactory());
        ModelFactory workloadFactory = new WorkloadFactory();
        System.out.println("Workload Factory: " + workloadFactory.activeFactory());
        
        // Set next factory
        System.out.println("\nChecking Chaining:");
        workItemFactory.setNextFactory(itemTaskFactory, ModelType.ITEM_TASK);
        System.out.println("WorkItem Factory: " + workItemFactory.nextFactory());
        itemTaskFactory.setNextFactory(workloadFactory, ModelType.WORKLOAD);
        System.out.println("ItemTask Factory: " + itemTaskFactory.nextFactory());
        workloadFactory.setNextFactory(workItemFactory, ModelType.WORK_ITEM);
        System.out.println("Workload Factory: " + workloadFactory.nextFactory());
        
        
        // Return configured factory
        System.out.println("\nFactory Chain Sanity Check Complete\n\n");
        return workItemFactory;
    }
}
