<%@ page contentType="application/json; charset=UTF-8" %><%
%><%@ include file='/jcore/doInitPage.jsp' %><%
%><%@ page import='org.json.*' %><%
%>
<%
	// Retourne l'etat du webservice des événements
	// Utile pour jsync
	String json ="" ;
	JSONObject jsonObject = new JSONObject(); 
	jsonObject.put("infotrafic.ws.failed", channel.getProperty("infotrafic.ws.failed"));	
	out.print(jsonObject.toString());
%>