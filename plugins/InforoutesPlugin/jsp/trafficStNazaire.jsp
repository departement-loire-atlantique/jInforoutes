<%@page import="fr.cg44.plugin.inforoutes.dto.TraficParametersDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnCalendrierDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnStatutDTO"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

PsnStatutDTO psnStatut = InforoutesApiRequestManager.getPsnStatut();
TraficParametersDTO traficParams = InforoutesApiRequestManager.getTraficParameters();
String lblCityA = glp("jcmsplugin.inforoute.stnazaire");
String lblCityB = glp("jcmsplugin.inforoute.stbrevin");
%>

<%@ include file="./includes/displayTraffic.jspf" %>