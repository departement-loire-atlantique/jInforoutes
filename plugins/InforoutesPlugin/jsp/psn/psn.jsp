<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%@ include file='/jcore/portal/doPortletParams.jspf' %>
<%@ page import="fr.cg44.plugin.inforoutes.legacy.pont.PontHtmlHelper"%>
<%
PortletJspCollection box = (PortletJspCollection) portlet; 
ServletUtil.backupAttribute(pageContext , "ShowChildPortalElement");
%>
<%@ include file='/types/AbstractCollection/doIncludePortletCollection.jspf'%>
<%
ServletUtil.restoreAttribute(pageContext , "ShowChildPortalElement");

boolean isPontFerme = PontHtmlHelper.isPontFerme();
request.setAttribute("isPontFerme", isPontFerme);
%>

<%@ include file='psn.jspf' %>