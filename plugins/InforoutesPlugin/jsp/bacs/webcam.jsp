<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/jcore/doInitPage.jspf" %>

<%
String nomWebcam = (String)request.getAttribute("lieuWebcam");
String[] propsWebcam = channel.getStringArrayProperty("jcmsplugin.inforoutes.webcam."+nomWebcam, new String[]{});
%>

<section class="ds44-mtb3 ds44-webcam">
	<h3 class="h5-like ds44-mb1" role="heading"><%= propsWebcam[0] %></h3>
	<div class="iframe_inforoutes">
        <img class="ds44-webcam-viewer" src="<%= propsWebcam[1] %>" alt="" data-webcam-src="<%= propsWebcam[1] %>" data-webcam-refresh-status="enabled" data-webcam-refresh-time="5" data-webcam-refresh-max="10" />
	</div>
    <p class="p-light ds44-mt1">
        <%= glp("jcmsplugin.inforoutes.webcam.rafraichissement") %>
    </p>
    <button class="ds44-btnStd ds44-bgGray ds44-btnStd--plat" title="<%= glp("jcmsplugin.inforoutes.webcam.activer-webcam") %> <%= propsWebcam[0] %>">
        <span class="ds44-btnInnerText"><span class="ds44-entitled-button"><%= glp("jcmsplugin.inforoutes.webcam.activer-webcam") %></span><span class="visually-hidden"> : <%= propsWebcam[0] %></span></span><i class="icon icon-webcam" aria-hidden="true"></i>
    </button>
</section>
