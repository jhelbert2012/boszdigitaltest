<%--

  (Training) Basic Content Page Component component.

  (Training) Basic Content Page Component + properties

--%>
<%@include file="/libs/foundation/global.jsp"%>
<%@page session="false" %>
<html>
    <head>
        <title>CQ5 Basic Content | <%= currentPage.getTitle() %> (Narrow)</title>
        <cq:include script="/libs/wcm/core/components/init/init.jsp" /> 
        <%currentDesign.writeCssIncludes(pageContext); %>
        <cq:includeClientLib categories="traininglib"/>
        <script type="text/javascript">
            // This function is called on page ready. It bootstraps the div tag with the jQuery UI dialog class.
            jQuery(function ($) {
                $("table").click(function () {
                      $(this).hide("explode",{ pieces: 48 }, 1000);
                })})

       </script>
    </head>
    <body class="narrow">
        <!--
        -----------------------------------------------
        using the default pbjects loaded
        by the <cq:defineObjects/> tag on global.jsp 
        -----------------------------------------------
        -->
        <!-- currentPage is the current CQ WCM page object, 
        type com.day.cq.wcm.api.Page interface -->
        <h1><%= currentPage.getTitle() %> | (Narrow)</h1>
        <hr/>
        <table>
            <tr class="table-title">
                <th colspan=2><h3>Page builtin properties</h3></th>
            </tr>
            <tr class="table-title">
                <th>Properties</th><th>Value</th>
            </tr>
            <tr class="odd-line"><td>Page Name (getPageTitle)</td><td><%= currentPage.getPageTitle() %></td></tr>
            <tr><td>Page Name (getName)</td><td><%= currentPage.getName() %></td></tr>
            <tr class="odd-line"><td>Page Description (getDescription)</td><td><%= currentPage.getDescription() %></td></tr>
            <tr><td>Page Path(getPath)</td><td><%= currentPage.getPath() %></td></tr>
            <tr class="odd-line"><td>Page Depth(getDepth)</td><td><%= currentPage.getDepth() %></td></tr>
        </table>
        <br/>       
        <table>
            <tr class="table-title">
               <th colspan=2><h3>Page properties (properties object)</h3></th>
            </tr>
            <tr class="odd-line"><td>Page Name ("jcr:title")</td><td><%= properties.get("jcr:title",String.class) %></td></tr>
            <tr><td>Page Name ("pageTitle")</td><td><%= properties.get("pageTitle",String.class) %></td></tr>
            <tr class="odd-line"><td>Page Name ("title")</td><td><%= properties.get("title",String.class) %></td></tr>
            <tr><td>Page Name ("name")</td><td><%= properties.get("name",String.class) %></td></tr>   
            <tr class="odd-line"><td>Page Description ("jcr:description")</td><td><%= properties.get("jcr:description",String.class) %></td></tr>
            <tr><td>Page Description ("description")</td><td><%= properties.get("description",String.class) %></td></tr>
            <tr class="odd-line"><td>Page Path("path")</td><td><%= properties.get("path",String.class) %></td></tr>
            <tr><td>Page Depth("depth")</td><td><%= properties.get("depth",String.class) %></td></tr>   
        </table>       
    </body>
</html>