<%@ include file='/jcore/doInitPage.jspf' %>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="ds" tagdir="/WEB-INF/tags"%>
<%@ include file='/jcore/media/mediaTemplateInit.jspf' %><%
%><%

EvenementDTO itEventDto = null;

try {
    itEventDto = Util.notEmpty(request.getAttribute("itEventDTO")) ? (EvenementDTO) request.getAttribute("itEventDTO") : null;
	
	if (Util.isEmpty(itEventDto)) {
	  return;
	}
} catch (Exception e) {
    logger.warn("Erreur sur marker EvenementDTO Inforoutes -> le contenu " + request.getAttribute("itEventDTO") + " n'est pas un EvenementDTO.");
    return;
}

/* 
TODO : sélection du CSS de l'icône selon la valeur de "Nature"
Option "Accident" -> icon icon-accident ds44-icoInfoRoutes redFlag
Option "Attention" -> icon icon-attention ds44-icoInfoRoutes orangeFlag

Lien entre les champs et les données affichées
cardTitle -> Ligne 1
marker -> Ligne 2 / Ligne 3 (optionnel)
date -> Ligne 4 / Ligne 5 (optionnel)
tag -> Ligne 6 (optionnel)
*/

%>

   <div class="ds44-flex-container">
        <div class="ds44-card__section--horizontal ds44-flex-valign-center ds44-flex-align-center">
            <p role="heading" aria-level="2" class="ds44-card__title"><%= itEventDto.getLigne1() %></p>
            <p class="ds44-docListElem ds44-mt-std"><i class="icon icon-marker ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.position") %> :</span>
            <%= itEventDto.getLigne2() %><jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne3()) %>"><br><%= itEventDto.getLigne3() %></jalios:if>
            </p>
            <p class="ds44-docListElem ds44-mt-std"><i class="icon icon-date ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.duree") %> :</span>
            <%= itEventDto.getLigne4() %><jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne5()) %>"><br><%= itEventDto.getLigne5() %></jalios:if>
            </p>
            <jalios:if predicate="<%= Util.notEmpty(itEventDto.getLigne6()) %>">
            <p class="ds44-docListElem ds44-mt-std"><i class="icon icon-tag ds44-docListIco" aria-hidden="true"></i><span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.typeintervention") %> :</span> <%= itEventDto.getLigne6() %></p>
            </jalios:if>
        </div>
    </div>
