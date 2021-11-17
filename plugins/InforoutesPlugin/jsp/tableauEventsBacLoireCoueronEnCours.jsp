<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

String paramNameTableau = channel.getProperty("jcmsplugin.inforoutes.api.params.bac-loire-coueron");
List<EvenementDTO> traficEvents = InforoutesApiRequestManager.getTraficEvents(paramNameTableau);
traficEvents = InforoutesUtils.filterEvenementDtoEnCours(traficEvents);
%>

<%@ include file="./includes/displayTableauEvents.jspf" %>