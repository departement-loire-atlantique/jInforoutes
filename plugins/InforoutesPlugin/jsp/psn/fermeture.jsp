<%@page import="fr.cg44.plugin.socle.SocleUtils"%>
<%@ page import="fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
PSNSens itFermeture = PontHtmlHelper.getProchaineFermeture();
%>

<jalios:if predicate='<%= Util.notEmpty(itFermeture) && itFermeture.getDateDeDebut() != null && itFermeture.getEdate() != null %>'>
    <div class="ds44-inner-container ds44-mtb5">
        <div class="grid-12-small-1">
            <div class="col-12">
            
				<div id="error-mgs9" class="ds44-msg-container error txtcenter" tabindex="-1" data-old-tabindex="-1">
				    <p class="ds44-message-text"><i class="icon icon-attention icon--sizeM" aria-hidden="true"></i>
				        <span class="ds44-iconInnerText">
                            <jalios:select>
                                <%
                                String dateDebut = SocleUtils.formatDate("dd/MM/yyyy", itFermeture.getDateDeDebut());
                                String dateFin = SocleUtils.formatDate("dd/MM/yyyy", itFermeture.getEdate());
                                String heureDebut = SocleUtils.formatDate("HH:mm", itFermeture.getDateDeDebut());
                                String heureFin = SocleUtils.formatDate("HH:mm", itFermeture.getEdate());
			                     %>
			                     
			                    <jalios:if predicate='<%= PontHtmlHelper.isOuvertureEtFermetureLeMemeJour(itFermeture) %>'>
                                    <%= glp("jcmsplugin.inforoutes.fermeture-date", dateDebut, heureDebut, heureFin) %>
			                    </jalios:if>
			                    
			                    <jalios:default>
                                    <%= glp("jcmsplugin.inforoutes.fermeture-periode", dateDebut, heureDebut, dateFin, heureFin) %>
			                    </jalios:default>
			                    
			                </jalios:select>
				        </span>
				    </p>

				</div>         


              
            </div>
        </div>
    </div>
</jalios:if>
