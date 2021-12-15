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

<div class="ds44-inner-container ds44-mtb3">
    <div class="grid-12-small-1">
        <div class="col-3"><%= getPortlet(bufferMap,"horaires") %></div>
        <div class="col-1 grid-offset ds44-hide-tiny-to-medium"></div>
        <div class="col-8">

			<h2 class="h2-like"><%= glp("jcmsplugin.inforoutes.en-direct") %></h2>
			<div class="grid-12-small-1 ds44-noMrg">
			    <div class="col-6"><%= getPortlet(bufferMap,"webcam1") %></div>
			    <div class="col-6"><%= getPortlet(bufferMap,"webcam2") %></div>
			</div>

        </div>
    </div>
</div>

<div class="ds44-inner-container ds44-mtb5">
    <div class="grid-12-small-1 ds44-mtb3">
        <div class="col-12">
          <%= getPortlet(bufferMap,"evenementsEnCours") %>
        </div>
    </div>
    <div class="grid-12-small-1 ds44-mtb3">
        <div class="col-12">
          <%= getPortlet(bufferMap,"evenementsAVenir") %>
        </div>
    </div>
</div>