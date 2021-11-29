<%@ page contentType="text/html; charset=UTF-8" %><%
%><%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ include file='/jcore/portal/doPortletParams.jspf' %><%
%><%@ include file='/plugins/SoclePlugin/jsp/facettes/commonParamsFacettes.jspf'%>
<%@ taglib prefix="ds" tagdir="/WEB-INF/tags"%>
<% 

PortletRechercheInforoutes obj = (PortletRechercheInforoutes)portlet; 


boolean isInPortletConteneur = Util.notEmpty(request.getAttribute("isInPortletConteneur")) ? true : false;
isInRechercheFacette = isInRechercheFacette || true;


%>

<div class="ds44-loader-text visually-hidden" tabindex="-1" aria-live="polite"></div>
<div class="ds44-loader hidden">
    <div class="ds44-loader-body">
        <svg class="ds44-loader-circular" focusable="false" aria-hidden="true">
            <circle class="ds44-loader-path" cx="30" cy="30" r="20" fill="none" stroke-width="5" stroke-miterlimit="10"></circle>
        </svg>
    </div>
</div>



<div class="ds44-facette">
    <div class="ds44-facette-body">

<form role="search" method='<%= channel.getBooleanProperty("jcmsplugin.socle.url-rewriting", false) ? "POST" : "GET" %>' data-seo-url='<%= channel.getProperty("jcmsplugin.socle.url-rewriting")%>' data-search-url="plugins/SoclePlugin/jsp/facettes/displayParameters.jsp" data-is-ajax='<%= isInRechercheFacette ? "true" : "false" %>' data-auto-load='<%= isInRechercheFacette ? "true" : "false" %>' action='plugins/SoclePlugin/jsp/facettes/displayResultDecodeParams.jsp'>
    <jalios:if predicate='<%= !isInRechercheFacette %>'>
      <p class="ds44-textLegend ds44-textLegend--mentions txtcenter"><%= glp("jcmsplugin.socle.facette.champs-obligatoires") %></p>
    </jalios:if>
    <div class="ds44-facetteContainer ds44-bgDark ds44-flex-container ds44-medium-flex-col">

        <% 
            request.setAttribute("isFilter", false);
        %>
        
        <% 
          String dataUrl = "plugins/SoclePlugin/jsp/facettes/acSearchCommune.jsp?allCommunes=true";
        %>
        
        <div class='ds44-fieldContainer ds44-fg1'>
        <ds:facetteAutoCompletion idFormElement='<%= ServletUtil.generateUniqueDOMId(request, glp("jcmsplugin.socle.facette.form-element")) %>' 
	        name='<%= "commune" + glp("jcmsplugin.socle.facette.form-element") + "-" + obj.getId() %>' 
	        request="<%= request %>" 
	        isFacetteObligatoire="<%=false %>" 
	        dataMode="select-only" 
	        dataUrl="<%= dataUrl %>" 
	        label='<%= glp("jcmsplugin.socle.facette.commune.default-label") %>'
	        option=' '
	        isLarge='<%= false %>'/>
        </div>
  
  
        <div class='ds44-fieldContainer ds44-fg1'>
        
		   
		<div class="ds44-form__container">
		   <div class="ds44-select__shape ds44-inpStd">
		      <p class="ds44-selectLabel" aria-hidden="true">Événements</p>
		      <div id="form-element-84573" data-name='evenement<%= glp("jcmsplugin.socle.facette.form-element") %>' class="ds44-js-select-checkbox ds44-selectDisplay"  data-required="false"></div>
		      <button type="button" id="button-form-element-84573" class="ds44-btnIco ds44-posAbs ds44-posRi ds44-btnOpen" aria-expanded="false" title="Select multiple - obligatoire"  ><i class="icon icon-down icon--sizeL" aria-hidden="true"></i><span id="button-message-form-element-84573" class="visually-hidden">Select multiple</span></button>
		      <button class="ds44-reset" type="button"><i class="icon icon-cross icon--sizeL" aria-hidden="true"></i><span class="visually-hidden">Effacer le contenu saisi dans le champ : Select multiple</span></button>
		   </div>
		   <div class="ds44-select-container hidden">
		      <div class="ds44-flex-container ds44--m-padding">
		         <button class="ds44-btnStd ds44-bgGray ds44-btnStd--plat ds44-fg1" type="button" aria-describedby="button-message-form-element-84573"><span class="ds44-btnInnerText">Tout cocher</span><i class="icon icon-check icon--medium" aria-hidden="true"></i></button>
		         <button class="ds44-btnStd ds44-bgGray ds44-btnStd--plat ds44-fg1 ds44-border-left--light" type="button" aria-describedby="button-message-form-element-84573"><span class="ds44-btnInnerText">Tout décocher</span><i class="icon icon-cross icon--medium" aria-hidden="true"></i></button>
		      </div>
		      <div class="ds44-listSelect">
		         <ul class="ds44-list" id="listbox-form-element-84573">
		            <li class="ds44-select-list_elem">
		               <div class="ds44-form__container ds44-checkBox-radio_list ">
		                  <input type="checkbox" id="name-check-form-element-18793-1" name='evenement<%= glp("jcmsplugin.socle.facette.form-element") %>' value="en cours" class="ds44-checkbox"   data-technical-field    /><label for="name-check-form-element-18793-1" class="ds44-boxLabel" id="name-check-label-form-element-18793-1">En cours</label>
		               </div>
		            </li>
		            <li class="ds44-select-list_elem">
		               <div class="ds44-form__container ds44-checkBox-radio_list ">
		                  <input type="checkbox" id="name-check-form-element-82894-2" name='evenement<%= glp("jcmsplugin.socle.facette.form-element") %>' value="prévisionnel" class="ds44-checkbox"   data-technical-field    /><label for="name-check-form-element-82894-2" class="ds44-boxLabel" id="name-check-label-form-element-82894-2">À venir</label>
		               </div>
		            </li>
		            <li class="ds44-select-list_elem">
		               <div class="ds44-form__container ds44-checkBox-radio_list ">
		                  <input type="checkbox" id="name-check-form-element-68911-3" name='evenement<%= glp("jcmsplugin.socle.facette.form-element") %>' value="terminé" class="ds44-checkbox"  data-technical-field     /><label for="name-check-form-element-68911-3" class="ds44-boxLabel" id="name-check-label-form-element-68911-3">Terminés</label>
		               </div>
		            </li>
		         </ul>
		      </div>
		      <button type="button" class="ds44-fullWBtn ds44-btnSelect ds44-theme" title="Valider la sélection de : Select multiple"><span class="ds44-btnInnerText">Valider</span><i class="icon icon-long-arrow-right ds44-noLineH" aria-hidden="true"></i></button>
		   </div>
		</div>
   
        
        </div>


        <% request.removeAttribute("isFilter"); %>


        <input type="hidden" name="redirectUrl" value="plugins/InforoutesPlugin/types/PortletRechercheInforoutes/displayResultInforoutes.jsp" data-technical-field />
        

        <div class="ds44-fieldContainer ds44-small-fg1">
            <% String styleButton = isInRechercheFacette ? "" : "--large"; %>
            <button class='<%= "jcms-js-submit ds44-btnStd ds44-btnStd"+styleButton+" ds44-theme" %>'>
                <span class="ds44-btnInnerText"><%= glp("jcmsplugin.socle.rechercher") %></span>
                <i class="icon icon-long-arrow-right" aria-hidden="true"></i>
            </button>                   
        </div>

    </div>

    

