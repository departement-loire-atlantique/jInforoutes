<%@page import="fr.cg44.plugin.inforoutes.InforoutesUtils"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="ds" tagdir="/WEB-INF/tags"%><%
%><%@ include file='/jcore/doInitPage.jspf' %><%
%><%

EvenementDTO itEventDto = null;

try {
    itEventDto = Util.notEmpty(request.getAttribute("itEventDTO")) ? (EvenementDTO) request.getAttribute("itEventDTO") : null;
	
	if (Util.isEmpty(itEventDto)) {
	  return;
	}
} catch (Exception e) {
    logger.warn("Erreur sur card EvenementDTO Inforoutes -> le contenu " + request.getAttribute("itEventDTO") + " n'est pas un EvenementDTO.");
    return;
}
%>

<p class="ds44-docListElem ds44-mt-std"><i class="icon icon-marker ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.position") %> :</span>
<%= itEventDto.getLigne2() %><jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne3()) %>"><br><%= itEventDto.getLigne3() %></jalios:if>
</p>
<p class="ds44-docListElem ds44-mt-std"><i class="icon icon-date ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.duree") %> :</span>
<%= itEventDto.getLigne4() %><jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne5()) %>"><br><%= itEventDto.getLigne5() %></jalios:if>
</p>
<jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne6()) %>">
<p class="ds44-docListElem ds44-mt-std"><i class="icon icon-tag ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.typeintervention") %> :</span> <%= itEventDto.getLigne6() %></p>
</jalios:if>
            
