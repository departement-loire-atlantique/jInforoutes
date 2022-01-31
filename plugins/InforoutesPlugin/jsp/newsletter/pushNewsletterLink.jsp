<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager" %>

<jalios:if predicate='<%= NewsletterManager.isAuthorized() && workspace.equals(channel.getWorkspace("$id.jcmsplugin.inforoutes.workspace.pcroutes")) %>'>
    <% 
    Boolean newsletterAdminMenu  = request.getAttribute("newsletterAdminMenu") != null;
    %>

	<li id="newsletterAdminMenu" class="<% if (newsletterAdminMenu){%> active<%}%>"> 
	    <a href="plugins/InforoutesPlugin/jsp/newsletter/pushNewsletter.jsp">
	   		<img class="icon" src="images/jalios/icons/mail_open.gif" alt=""/>
	    	Push par courriel
	    </a>
	</li>
</jalios:if>