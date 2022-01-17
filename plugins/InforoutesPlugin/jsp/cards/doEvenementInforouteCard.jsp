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

HashMap<String, HashMap<String, List<EvenementDTO>>> eventLies = (HashMap<String, HashMap<String, List<EvenementDTO>>>) request.getAttribute("eventLien");
HashMap<String, List<EvenementDTO>> eventNature = eventLies.get(itEventDto.getSnm());

String eventLiesText = ""; 
int cpt = 0;
for(String itNature : eventNature.keySet()) { 
  // Retire l'évènement courant des evenements liés
  List<EvenementDTO> itEventList = new ArrayList<EvenementDTO>();
  itEventList.addAll(eventNature.get(itNature));  
  itEventList.remove(itEventDto);
  // Création du libellé
  if(itEventList.size() > 0) {    
    if(cpt>0) {
      eventLiesText += ", ";
    }    
    eventLiesText += itEventList.size() + " " + itNature.toLowerCase() + (itEventList.size() > 1 ? "s" : "");
    cpt++;
  }
}



String cssCard = Util.notEmpty(request.getAttribute("cssCard")) ? request.getAttribute("cssCard").toString() : "";

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

<section class='ds44-card ds44-card--horizontal <%= cssCard %>'>
   <div class="ds44-card__section">
        <div class="ds44-innerBoxContainer">
            <p role="heading" aria-level="2" class="h4-like ds44-cardTitle"><i class="icon ds44-icoInfoRoutes <%= InforoutesUtils.getClasseCssNatureEvt(itEventDto.getNature()) %>" aria-hidden="true"></i><a href="#" class="ds44-card__globalLink"><%= itEventDto.getLigne1() %></a></p>
            <jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteInner.jsp" />
            <jalios:if predicate="<%= Util.notEmpty(eventLiesText) %>">
            <p class="ds44-docListElem ds44-mt-std"><i class="icon icon-plus ds44-docListIco" aria-hidden="true"></i><span><%= glp("jcmsplugin.inforoutes.evenement-lies") %> :</span> <%= eventLiesText %></p>
            </jalios:if>            
        </div>
        <i class="icon icon-arrow-right ds44-cardArrow" aria-hidden="true"></i>
    </div>
</section>