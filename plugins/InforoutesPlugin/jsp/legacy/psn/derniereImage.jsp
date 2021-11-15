<%@page import="fr.cg44.plugin.webcam.WebCamManager"%><%
if(WebCamManager.getInstance()!=null&&WebCamManager.getInstance().isValidImage()){
String imageWebcamCourant = String.valueOf(WebCamManager.getInstance().getLastCapture());
String date = WebCamManager.getFormattedDateImage(imageWebcamCourant,WebCamManager.heureMinuteSecondeFormatter);
String src=WebCamManager.getRelativeImage(imageWebcamCourant);%>{"src":"<%=src %>","date":"<%=date %>"}<%}else{%>{"src":"","date":""}<%}%>