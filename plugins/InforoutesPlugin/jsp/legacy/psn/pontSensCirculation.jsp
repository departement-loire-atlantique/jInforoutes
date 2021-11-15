<%@page import="fr.cg44.plugin.pont.PontHtmlHelper"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%--
  @Summary: Page d'affichage des alertes
  @Category: Created
  @Author: sroux
  @Customizable: True
  @Requestable: True
--%>
<%
	
%><%@ include file='/jcore/doInitPage.jsp'%>
<%@ include file='/jcore/portal/doPortletParams.jsp'%>
<%@ include file='/front/doFullDisplay.jspf'%>
<%


jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/psn/types/PortletJsp/pontSensCirculation.css");

PortletJsp box = (PortletJsp) portlet;
PSNSens sensCourant = (PSNSens)PontHtmlHelper.getModeCirculationCourant();
List<PSNSens> prochainsSens = PontHtmlHelper.getProchaineModeCirculation();

Date now = new Date();

%>
<div class="psn-sens">
	
	<h2 class="titre-psc"><%= box.getDisplayTitle() %></h2>
	
	<jalios:if predicate='<%=Util.isEmpty(sensCourant) || Util.isEmpty(prochainsSens) %>'>
		<p><%=glp("fr.cg44.plugin.pont.service.indisponible")%></p>
	</jalios:if>
	
	
	
	<jalios:if predicate='<%=Util.notEmpty(sensCourant) && Util.notEmpty(prochainsSens)%>'>
		<% CG44PontPictogramme picto = sensCourant.getSensDeCirculation(); %>
		
		<div class="psn-table">
		
			<!--  Actuellement -->
			<div class="sens-leftcol">
				<p>
					<span class="col-title"> Actuellement : </span>
					<span class="direction"><%= (Util.notEmpty(picto.getMentionHTMLHaut())) ? picto.getMentionHTMLHaut() : "" %></span>
					
					<span class="direction">
						<img width="81" height="82" alt="<%=picto.getTexteAlternatif()%>"
							title="<%=picto.getTexteAlternatif()%>"
							src="<%=picto.getPictogramme()%>" />
					</span>	
							
					<span class="direction"><%= (Util.notEmpty(picto.getMentionHTMLBas())) ? picto.getMentionHTMLBas() : "" %></span>				
				</p>	
			</div>
			
			
			<!--  Changements programmés  -->
			<div class="sens-rightcol">
				<p>
					<span class="col-title"> Changements programmés : </span>
								
					<jalios:foreach collection="<%=prochainsSens%>" type="PSNSens" name="prochainSens">
						<% CG44PontPictogramme prochainPicto = prochainSens.getSensDeCirculation(); %>
						<span class="changements" >
							<span class="infotrafic-stnaz-mode-picto" >
								<img height="50" width="50" alt="<%=prochainPicto.getTexteAlternatif()%>"
									title="<%=prochainPicto.getTexteAlternatif()%>"
									src="<%=prochainPicto.getPictogramme()%>" />
							</span>
							<jalios:if predicate="<%=Util.notEmpty( prochainSens.getDateDeDebut() ) && now.after(prochainSens.getDateDeDebut())%>">
							<span class="infotrafic-stnaz-mode-info nextdate" >
									Changement <br>imminent
							</span>
							</jalios:if>
							
							<jalios:if predicate="<%=Util.notEmpty( prochainSens.getDateDeDebut() ) && now.before(prochainSens.getDateDeDebut()) %>">
								<span class="infotrafic-stnaz-mode-info nextdate" >
										Le <jalios:date date='<%=prochainSens.getDateDeDebut()%>' format='dd|MM|yyyy' /><br>
										à <jalios:date date='<%=prochainSens.getDateDeDebut()%>' format='HH:mm' />
		
								</span>
							</jalios:if>
						</span>
					</jalios:foreach>											
				</p>	
			</div>	
			
		</div>			
	</jalios:if>
</div>

<jalios:if predicate='<%= Util.notEmpty(box.getAbstract()) %>'>	
	<p style="margin-top:12px" class="description-circu"><%= box.getAbstract() %></p>
</jalios:if>
	
<div class="clear"></div>	



