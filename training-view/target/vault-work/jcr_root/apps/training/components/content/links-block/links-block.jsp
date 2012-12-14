<%--

  Links block component.

  Links block component

--%>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<cq:defineObjects />
<cq:includeClientLib categories="training.customwidgets" />
<%
// dialog properties
String title = properties.get("title/text",String.class);
String links[] = properties.get("links/links",String[].class);
// styled used to display the anchor tag
String style = properties.get("links/style",String.class);

%>
            <nav class="footer-block">
                <h4><%= title %></h4>
                <ul>
                <% if (links!= null) {
                    for (String link : links) {
                        String label = null;
                        String path = null;
                        String parts[] = link.split("\t");
                        if(parts.length > 1){
                            label = parts[0];
                            path = parts[1];
                            if(!path.matches("^http[s]*://")){
                                path = path + ".html";
                            }
                            if (style!=null){                         
                %>
                <li><a class="<%= style %>" href="<%= path %>"><%= label %></a></li>
                <%} else { %>
                <li><a href="<%= path %>"><%= label %></a></li>
                <%} } } } 
                %>
                </ul>
                <cq:include path="par" resourceType="foundation/components/parsys" />
            </nav>