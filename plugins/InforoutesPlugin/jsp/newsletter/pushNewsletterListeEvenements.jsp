<%@page contentType="text/plain; charset=UTF-8" %>

<div class="col-md-12">
    <hr/>
    <%
    String filtreNature = Util.notEmpty(request.getParameter("filtreNature"))?request.getParameter("filtreNature"):"Tous";
    Set collection = channel.getPublicationSet(RouteEvenement.class, channel.getDefaultAdmin());   
    EventByDateAndNatureComparator eventComparator = new EventByDateAndNatureComparator(userLang); 
    DataSelector eventSelector = new LieuCurrentEventSelector(new String[]{filtreNature});
    Set<RouteEvenement> currentEventSet = JcmsUtil.select(collection, eventSelector, eventComparator);
    %>

    <h2><%= glp("jcmsplugin.inforoutes.evenements-en-cours") %></h2>
    
    <form action="plugins/InforoutesPlugin/jsp/newsletter/pushNewsletter.jsp#evenementFiltre" name="filtreNature" class="form-inline">
        <div class="form-group">
            <label for="evenementFiltre" class="control-label"> Filtre sur les événements </label>
            <select name="filtreNature" onchange="this.form.opSubmitFiltre.click()" id="evenementFiltre" class="form-control">
                <option <%= filtreNature.equals(glp("jcmsplugin.inforoutes.api.params.tous.label"))?"selected":"" %>
                value='<%= glp("jcmsplugin.inforoutes.api.params.tous.label")%>'><%= glp("jcmsplugin.inforoutes.api.params.tous.label") %></option>
                
                <option <%= filtreNature.equals(glp("jcmsplugin.inforoutes.api.params.bac-loire-coueron.label"))?"selected":"" %>
                value='<%= glp("jcmsplugin.inforoutes.api.params.bac-loire-coueron.label")%>'><%= glp("jcmsplugin.inforoutes.api.params.bac-loire-coueron.label") %></option>
                
                <option <%= filtreNature.equals(glp("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre.label"))?"selected":"" %>
                value='<%= glp("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre.label")%>'><%= glp("jcmsplugin.inforoutes.api.params.bac-loire-basse-indre.label") %></option>
                
                <option <%= filtreNature.equals(glp("jcmsplugin.inforoutes.api.params.pt-st-nazaire.label"))?"selected":"" %>
                value='<%= glp("jcmsplugin.inforoutes.api.params.pt-st-nazaire.label")%>'><%= glp("jcmsplugin.inforoutes.api.params.pt-st-nazaire.label") %></option>
            </select>

            <input type="submit" name="opSubmitFiltre" class="hidden" />
            <input type="hidden" name="csrftoken" value="<%= HttpUtil.getCSRFToken(request) %>">
        </div>
    </form>

    <jalios:select>
    
        <jalios:if predicate="<%= Util.notEmpty(currentEventSet) %>">
            <table class="table table-striped">
                <caption class="hidden"><%= glp("jcmsplugin.inforoutes.evenements-en-cours.title", filtreNature) %></caption>
                <thead>
                    <tr>
                        <th colspan="2"><%= glp("jcmsplugin.inforoutes.type") %></th>
                        <th><%= glp("jcmsplugin.inforoutes.lieu") %></th>
                        <th><%= glp("jcmsplugin.inforoutes.date") %></th>
                        <th><%= glp("jcmsplugin.inforoutes.significatif") %></th>
                    </tr>
                </thead>
                <tbody> 
                    <jalios:foreach collection='<%= currentEventSet %>' type='RouteEvenement' name='event'>
                        <%
                        String nature = InforoutesUtils.getNatureParam(event.getNature(userLang));
                        String picto = InforoutesUtils.getNatureValue("png", nature);
                            
                        %>
                        <tr>
                            <td><img src='<%= picto %>'/></td>
                            <td>
                                <jalios:if predicate='<%= Util.notEmpty(event.getLigne1(userLang)) %>'>
                                    <%= event.getLigne1(userLang) %>
                                </jalios:if>
                            </td>
                            <td>
                                <jalios:if predicate='<%= Util.notEmpty(event.getLigne2(userLang)) %>'>
                                    <%= event.getLigne2(userLang) %><br />
                                </jalios:if>
                                <jalios:if predicate='<%= Util.notEmpty(event.getLigne3(userLang)) %>'>
                                    <%= event.getLigne3(userLang) %><br />
                                </jalios:if>
                            </td>
                            <td>
                                <jalios:if predicate='<%= Util.notEmpty(event.getLigne4(userLang)) %>'>
                                    <%= event.getLigne4(userLang) %>
                                </jalios:if>
                            </td>
                            <td>
                                <div class="ajax-refresh-div">
                                    <%-- Modifie dynamiquement en Ajax le champ "Significatif" du contenu "Route événement" --%>
                                    <form action="plugins/InforoutesPlugin/jsp/newsletter/eventSignificatifForm.jsp">
                                        <input type="hidden" name="eventId" value="<%= event.getId() %>" />
                                        <input onchange="this.form.opSubmit.click()" type="radio"
                                            name="significatif" value="true"
                                            <%= event.getSignificatif().equals("oui")?"checked":"" %>> Oui <br />
                                        <input onchange="this.form.opSubmit.click()" type="radio"
                                            name="significatif" value="false"
                                            <%= event.getSignificatif().equals("non")?"checked":"" %>> Non
                                        <input type="submit" name="opSubmit" class="hidden ajax-refresh" />
                                    </form>
                                </div>
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