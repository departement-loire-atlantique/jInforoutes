<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jspf' %>
<%@ include file='/jcore/portal/doPortletParams.jspf' %><% 
PortletJspCollection box = (PortletJspCollection) portlet; 
ServletUtil.backupAttribute(pageContext , "ShowChildPortalElement");
%>
<%@ include file='/types/AbstractCollection/doIncludePortletCollection.jspf'%>
<%
ServletUtil.restoreAttribute(pageContext , "ShowChildPortalElement");
%>

<%@ include file='bacs.jspf' %>