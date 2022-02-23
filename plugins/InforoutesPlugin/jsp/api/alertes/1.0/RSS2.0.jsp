<%
  // inform doInitPage to set the proper content type
  request.setAttribute("ContentType", "text/xml; charset=UTF-8"); 
%><?xml version="1.0" encoding="UTF-8"?>
<%@ include file="/jcore/doInitPage.jsp" %>
<% String feedFormat = "Rss2"; %>
<%@ include file="doFeedInit.jsp" %>

<jalios:cache id='<%= idCache %>' disabled='<%= feedCacheDisabled %>' classes='<%= invalidClassArray %>' timeout='60' session='false'>  

<jalios:query name="alertes" queryString="<%= StrQuery %>" />  

<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
<channel>
  <atom:link href="<%= request.getRequestURL() %>" rel="self" type="application/rss+xml" />
  <title><%= feedTitle %></title>
  <link><%= ServletUtil.getBaseUrl(request) %></link>
  <description><%= feedDescription %></description>
  <language><%= userLang %></language>
  <lastBuildDate><%= Util.formatRfc822Date(new Date()) %></lastBuildDate>
  <generator>feedCreator</generator>
  <copyright><%= feedRights %></copyright>

<jalios:foreach collection="<%= alertes %>" name="it" type="generated.AlertCG" >
  <item>
    <guid><%= ServletUtil.getBaseUrl(request) + it.getDisplayUrl(userLocale) %></guid>
    <title><%= XmlUtil.normalize(it.getTitle()) %></title>
    <link><%= getAlerteLink(it, userLocale) %></link>
    <author>Conseil général de Loire-Atlantique (mailto:contact@loire-atlantique.fr)</author>
    <description><%=Util.html2text(renderPubAbstract(it, userLocale)) %></description>
    <%
    Category rootCatAlertes = channel.getCategory(idCatParenteAlertes);
    Set<Category> catsAlertes = rootCatAlertes.getChildrenSet();
    Set<Category> catsPub = it.getCategorySet();
    Set<Category> catsAlerte = Util.interSet(catsAlertes, catsPub);
    Iterator itr = catsAlerte.iterator();
    while(itr.hasNext()) {
      %><category><%= itr.next() %></category>
      <%
    }
    %>
    <pubDate><%= Util.formatRfc822Date(it.getCdate()) %></pubDate>
  </item>
</jalios:foreach>
  
</channel>
</rss>

</jalios:cache>