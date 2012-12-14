<%@include file="/libs/foundation/global.jsp"%>
<%@page import="java.util.Iterator,
                 com.day.cq.wcm.api.Page,
                 javax.jcr.Property,
                 java.util.ArrayList,
                 java.util.Set"%>

<p>Properties:</p>
<ul>
<%
Set<String> props = currentPage.getProperties().keySet(); 
for (String key : props ) { 
    String prop = currentPage.getProperties().get(key).toString();
    if(prop.length() < 50){
    %><li><%= key %> - <%= prop %></li><%
    } else {
    %><li><%= key %> - <%= prop.substring(0,40) %>(<a href="http://localhost:4502<%= currentPage.getPath()+"/jcr:content/"+key%>">...</a>)</li><%
    } }
%>
</ul>
<hr>