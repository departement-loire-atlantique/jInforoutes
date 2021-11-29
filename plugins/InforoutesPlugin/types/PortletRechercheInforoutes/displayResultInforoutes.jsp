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



//Permet la recherche geolocalisée

String northWestLat = request.getParameter("map[nw][lat]");
String northWestLng = request.getParameter("map[nw][long]");

String northEastLat = request.getParameter("map[ne][lat]");
String northEastLng = request.getParameter("map[ne][long]");

String southEastLat = request.getParameter("map[se][lat]");
String southEastLng = request.getParameter("map[se][long]");

String southWestLat = request.getParameter("map[sw][lat]");
String southWestLng = request.getParameter("map[sw][long]");

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

//Limiter les résultats au nombre indiqué dans la portlet inforoutes
// int limitResults = box.getNombreDeResultats();
// if (allEvents.size() > limitResults) {
// 	List<EvenementInfolocale> limitedListEvents = new ArrayList<>();
// 	while (limitedListEvents.size() < limitResults) {
// 	   limitedListEvents.add(allEvents.get(limitedListEvents.size()));
// 	}
// 	allEvents = new ArrayList<>(limitedListEvents);
// }

jsonObject.addProperty("nb-result-per-page", 500);
jsonObject.addProperty("max-result", 500);
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
%><%= jsonObject %>