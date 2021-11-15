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
<div class="psn-webcam">
	<div class="webcam-carousel">
		<div class="thumbs"></div>
		
		<div id="webcam-content">
			<div class="images-webcam cycle-slideshow" data-cycle-timeout=0
								data-cycle-slides="> div.main" data-cycle-pager=".thumbs"
				data-cycle-allow-wrap="false">
				<jalios:if
					predicate="<%=webcamManager == null ||!webcamManager.isWebCamActivated() || !webcamManager.isValidImage()%>">
					<div id="image_courante" class="main"
						data-cycle-pager-template="<a href=#>Direct</a>">
						<div class="webcam-time">Le flux de webcam est momentanément
							indisponible</div>
						<div class="webcam-image"><img id="img_fixe"
							src="plugins/InforoutesPlugin/images/psn/flux_indispo.png" height="<%=imgWebcamHeight%>"
							width="<%=imgWebcamWidth%>" />
							</div>
					</div>
				</jalios:if>

				<jalios:if
					predicate="<%=webcamManager !=null && webcamManager.isWebCamActivated() && webcamManager.isValidImage()%>">
					<div id="image_courante" class="main"
						data-cycle-pager-template="<a href='#' alt='Image en direct de la circulation sur le pont de saint nazaire'  title='Image en direct de la circulation sur le pont de saint nazaire'>Direct</a>">
						<%
							String imageWebcamCourant = String.valueOf(WebCamManager.getInstance().getLastCapture());
						%>
						<div class="webcam-time">
							Dernière capture à <span id="date_refresh"><%=WebCamManager.getFormattedDateImage(imageWebcamCourant, WebCamManager.heureMinuteSecondeFormatter)%></span>
							<span id="lien-auto" style="display:none">(Mise à jour auto <a id="arret"
								href="javascript:JCMS.plugin.webcam.WebcamUtils.pauseWebcam()"
								class="webcam-pause"								
								title="Désactiver le rafraichissement automatique de l'image de la circulation sur le pont de saint nazaire"
								>Désactiver</a><a style="display: none"
								id="reprise"
								href="javascript:JCMS.plugin.webcam.WebcamUtils.pauseWebcam()"								
								title="Désactiver le rafraichissement automatique de l'image de la circulation sur le pont de saint nazaire"
								class="webcam-pause">Activer</a>)</span>
						</div><div class="webcam-image">
						<img id="img_refresh"
							src="<%=WebCamManager.getRelativeImage(imageWebcamCourant)%>" alt="Image en direct de la circulation sur le pont de saint nazaire" title="Image en direct de la circulation sur le pont de saint nazaire"
							height="<%=imgWebcamHeight%>" width="<%=imgWebcamWidth%>" /></div>
					</div>
					<%
					Collection<String> listeImage = webcamManager.getHistoryImage();
						int imgCount = 1;
							for (String imageWebcam : listeImage) {
					%>
					<div class="main" style="display:none"
						data-cycle-pager-template="<a href=# title='Voir image de la circulation sur le pont de saint nazaire <%=webcamManager.getHistoryImageDate(imageWebcam)%>'><%=webcamManager.getHistoryImageDate(imageWebcam)%></a>">
						<div class="webcam-time">
							Capturée à
							<%=webcamManager.getHistoryImageDate(imageWebcam)%>
							(<a href="" data-cycle-cmd="goto" data-cycle-arg="0"
								title="Image en direct de la circulation sur le pont de saint nazaire">Retour au direct</a>)
						</div>
						<div class="webcam-image"><img src="<%=imageWebcam%>" height="<%=imgWebcamHeight%>" width="<%=imgWebcamWidth%>" alt="Image de la circulation sur le pont de saint nazaire à <%=webcamManager.getHistoryImageDate(imageWebcam)%>"  title="Image de la circulation sur le pont de saint nazaire à <%=webcamManager.getHistoryImageDate(imageWebcam)%>"/></div>
					</div>
					<%
						imgCount++;
							}
					%>
			</jalios:if>
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

