<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 4/26/2016
  Time: 4:40 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="explore"/>
    <title></title>
</head>

<body>

<div class="tagBar skipHeader">
    <div class="container-fluid">
        <g:render template="explore/tags" model="${[tags: tags]}"/>
        <sec:ifLoggedIn>
            <place:waitingEditSuggestions/>
        </sec:ifLoggedIn>

    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-4">
            <div class="byPassHorizontalMargins explorePlaceListContainer" id="placeList" itemscope
                 itemtype="http://schema.org/ItemList">
                <div class="searchResultHeader">
                    <g:if test="${places?.size()}">
                        <h1 itemprop="name">
                            ${places?.size()}
                            <g:if test="${params.id}">
                                ${params.id}
                            </g:if>
                            <g:else>
                                <g:message code="place"/>
                            </g:else>
                            <g:if test="${params.near || params.city}">
                                <g:message code="place.search.result.hiddenHeading.location"/>
                                ${params.city ?: places?.find()?.city}
                            </g:if>
                            <g:if test="${currentTags}">
                                <g:message code="place.search.result.hiddenHeading.tags"/>
                                ${currentTags?.join(message(code: 'and')?.toString())}
                            </g:if>
                        </h1>
                        <link itemprop="itemListOrder" href="http://schema.org/ItemListOrderAscending" />
                        <meta itemprop="numberOfItems" content="${places?.size()}" />
                    </g:if>
                    <g:if test="${request["query"]}">
                        <g:message code="place.search.result.heading"
                                   args="${[request["query"], params.city ?: message(code: 'search.location.nearMe')]}"/>
                    </g:if>
                    <g:else>
                        <g:message code="place.search.result.noQueryHeading"
                                   args="${[params.city ?: message(code: 'search.location.nearMe')]}"/>
                    </g:else>

                    <g:render template="explore/currentTags" model="${[tags: currentTags]}"/>
                </div>
                <ul class="exploreList" itemscope itemtype="http://schema.org/ItemList">
                    <g:each in="${places}" var="place" status="index">
                        <place:exploreItem place="${place}" index="${index}"/>
                    </g:each>
                </ul>
            </div>
        </div>

        <div class="col-sm-8">
            <div class="byPassHorizontalMargins" id="placeListMap">
                <map:explore visitorLocation="0" places="${places}"
                             center="${params.near ? params.near?.split(',')?.collect { it?.toDouble() } : null}"/>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    function resizeExploreLayout() {
        var navbarHeight = $('.navbar-fixed-top').height() + $('.tagBar').height() + 21;
        navbarHeight = 93;
        //console.log(navbarHeight);
        //console.log($(window).height());
        $('#placeList').height($(window).height() - navbarHeight);
        var mapContainer = $('#map_explore');
        mapContainer.height($(window).height() - navbarHeight);
        mapContainer.css('width', '100%');
        map_explore.updateSize();
        map_explore.getView().fit(extent_explore, map_explore.getSize());
    }

    $(document).ready(function () {
        $(window).resize(resizeExploreLayout);
        resizeExploreLayout();

        $('.exploreList li').mouseenter(function () {
            showFeatureOnMap($(this).attr('data-id'));
        }).mouseleave(function () {
            hideFeatureOnMap($(this).attr('data-id'));
        });
    });
</g:javascript>
</body>
</html>