<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

String paramNameTableau = channel.getProperty("jcmsplugin.inforoutes.api.params.pt-st-nazaire");
List<EvenementDTO> traficEvents = InforoutesApiRequestManager.getTraficEvents(paramNameTableau);
%>

<%@ include file="./includes/displayTableauEvents.jspf" %>