<%@ page contentType="application/json; charset=UTF-8" %><%
%><%@ 	include file='/jcore/doInitPage.jsp'%><%
%><%@page import="com.jalios.util.HttpClientUtils"%>

<jalios:cache id="pontAncenis" timeout="1">
<%
	// JSON de Neavia avec commentaires :
	// http://188.165.253.140/anc_if/anc_if.php?comments=1

	//String url = "http://188.165.253.140/anc_if/anc_if.php";
	String url = Channel.getChannel().getProperty("fr.cg44.plugin.ancenis.json.url") ;	
	try {
		%><%= HttpClientUtils.getContent(url) %><%
		} catch (Exception e) {
				%>{}<% 
		}%>
</jalios:cache>	