<%@include file="/libs/foundation/global.jsp"%>
<cq:setContentBundle/>
<%@page import="java.util.Date" %>
<%
int levelDiff = currentNode.getDepth() - currentPage.getDepth();
String user = currentNode.getNode("../../../../" + currentPage.getName()).getProperty("jcr:createdBy").getString(); 
Date date = currentNode.getNode("../../../../" + currentPage.getName()).getProperty("jcr:created").getDate().getTime();
%>
<div>
    <fmt:message key="today_is"/><%= date.toLocaleString() %> <fmt:message key="this_article_was_created_by" /><%= user %>
</div>
<hr>
<div>
    <div>
        <cq:include path="mainimage" resourceType="foundation/components/image"/>
        <div>
            <fmt:message key="resume"/>
            <cq:include path="resume" resourceType="foundation/components/text"/>
        </div>
    </div>
</div>