<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnCalendrierDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnStatutDTO"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

PsnStatutDTO psnStatut = InforoutesApiRequestManager.getPsnStatut();
request.setAttribute("enforcedDateFormat", channel.getProperty("jcmsplugin.inforoutes.pattern.psn.jsondate"));

%>
<jalios:select>
	<jalios:if predicate="<%= Util.isEmpty(psnStatut) %>">
	   <p><%= glp("jcmsplugin.inforoutes.erreur.senscirculation") %></p>
	</jalios:if>
	<jalios:default>
	   <%@ include file="./includes/displaySensCirculation.jspf" %>
	</jalios:default>
</jalios:select>

<%

request.removeAttribute("enforcedDateFormat");

%>