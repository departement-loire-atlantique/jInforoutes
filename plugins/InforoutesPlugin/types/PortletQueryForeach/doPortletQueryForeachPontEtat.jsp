<%@ include file='/jcore/doInitPage.jsp' %>
<%@ include file='/jcore/portal/doPortletParams.jsp' %>
<% 
  jcmsContext.addCSSHeader("plugins/InforoutesPlugin/css/psn/types/PortletQueryForeach/doPortletQueryForeachPontEtat.css");

  PortletQueryForeach box = (PortletQueryForeach) portlet;
%>

<%@ include file='/types/PortletQueryForeach/doQuery.jsp' %>
<%@ include file='/types/PortletQueryForeach/doSort.jsp' %>


<div class="etat-trafic">

<jalios:if predicate='<%= Util.isEmpty(collection) %>'>
    <div class='etatTrafic-warn'>
        <p><%= glp("fr.cg44.plugin.pont.service.indisponible") %></p>
    </div>
</jalios:if>

<% Publication displayedPub = null; %>
<%@ include file='/types/PortletQueryForeach/doForeachHeader.jsp' %>
    <%           
      if (itPub instanceof com.jalios.jcms.portlet.PortalElement) {
        %><jalios:include pub="<%= itPub %>" /><%
      } else {
        displayedPub = itPub;
        request.setAttribute("isInContentFullDisplay", Boolean.TRUE);
        %><jsp:include page='<%= "/"+itPub.getTemplatePath(jcmsContext) %>' flush='true' /><%
        request.removeAttribute("isInContentFullDisplay");
      }
    %>
<%@ include file='/types/PortletQueryForeach/doForeachFooter.jsp' %>
<%
request.setAttribute("doPortletQueryForeachFulls.publication", displayedPub); 
%>
<%@ include file='/types/PortletQueryForeach/doPager.jsp' %>

</div> 