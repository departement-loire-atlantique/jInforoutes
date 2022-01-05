<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jspf'%>

<h2 class="h2-like h2-like--mobileSize"><%= glp("jcmsplugin.inforoutes.en-direct") %></h2>

<% request.setAttribute("lieuWebcam", "psn"); %>
<jsp:include page="webcam.jsp" />

