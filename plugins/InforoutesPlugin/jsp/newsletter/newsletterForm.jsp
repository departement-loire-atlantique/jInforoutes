<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager" %>
<%@page import="fr.cg44.plugin.socle.mailjet.MailjetManager"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>

<%
if (!NewsletterManager.isAuthorized()) {
  sendForbidden(request, response);
  return;
}

request.setAttribute("newsletterAdminMenu", "true");
ModeleNewsletter newsletterModel = (ModeleNewsletter) channel.getPublication(request.getParameter("newsletterId"));
ModeleNewsletter newsletter = (ModeleNewsletter) newsletterModel.clone();
String valeursDefautTab[] = new String[newsletterModel.getLibelle().length];			
if (newsletter.getValeurParDefaut() != null) {
  valeursDefautTab = newsletter.getValeurParDefaut().clone();
}
newsletter.setValeurParDefaut(valeursDefautTab);
	
String valeursForm[] = request.getParameterValues("val");
if(Util.notEmpty(valeursForm)){
  newsletter.setValeurParDefaut(valeursForm);
}
%>

<% if(!jcmsContext.isAjaxRequest()){	%>
	<%@ include file='/work/doWorkHeader.jspf' %>
<%}%>


<% String op = request.getParameter("opSubmit"); %>

<jalios:select>
  
    <jalios:if predicate='<%= "Envoyer".equals(op) %>'>
        <%
		NewsletterManager newsletterManager = new NewsletterManager();
		String urlImageWebcam = newsletterManager.saveWebcamPSNToJpgFile();
		String gabaritHTMLWithArchivedWebcamImage = newsletter.getGabaritHTML().replaceAll(Pattern.quote("[URL_WEBCAM_PSN]"), urlImageWebcam);
		newsletter.setGabaritHTML(gabaritHTMLWithArchivedWebcamImage);
		Integer code = newsletterManager.sendMailService(newsletter);
		if (code == 0) {
			newsletterManager.sendMailJCMS(newsletter);
		}
		%>
		
		<jalios:if predicate="<%= 0 == code %>">
			Envoi effectué avec succès
			<% 
			// Enregistrement automatique du dernier envoi en tant que valeurs par défaut
			if(newsletterModel.getRepriseDesValeursDuDernierEnvoi()){
				newsletter.performUpdate(channel.getDefaultAdmin());
			}
		
			// Enregistrement vers la newsletter liée
			if(Util.notEmpty(newsletterModel.getNewsletterLiee())){
				ModeleNewsletter newsletterLiee = newsletterModel.getNewsletterLiee();
				NewsletterManager.copyNewsletterLiee(newsletter, newsletterLiee);
			}
			%>
		</jalios:if>
		
		<jalios:if predicate="<%= 0 != code %>">
			Impossible d'envoyer la newsletter
		</jalios:if>
		
	</jalios:if>
	
	<jalios:default>
        <div class="row">    
            <h1 class="boTitle">Envoyer : <%= newsletter.getGroupeDuModele() %> - <%= newsletter.getSousGroupeDuModele() %>  : <%= newsletterModel.getTitle()  %>
                <jalios:edit pub="<%= newsletter %>" />
            </h1>
        
            <%-- Colonne droite avec paramétrage de la newsletter--%>        
            <div class="col-md-4">
                <%@ include file='newsletterFormEdition.jspf'%>
            </div>
	      
            <%-- Colonne principale avec aperçu de la newsletter--%>	      
            <div class="col-md-8">
                <%@ include file='newsletterFormPreview.jspf'%>       
            </div>
        </div>
      
    </jalios:default>
    
</jalios:select>


<% if(!jcmsContext.isAjaxRequest()){ %>
    <%@ include file='/work/doWorkFooter.jspf' %>
<%}%>