<%@include file="/libs/foundation/global.jsp" %>
<%@ page import="com.day.cq.commons.Doctype,
                   org.apache.commons.lang.StringEscapeUtils" %>
<cq:setContentBundle/>
<div width="700px;">
    <div>
<p><fmt:message key="label_date"/><%= currentNode.getParent().getProperty("jcr:createdBy").getString() %>
<fmt:message key="label_createdby"/><%= currentNode.getParent().getProperty("jcr:created").getDate().getTime() %></p>
</div>
<div style="float:left;width:200px">
<cq:include path="content/mainimage" resourceType="foundation/components/image" />
</div>
<div style="width:300px;float:left">
<fmt:message key="label_summary"/>
<cq:include path="content/summary" resourceType="foundation/components/text" />
</div>
<br/>
<div style="clear: both;">
<cq:include path="content/par" resourceType="/libs/foundation/components/parsys" />
</div>
</div>