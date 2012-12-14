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
import javax.jcr.RepositoryException;

import java.util.Calendar;
import java.util.Iterator;

/**
 * Sample workflow process that archives child pages.
 */

@Component(immediate=true, label="Delete pages")
@Properties({
	@Property(name=Constants.SERVICE_DESCRIPTION, value="Delete pages"),
	@Property(name=Constants.SERVICE_VENDOR, value="Boszdigital"),
	@Property(name="process.label", value="Delete pages")
})
@Service

public class DeletePages implements WorkflowProcess {
    
    private static final String TYPE_JCR_PATH = "JCR_PATH";
    
    private static final Logger log = LoggerFactory.getLogger(ArchivePages.class);

    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) throws WorkflowException {
        WorkflowData workflowData = item.getWorkflowData();
        log.info("==================== <> ======================");
        Iterator props = args.entrySet().iterator();
        String pathToArchive = args.get("PROCESS_ARGS",String.class);
        if(pathToArchive!=null)
            if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
                String path = workflowData.getPayload().toString();
                try {
                    Node node = (Node) session.getSession().getItem(path);
                    log.info("Path: " + path);
                    if (node != null) {
                        if(node.hasNodes()){
                            Iterator<Node> nodes = node.getNodes();
                            Node archiveNode = (Node) session.getSession().getItem(pathToArchive);
                            while(nodes.hasNext()){
                                Node act = (Node) nodes.next();
                                log.info("Child Path: " + act.getPath());
                                if (!act.getName().equals("jcr:content")){
                                    act.remove();
                                }
                            }
                        }
                    }
                } catch (RepositoryException e) {
                    throw new WorkflowException(e.getMessage(), e);
                }
            }
    }
}
