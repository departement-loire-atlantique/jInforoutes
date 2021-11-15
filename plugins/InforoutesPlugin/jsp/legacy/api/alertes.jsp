<%@ page contentType="application/json; charset=UTF-8" %><%
%><%@ include file='/jcore/doInitPage.jsp' %><%
%><%@ include file='corsFilter.jsp' 						%><%
%><%@ page import='fr.cg44.plugin.alertemobilite.ws.AlerteJsonUtil' %><%
%>
<%
	response.setContentType("application/json");
	String[] filters = request.getParameterValues("filter");
	String id = request.getParameter("id");
	
	String json ="" ;
		
	// Si un id est passé en paramètre de la requète
	if(Util.notEmpty(id)){
		json = AlerteJsonUtil.getAlerteId(id);
	// Si un ou plusieurs filtre sont passés en paramètre de la requête
	}else if (Util.notEmpty(filters)) {
		json = AlerteJsonUtil.getAlertesFilter(filters);			
	} else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		json = "{\"error\":\"Veuillez renseigner au moins un filtre ou un id.\"}";		
	}
	out.print(json);
%>