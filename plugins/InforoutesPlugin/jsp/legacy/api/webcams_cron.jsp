<%@     page contentType="application/json; charset=UTF-8"              %><%
%><%@   include file='/jcore/doInitPage.jsp'                        %><%
%><%@   include file='corsFilter.jsp'                       %><%
%><%@   page import="fr.cg44.plugin.inforoutes.legacy.alertemobilite.ws.Webcams"      %><%
%><%@   page import="java.io.FileInputStream"                       %><%
%><%@   page import="java.io.InputStream"                           %><%
%><%@   page import="java.net.URL"                                  %><%
%><%@   page import="fr.cg44.plugin.inforoutes.legacy.webcam.WebCamManager"           %><%
%><%@   page import="org.apache.commons.io.IOUtils"                 %><%
%>

<%-- 21/02/2022 SGU. Ne pas pointer directement sur ce fichier. Appeler plutôt webcams.jsp --%>

<%
    String id = request.getParameter("id");
    response.setContentType("application/json");
    // Si un ID est passé en paramètre de la requête    
    if (Util.notEmpty(id)) {
            
        // webcams psn
        if ("psn".equals(id)) {
            
            String filenamepsn = channel.getUploadParentPath()+"//upload//webcam//psn//psn_webcam_1.jpg";
            InputStream streamImage = new FileInputStream(new File(filenamepsn));
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
        else if ("coueron1".equals(id) || "coueron2".equals(id) || "indre1".equals(id) || "indre2".equals(id)) {

            String webcamBDLCity = id.substring(0, id.length() - 1);
            String webcamBDLNumber = id.substring(id.length() - 1);
            String strUrl = ServletUtil.getBaseUrl(request) + "mediaCache/" + webcamBDLCity + "/" + webcamBDLNumber + ".jpg";

            ServletContext sc = getServletContext();
            String mimeType = "image/jpeg";

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename="+ id +".jpg");
            response.flushBuffer();          
             
            // Open the file and output streams
            InputStream in = new URL(strUrl).openStream();
            ServletOutputStream outs = response.getOutputStream();
            // Copy the contents of the file to the output stream
            IOUtils.copy(in, outs);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(outs);

        // Si l'image n'a pas pu être récupèrée, Code 500 avec code erreur JSON
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String json = "{\"error\":\"Impossible de récupérer l'image pour " + id + "\"}";
            out.print(json);
        }
    // Si aucun paramètre n'est passé, Code 500 avec code erreur JSON
    } else {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String json = "{\"error\":\"Veuillez renseigner au moins un ID.\"}";
        out.print(json);
    }
%>