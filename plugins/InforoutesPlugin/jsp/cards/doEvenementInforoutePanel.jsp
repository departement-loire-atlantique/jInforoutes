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


List<EvenementDTO> eventLieList = new ArrayList<EvenementDTO>();
for(String itNature : eventNature.keySet()) { 
  // Retire l'évènement courant des evenements liés
  List<EvenementDTO> itEventList = new ArrayList<EvenementDTO>();
  itEventList.addAll(eventNature.get(itNature));  
  itEventList.remove(itEventDto);
  // Calcul du nombre d'evenement lies
  eventLieList.addAll(itEventList);
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


<main role="main" id="content">

<article class="ds44-container-large">

    <div class="ds44-js-results-card">
    
        <%-- bouton Retour a la liste --%>
        <% request.setAttribute("isSearchFacetPanel", true); %>
        <%@ include file="/plugins/SoclePlugin/jsp/facettes/doRetourListe.jspf" %>
    
        <div class="ds44-inner-container ds44--xl-padding-t ds44--m-padding-b ds44-tablette-reduced-pt">        
            <h1 class="h1-like mbs mts " id="idTitre1"><%= itEventDto.getLigne1() %></h1>
        </div>
    
    
        <section class="ds44-box ds44-theme ds44-mb1">        
             <div class="ds44-flex-container">
		        <div class="ds44-card__section--horizontal ds44-flex-valign-center ds44-flex-align-center">
		            <jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteInner.jsp" />	         
		        </div>
		    </div>                      
        </section>
        
        
        <jalios:if predicate="<%= Util.notEmpty(eventLieList) %>">
	        <section>        
	            <div class="ds44-mlr3 ds44-mb1 h5-like"><strong><%= glp("jcmsplugin.inforoutes.evenement-lies") %> :</strong></div>                   
	            <jalios:foreach collection="<%= eventLieList %>" name="itEventLie" type="EvenementDTO">
	                <div class="ds44-flex-container">
		                <div class="ds44-card__section--horizontal ds44-flex-valign-center ds44-flex-align-center">                                     
		                    <p role="heading" aria-level="2" class="ds44-card__title"><%= itEventLie.getLigne1() %></p> 
		                    <% request.setAttribute("itEventDTO", itEventLie); %>                                   
		                    <jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteInner.jsp" />                       
		                </div>
	                </div>
	            </jalios:foreach>           	                       
	        </section>
        </jalios:if>
                      
    </div>
    
</article>

</main>



