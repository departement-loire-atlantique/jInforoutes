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

List<EvenementDTO> allEvents = InforoutesApiRequestManager.getAllEvent();
List<TraceEvtSpiralDTO> allTraces = InforoutesApiRequestManager.getAllTrace();


String etat[] = request.getParameterValues("evenement");
List<String> etatParamsList = new ArrayList<String>();

if(Util.notEmpty(etat)) {
  etatParamsList = Arrays.asList(etat);
}

Map<String, EvenementDTO> eventsMap = new HashMap<String, EvenementDTO>();
List<EvenementDTO> selectEtatEvent = new ArrayList<EvenementDTO>();

//Filtre sur le status de l'événement poar rapport à la facette
for(EvenementDTO itEvent : allEvents) {   
  if( (Util.isEmpty(etatParamsList) && "en cours".equalsIgnoreCase(itEvent.getStatut())) ||
     (etatParamsList.contains(itEvent.getStatut())))  {
    selectEtatEvent.add(itEvent);
    eventsMap.put(itEvent.getIdentifiant(), itEvent);
  } 
}
allEvents = selectEtatEvent;


//Affiche seulement les tracés reliés à un marqueur
List<TraceEvtSpiralDTO> filtreTraces = new ArrayList<TraceEvtSpiralDTO>();
for(TraceEvtSpiralDTO itTrace : allTraces) { 
  if(  eventsMap.containsKey((itTrace.getErf() + itTrace.getSnm())) ) {
    //System.out.println(itTrace.getErf() + itTrace.getSnm());
    filtreTraces.add(itTrace);
  }   
}



//Permet la recherche geolocalisée

String northWestLat = request.getParameter("map[nw][lat]");
String northWestLng = request.getParameter("map[nw][long]");

String northEastLat = request.getParameter("map[ne][lat]");
String northEastLng = request.getParameter("map[ne][long]");

String southEastLat = request.getParameter("map[se][lat]");
String southEastLng = request.getParameter("map[se][long]");

String southWestLat = request.getParameter("map[sw][lat]");
String southWestLng = request.getParameter("map[sw][long]");

String commune = request.getParameter("commune");


if(Util.notEmpty(allEvents) && Util.notEmpty(northWestLat) && Util.notEmpty(northWestLng) && Util.notEmpty(southEastLat) && Util.notEmpty(southEastLng)) {
  List<EvenementDTO> selectEvent = new ArrayList<EvenementDTO>();
  for(EvenementDTO itEvent : allEvents) {
    if( Double.parseDouble(itEvent.getLatitude()) < Double.parseDouble(northWestLat) && Double.parseDouble(itEvent.getLatitude()) > Double.parseDouble(southEastLat) &&
        Double.parseDouble(itEvent.getLongitude()) < Double.parseDouble(northEastLng) && Double.parseDouble(itEvent.getLongitude()) > Double.parseDouble(southWestLng)) {
      selectEvent.add(itEvent);
    }
  }   
  allEvents = selectEvent;  
}




%><%



JsonArray jsonArray = new JsonArray();

JsonObject jsonObject = new JsonObject();

jsonObject.addProperty("nb-result", allEvents.size());


jsonObject.addProperty("nb-result-per-page", 500);
jsonObject.addProperty("max-result", 500);


// Permet le d'indiquer au JS que le filtre voulu est un zoom sur la commune indiquée
City communeCity = SocleUtils.getCommuneFromInsee(commune);

	jsonObject.addProperty("geojsonId", communeCity != null ? communeCity.getImportId() : "");


jsonObject.add("result", jsonArray);




session.setAttribute("isSearchFacetLink", true);

%><%

%><jalios:foreach collection="<%= allEvents %>" name="itEvent" type="EvenementDTO"> <%

    %><jalios:buffer name="itPubListGabarit"><%
            request.setAttribute("itEventDTO", itEvent);
            %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteCard.jsp" /><%
    %></jalios:buffer><%
    
    %><jalios:buffer name="itPubMarkerGabarit"><%
        request.setAttribute("itEventDTO", itEvent);
        %><jsp:include page="/plugins/InforoutesPlugin/jsp/cards/doEvenementInforouteMarker.jsp" /><%
    %></jalios:buffer>
    <%
    %><%     
     jsonArray.add(InforoutesApiRequestManager.eventToJsonObject(itEvent, itPubListGabarit, itPubMarkerGabarit, null));
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