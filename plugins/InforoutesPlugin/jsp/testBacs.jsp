<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.BacsHoraires"%>
<%@page import="fr.cg44.plugin.inforoutes.InforoutesUtils"%>

<%@ include file='/jcore/doInitPage.jspf' %>

<h1>Test requêtes Inforoutes</h1>

<h2>Horaires des bacs</h2>
<%
BacsHoraires bacsHoraires = InforoutesApiRequestManager.getBacsHoraires();
%>

<jalios:select>
    <jalios:if predicate="<%= Util.isEmpty(bacsHoraires) %>">
       <strong>Anomalie -> voir les logs</strong>
    </jalios:if>
    <jalios:default>
       <p><%= bacsHoraires.getBacsHoraires().getLiaison1().getFrom() %></p>
    </jalios:default>
</jalios:select>


