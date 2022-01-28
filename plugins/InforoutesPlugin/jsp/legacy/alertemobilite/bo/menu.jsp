<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@page import="com.jalios.jcms.JcmsUtil"%>
<%@page import="com.jalios.jcms.Channel"%>
<% 
if (Channel.getChannel().getCurrentJcmsContext().getWorkspace().getId().equals(Channel.getChannel().getProperty("fr.cg44.plugin.alertemobilite.workspace.infotrafic.id"))) { 
	
  Boolean usersAdminMenu  = request.getAttribute("usersAdminMenu") != null;
  Boolean isActive = request.getAttribute("editHistoriqueAlertesWAMenu") != null;
%>


<div class="app-sidebar-section" id="editHistoriqueAlertesWAMenu">
  <div class='app-sidebar-section-title <%= isActive ? "active" : "" %> '><a href="plugins/InforoutesPlugin/jsp/legacy/alertemobilite/bo/routeEvenementAlerteHistorique.jsp" class=""><%= JcmsUtil.glp(userLang, "jcmsplugin.alertemobiliteplugin.administration.link") %></a></div>
</div>


<% } %>


