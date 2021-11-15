<%@page import="fr.cg44.plugin.webcam.WebCamManager"%>
<%@ include file='/jcore/doInitPage.jsp'%>
<%@ include file='/jcore/portal/doPortletParams.jsp'%>

<%
	PortletJsp box = (PortletJsp) portlet;

	jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/psn/types/PortletJsp/webcam.css");

	jcmsContext.addJavaScript("plugins/InforoutesPlugin/js/psn/jquery.cycle2.min.js");
	jcmsContext.addJavaScript("plugins/InforoutesPlugin/js/psn/plugin.js");
	String imgWebcamHeight = "264";
	String imgWebcamWidth = "352";
	
%>
<%
	WebCamManager webcamManager = WebCamManager.getInstance();
%>

<jalios:javascript>
setInterval(function(){
    jQuery("#webcam-image-psn").attr("src", "/upload/webcam/psn/psn_webcam_1.jpg?"+new Date().getTime());
},2000);
</jalios:javascript>

<div class="psn-webcam">
	<div class="webcam-carousel">
		<div class="thumbs"></div>
		
		<div id="webcam-content">
			<div class="images-webcam cycle-slideshow" data-cycle-timeout=0 data-cycle-slides="> div.main" data-cycle-pager=".thumbs" data-cycle-allow-wrap="false">
					<div id="image_courante" class="main" data-cycle-pager-template="<a href=#>Direct</a>">
					<div class="webcam-time" style="margin-bottom: 5px;"></div>
					<div class="webcam-image"><img id="webcam-image-psn" src="/upload/webcam/psn/psn_webcam_1.jpg" height="<%=imgWebcamHeight%>" width="<%=imgWebcamWidth%>" />
						</div>
				</div>
			</div>					
		</div>				
		<div class="clear"></div>
	</div>	
</div>

	
<jalios:if predicate='<%= Util.notEmpty(box.getDataImage()) %>'>
	<div class="psn-camera">
		<a href="<%= box.getDataImage() %>" class="modal modal-cg"><p> <img src="plugins/InforoutesPlugin/images/psn/psn_webcam.png" alt="" /> <psan class="plan"><%= glp("fr.cg44.plugin.pont.voir.plan") %></psan> </p> </a>
	</div>	
</jalios:if>

