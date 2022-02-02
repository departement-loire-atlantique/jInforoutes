<%@ page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager" %>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterBean" %>
<%@page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %><%

if (!NewsletterManager.isAuthorized()) {
  sendForbidden(request, response);
  return;
}

String from = channel.getProperty("jcmsplugin.inforoutes.newsletter.contact.supportjcms.id");
String limit = "20";
String sort = "ID DESC";

Collection dernierEnvoi = NewsletterManager.getNewsletterList(from, limit, sort);

%>
 
<form action="plugins/InforoutesPlugin/jsp/newsletter/derniersEnvoisNewsletter.jsp" method="POST" class="form-horizontal">	  
    <input type="submit" name="opSubmit" value="<%= glp("jcmsplugin.inforoutes.newsletter.refresh")%>" class="btn btn-primary ajax-refresh"/>  	  
</form>
 
 
 <jalios:if predicate='<%= Util.isEmpty(dernierEnvoi) %>'>
 	<p><%= glp("jcmsplugin.inforoutes.newsletter.message-aucun-envoi")%></p>
 </jalios:if>
 
 <jalios:if predicate='<%= Util.notEmpty(dernierEnvoi) %>'>
	 <table class="table table-striped">     
      	<tr>
      		<th><%= glp("jcmsplugin.inforoutes.newsletter.expediteur")%></th>
      		<th><%= glp("jcmsplugin.inforoutes.newsletter.date-envoi")%></th>
      		<th><%= glp("jcmsplugin.inforoutes.newsletter.revoir")%></th>
      	</tr>
      	
		<jalios:foreach name="itNewsletter" type="NewsletterBean" collection="<%= NewsletterManager.getNewsletterList(from, limit, sort) %>">     
      	<tr>
      		<td>
      			<%-- <p><%= itNewsletter.getFromName()</p> --%>
      			<p><%= itNewsletter.getSubject() %></p>    			
      		</td>
      		<td>
      		<%
      		
      		SimpleDateFormat sdfin = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
      		SimpleDateFormat sdfout = new SimpleDateFormat("d/MM/yyyy à hh:mm:ss");
      		Date date = sdfin.parse(itNewsletter.getDate());
      		String strDate = sdfout.format(date);
      		
      		
      		%>
      			<p><%= strDate %></p> 
      		</td>
      		<td>
      			<%= itNewsletter.getLink() %>
      		</td>    		
      	</tr>
    	</jalios:foreach> 
    </table>	
 </jalios:if>