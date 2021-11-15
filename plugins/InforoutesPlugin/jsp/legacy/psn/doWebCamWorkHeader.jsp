  <%@page import="com.jalios.jcms.Channel"%>
<% if (Channel.getChannel().getCurrentJcmsContext().getWorkspace().getId().equals(Channel.getChannel().getProperty("plugin.routes.general.ws.direction.id"))) { 	
	Boolean webcamAdminMenu  = request.getAttribute("webcamAdminMenu") != null;
%>
 
 <li id="webcamAdminMenu" class="menuText <% if (webcamAdminMenu){%> active<%}%>"> 
      <a href="plugins/InforoutesPlugin/jsp/psn/adminWebCam.jsp">Webcam</a>
</li>
<%} %>
    