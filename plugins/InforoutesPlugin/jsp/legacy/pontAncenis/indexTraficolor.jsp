<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file='/jcore/doInitPage.jsp' %>

<%
 final String cheminPlugin="plugins/InforoutesPlugin";
 //jcmsContext.addJavaScript(cheminPlugin+"/js/pontancenis.js");
 jcmsContext.addCSSHeader(cheminPlugin+"/css/pontAncenis/types/PortletJsp/indexTraficolor.css");
%>
<jalios:javascript>
<%@ include file='/plugins/InforoutesPlugin/js/pontAncenis/pontancenis.js.jsp'%>
</jalios:javascript>
<div id="traficolor">
	<img alt="carte" class="fond" src="<%=cheminPlugin %>/images/pontAncenis/tc_EE.png" />
	
	<div id="pictosens"><img/></div>
	<div class="informations-carte">
		<div class="pmv ancenis">
			<div class="ligne1"></div>
			<div class="ligne2"></div>
			<div class="ligne3"></div>
			<div class="ligne4"></div>
		</div>
		
		<div class="pmv lire">
			<div class="ligne1"></div>
			<div class="ligne2"></div>
			<div class="ligne3"></div>
			<div class="ligne4"></div>
		</div>
		
		<div id="webcams">
			<div class="w1 CAM1-nord">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_bas_off.png">
			</div>
			<div class="w2 CAM2-nord">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_bas_off.png">
			</div>
			<div class="w3 EV1-ouest">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_haut_off.png">
			</div>
			<div class="w4 EV2-nord">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_haut_off.png">
			</div>
			<div class="w5 EV2-sud">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_bas_off.png">
			</div>
			<div class="w6 EV1-est">
				<img src="<%=cheminPlugin %>/images/pontAncenis/webcam_angle_off.png">
			</div>
		</div>
		
		<div id="popupwebcam"><img alt="" src="<%=cheminPlugin %>/images/pontAncenis/fond_webcam.png" /></div>
		<div id="imagewebcam"><img alt="" width="280px" /></div>
		<div id="refresh"><img alt="actualiser"  src="<%=cheminPlugin %>/images/pontAncenis/btn_actualiser.png"/></div>
	</div>
</div>
