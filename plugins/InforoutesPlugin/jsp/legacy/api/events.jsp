<%@ 	page contentType="text/html; charset=UTF-8" 			%><%
%><%@ 	include file='/jcore/doInitPage.jsp' 						%><%
%><%@ 	include file='corsFilter.jsp' 						%><%
%><%@	page import="fr.cg44.plugin.alertemobilite.ws.Events"		%><%
%><%

	String[] filters = request.getParameterValues("filter");
	response.setContentType("application/json");
	// Si un ou plusieurs Ids est passés en paramètre de la requête
	if (Util.notEmpty(filters)) {
		String json = Events.generateEvents(filters);
		out.print(json);
	} else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		String json = "{\"error\":\"Veuillez renseigner au moins un filtre.\"}";
		out.print(json);
	}
%>