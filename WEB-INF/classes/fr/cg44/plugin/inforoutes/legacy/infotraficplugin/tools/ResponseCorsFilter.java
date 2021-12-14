package fr.cg44.plugin.inforoutes.legacy.infotraficplugin.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// http://www.w3.org/wiki/CORS_Enabled
public class ResponseCorsFilter {

	public static void addPublicCORSHeader(HttpServletRequest request, HttpServletResponse response) {
		// declare API as public
        response.addHeader("Access-Control-Allow-Origin", "*");
        
        response.addHeader("Access-Control-Allow-Methods", "GET");

        String reqHead = request.getHeader("Access-Control-Request-Headers");
        if(null != reqHead && !reqHead.equals(null)){
        	// http://code.google.com/p/chromium/issues/detail?id=108394
        	response.addHeader("Access-Control-Allow-Headers", reqHead);
        }
    }
}