</form>


</div>


<!-- <div class="ds44-facette-mobile-button ds44-bgDark ds44--l-padding ds44-show-tiny-to-medium ds44-hide-medium"> -->
<!--     <button class="ds44-btnStd ds44-btn--contextual ds44-w100 ds44-js-toggle-search-view"> -->
<%--         <span class="ds44-btnInnerText ds44-facette-mobile-button-collapse"><%= glp("jcmsplugin.socle.recherche.affiner") %></span> --%>
<%--         <span class="ds44-btnInnerText ds44-facette-mobile-button-expand"><%= glp("jcmsplugin.socle.recherche.masquer") %></span> --%>
<!--     </button> -->
<!-- </div> -->





</div>



<div class='ds44-flex-container ds44-results ds44-results--mapVisible ds44-results--empty'>
 
 
   <div class="ds44-listResults ds44-innerBoxContainer ds44-innerBoxContainer--list">
       <div class="ds44-js-results-container">
           <div class="ds44-js-results-card" data-url="plugins/SoclePlugin/jsp/facettes/displayPub.jsp" aria-hidden="true"></div>
           <div class="ds44-js-results-list" data-display-mode='<%="inline" %>'>
               <p aria-level="2" role="heading" id="ds44-results-new-search" class="h3-like mbs txtcenter center ds44--3xl-padding-t ds44--3xl-padding-b">
                 <span aria-level="2" role="heading"><%= glp("jcmsplugin.socle.faire.recherche") %></span>
               </p>            
           </div>
       </div>
   </div>
   
   
   
<%--    <button type="button" title='<%= HttpUtil.encodeForHTMLAttribute(glp("jcmsplugin.socle.recherche.carte.masquer")) %>' class="ds44-btnStd-showMap ds44-btnStd ds44-btn--invert ds44-js-toggle-map-view"> --%>
<%--      <span class="ds44-btnInnerText"><%= glp("jcmsplugin.socle.recherche.carte.masquer") %></span><i class="icon icon-map" aria-hidden="true"></i> --%>
<!--    </button> -->
   
   <div class="ds44-mapResults">
       <div class="ds44-mapResults-container">
           <div class="ds44-js-map" 
                 data-geojson-url='<%= channel.getProperty("jcmsplugin.socle.recherche.geojson.departement.url") %>'
                 data-geojson-mode='<%= "static" %>' 
                ></div>
           
<%--            <button type="button" title='<%= HttpUtil.encodeForHTMLAttribute(glp("jcmsplugin.socle.recherche.carte.masquer")) %>' class="ds44-btnStd-showMap ds44-btnStd ds44-btn--invert ds44-js-toggle-map-view"> --%>
<%--                <span class="ds44-btnInnerText"><%= glp("jcmsplugin.socle.recherche.carte.masquer") %></span><i class="icon icon-map" aria-hidden="true"></i> --%>
<!--            </button> -->
           
       </div>
    </div>
   
</div> 
