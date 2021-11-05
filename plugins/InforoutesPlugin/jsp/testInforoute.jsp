<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.PsnStatutDTO"%>
<%@ include file='/jcore/doInitPage.jspf' %>

<h1>Test requêtes Inforoutes</h1>

<h2>getPsnStatut</h2>
<%

PsnStatutDTO psnStatut = InforoutesApiRequestManager.getPsnStatut();

%>
<jalios:select>
	<jalios:if predicate="<%= Util.isEmpty(psnStatut) %>">
	   <strong>Anomalie -> voir les logs</strong>
	</jalios:if>
	<jalios:default>
	   <p><%= psnStatut.getCode_current_mode() %> / <%= psnStatut.getLib_current_mode() %> / <%= psnStatut.getTime_certe() %> / <%= psnStatut.getTime_st_brevin() %> / <%= psnStatut.getNext_mode() %></p>
	</jalios:default>
</jalios:select>