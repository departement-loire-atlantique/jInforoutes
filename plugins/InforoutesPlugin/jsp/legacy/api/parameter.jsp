<%@ 	page contentType="application/json; charset=UTF-8" 			%><%
%><%@ 	include file='/jcore/doInitPage.jsp' 						%><%
%><%@ 	include file='corsFilter.jsp' 						%><%
%><%@	page import="fr.cg44.plugin.alertemobilite.ws.Parameter"	%><%
%><%
	String[] ids = request.getParameterValues("id");
	Boolean isJson = "json".equalsIgnoreCase(request.getParameter("format"));
	response.setContentType("application/json");
	// Si un ou plusieurs Ids est passés en paramètre de la requête
	if (Util.notEmpty(ids)) {
		String json = Parameter.generateParameter(ids, isJson);
		out.print(json);
	} else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		String json = "{\"error\":\"Veuillez renseigner au moins un ID.\"}";
		out.print(json);
	}
%>