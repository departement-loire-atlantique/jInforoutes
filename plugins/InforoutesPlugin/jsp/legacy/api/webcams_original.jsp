<%@ 	page contentType="application/json; charset=UTF-8" 				%><%
%><%@ 	include file='/jcore/doInitPage.jsp' 						%><%
%><%@ 	include file='corsFilter.jsp' 						%><%
%><%@	page import="fr.cg44.plugin.alertemobilite.ws.Webcams"		%><%
%><%@	page import="java.io.InputStream"							%><%
%><%@	page import="java.io.FileInputStream"						%><%
%><%@	page import="java.io.BufferedOutputStream"					%><%
%><%@	page import="java.net.URL"									%><%
%><%@	page import="fr.cg44.plugin.webcam.WebCamManager"			%><%
%><%@	page import="org.apache.commons.io.IOUtils"					%><%
%><%
	String id = request.getParameter("id");
	response.setContentType("application/json");
	// Si un ID est passé en paramètre de la requête	
	if (Util.notEmpty(id)) {
		// Appel le rafraichissement des webcam bdl et donne le lien pour psn
		InputStream streamImage = Webcams.getWebcamImagePath(id, jcmsContext);
		String filename = channel.getUploadParentPath()+"//upload//webcam//bdl//"+id+".jpg";
		File file = new File(filename);
			
		// webcams psn ou pont de mauves
		if("psn".equals(id) || "mauves1".equals(id) || "mauves2".equals(id)){			
			response.setContentType("image/jpeg");
			response.setHeader("Content-Disposition", "attachment; filename="+ id +".jpg");
	        
	        ServletOutputStream outs = response.getOutputStream();
	         
	        byte[] outputByte = new byte[4096];
	        while(streamImage.read(outputByte, 0, 4096) != -1){
	        	outs.write(outputByte, 0, 4096);
	        }
	        streamImage.close();
	        outs.flush();
	        outs.close();
		}
		
		// webcam bdl
		else if (file != null && file.exists()) {
						
			ServletContext sc = getServletContext();
			String mimeType = sc.getMimeType(filename);
			
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment; filename="+ id +".jpg");
			 // Set content size
			
			response.setContentLength((int) file.length());
			// Open the file and output streams
			FileInputStream in = new FileInputStream(file);			   
			ServletOutputStream outs = response.getOutputStream();
			response.flushBuffer();			 
			// Copy the contents of the file to the output stream
			IOUtils.copy(in, outs);			 
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(outs);

	    // Si l'image n'a pas pu être récupèrée, Code 500 avec code erreur JSON
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			String json = "{\"error\":\"Impossible de récupérer l'image.\"}";
			out.print(json);
		}
    // Si aucun paramètre n'est passé, Code 500 avec code erreur JSON
	} else {
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		String json = "{\"error\":\"Veuillez renseigner au moins un ID.\"}";
		out.print(json);
	}
%>