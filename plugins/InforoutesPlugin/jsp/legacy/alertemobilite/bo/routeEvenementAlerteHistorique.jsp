<%@	page contentType="text/html; charset=UTF-8" 								%><%
%><%@ include file="/jcore/doInitPage.jsp" 											%><%
	// Selectionne le menu
	request.setAttribute("historiqueAlertes", "true");

	// Chargement du CSS et du Javascript
	jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/plugin.css");

%><%@ include file="/work/doWorkHeader.jsp"										%><%
%><%@ include file='/jcore/portal/doPortletParams.jsp' 								%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.selector.CurrentAlerteSelector"	%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.selector.EnErreurAlerteSelector"	%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.selector.FutureAlerteSelector"		%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.selector.PastAlerteSelector"		%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.comparator.RouteEvenementAlerteAlarmComparator"	%><%
%><%@ page import="fr.cg44.plugin.alertemobilite.EventUtil"							%><%

	// Récupère les alertes en cours par dates de publication
	Set<RouteEvenementAlerte> alertesAVenir = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new FutureAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes à venir par dates de publication
	Set<RouteEvenementAlerte> alertesEnCours = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new CurrentAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes en erreur par dates de publication
	Set<RouteEvenementAlerte> alertesEnErreur = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new EnErreurAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
	// Récupère les alertes en cours par dates de publication
	Set<RouteEvenementAlerte> alertesEnvoyees = channel.select(channel.getPublicationSet(RouteEvenementAlerte.class, channel.getDefaultAdmin(), false), new PastAlerteSelector(), new RouteEvenementAlerteAlarmComparator());
%>
<div class="AlerteMobiliteHistorique AdminArea">
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