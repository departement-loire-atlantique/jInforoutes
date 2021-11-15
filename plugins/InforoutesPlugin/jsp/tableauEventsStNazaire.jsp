<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

List<EvenementDTO> traficParams = InforoutesApiRequestManager.getTraficEvents(channel.getProperty());
%>

<%@ include file="./includes/displayTraffic.jspf" %>