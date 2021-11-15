<%@page import="fr.cg44.plugin.alertemobilite.alarm.RouteEvenementAlerteAlarmListener"%>
<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>

<h3>Exécution manuelle de RouteEvenementAlerteAlarmListener()</h3>

<%
if ( !isAdmin) {
  sendForbidden(request, response);
  return;
}
%>

<% Date date = new Date(); %>

<p><%= date %></p>

<%

	RouteEvenementAlerteAlarmListener alarm = new RouteEvenementAlerteAlarmListener();
	alarm.handleAlarm(null);

%>