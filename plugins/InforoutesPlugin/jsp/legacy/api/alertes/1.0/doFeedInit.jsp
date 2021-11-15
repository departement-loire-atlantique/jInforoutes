<%
  Class[] invalidClassArray = new Class[] { AlertCG.class };
  boolean feedCacheDisabled = isLogged;
%><%@page import="org.apache.commons.lang.StringUtils" %><%!
  public String renderPubAbstract(Publication pub, Locale userLocale) {
    String userLang = userLocale.getLanguage();
    TypeFieldEntry fieldEntry = channel.getTypeAbstractFieldEntry(pub.getClass());
    if (fieldEntry != null && fieldEntry.isFieldWiki()) {
      String baseUrl = ServletUtil.getBaseUrl(channel.getCurrentServletRequest());
      return WikiRenderer.wiki2html(pub.getAbstract(userLang), userLocale, new WikiRenderingHints("wiki feed", false, baseUrl));
    }
    if (fieldEntry != null && fieldEntry.isWysiwyg()) {
      String txt = pub.getAbstract(userLang);
      return JcmsUtil.convertUri2Url(txt, ServletUtil.getBaseUrl(channel.getCurrentServletRequest()));
    } 
    return pub.getAbstract(userLang);
  }
%><%!
  public String getAlerteLink(AlertCG alerte, Locale userLocale) {
    String alerteLink = "";
    String baseUrl = ServletUtil.getBaseUrl(channel.getCurrentServletRequest());
    if (Util.notEmpty(alerte.getDescription())) {
      alerteLink = baseUrl + alerte.getDisplayUrl(userLocale);
    } else {
      alerteLink = channel.getUrl();
    }
    return alerteLink;
  }
%><%

  String feedTitle = "Alertes info route du conseil G�n�ral de Loire-Atlantique";
  String feedDescription = "Flux des alertes du portail mobilit� du conseil G�n�ral de Loire-Atlantique";
  String feedDate = Util.formatW3cDate(new Date());
  String feedCreator = "Conseil g�n�ral de Loire-Atlantique (mailto:contact@loire-atlantique.fr)";
  String feedRights = "Conseil g�n�ral de Loire-Atlantique";
  
  String idCatParenteAlertes = "dev_5002"; // Navigation principale
  String strCatsAlertes = "inforoutes:dev_5004;pontstnaz:dev_5005;bacs:dev_5006;pontancenis:dev_5007;conditionshivernales:dev_5008;pistescyclables:dev_5009";
  List<String> listeCatsAlertes = Util.splitToList(strCatsAlertes, ";");
  Map<String, String> mapCatsAlertes = new HashMap<String, String>();
  for (String el : listeCatsAlertes) {
      mapCatsAlertes.put(StringUtils.substringBefore(el, ":"), StringUtils.substringAfter(el, ":"));
  }

  Enumeration<String> parameterNames = request.getParameterNames();
  String StrQuery = "types=generated.AlertCG&cids=";
  String idCache = feedFormat + "_alerte_";
  
  String[] catsParameter = request.getParameterValues("cat");
  if (catsParameter == null) {
    // Si pas de param�tre 'cat' : on prend la cat�gorie parente des types d'alertes pour r�cup�rer toutes les alertes
    StrQuery += idCatParenteAlertes;
    idCache += idCatParenteAlertes;
  } else {
    // V�rifie si param�tre valide
    // si diff�rent que 'cat' et 'utm_*' (tracker Google Analytics), renvoie erreur 500
    while(parameterNames.hasMoreElements()) {
      String param = parameterNames.nextElement();
      if (!param.equals("cat") && !param.startsWith("utm_")) throw new Exception("Param�tre '" + param + "' non valide");
      //if (!param.startsWith("utm_")) throw new Exception("Param�tre '" + param + "' non valide");
    }
    Iterator itcats = Arrays.asList(catsParameter).iterator();
    List<String> listCids = new ArrayList<String>();
    while(itcats.hasNext()) {
      // Erreur 500 si valeur du param�tre 'cat' non valide
      String cat = (String) itcats.next();
      if (Util.isEmpty(cat)) throw new Exception("Param�tre 'cat' de valeur nulle non valide");
      if (!mapCatsAlertes.containsKey(cat)) throw new Exception("Param�tre 'cat' de valeur " + cat + " non valide");
      listCids.add(mapCatsAlertes.get(cat));
    }
    StrQuery = "types=generated.AlertCG&catMode=or&cids=" + StringUtils.join(listCids, "&cids=");
    idCache += StringUtils.join(listCids, '_');
  }
%>