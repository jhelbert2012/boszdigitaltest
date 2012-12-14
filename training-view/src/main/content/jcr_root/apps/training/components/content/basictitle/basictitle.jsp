<%--

  Basic Title Component component.

  Basic Title Component

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" %><%
%><%
    String title = currentPage.getTitle();
    if(properties.get("title",String.class)!=null)
    	title = properties.get("title",String.class);
    
    String subtitle = currentPage.getProperties().get("subtitle",String.class);
%>
<div class="title-container">
    <div class="page-title">
        <h1><%= title %></h1>
    </div>
<%
    if (subtitle != null && !subtitle.equals("")){
    %>
    <div class="page-title">
        <h2><%= subtitle %></h2>
    </div>
    <% }
    %>
</div>