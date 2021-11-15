<%@	page import="fr.cg44.plugin.infotraficplugin.tools.ResponseCorsFilter"		%><%
%><%
	// Add CORS support
	ResponseCorsFilter.addPublicCORSHeader(request, response);
%>