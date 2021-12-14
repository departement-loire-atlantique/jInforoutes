<%@ 	page contentType="application/json; charset=UTF-8" 				%><%
%><%@ 	include file='/jcore/doInitPage.jsp' 						%><%
%><%@ 	include file='corsFilter.jsp' 						%><%
%><%@	page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.Psnstatus"	%><%
%><%@	page import="fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper"			%><%
%><%
	String json = Psnstatus.generateStatus();
	json = json.replaceAll("MODE_PARTICULIER", "INDETERMINE" );
	response.setContentType("application/json");
	out.print(json);
%>