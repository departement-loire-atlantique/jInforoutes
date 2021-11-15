<%@ page contentType="text/plain; charset=UTF-8"%>
 <%@page import="com.jalios.jcms.Channel"%>
<% 
if (Channel.getChannel().getCurrentJcmsContext().getWorkspace().getId().equals(Channel.getChannel().getProperty("plugin.routes.general.ws.direction.id"))) { 
	Boolean usersAdminMenu  = request.getAttribute("usersAdminMenu") != null;
%>

<li id="usersAdminMenu" class="menuText <% if (usersAdminMenu){%> active<%}%>"> 
    <a href="plugins/InforoutesPlugin/jsp/infoRoutes/docPCRoute.jsp"><img alt="" class="icon" src="images/jalios/assets/16x16/info.gif">Doc contribution</a>
</li>
<% } %>