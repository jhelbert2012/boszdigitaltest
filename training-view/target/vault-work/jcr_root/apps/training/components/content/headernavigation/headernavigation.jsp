<%--

  Header Navigation Component component.

  Header Navigation Component

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %>
<%@ page import="java.util.Iterator, 
                 com.day.cq.wcm.api.PageFilter, 
                 com.day.cq.wcm.api.Page" %>
<% 
// get starting point of navigation 
Page navRootPage = null;
if(currentStyle.get("initialPath",String.class)!=null){
    navRootPage = currentPage.getPageManager().getPage(currentStyle.get("initialPath",String.class));
} else if (properties.get("initialPath",String.class)!=null){
    navRootPage = currentPage.getPageManager().getPage(properties.get("initialPath",String.class));
}

if (navRootPage != null) { 
    Iterator<Page> children = navRootPage.listChildren(new PageFilter(request)); 
    %> <ul> <%
    while (children.hasNext()) { 
        Page child = children.next(); 
        if(!child.getPath().equals(currentPage.getPath())){
        %><li><a href="<%= child.getPath() %>.html"><%=child.getTitle() %></a></li>
        <% } }
    %> </ul> <%
} else { %>
    <p>Path...</p>
    <% } %>