<%@ page contentType="application/json; charset=UTF-8"%>
<%
	
%><%@ include file='/jcore/doInitPage.jsp'%>
<%
	
%><%@ include file='corsFilter.jsp'%>
<%
	
%><%@page import="com.jalios.util.HttpClientUtils"%>
<%
	
%><%@page import="org.json.*"%>
<%
	
%>
<%
	response.setContentType("application/json");
	String jsonString = "";

	String url = channel.getProperty("fr.cg44.plugin.mauves.json.url");
	try {
		jsonString = HttpClientUtils.getContent(url);
	} catch (Exception e) {
	}

	// supprime les objets inutiles du flux json pour les mobiles 
	JSONObject jsonObject = new JSONObject(jsonString);
	jsonObject.put("DureeNordSud",
			((JSONObject) ((JSONObject) jsonObject.get("bluevia")).get("200")).get("duree"));
	jsonObject.put("DureeSudNord",
			((JSONObject) ((JSONObject) jsonObject.get("bluevia")).get("202")).get("duree"));
	// DEBUG
	//jsonObject.put("DureeNordSud", 250);
	//jsonObject.put("DureeSudNord", 350);
	jsonObject.put("MauvesWebcam",
			((JSONObject) ((JSONObject) jsonObject.get("images")).get("30001")).get("url"));
	jsonObject.put("DivatteWebcam",
			((JSONObject) ((JSONObject) jsonObject.get("images")).get("30000")).get("url"));
	jsonObject.remove("images");
	jsonObject.remove("bluevia");

	out.print(jsonObject.toString());
	//out.print("error");
%>