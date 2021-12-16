<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/jcore/doInitPage.jspf" %>
<% jcmsContext.addJavaScript("plugins/InforoutesPlugin/js/webcams.js"); %> 

<%
String nomWebcam = (String)request.getAttribute("lieuWebcam");
String[] propsWebcam = channel.getStringArrayProperty("jcmsplugin.inforoutes.webcam."+nomWebcam, new String[]{});
%>

<section class="ds44-mtb3">
	<h3 class="h5-like ds44-mb1" role="heading"><%= propsWebcam[0] %></h3>
	<div class="iframe_inforoutes">
		<img id="imageWebcam<%= nomWebcam %>" src="<%= propsWebcam[1] %>" data-webcam-src="<%= propsWebcam[1] %>" alt="">
	</div>
	<p class="p-light ds44-mt1">
		<span class="visually-hidden"><%= glp("jcmsplugin.inforoutes.webcam.titre", nomWebcam) %></span>
		<%= glp("jcmsplugin.inforoutes.webcam.rafraichissement") %>
		<button data-webcam-name="<%= nomWebcam %>" type="button" class="ds44-linkUnderline" title="<%= glp("jcmsplugin.inforoutes.webcam.activer-webcam", nomWebcam) %>"><%= glp("jcmsplugin.inforoutes.webcam.activer") %></button>
	</p>
</section>
