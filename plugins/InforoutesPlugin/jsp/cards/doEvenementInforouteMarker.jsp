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

HashMap<String, HashMap<String, List<EvenementDTO>>> eventLies = (HashMap<String, HashMap<String, List<EvenementDTO>>>) request.getAttribute("eventLien");
HashMap<String, List<EvenementDTO>> eventNature = eventLies.get(itEventDto.getSnm());


int nbEventLie = 0;
for(String itNature : eventNature.keySet()) { 
  // Retire l'évènement courant des evenements liés
  List<EvenementDTO> itEventList = new ArrayList<EvenementDTO>();
  itEventList.addAll(eventNature.get(itNature));  
  itEventList.remove(itEventDto);
  // Calcul du nombre d'evenement lies
  if(itEventList.size() > 0) {    
    nbEventLie += itEventList.size();
  }
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
            <jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteInner.jsp" />
            <jalios:if predicate="<%= nbEventLie > 0 %>">
            <p class="ds44-docListElem ds44-mt-std"><i class="icon icon-plus ds44-docListIco" aria-hidden="true"></i><span><%= glp("jcmsplugin.inforoutes.evenement-lies") %> :</span> <%= nbEventLie %></p>
            </jalios:if>   
        </div>
    </div>
