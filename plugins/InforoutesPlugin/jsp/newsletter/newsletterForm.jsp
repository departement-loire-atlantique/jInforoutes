<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="fr.cg44.plugin.inforoutes.newsletter.NewsletterManager" %>
<%@ page import="fr.cg44.plugin.inforoutes.legacy.tools.mailjet.MailjetClient" %>
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

<div class="">

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
      <div id="docs" class="mainBlock" style="margin-bottom: 15px;">    
        <h1 style='background-image:url("plugins/NewsletterPlugin/images/assets/icon_mail.gif");' class="boTitle icon">Envoyer : <%= newsletter.getGroupeDuModele() %> - <%= newsletter.getSousGroupeDuModele() %>  : <%= newsletterModel.getTitle()  %><jalios:edit pub="<%= newsletter %>" /></h1>        
         <div class="" style="min-width:300px; float:left; margin-right:10px" > 
	       <form action="plugins/NewsletterPlugin/jsp/newsletter/newsletterForm.jsp" method="POST" class="form-horizontal">
	       	<fieldset style="margin-right: 5px;">	
	       	  <legend>Edition : <%= newsletterModel.getGroupeDuModele() %></legend>   	       	  		
	       		<% for (int i = 0; i < newsletterModel.getLibelle().length ;i++){%>
		       		
      			    <%  
			    	String defautText =  newsletter.getValeurParDefaut().length > i && Util.notEmpty(newsletter.getValeurParDefaut()[i])?newsletter.getValeurParDefaut()[i]:"";								
					%>
		       				       		
				  <jalios:select>
				  
					 <jalios:if predicate='<%= "area".equals(newsletterModel.getType1()[i]) %>'>							
						<!-- Textarea -->
						<div class="control-group">
						  <label class="control-label" for="<%=NewsletterManager.getNomTechnique(newsletter, i) %>"><%= newsletterModel.getLibelle()[i] %></label>
						  <div class="controls">                     
						    <textarea id="<%=NewsletterManager.getNomTechnique(newsletter, i) %>" name="val" style="width:100%"><%= defautText %></textarea>
						  </div>
						</div>					
					  </jalios:if>
					  
					  <jalios:if predicate='<%= "select".equals(newsletterModel.getType1()[i]) %>'>	
					  	<% String choix[] = newsletterModel.getListeDeValeurs()[i].split(";"); %> 
						<!-- Select Basic -->
						<div class="control-group">
						  <label class="control-label" for="<%=NewsletterManager.getNomTechnique(newsletter, i) %>"><%= newsletterModel.getLibelle()[i] %></label>
						  <div class="controls">
						    <select id="<%=NewsletterManager.getNomTechnique(newsletter, i) %>" name="val" class="input-xlarge">
						    	<option></option>
						    	<jalios:foreach name="itChoix" type="String" array="<%= choix %>">
							      <option <%= itChoix.trim().equals(defautText)?"selected='selected'":"non"%>><%= itChoix.trim() %></option>
							    </jalios:foreach>
						    </select>	
						  </div>
						</div>
					  </jalios:if>
						
					  <jalios:default>			
						<!-- Text input -->
						<div class="control-group">
						  <label class="control-label" for="<%=NewsletterManager.getNomTechnique(newsletter, i) %>"><%= newsletterModel.getLibelle()[i] %></label>
						  <div class="controls">					
						    <input id="<%=NewsletterManager.getNomTechnique(newsletter, i) %>" name="val" style="width:100%" value="<%= defautText %>" class="input-xlarge" type="text">  
						  </div>
						</div>
					  </jalios:default>
					
				  </jalios:select>
		       		
	       		<%}%>
	       		
	       		<hr/>
	       		<div style="text-align:right">
			       	<input type="hidden" name="newsletterId" value="<%= newsletterModel.getId() %>" />
			       	<input type="submit" name="opSubmit" value="Mise à jour de l'aperçu" class="btn"/>  
			       	<input type="submit" name="opSubmit" value="Envoyer" class="btn btn-primary confirm modal"/> 
			    </div> 
	       		
	       	 </fieldset>	       	 	     			       
	       </form>	       	       	       
	      </div>
	      	      
	      <div class="" style="display: inline-block; width: 620px;"> 
	      
	      <% if (loggedMember.belongsToGroup(channel.getGroup("j_1"))) { %>
		      <jalios:if predicate="<%= Util.notEmpty(newsletter.getIdDuGroupeDeContactsDansMailjet()) %>">
		     	<p style="background-color:#F0F0F0">
		      		<span style="vertical-align: top; margin:5px 10px 5px 10px; font-weight:bold; display:inline-block; width:80px">
		      			À... (mailjet) :
		      		</span>
		      		<span style="margin-bottom: 4px; margin-top: 4px; background-color:#F5F5F5; display:inline-block; width:505px; border: 1px solid #F9F9F9">
						<%= StringUtils.join(MailjetClient.getEmailsFromGroup(newsletter.getIdDuGroupeDeContactsDansMailjet()), "; ") %>
		      		</span>
		      	</p>
		      </jalios:if>
	      <% } %>
	      
	      <% if (loggedMember.belongsToGroup(channel.getGroup("j_1"))) { %>
		      <jalios:if predicate="<%= Util.notEmpty(newsletter.getGroupeJCMS()) %>">
		      	<p style="background-color:#F0F0F0">
		      		<span style="vertical-align: top; margin:5px 10px 5px 10px; font-weight:bold; display:inline-block; width:80px">
		      			À... (jcms) :
		      		</span>
		      		<span style="margin-bottom: 4px; margin-top: 4px; background-color:#F5F5F5; display:inline-block; width:505px; border: 1px solid #F9F9F9">
		      			<jalios:foreach name="itContact" type="Member" collection="<%= newsletter.getGroupeJCMS().getMemberSet() %>">
		      				<jalios:if predicate="<%= Util.notEmpty(itContact.getEmail()) %>">
		      					<%= itContact.getEmail() + " ; " %>
		      				</jalios:if>
		      			</jalios:foreach>
		      		</span>
		      	</p>
		      </jalios:if>
	      <% } %>
	      	
	      	<p style="background-color:#F0F0F0">
	      		<span style="vertical-align: top; margin:5px 10px 5px 10px; font-weight:bold; display:inline-block; width:80px">
	      			Objet :
	      		</span>
	      		<span style="margin-bottom: 4px; margin-top: 4px; background-color:#F5F5F5; display:inline-block; width:505px; border: 1px solid #F9F9F9">
					<% 
				       	try { 
				       		out.print(NewsletterManager.buildSubject(newsletter)); 
				       	} catch (Exception e) {
				       		out.print(e.getMessage()); 
				        } 
			       	%>
	      		</span>
	      	</p>
	      	
	       	<% 
	       	try { 
	       		out.print(NewsletterManager.buildMessage(newsletter)); 
	       	} catch (Exception e) {
	       		out.print(e.getMessage()); 
	        } 
	       	%>
	       	
         </div>                  
      </div>
      
   </jalios:default>
  </jalios:select>
</div>

 <% if(!jcmsContext.isAjaxRequest()){	%>
	<%@ include file='/work/doWorkFooter.jspf' %>
<%}%>