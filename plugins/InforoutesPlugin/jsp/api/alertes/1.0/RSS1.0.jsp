<%
  // inform doInitPage to set the proper content type
  request.setAttribute("ContentType", "text/xml; charset=UTF-8"); 
%><?xml version="1.0" encoding="UTF-8"?><%@ include file="/jcore/doInitPage.jsp" %><% String feedFormat = "Rss1"; %><%@ include file="doFeedInit.jsp" %>

<jalios:cache id='<%= idCache %>' disabled='<%= feedCacheDisabled %>' classes='<%= invalidClassArray %>' timeout='60' session='false'>  

<jalios:query name="alertes" queryString="<%= StrQuery %>" />  

<rdf:RDF 
  xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
  xmlns="http://purl.org/rss/1.0/"
  xmlns:dc="http://purl.org/dc/elements/1.1/"
>
	
	<channel rdf:about="<%= ServletUtil.getBaseUrl(request) %>">
		<title><%= feedTitle %></title>
		<link><%= ServletUtil.getBaseUrl(request) %></link>
		<description><%= feedDescription %></description>
    <dc:date><%= feedDate %></dc:date>
    <dc:creator><%= feedCreator %></dc:creator>
		<dc:rights><%= feedRights %></dc:rights>
		<items>
			<rdf:Seq>
			  <jalios:foreach collection="<%= alertes %>" name="it" type="generated.AlertCG" >
		      <rdf:li resource="<%= getAlerteLink(it, userLocale) %>" />
		    </jalios:foreach>
			</rdf:Seq>
		</items>
	</channel>

<jalios:foreach collection="<%= alertes %>" name="it" type="generated.AlertCG" >
    <item rdf:about="<%= ServletUtil.getBaseUrl(request) + it.getDisplayUrl(userLocale) %>">
    <title><%= XmlUtil.normalize(it.getTitle()) %></title>
    <link><%= getAlerteLink(it, userLocale) %></link>
    <description><%=Util.html2text(renderPubAbstract(it, userLocale)) %></description>
  </item>
</jalios:foreach>

</rdf:RDF>

</jalios:cache>