<%--

  Greetings OSGi component.

  

--%><%
%><%@include file="/libs/foundation/global.jsp"%><%
%><%@page session="false" import="com.boszdigital.crxde.training.web.HelloWorld,
com.boszdigital.training.services.*" %><%
%><%
  HelloWorld greeting = new HelloWorld();
  GoodbyeWorldService bye = (GoodbyeWorldService)sling.getService(GoodbyeWorldService.class);
%>
<%= greeting.getMessage("Helbert") %>
<%= bye.getMessage("Helbert") %>