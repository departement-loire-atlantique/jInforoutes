<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

// String paramNameTableau = channel.getProperty("jcmsplugin.inforoute.api.params.pt-st-nazaire");
String paramNameTableau = "Tous";
List<EvenementDTO> traficEvents = InforoutesApiRequestManager.getTraficEvents(paramNameTableau);
%>

<%@ include file="./includes/displayTableauEvents.jspf" %>