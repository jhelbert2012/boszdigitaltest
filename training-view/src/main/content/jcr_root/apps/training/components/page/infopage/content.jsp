<%@include file="/libs/foundation/global.jsp" %>
<%@page import="com.day.cq.wcm.api.WCMMode" %>
<div class="content-container">
<%
    if (WCMMode.fromRequest(request) == WCMMode.EDIT && WCMMode.fromRequest(request) != WCMMode.DISABLED) {
        %> <cq:include path="content/list" resourceType="training/components/content/listproperties" /> <%
    }
%>

<cq:include path="content/par" resourceType="foundation/components/parsys" />
</div>