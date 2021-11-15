<%@ page contentType="text/html; charset=UTF-8" %>
<%--
  @Summary: Liste des évènements du trafic en temps réel. Les évènements "prévisionnels" sont affichés
  @Author: Wyniwyg Atlantique
  @Customizable: True
--%>
<%@page import="fr.cg44.plugin.infotraficplugin.selector.LieuFutureEventSelector"%>
<%@page import="fr.cg44.plugin.infotraficplugin.comparator.EventByDateAndNatureComparator"%>
<%@page import="fr.cg44.plugin.infotraficplugin.comparator.EventDatePubMAJComparator"%>
<%@page import="fr.cg44.plugin.infotraficplugin.InfoTraficTempsReelContentFactory"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>

<%@ include file='/jcore/doInitPage.jsp' %>
<%@ include file='/jcore/portal/doPortletParams.jsp' %>

<% 

//Rattachement de l'événement du bac de loire de la catégorie courante (sert pour le selector)
String rattachement = "";
if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.indre.cat"))){
	rattachement = channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.indret");
}else if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.coueron.cat"))){
	rattachement = channel.getProperty("fr.cg44.plugin.alertemobilite.bac.rattachement.coueron");
}



// Evènements prévisionnels
DataSelector eventSelector = new LieuFutureEventSelector(new String[]{rattachement});
%>

<%@ include file='/plugins/InforoutesPlugin/jsp/infoRoutes/doPortletQueryForeachInfoTraficEvents.jspf' %>