<%@ page contentType="text/html; charset=UTF-8" %><%
%><%-- This file has been manualy generated. --%><%
%><%--
  @Summary: Portlet JSP Collection display page for fiches horaires
  @Category: Created
  @Author: sroux
  @Customizable: True
  @Requestable: True
--%><%
%>
<%@ include file='/jcore/doInitPage.jsp'%>
<%@ include file='/jcore/portal/doPortletParams.jsp'%>

<% ServletUtil.backupAttribute(pageContext , "ShowChildPortalElement"); %>
<%-- Charge toutes les portlets dans une nouvelle ArrayList "bufferList" --%>
<% ServletUtil.restoreAttribute(pageContext , "ShowChildPortalElement");%>

<%
jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/bdl/types/jsp/webcamBacsDeLoire.css");

PortletJsp box = (PortletJsp) portlet;

// Routeur de la webcam par rapport a la catégorie courante (indre ou coueron)
String propertyWebCamName = "";
String webcamPrefix = "";
if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.indre.cat"))){
	propertyWebCamName = "cg44.mobilityplugin.bdl.indre";
	webcamPrefix = "indre";
}else if(currentCategory.getId().equals(channel.getProperty("cg44.mobilityplugin.bdl.coueron.cat"))){
	propertyWebCamName = "cg44.mobilityplugin.bdl.coueron";
	webcamPrefix = "coueron";
}


String[] propWebCam1 = new String[]{"",""};
String[] propWebCam2 = new String[]{"",""};
String webCam1SansCachePath = "upload/webcam/bdl-ftp/" + webcamPrefix + "1.jpg";
String webCam2SansCachePath = "upload/webcam/bdl-ftp/" + webcamPrefix + "2.jpg";
if (Util.notEmpty(propertyWebCamName)) {
	
	String propWebCam1Prop = channel.getProperty(propertyWebCamName+"1");
	String propWebCam2Prop = channel.getProperty(propertyWebCamName+"2");
	
	if (Util.notEmpty(propWebCam1Prop)) {
		propWebCam1 = Util.split(propWebCam1Prop, "|");
	}
	
	if (Util.notEmpty(propWebCam2Prop)) {
		propWebCam2 = Util.split(propWebCam2Prop, "|");
	}
}
%>

<div class="webcam-bac-de-loire page">
   <h2><%= box.getDisplayTitle() %></h2>
	<div class="webcam1">
	<div class="titre-webcam"><%=propWebCam1[0] %></div>
	<div class="image-webcam"><img id="img1" src="<%= webCam1SansCachePath %>" alt="image des bacs de <%=propWebCam1[0] %>" title="image des bacs de <%=propWebCam1[0] %>"/></div>
	<div class="info-webcam">Rafraîchie toutes les <%=channel.getProperty("cg44.mobilityplugin.bdl.refresh","10")%> s <span class="time"></span> (Mise à jour auto <a id="arret"
		href="javascript:pauseWebcam1()"
		class="webcam-pause"
		title="Désactiver le rafraîchissement automatique de l'image des bacs de <%=propWebCam1[0] %>"
		>Désactiver</a><a style="display: none"
		id="reprise"
		href="javascript:pauseWebcam1()"
		title="Désactiver le rafraîchissement automatique de l'image des bacs de <%=propWebCam1[0] %>"
		class="webcam-pause">Activer</a>)</div>
	</div>
	<div class="webcam-space"></div>
	<div class="webcam2">
	<div class="titre-webcam"><%=propWebCam2[0] %></div>
	<div class="image-webcam"><img id="img2" src="<%= webCam2SansCachePath %>"  alt="image des bacs de <%=propWebCam2[0] %>" title="image des bacs de <%=propWebCam2[0] %>"/></div>
	<div class="info-webcam">Rafraîchie toutes les <%=channel.getProperty("cg44.mobilityplugin.bdl.refresh","10")%> s (Mise à jour auto <a id="arret"
				href="javascript:pauseWebcam2()"
				class="webcam-pause"
				title="Désactiver le rafraîchissement automatique de l'image des bacs de <%=propWebCam2[0] %>"
				>Désactiver</a><a style="display: none"
				id="reprise"
				href="javascript:pauseWebcam2()"
				alt="Désactiver le rafraîchissement automatique de l'image des bacs de <%=propWebCam2[0] %>"
				title="Désactiver le rafraîchissement automatique de l'image des bacs de <%=propWebCam2[0] %>"
				class="webcam-pause">Activer</a>)</div>
	</div>
	<div class="clear">
	</div>
      <script>
      //INITITALISATION  DES PROPRIETES COMMUNES
      var nbSecond =  <%=channel.getProperty("cg44.mobilityplugin.bdl.refresh","10")%>/2;
      var maxCountImage = 12;
     
     //WEBCAM 1
	var webCamActive1 = true;
    var imageCount1=0;
    function refreshLastImageWebCam1(){
    	
		now=new Date();
		if(webCamActive1){
			var img = jQuery("<img />").attr("src", "<%= webCam1SansCachePath %>?timestamp=" + now.getTime())
				.load(function() {
		        	if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
		        	} else {
						jQuery("#img1").attr("src", img.attr("src"));
		        	}
		        });
    	 
    	 	imageCount1++;
	    	if(imageCount1<maxCountImage){
				setTimeout(refreshLastImageWebCam1,nbSecond*1000);
			}else{
				pauseWebcam1();
			}
		}
	}			
    
    function pauseWebcam1(){
		webCamActive1=!webCamActive1;
		jQuery(".webcam1 #arret").toggle();
		jQuery(".webcam1  #reprise").toggle();
		if(webCamActive1){
			if(imageCount1>=maxCountImage){
				 imageCount1=0;
			 }
			refreshLastImageWebCam1();
		}
	}
    
	setTimeout(refreshLastImageWebCam1,nbSecond*1000);
	
	//WEBCAM 2 
	var imageCount2=0;
	var webCamActive2 = true;
    function refreshLastImageWebCam2(){
    	if(webCamActive2){
    	now=new Date();
   		var img = jQuery("<img />").attr("src", "<%= webCam2SansCachePath %>?timestamp=" + now.getTime())
		    .load(function() {
		        if (!this.complete || typeof this.naturalWidth == "undefined" || this.naturalWidth == 0) {
		           
		        } else {
					 jQuery("#img2").attr("src", img.attr("src"));
		        }
		    });
	    imageCount2++;
	    if(imageCount2<maxCountImage){
	    	setTimeout(refreshLastImageWebCam2,nbSecond*1000);
	     }else{
	     	pauseWebcam2();
		}
		}
	}	
      
      
      function pauseWebcam2(){
			webCamActive2=!webCamActive2;
			if(webCamActive2){
				jQuery(".webcam2 #arret").show();
				jQuery(".webcam2  #reprise").hide();
				if(imageCount2>=maxCountImage){
					 imageCount2=0;
				 }
				refreshLastImageWebCam2();
				 
			}else{
				jQuery(".webcam2 #arret").hide();
				jQuery(".webcam2  #reprise").show();
			}
		}
      setTimeout(refreshLastImageWebCam2,nbSecond*1000);
  
      </script>
</div>