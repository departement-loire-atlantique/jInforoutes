<%@	page contentType="text/html; charset=UTF-8" 								%><%
%><%@ include file="/jcore/doInitPage.jsp" 											%><%
	// Selectionne le menu
	request.setAttribute("editHistoriqueAlertesWAMenu", "true");

	// Chargement du CSS et du Javascript
	//jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/plugin.css");

%><%@ include file="/work/doWorkHeader.jspf"										%><%
%><%@ include file='/jcore/portal/doPortletParams.jsp' 								%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.CurrentAlerteSelector"	%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.EnErreurAlerteSelector"	%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.FutureAlerteSelector"		%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.selector.PastAlerteSelector"		%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.comparator.RouteEvenementAlerteAlarmComparator"	%><%
%><%@ page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.EventUtil"							%><%

	// Récupère les alertes en cours par dates de publication
	Set<RouteEvenementAlerte> alertesAVenir = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new FutureAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes à venir par dates de publication
	Set<RouteEvenementAlerte> alertesEnCours = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new CurrentAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes en erreur par dates de publication
	Set<RouteEvenementAlerte> alertesEnErreur = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new EnErreurAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes en cours par dates de publication
	Set<RouteEvenementAlerte> alertesEnvoyees = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new PastAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
%>

<style>

.borderBottom {
 border-bottom: 1px dotted #000000;
}
.AlerteMobiliteHistorique {
 font-size: 60px;
}
.AlerteMobiliteHistorique .formLabel {
 font-size: 11pt;
 font-weight: bold;
}
.AlerteMobiliteHistorique table {
 width: 100%;
 margin-bottom: 20px;
 table-layout: fixed;
}
.AlerteMobiliteHistorique table .date_exp_prevue,
.AlerteMobiliteHistorique table .date_exp_reelle {
 width: 100px;
}
.AlerteMobiliteHistorique table tr {
 background-color: #fafafa;
 font-family: Arial, Helvetica, sans-serif;
 font-size: 10pt;
 word-wrap: break-word;
}
.AlerteMobiliteHistorique table .rattachement {
 width: 170px;
}
.AlerteMobiliteHistorique table .message {
 width: 400px;
}
.AlerteMobiliteHistorique table .statut {
 width: 120px;
}
.AlerteMobiliteHistorique .request {
 background-color: #428bca;
 max-width: 100px;
 height: 10px;
 margin: 0 auto 4px auto;
 padding: 0.2em 0.6em 0.3em;
 font-size: 75%;
 font-weight: bold;
 line-height: 1;
 color: #ffffff;
 text-align: left;
 vertical-align: baseline;
 border-radius: 0.25em;
 margin-bottom: 4px;
 word-wrap: break-word;
 overflow: hidden;
}
.AlerteMobiliteHistorique .request a {
 color: white;
 text-decoration: none;
}
.AlerteMobiliteHistorique .request div {
 text-align: center;
 margin-bottom: 4px;
}
.AlerteMobiliteHistorique .request:hover {
 height: auto;
}
.AlerteMobiliteHistorique .status {
 max-height: 36px;
 overflow: hidden;
}
.AlerteMobiliteHistorique .status:hover {
 max-height: none;
}


</style>


