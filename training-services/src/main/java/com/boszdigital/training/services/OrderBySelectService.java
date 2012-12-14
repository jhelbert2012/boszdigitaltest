package com.boszdigital.training.services;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Sample workflow process that archives child pages.
 */

@Component(immediate=true, label="Order By Select")
@Properties({
	@Property(name=Constants.SERVICE_DESCRIPTION, value="Order By Select"),
	@Property(name=Constants.SERVICE_VENDOR, value="Boszdigital"),
	@Property(name="process.label", value="Order By Select")
})
@Service

public class OrderBySelectService implements WorkflowProcess {
    
    private static final String TYPE_JCR_PATH = "JCR_PATH";
    
    private static final Logger log = LoggerFactory.getLogger(OrderBySelectService.class);

    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        WorkflowData workflowData = item.getWorkflowData();
        log.info("==================== <> ======================");
        Iterator props = args.entrySet().iterator();
        String pathToArchive = args.get("PROCESS_ARGS",String.class);
        if(pathToArchive!=null)
            if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
                String path = workflowData.getPayload().toString();
                try {
                    Node node = (Node) session.getSession().getItem(path + "/jcr:content");
                    
                    String orderBy = node.getProperty("orderby").getString();
                    log.info("----------orderby: " + orderBy);
                    Node nodeToOrder = (Node) session.getSession().getItem(pathToArchive);
                    Iterator<Node> iterator = nodeToOrder.getNodes();
                    List<Node> nodes = copyIterator(iterator);

                    Collections.sort(nodes, new NodeComparator(orderBy));
                  
                    for(Node orderNode: nodes){
                        session.getSession().getWorkspace().move(pathToArchive + "/" + orderNode.getName(), pathToArchive + "/" +  orderNode.getName());
                    }

                } catch (RepositoryException e) {
                    throw new WorkflowException(e.getMessage(), e);
                }
            }
            
    }
    
    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<T>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }
    
    class NodeComparator implements Comparator{
    	String orderBy;
		public NodeComparator(String orderBy){
			this.orderBy = orderBy;
		}
		
    	@Override
		public int compare(Object arg0, Object arg1) {
    		Node node0 = (Node)arg0;
    		Node node1 = (Node)arg1;
         	try {
        		if(orderBy.equals("numberOfTracks")){
        			
        			String title = node0.getProperty("title").getString();
        			long length0 = 0;
        			long length1 = 0;
        			
        			if(node0.getProperty("tracklist").isMultiple()){
        				length0 = node0.getProperty("tracklist").getValues().length;
        			}
        			else{
        				length0 = node0.getProperty("tracklist").getString().isEmpty() ? 0 : 1;
        			}
        			
        			if(node1.getProperty("tracklist").isMultiple()){
        				
        				length1 = node1.getProperty("tracklist").getValues().length;
        			}
        			else{
        				length1 = node1.getProperty("tracklist").getString().isEmpty() ? 0 : 1;
        			}
        			
        			log.info(length0 + "," + length1);

        			return (length0 > length1 ? -1: (length0 == length1? 0: 1));
	            }
	            else if(orderBy.equals("title")){
	            	String title0 = node0.getProperty("title").getString();
	            	String title1 = node1.getProperty("title").getString();
	            	return title0.compareTo(title1);
	            } 
	            else if(orderBy.equals("gender")){
	            	String gender0 = node0.getProperty("gender").getString();
	            	String gender1 = node1.getProperty("gender").getString();
	            	return gender0.compareTo(gender1);	            	
	            }
				} catch (PathNotFoundException e) {
					// TODO Auto-generated catch block
					log.error("path not found",e);
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					log.error("repository exception",e);
				}

			return 0;
		}
    	
    }
}
