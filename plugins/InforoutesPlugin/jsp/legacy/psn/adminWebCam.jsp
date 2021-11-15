<%@page import="fr.cg44.plugin.webcam.util.WebCamJcmsProperties"%>
<%@page import="fr.cg44.plugin.webcam.WebCamManager"%>
<%@ include file='/jcore/doInitPage.jsp'%>
<% 

  if (!isLogged) {
    sendForbidden(request, response);
    return;
  }
//gestion des paramètres
request.setAttribute("webcamAdminMenu", "true");
String purge = request.getParameter("purge");
String message="";
if("true".equals(purge)){
	int nbDelete= WebCamManager.getInstance().purgeHistorique();
	message=" Purge de "+nbDelete+" images de l'historique OK";
}
if(loggedMember.isAdmin()) {
	String restart = request.getParameter("restart");
	
	if("true".equals(restart)){
		try{
			WebCamManager.getInstance().restartWebCamCapture();
			message="Redémarrage de la WebCam Ok";
		}	catch(Exception e){
			message = e.getMessage();
		}
	}
}


%><%@ include file='/work/doWorkHeader.jsp'%>


<div id="adminFunc" class="mainBlock" style="margin-bottom: 15px;">
	<h1 class="boTitle icon iconMonitoring">
		Gestion : Webcam
	</h1>
	<div style="font-weight: bold; color: red"><%=("".equals(message))?"":message %></div>
	
	<div class="row-fluid">
		<div class="box span6">    
	       <div class="box-header">
	         <h3>Purge</h3>
	       </div>
			<ul style="list-style:none">
				<li><a
					href="plugins/InforoutesPlugin/jsp/psn/adminWebCam.jsp?purge=true"><img
						class="icon" alt=""
						src="images/jalios/icons/cross-hover.gif">Purger les
						images de l'historique</a></li>
				<%if(loggedMember.isAdmin()) {%>
				<li><a
					href="plugins/InforoutesPlugin/jsp/psn/adminWebCam.jsp?restart=true"><img
						class="icon" alt=""
						src="images/jalios/icons/refresh.png">Redémarrer
						la webcam</a></li>
				<%} %>
			</ul>
	       </div>
         
	         <% WebCamManager camManager = WebCamManager.getInstance(); %>
	         <div class="box span6">
	         	<div class="box-header">
				<h3>Information sur la webcam</h3>
			</div>
	
			<ul style="list-style:none">
				<li>Derniere capture: <span style="font-weight: bold"><jalios:date
							date='<%=new Date(camManager.getLastCapture()) %>'
							format='dd/MM/yyyy HH:mm:ss' /></span></li>
				<li>Date de démarrage de la capture: <span
					style="font-weight: bold"><jalios:date
							date='<%=camManager.getWebCamCaptureStart()%>'
							format='dd/MM/yyyy HH:mm:ss' /></span></li>
				<li>Capture de l'image active: <span
					style="font-weight: bold"><%=(camManager.isWebCamCaptureAlive())?"<span style=\"color:green\">OUI":"<span style=\"color:red\">NON" %></span></span></li>
				<li>La dernière image valide a moins de <%=WebCamJcmsProperties.DELAI_WEBCAM_EN_ERREUR.getInteger()%>s:
					<span style="font-weight: bold"><%=(camManager.isValidImage())?"<span style=\"color:green\">OUI":"<span style=\"color:red\">NON" %></span></span>
												</li>
	<li>Mail d'alerte envoyé après <%=WebCamJcmsProperties.DELAI_MAIL_ALERT_SUPERVISION.getLong()%>s:
					<span style="font-weight: bold"><%=(camManager.isMailSend())?"<span style=\"color:red\">OUI":"<span style=\"color:green\">NON" %></span></span>
				</li>
	
			</ul>
		</div>
	</div>
         
</div>
	
	

	<%@ include file='/work/doWorkFooter.jsp'%>