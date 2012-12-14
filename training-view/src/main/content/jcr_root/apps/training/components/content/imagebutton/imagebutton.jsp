<%@ page import="com.day.cq.wcm.foundation.Image,
    com.day.cq.wcm.api.WCMMode,
    com.day.cq.wcm.mobile.api.device.DeviceGroup"%>
<%@include file="/libs/foundation/global.jsp"%><%
final DeviceGroup deviceGroup = slingRequest.adaptTo(DeviceGroup.class);
String deviceGroupName = "";
if (null != deviceGroup) {
    deviceGroupName = deviceGroup.getName();
}
String suffix = currentDesign.equals(resourceDesign) ? "" : "/" + currentDesign.getId();
//add mod timestamp to avoid client-side caching of updated images
long tstamp = properties.get("jcr:lastModified", properties.get("jcr:created", System.currentTimeMillis()));
suffix += "/" + tstamp + ".png";

Image image = new Image(resource,"image");
image.setSelector(".img");
String imageHref = image == null ? "" : image.getFileReference();
String imageId = properties.get("image/id", "");

if(null != imageId && 0 < imageId.length()){ %>
    <img alt="Action Button" src="<%=resource.getPath() %>.button.png<%= suffix %>" id="<%= imageId %>" />
<% } else{ %>
   <img alt="Action Button" src="<%=resource.getPath() %>.button.png<%= suffix %>" />
<% } %>
 