<%@page import="fr.cg44.plugin.infotraficplugin.helper.BdlHelper"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file='/jcore/doInitPage.jsp'%>
<%@ include file='/jcore/portal/doPortletParams.jsp'%>

<%
jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/bdl/types/jsp/horairesBacsDeLoire.css");

// La liaison du bac de loire suivant la catégorie courante
String liaison = "";
if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.indre.cat"))){
	liaison = "liaison1";
}else if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.coueron.cat"))){
	liaison = "liaison2";
}

%>

<div class="horaires-bdl">

	<table>
		<caption>Les horaires du bac entre <%= BdlHelper.getRequete(liaison, "from") %> et <%= BdlHelper.getRequete(liaison, "to") %></caption>
		
		<tbody>	
			<!-- Liaison destinations -->		
			<tr>
				<td class="blanc"></td>
				<th class="ligne"><%= BdlHelper.getRequete(liaison, "from") %></th>
				<th class="ligne"><%= BdlHelper.getRequete(liaison, "to") %></th>
			</tr>			
			<!-- Premiers départs -->
			<tr>
				<th class="colonne">Premiers départs</td>
				<td><%= BdlHelper.getRequete(liaison, "from_first") %></td>
				<td><%= BdlHelper.getRequete(liaison, "to_first") %></td>
			</tr>
			<!-- Départs suivants -->
			<tr>
				<th class="colonne">Départs suivants
				<td colspan="2"><%= BdlHelper.getRequete(liaison, "from_period")  %></td>
			</tr>
			<!-- Derniers départs -->
			<tr>
				<th class="colonne">Derniers départs</td>
				<td><%= BdlHelper.getRequete(liaison, "from_last") %></td>
				<td><%= BdlHelper.getRequete(liaison, "to_last") %></td>
			</tr>
		</tbody>
	</table>
	
	<p><%= BdlHelper.getRequete(liaison, "from_message") %></p>
	
</div>