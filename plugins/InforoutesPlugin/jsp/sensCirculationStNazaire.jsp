<%@page import="fr.cg44.plugin.inforoutes.dto.PsnCalendrierDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnStatutDTO"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

PsnStatutDTO psnStatut = InforoutesApiRequestManager.getPsnStatut();

%>

<%@ include file="./includes/displaySensCirculation.jspf" %>