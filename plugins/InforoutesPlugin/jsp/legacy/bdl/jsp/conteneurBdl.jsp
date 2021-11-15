<%@ page contentType="text/html; charset=UTF-8" 
%><%@ include file='/jcore/doInitPage.jsp' 
%><%@ include file='/jcore/portal/doPortletParams.jsp'  
%><% 
	PortletJspCollection box = (PortletJspCollection) portlet;
  ServletUtil.backupAttribute(pageContext , "ShowChildPortalElement");

%>
  
<%@ include file='/types/AbstractCollection/doIncludePortletCollection.jsp'%>

<div class="JSPCollection">
	
		<div class="row-fluid">
			<div class="span4 block-spacer">
				<%= getPortlet(bufferMap,"gauche") %>
			</div>
			<div class="span8 block-spacer">
				<%= getPortlet(bufferMap,"droite") %>
			</div>
		</div>
		
		<div class="row-fluid">
			<div class="span12">
				<%= getPortlet(bufferMap,"bas") %>
			</div>
		</div>	
			
</div>