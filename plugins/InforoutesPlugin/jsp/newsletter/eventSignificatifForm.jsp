<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager"%>


<%

RouteEvenement event = (RouteEvenement) channel.getPublication(request.getParameter("eventId"));
RouteEvenement clone = (RouteEvenement) event.getUpdateInstance();
clone.setSignificatif(HttpUtil.getBooleanParameter(request, "significatif", false) ? "oui" : "non");
clone.performUpdate(channel.getDefaultAdmin());
%>

<form action="plugins/InforoutesPlugin/jsp/newsletter/eventSignificatifForm.jsp">
 	 <input type="hidden" name="eventId" value="<%= event.getId() %>" />  	 	 	
 	 <input onchange="this.form.opSubmit.click()" type="radio" name="significatif" value="true" <%= event.getSignificatif().equals("oui")?"checked":"" %>> Oui <br/>
	<input onchange="this.form.opSubmit.click()" type="radio" name="significatif" value="false" <%= event.getSignificatif().equals("non")?"checked":"" %>> Non
	<input type="submit" name="opSubmit" class="ajax-refresh hidden" />
</form>  