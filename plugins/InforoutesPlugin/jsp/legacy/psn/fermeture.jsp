<%@page import="fr.cg44.plugin.pont.PontHtmlHelper"%>
<%@ include file='/jcore/doInitPage.jsp' %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/psn/types/PortletJsp/fermeture.css");

PSNSens itFermeture = PontHtmlHelper.getProchaineFermeture();
%>

<% if (Util.notEmpty(itFermeture) && itFermeture.getDateDeDebut() != null && itFermeture.getEdate() != null) { %>
  <div class="skin-fermeture">
		<div class="fermeture">
            <%if(PontHtmlHelper.isOuvertureEtFermetureLeMemeJour(itFermeture)){ %>
            <p class='uneligne'><span class="strong">Fermeture</span> le <jalios:date date='<%= itFermeture.getDateDeDebut() %>' format='dd/MM/yyyy'/> 
            de <jalios:date date='<%= itFermeture.getDateDeDebut() %>' format='HH:mm'/>
            à <jalios:date date='<%= itFermeture.getEdate() %>' format='HH:mm'/>
            </p>
            <%}else{ %>
            <p>
               <span class="strong">Fermeture</span> du <jalios:date date='<%= itFermeture.getDateDeDebut() %>' format='dd/MM/yyyy'/> - <jalios:date date='<%= itFermeture.getDateDeDebut() %>' format='HH:mm'/>
                au  <jalios:date date='<%= itFermeture.getEdate() %>' format='dd/MM/yyyy'/> - <jalios:date date='<%= itFermeture.getEdate() %>' format='HH:mm'/>
			<%} %>
		</div>
	</div>
<% } %>

