<%@page import="fr.cg44.plugin.inforoutes.comparator.EventDTOByDateAndNatureComparator"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.TraceEvtSpiralDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.socle.infolocale.util.InfolocaleUtil"%>
<%@page import="fr.cg44.plugin.socle.infolocale.InfolocaleEntityUtils"%>
<%@page import="fr.cg44.plugin.socle.infolocale.RequestManager"%>
<%@page import="fr.cg44.plugin.socle.queryfilter.CategoryFacetUtil"%>
<%@page import="fr.cg44.plugin.socle.SocleUtils"%>
<%@page import="com.jalios.io.IOUtil"%><%
%><%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%><%
%><%@page import="com.google.gson.JsonObject"%><%
%><%@page import="com.google.gson.JsonArray"%><%

request.setAttribute("inFO", Boolean.TRUE);

%><%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ include file="/jcore/portal/doPortletParams.jspf" %><%


response.setContentType("application/json");

// PortletAgendaInfolocale boxTmp = (PortletAgendaInfolocale) (channel.getPublication(request.getParameter("boxId"))).clone();  
// PortletAgendaInfolocale box = new PortletAgendaInfolocale(boxTmp);

List<EvenementDTO> allEvents = InforoutesApiRequestManager.getTraficEvents();
Set<EvenementDTO> filterEvents = new TreeSet<EvenementDTO>(new EventDTOByDateAndNatureComparator());
List<TraceEvtSpiralDTO> allTraces = InforoutesApiRequestManager.getTraficEventsTrace();


String etat[] = request.getParameterValues("evenement");
List<String> etatParamsList = new ArrayList<String>();

if(Util.notEmpty(etat)) {
  etatParamsList = Arrays.asList(etat);
}

Map<String, EvenementDTO> eventsMap = new HashMap<String, EvenementDTO>();
List<EvenementDTO> selectEtatEvent = new ArrayList<EvenementDTO>();

// Filtre sur le status de l'événement poar rapport à la facette
for(EvenementDTO itEvent : allEvents) {   
  if( (Util.isEmpty(etatParamsList) && "en cours".equalsIgnoreCase(itEvent.getStatut())) ||
     (etatParamsList.contains(itEvent.getStatut())))  {
    filterEvents.add(itEvent);
    eventsMap.put(itEvent.getIdentifiant(), itEvent);
  } 
}



// Affiche seulement les tracés reliés à un marqueur
List<TraceEvtSpiralDTO> filtreTraces = new ArrayList<TraceEvtSpiralDTO>();
for(TraceEvtSpiralDTO itTrace : allTraces) { 
  if(eventsMap.containsKey((itTrace.getErf() + itTrace.getSnm()))) {
    filtreTraces.add(itTrace);
  }   
}



// Permet la recherche geolocalisée

String northWestLat = request.getParameter("map[nw][lat]");
String northWestLng = request.getParameter("map[nw][long]");

String northEastLat = request.getParameter("map[ne][lat]");
String northEastLng = request.getParameter("map[ne][long]");

String southEastLat = request.getParameter("map[se][lat]");
String southEastLng = request.getParameter("map[se][long]");

String southWestLat = request.getParameter("map[sw][lat]");
String southWestLng = request.getParameter("map[sw][long]");

String commune = request.getParameter("commune");



//*************************************************************************
// Regroupe les evenements lies entre eux (par snm puis par nature)
//*************************************************************************

HashMap<String, HashMap<String, List<EvenementDTO>>> eventLies = new HashMap<String, HashMap<String, List<EvenementDTO>>>();
for(EvenementDTO itEvent : filterEvents) {
  
  // Ajout du snm
  if(Util.isEmpty(eventLies.get(itEvent.getSnm()))) {
    eventLies.put(itEvent.getSnm(), new HashMap<String, List<EvenementDTO>>());
  }
  
  // Ajout de l'evenement suivant sa nature
  HashMap<String, List<EvenementDTO>> eventNature = eventLies.get(itEvent.getSnm());
  List<EvenementDTO> eventList = new ArrayList<EvenementDTO>();
  if(Util.notEmpty(eventNature.get(itEvent.getNature()))) {
    eventList = eventNature.get(itEvent.getNature());      
  } else {
    eventNature.put(itEvent.getNature(), eventList);
  }
  eventList.add(itEvent);
}


//*************************************************************************
// Filtre les evenements suivant la position de la carte
//*************************************************************************

