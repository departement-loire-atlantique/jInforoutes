<%@ page contentType="text/plain; charset=UTF-8"%>
<%@ include file='/jcore/doInitPage.jsp' %><% 

  if (!isLogged) {
    sendForbidden(request, response);
    return;
  }

	request.setAttribute("usersAdminMenu", "true");

%><%@ include file='/work/doWorkHeader.jsp' %>

    
    
      <div id="docs" class="mainBlock" style="margin-bottom: 15px;">
      
        <h1 class="boTitle icon iconBooks">Documentation : Contribution du PC Route</h1>
        
         <div class="box" style="width:50%" >    
	       <div class="box-header">
	         <h3>Documents</h3>
	       </div>
	        
	        <ul style="list-style:none">
				<li><a href="plugins/InforoutesPlugin/docs/pdf/contribution_alerte.pdf"  target="_blank"  title="Ouverture dans une nouvelle fenêtre"><img style="margin: 5px; vertical-align: middle;" alt="" src="images/jalios/icons/files/pdf.gif">Saisie d'une alerte</a></li>
	        </ul>
          </div>
      </div>



<%@ include file='/work/doWorkFooter.jsp' %>

