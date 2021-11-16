<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

String paramNameTableau = channel.getProperty("jcmsplugin.inforoute.api.params.bac-loire-basse-indre");
List<EvenementDTO> traficEvents = InforoutesApiRequestManager.getTraficEvents(paramNameTableau);
traficEvents = InforoutesUtils.filterEvenementDtoEnCours(traficEvents);
%>

<%@ include file="./includes/displayTableauEvents.jspf" %>