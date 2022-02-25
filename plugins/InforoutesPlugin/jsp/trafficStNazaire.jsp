<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.TraficParametersDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnCalendrierDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnStatutDTO"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%

PsnStatutDTO psnStatut = InforoutesApiRequestManager.getPsnStatut();
TraficParametersDTO traficParams = InforoutesApiRequestManager.getTraficParameters();
String lblCityA = glp("jcmsplugin.inforoutes.stnazaire");
String lblCityB = glp("jcmsplugin.inforoutes.stbrevin");
%>
<h2 class="h2-like h2-like--mobileSize"><%= glp("jcmsplugin.inforoutes.trafic") %></h2>

<jalios:select>
    <jalios:if predicate="<%= Util.isEmpty(traficParams) || Util.notEmpty(psnStatut.getError()) %>">
        <p><%= glp("jcmsplugin.inforoutes.erreur.trafic") %></p>
    </jalios:if>
    <jalios:default>
        <%@ include file="./includes/displayTraffic.jspf" %>
    </jalios:default>
</jalios:select>