if(Util.notEmpty(filterEvents) && Util.notEmpty(northWestLat) && Util.notEmpty(northWestLng) && Util.notEmpty(southEastLat) && Util.notEmpty(southEastLng)) {
  Set<EvenementDTO> selectEvent = new HashSet<EvenementDTO>();
  for(EvenementDTO itEvent : filterEvents) {
    if( Double.parseDouble(itEvent.getLatitude()) < Double.parseDouble(northWestLat) && Double.parseDouble(itEvent.getLatitude()) > Double.parseDouble(southEastLat) &&
        Double.parseDouble(itEvent.getLongitude()) < Double.parseDouble(northEastLng) && Double.parseDouble(itEvent.getLongitude()) > Double.parseDouble(southWestLng)) {
      selectEvent.add(itEvent);
    }
  }   
  filterEvents = Util.interSet(filterEvents, selectEvent);  
}



//*************************************************************************
// Groupe les déviations ratachées à un chantier en gardant l'odre de trie
//*************************************************************************

// Créer une map de chantier (clé) et déviations associées (valeurs)
HashMap<String, List<EvenementDTO>> chantierDeviation = new HashMap<String, List<EvenementDTO>>();
for(EvenementDTO event : filterEvents){
    if("Chantier".equals(event.getNature()) && Util.notEmpty(event.getSnm())){
        chantierDeviation.put(event.getSnm(), new ArrayList<EvenementDTO>());
    }   
}


// Associer les déviations à leur chantier
Collection<EvenementDTO> collectionChantier = new ArrayList<EvenementDTO>();
for(EvenementDTO event : filterEvents){
  if("Déviation".equals(event.getNature())){
    // Si un chanter éxiste pour cette déviation l'associer au chantier
    if(chantierDeviation.get(event.getSnm()) != null ){
      List<EvenementDTO> deviations = chantierDeviation.get(event.getSnm());
      deviations.add(event);
    } else {
      collectionChantier.add(event);
    } 
  } else {
      collectionChantier.add(event);
  }
}


// Liste final avec chantiers et déviations associées et triés
Collection<EvenementDTO> collectionBis = new ArrayList<EvenementDTO>();
for(EvenementDTO event : collectionChantier){
  collectionBis.add(event);
  if("Chantier".equals(event.getNature()) && Util.notEmpty(chantierDeviation)){
    for(EvenementDTO deviation : chantierDeviation.get(event.getSnm())){
      if(deviation != null){
        collectionBis.add(deviation);
      }
    }
  }
}

//*********************************************************
// Fin de Groupement des déviations ratachées à un chantier
//*********************************************************






%><%



JsonArray jsonArray = new JsonArray();

JsonObject jsonObject = new JsonObject();

jsonObject.addProperty("nb-result", collectionBis.size());


jsonObject.addProperty("nb-result-per-page", 500);
jsonObject.addProperty("max-result", 500);


// Permet le d'indiquer au JS que le filtre voulu est un zoom sur la commune indiquée
City communeCity = SocleUtils.getCommuneFromInsee(commune);
jsonObject.addProperty("geojsonId", communeCity != null ? communeCity.getImportId() : "");


jsonObject.add("result", jsonArray);




session.setAttribute("isSearchFacetLink", true);
request.setAttribute("eventLien", eventLies);

%><%

%><jalios:foreach collection="<%= collectionBis %>" name="itEvent" type="EvenementDTO"> <%

    %><jalios:buffer name="itPubListGabarit"><%
            request.setAttribute("itEventDTO", itEvent);
            %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteCard.jsp" /><%
    %></jalios:buffer><%
    
    %><jalios:buffer name="itPubMarkerGabarit"><%
        request.setAttribute("itEventDTO", itEvent);
        %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteMarker.jsp" /><%
    %></jalios:buffer><%
    
    %><jalios:buffer name="itPubFullGabarit"><%
        request.setAttribute("itEventDTO", itEvent);
        %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforoutePanel.jsp" /><%
    %></jalios:buffer><%

    %><%     
     jsonArray.add(InforoutesApiRequestManager.eventToJsonObject(itEvent, itPubListGabarit, itPubMarkerGabarit, itPubFullGabarit));
    %><%
                                        
%></jalios:foreach><%


%><jalios:foreach collection="<%= filtreTraces %>" name="itTrace" type="TraceEvtSpiralDTO"> <%

	%><jalios:buffer name="itPubMarkerGabarit"><%
	    request.setAttribute("itEventDTO", eventsMap.get(itTrace.getErf() + itTrace.getSnm()));
        %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteMarker.jsp" /><%
	%></jalios:buffer>
	<%
     jsonArray.add(InforoutesApiRequestManager.traceToJsonObject(itTrace, itPubMarkerGabarit));
%><%
%></jalios:foreach><%

%><%= jsonObject %>