<%@ page contentType="text/html; charset=UTF-8" %>
<%--
  @Summary: Liste des évènements du trafic en temps réel. Les évènements "en cours", 
            "à venir" ou "terminés" sont affichés
  @Author: Wyniwyg Atlantique
  @Customizable: True
--%>
<%@page import="fr.cg44.plugin.infotraficplugin.selector.CurrentEventSelector"%>
<%@page import="fr.cg44.plugin.infotraficplugin.comparator.EventByDateAndNatureComparator"%>
<%@page import="fr.cg44.plugin.infotraficplugin.comparator.EventDatePubMAJComparator"%>
<%@page import="fr.cg44.plugin.infotraficplugin.InfoTraficTempsReelContentFactory"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>

<%@ include file='/jcore/doInitPage.jsp' %>
<%@ include file='/jcore/portal/doPortletParams.jsp' %>

<% 
// Evènements en cours
DataSelector eventSelector = new CurrentEventSelector();
%>

<%@ include file='/plugins/InforoutesPlugin/jsp/infoRoutes/doPortletQueryForeachInfoTraficEvents.jspf' %>