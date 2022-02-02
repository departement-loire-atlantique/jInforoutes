<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager" %>

<jalios:if predicate='<%= NewsletterManager.isAuthorized() && workspace.equals(channel.getWorkspace("$id.jcmsplugin.inforoutes.workspace.pcroutes")) %>'>
    <% 
    Boolean newsletterAdminMenu  = request.getAttribute("newsletterAdminMenu") != null;
    %>

    <div class="app-sidebar-section" id="newsletterAdminMenu">
        <div class='app-sidebar-section-title <%= newsletterAdminMenu ? "active" : "" %> '>
            <a href="plugins/InforoutesPlugin/jsp/newsletter/pushNewsletter.jsp" class="">Push par courriel</a>
        </div>
    </div>

</jalios:if>