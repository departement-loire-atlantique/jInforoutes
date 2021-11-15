<%@ page contentType="application/json; charset=UTF-8" %><%
%><%@ include file='/jcore/doInitPage.jsp' %><%
%><%@ include file='corsFilter.jsp' 						%><%
%><%@page import="com.jalios.util.HttpClientUtils"%><%
%><%@page import="org.json.*"%><%
%>
<%
	response.setContentType("application/json");
	String jsonString ="" ;
				
	String url = Channel.getChannel().getProperty("fr.cg44.plugin.ancenis.json.url") ;
	try {
		jsonString = HttpClientUtils.getContent(url);
	} catch (Exception e) {
	}

	JSONObject jsonObject = new JSONObject(jsonString);
	
	// supprime les objets inutiles du flux json pour les mobiles 
	jsonObject.remove("webcams");
	jsonObject.remove("feux");
	((JSONObject)jsonObject.get("sens_circulation")).remove("url_pictogramme");
		
	out.print(jsonObject.toString());
%>