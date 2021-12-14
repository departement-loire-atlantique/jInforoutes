<%@	page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.tools.ResponseCorsFilter"		%><%
%><%
	// Add CORS support
	ResponseCorsFilter.addPublicCORSHeader(request, response);
%>