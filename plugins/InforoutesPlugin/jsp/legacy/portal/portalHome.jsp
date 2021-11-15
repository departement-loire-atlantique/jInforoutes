<%@ page contentType="text/html; charset=UTF-8" 
%><%@ include file='/jcore/doInitPage.jsp' 
%><%@ include file='/jcore/portal/doPortletParams.jsp'  
%><% 
	PortalJspCollection box = (PortalJspCollection) portlet;
  ServletUtil.backupAttribute(pageContext , "ShowChildPortalElement");
//Drag and Drop required a custom class wrapper 'dnd-container' and the wrapper ID. They also REQUIRED a DOM ID
 String dndCSS = (isLogged && loggedMember.canWorkOn(box)) ? "ID_"+box.getId()+" dnd-container" : ""; 
 jcmsContext.addCSSHeader("plugins/EServicePlugin/css/portal/portal.css");
 jcmsContext.addCSSHeader("plugins/EServicePlugin/css/portal/portalHome.css"); 
%>
  
<%@ include file='/types/AbstractCollection/doIncludePortletCollection.jsp'%>
<jalios:include target="OLD_BROWSER"/>
<div class="portail">
	

<%= getPortlet(bufferMap,"header") %>

	<div class="container" >
		<% String alertContent=getPortlet(bufferMap, "alert");
	if(Util.notEmpty(alertContent)){
	%>
		<div class="alerts-container"><%-- PQF des alertes--%>
			<%=alertContent%>
		</div><%
		}%>
		<div class="row-fluid">
			<div class="span9 block-spacer">
				<%= getPortlet(bufferMap,"bloc1") %>
			</div>
			<div class="span3 block-spacer">
				<%= getPortlet(bufferMap,"bloc2") %>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<%= getPortlet(bufferMap,"bloc3") %>
			</div>
		</div>	
	</div>
	<%= getPortlet(bufferMap,"footer") %>
</div>