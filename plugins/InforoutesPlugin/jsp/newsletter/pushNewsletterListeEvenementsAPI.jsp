<%@page contentType="text/plain; charset=UTF-8" %>
<div class="col-md-12">
    <%
    String filtreNature = Util.notEmpty(request.getParameter("filtreNature"))?request.getParameter("filtreNature"):"Tous";
    List<EvenementDTO> traficEvents = InforoutesApiRequestManager.getTraficEvents(filtreNature);
    traficEvents = InforoutesUtils.filterEvenementDtoEnCours(traficEvents);
    SimpleDateFormat sdfJson = new SimpleDateFormat(channel.getProperty("jcmsplugin.inforoutes.pattern.jsondate"));
    SimpleDateFormat sdfShownDate = new SimpleDateFormat(channel.getProperty("jcmsplugin.inforoutes.pattern.showndate"));
    SimpleDateFormat sdfShownHour = new SimpleDateFormat(channel.getProperty("jcmsplugin.inforoutes.pattern.shownhour"));
    %>

    <h2><%= glp("jcmsplugin.inforoutes.evenements-en-cours") %></h2>
    
    <form action="plugins/InforoutesPlugin/jsp/newsletter/pushNewsletter.jsp#evenementFiltre" name="filtreNature" class="form-inline">
        <div class="form-group">
            <label for="evenementFiltre" class="control-label"> Filtre sur les événements </label>
            <select name="filtreNature" onchange="this.form.opSubmitFiltre.click()" id="evenementFiltre" class="form-control">
                <option <%= filtreNature.equals(channel.getProperty("jcmsplugin.inforoutes.api.params.tous"))?"selected":"" %>
                value='<%=channel.getProperty("jcmsplugin.inforoutes.api.params.tous")%>'><%= glp("jcmsplugin.inforoutes.api.params.tous.label") %></option>
                
                <option <%= filtreNature.equals(channel.getProperty("jcmsplugin.inforoutes.api.params.bac-loire-coueron"))?"selected":"" %>
                value='<%=channel.getProperty("jcmsplugin.inforoutes.api.params.bac-loire-coueron")%>'><%= glp("jcmsplugin.inforoutes.api.params.bac-loire-coueron.label") %></option>
                
                <option <%= filtreNature.equals(channel.getProperty("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre"))?"selected":"" %>
                value='<%=channel.getProperty("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre")%>'><%= glp("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre.label") %></option>
                
                <option <%= filtreNature.equals(channel.getProperty("jcmsplugin.inforoutes.api.params.pt-st-nazaire"))?"selected":"" %>
                value='<%=channel.getProperty("jcmsplugin.inforoutes.api.params.pt-st-nazaire")%>'><%= glp("jcmsplugin.inforoutes.api.params.pt-st-nazaire.label") %></option>
            </select>

            <input type="submit" name="opSubmitFiltre" class="hidden" />
            <input type="hidden" name="csrftoken" value="<%= HttpUtil.getCSRFToken(request) %>">
        </div>
    </form>

    <jalios:select>
    
        <jalios:if predicate="<%= Util.notEmpty(traficEvents) %>">
            <table class="inforoutes">
                <caption class="visually-hidden"><%= glp("jcmsplugin.inforoutes.evenements-en-cours.title", filtreNature) %></caption>
                <thead>
                    <tr>
                       <th colspan="2" scope="col"><%= glp("jcmsplugin.inforoutes.type") %></th>
                       <th scope="col"><%= glp("jcmsplugin.inforoutes.lieu") %></th>
                       <th scope="col"><%= glp("jcmsplugin.inforoutes.date") %></th>
                       <th scope="col"><%= glp("jcmsplugin.inforoutes.significatif") %></th>
                    </tr>
                 </thead>
                 <tbody>
                    <jalios:foreach collection="<%= traficEvents %>" type="fr.cg44.plugin.inforoutes.dto.EvenementDTO" name="itEvent">
                        <%
                        Date itDate = sdfJson.parse(itEvent.getDatePublication());
                        %>
                        <tr>
                           <td><i class="icon ds44-icoInfoRoutes <%= InforoutesUtils.getClasseCssNatureEvt(itEvent) %>" aria-hidden="true"></i></td>
                           <td><span><%= itEvent.getType() %></span></td>
                           <td>
                              <p><%= itEvent.getLigne2() %></p>
                              <jalios:if predicate='<%= Util.notEmpty(itEvent.getLigne3()) && !"null".equals(itEvent.getLigne3()) %>'><p><%= itEvent.getLigne3() %></p></jalios:if>
                           </td>
                           <td>
                              <p><%= itEvent.getLigne4() %></p>
                              <jalios:if predicate='<%= Util.notEmpty(itEvent.getLigne5()) && !"null".equals(itEvent.getLigne5()) %>'><p><%= itEvent.getLigne5() %></p></jalios:if>
                           </td>
                           <td>
                           <%-- TODO : implémentation d'événements significatifs--%>
                           </td>
                        </tr>
                    </jalios:foreach>
                 </tbody>
            </table>
        </jalios:if>
        
        <jalios:default>
            <p><%= glp("jcmsplugin.inforoutes.pasdeperturbation") %></p>
        </jalios:default>
        
    </jalios:select>
</div>