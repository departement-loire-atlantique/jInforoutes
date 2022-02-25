<%
  // inform doInitPage to set the proper content type
  request.setAttribute("ContentType", "text/xml; charset=UTF-8"); 
%><?xml version="1.0" encoding="UTF-8"?>
<%@ include file="/jcore/doInitPage.jsp" %>
<% String feedFormat = "Atom1"; %>
<%@ include file="doFeedInit.jsp" %>

<jalios:cache id='<%= idCache %>' disabled='<%= feedCacheDisabled %>' classes='<%= invalidClassArray %>' timeout='60' session='false'>  

<jalios:query name="alertes" queryString="<%= StrQuery %>" />  

<feed xmlns="http://www.w3.org/2005/Atom" xml:lang="<%= userLang %>">
  <id><%= XmlUtil.normalize(ServletUtil.getUrl(request)) %></id>
  <title><%= feedTitle %></title>
  <updated><%= DateUtil.formatW3cDate(new Date()) %></updated>>
  <link rel="self" href="<%= ServletUtil.getUrl(request) %>" />
  <generator>feedCreator</generator>
  <rights><%= feedRights %></rights>
    
<jalios:foreach collection="<%= alertes %>" name="it" type="generated.AlertCG" >
  <entry>
    <id><%= ServletUtil.getBaseUrl(request) + it.getDisplayUrl(userLocale) %></id>
    <title><%= XmlUtil.normalize(it.getTitle()) %></title>
    <updated><%= DateUtil.formatW3cDate(it.getMdate()) %></updated>
    <author>
      <name>Département de Loire-Atlantique</name>
      <email>contact@loire-atlantique.fr</email>
    </author>
    <link rel="alternate" type="text/html" href="<%= getAlerteLink(it, userLocale) %>" />
    <summary type="html"><%= XmlUtil.normalize(renderPubAbstract(it, userLocale)) %></summary>
    <%
    Category rootCatAlertes = channel.getCategory(idCatParenteAlertes);
    Set<Category> catsAlertes = rootCatAlertes.getChildrenSet();
    Set<Category> catsPub = it.getCategorySet();
    Set<Category> catsAlerte = Util.interSet(catsAlertes, catsPub);
    Iterator itr = catsAlerte.iterator();
    while(itr.hasNext()) {
      %><category term="<%= itr.next() %>" />
      <%
    }
    %>
    <published><%= DateUtil.formatW3cDate(it.getCdate()) %></published>
  </entry>
</jalios:foreach>
</feed>

</jalios:cache>
