<%@page contentType="text/plain; charset=UTF-8"%>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.ComparatorNewsletter"%>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager"%>
<%@page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterBean"%>
<%@page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.CurrentEventSelector"%>
<%@page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator.EventByDateAndNatureComparator"%>
<%@page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.comparator.EventDatePubMAJComparator"%>
<%@page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.InfoTraficTempsReelContentFactory"%>
<%@page import="fr.cg44.plugin.inforoutes.legacy.infotraficplugin.selector.LieuCurrentEventSelector"%>
<%@page import="org.apache.commons.lang.ArrayUtils"%>
<%@page import="fr.cg44.plugin.inforoutes.dto.EvenementDTO"%>
<%@page import="fr.cg44.plugin.inforoutes.api.InforoutesApiRequestManager"%>
<%@page import="fr.cg44.plugin.inforoutes.InforoutesUtils"%>
<%@ include file='/jcore/doInitPage.jsp'%>

<% 
if (!NewsletterManager.isAuthorized()) {
  sendForbidden(request, response);
  return;
}

request.setAttribute("newsletterAdminMenu", "true");
jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/plugin.css");
%>

<%@ include file='/work/doWorkHeader.jspf'%>

<div class="row push-courriel">
  
    <h1 class="boTitle icon">Push par courriel</h1>

    <%
    Map<String, Set<ModeleNewsletter>> newsletterMap = new HashMap<String, Set<ModeleNewsletter>>();        
    for(ModeleNewsletter news : channel.getPublicationSet(ModeleNewsletter.class, loggedMember)){               
        Set newsletterSet = newsletterMap.get(news.getGroupeDuModele());
        if(newsletterSet == null){
            newsletterSet = new TreeSet(new ComparatorNewsletter());
        }
        newsletterSet.add(news);
        newsletterMap.put(news.getGroupeDuModele(), newsletterSet);
    }

    List listeGroupesParOrdreAlpha = new ArrayList();
    listeGroupesParOrdreAlpha.addAll(newsletterMap.keySet());
    Collections.sort(listeGroupesParOrdreAlpha);
    %>

    <%-- Colonne gauche avec liste des différents types d'envois --%>
    <div class="col-md-4">
    
        <jalios:foreach name="itNewsletterGroupe" type="String" collection="<%= listeGroupesParOrdreAlpha %>">

            <div class="panel panel-default">
                <div class="panel-heading"><h2><%= itNewsletterGroupe %></h2></div>
                <div class="panel-body">

                    <% String sousGroupe = null ; %>
                    <jalios:foreach name="itNewsletter" type="ModeleNewsletter" collection="<%=  newsletterMap.get(itNewsletterGroupe) %>">
                        <jalios:if predicate="<%= !itNewsletter.getSousGroupeDuModele().equals(sousGroupe) %>">
                            <h3><%= itNewsletter.getSousGroupeDuModele() %></h3>
                            <% sousGroupe = itNewsletter.getSousGroupeDuModele(); %>
                        </jalios:if>
                        <div style="padding-left: 30px">
                            <jalios:edit pub="<%= itNewsletter %>" />
                            <img class="icon" alt="" src="images/jalios/icons/mail_open.gif">
                                <a href="plugins/InforoutesPlugin/jsp/newsletter/newsletterForm.jsp?newsletterId=<%= itNewsletter.getId() %>">
                                    <%= itNewsletter %>
                                </a>
                        </div>
                    </jalios:foreach>
                </div>
            </div>
        </jalios:foreach>
        
    </div>
    
    <%-- Colonne droite avec liste des derniers envois (ajax)--%>
    <div class="col-md-8">
        
        <div class="ajax-refresh-div">
            <form action="plugins/InforoutesPlugin/jsp/newsletter/derniersEnvoisNewsletter.jsp" method="POST" class="form-horizontal">
                <input type="submit" name="opSubmit" value="Voir les derniers envois" class="btn btn-primary ajax-refresh" />
            </form>
        </div>

    </div>

</div>

<%-- Liste des événements --%>
<div class="row push-courriel">
    <%@ include file='pushNewsletterListeEvenements.jsp'%>
</div>

<%@ include file='/work/doWorkFooter.jspf'%>