<div class="AlerteMobiliteHistorique">
	<h1 class="boTitle"><%= glp("jcmsplugin.alertemobiliteplugin.administration.titre") %></h1>
	
	<div class="formLabel"><%= glp("jcmsplugin.alertemobiliteplugin.administration.config") %></div>
	<table class="tabPrevisionnel listBorder" cellspacing="1" cellpadding="3">
		<tr>
			<td class="listHeader">Paramètre</td>
			<td class="listHeader">Valeur</td>
		</tr>
		<tr>
			<td>Sandbox</td>
			<td align="center"><%= channel.getProperty("fr.cg44.plugin.alertemobilite.push.api.sandbox") %></td>
		</tr>
		<tr>
			<td>IOS</td>
			<td align="center"><%= channel.getProperty("fr.cg44.plugin.alertemobilite.push.api.ios") %></td>
		</tr>
		<tr>
			<td>Android</td>
			<td align="center"><%= channel.getProperty("fr.cg44.plugin.alertemobilite.push.api.android") %></td>
		</tr>
		<tr>
			<td>Windows</td>
			<td align="center"><%= channel.getProperty("fr.cg44.plugin.alertemobilite.push.api.windows") %></td>
		</tr>
	</table>
	
	<div class="formLabel"><%= glp("jcmsplugin.alertemobiliteplugin.administration.future") %></div>
	<table class="tabPrevisionnel listBorder" cellspacing="1" cellpadding="3">
		<tr>
			<td class="listHeader identifiant">Identifiant</td>
			<td class="listHeader message">Message</td>
			<td class="listHeader date_exp_prevue">Expéd. prévue</td>
		</tr>
		<jalios:select>
			<jalios:if predicate="<%= Util.notEmpty(alertesAVenir) %>">
				<jalios:foreach name="alerte" type="RouteEvenementAlerte" collection="<%= alertesAVenir %>" counter="count">
					<tr class='<%= (count%2 == 0)? "odd" : "even" %>'>
						<td class="identifiant"><%= alerte.getIdentifiant() %></td>
						<td class="message"><%= alerte.getTitle() %></td>
						<td class="date_exp_prevue" align="center"><%= EventUtil.sdf.format(alerte.getDateExpedition()) %></td>
					</tr>
				</jalios:foreach>
			</jalios:if>
			<jalios:default>
				<tr>
					<td align="center" colspan="3"><%= glp("jcmsplugin.alertemobiliteplugin.administration.future.no") %></td>
				</tr>
			</jalios:default>
		</jalios:select>
	</table>
	
	<div class="formLabel"><%= glp("jcmsplugin.alertemobiliteplugin.administration.current") %></div>
	<table class="tabEncours listBorder" cellspacing="1" cellpadding="3">
		<tr>
			<td class="listHeader rattachement">Rattachement</td>
			<td class="listHeader identifiant">Identifiant</td>
			<td class="listHeader message">Message</td>
			<td class="listHeader date_exp_prevue">Expéd. prévue</td>
		</tr>
		<jalios:select>
			<jalios:if predicate="<%= Util.notEmpty(alertesEnCours) %>">
				<jalios:foreach name="alerte" type="RouteEvenementAlerte" collection="<%= alertesEnCours %>" counter="count">
					<tr class='<%= (count%2 == 0)? "odd" : "even" %>'>
						<td class="identifiant"><%= Util.isEmpty(alerte.getRattachement()) ? "" : alerte.getRattachement() %></td>
						<td class="message"><%= Util.isEmpty(alerte.getIdentifiant()) ? "N/A" : alerte.getIdentifiant() %></td>
						<td><%= alerte.getTitle() %></td>
						<td class="date_exp_prevue" align="center"><%= EventUtil.sdf.format(alerte.getDateExpedition()) %></td>
					</tr>
				</jalios:foreach>
			</jalios:if>
			<jalios:default>
				<tr>
					<td align="center" colspan="4"><%= glp("jcmsplugin.alertemobiliteplugin.administration.current.no") %></td>
				</tr>
			</jalios:default>
		</jalios:select>
	</table>
	
	<div class="formLabel">Envois en erreur</div>
	<table class="tabEnErreur listBorder" cellspacing="1" cellpadding="3">
		<tr>
			<td class="listHeader rattachement">Rattachement</td>
			<td class="listHeader identifiant">Identifiant</td>
			<td class="listHeader message">Message</td>
			<td class="listHeader date_exp_prevue">Expéd. prévue</td>
		</tr>
		<jalios:select>
			<jalios:if predicate="<%= Util.notEmpty(alertesEnErreur) %>">
				<jalios:foreach name="alerte" type="RouteEvenementAlerte" collection="<%= alertesEnErreur %>" counter="count">
					<tr class='<%= (count%2 == 0)? "odd" : "even" %>'>
						<td class="identifiant"><%= Util.isEmpty(alerte.getRattachement()) ? "" : alerte.getRattachement() %></td>
						<td class="message"><%= Util.isEmpty(alerte.getIdentifiant()) ? "N/A" : alerte.getIdentifiant() %></td>
						<td><%= alerte.getTitle() %></td>
						<td class="date_exp_prevue" align="center"><%= EventUtil.sdf.format(alerte.getDateExpedition()) %></td>
					</tr>
				</jalios:foreach>
			</jalios:if>
			<jalios:default>
				<tr>
					<td align="center" colspan="4">Aucun envoi en erreur</td>
				</tr>
			</jalios:default>
		</jalios:select>
	</table>
	
	<div class="formLabel"><%= glp("jcmsplugin.alertemobiliteplugin.administration.last") %></div>
	<table class="tabEnvoyes listBorder" cellspacing="1" cellpadding="3">
		<tr>
			<td class="listHeader rattachement">Rattachement</td>
			<td class="listHeader identifiant">Identifiant</td>
			<td class="listHeader message">Message</td>
			<td class="listHeader date_exp_prevue">Expéd. prévue</td>
			<td class="listHeader date_exp_reelle">Expéd. réelle</td>
			<td class="listHeader statut">Statut</td>
		</tr>
		<jalios:select>
			<jalios:if predicate="<%= Util.notEmpty(alertesEnvoyees) %>">
				<jalios:foreach name="alerte" type="RouteEvenementAlerte" collection="<%= alertesEnvoyees %>" counter="count">
					<tr class='<%= (count%2 == 0)? "odd" : "even" %>'>
						<td class="rattachement"><%= alerte.getRattachement() %></td>
						<td class="identifiant"><%= Util.isEmpty(alerte.getIdentifiant()) ? "N/A" : alerte.getIdentifiant() %></td>
						<td class="message"><%= alerte.getTitle() %></td>
						<td class="date_exp_prevue" align="center"><%= EventUtil.sdf.format(alerte.getDateExpedition()) %></td>
						<td class="date_exp_reelle" align="center"><jalios:if predicate="<%= Util.notEmpty(alerte.getDateExpeditionReelle()) %>"><%= EventUtil.sdf.format(alerte.getDateExpeditionReelle()) %></jalios:if></td>
						<td class="statut" align="center">
							<jalios:if predicate="<%= Util.notEmpty(alerte.getRequete()) %>">
								<div class="request"><div>Requête:</div><%= alerte.getRequete() %></div>
							</jalios:if>
							<div class="status">
							<jalios:select>
								<jalios:if predicate="<%= EventUtil.isIdNotification(alerte.getStatut()) %>">
									<a target="_blank" href=""><%= alerte.getStatut() %></a>
								</jalios:if>
								<jalios:default>	
									<%= alerte.getStatut() %>
								</jalios:default>
							</jalios:select>
							</div>
						</td>
					</tr>
				</jalios:foreach>
			</jalios:if>
			<jalios:default>
				<tr>
					<td align="center" colspan="6"><%= glp("jcmsplugin.alertemobiliteplugin.administration.last.no") %></td>
				</tr>
			</jalios:default>
		</jalios:select>
	</table>
</div>

<%@ include file='/work/doWorkFooter.jspf' %>