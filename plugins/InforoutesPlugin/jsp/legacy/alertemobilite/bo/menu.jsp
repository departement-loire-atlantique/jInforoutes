<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@page import="com.jalios.jcms.JcmsUtil"%>
<%@page import="com.jalios.jcms.Channel"%>
<% 
if (Channel.getChannel().getCurrentJcmsContext().getWorkspace().getId().equals(Channel.getChannel().getProperty("fr.cg44.plugin.alertemobilite.workspace.infotrafic.id"))) { 
	Boolean usersAdminMenu  = request.getAttribute("usersAdminMenu") != null;
%>

<li id="usersAdminMenu" class="menuText <% if (usersAdminMenu){%> active<%}%>"> 
    <a href='plugins/InforoutesPlugin/jsp/alertemobilite/bo/routeEvenementAlerteHistorique.jsp'><img alt="" class="icon" src="images/jalios/assets/16x16/list.gif"><%= JcmsUtil.glp(userLang, "jcmsplugin.alertemobiliteplugin.administration.link") %></a>
</li>
<% } %>