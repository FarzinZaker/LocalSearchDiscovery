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
    <title>
        <g:if test="${request["query"]}">
            <g:message code="place.search.result.title"
                       args="${[request["query"], params.city ?: params.address ?: message(code: 'search.location.nearMe')]}"/>
        </g:if>
        <g:else>
            <g:message code="place.search.result.noQueryTitle"
                       args="${[params.city ?: params.address ?: message(code: 'search.location.nearMe')]}"/>
        </g:else>
    </title>
</head>

<body>

<div class="tagBar skipHeader">
    <div class="container-fluid">
        <g:render template="explore/tags" model="${[tags: tags]}"/>
    </div>
</div>

<div class="container-fluid">
    <div class="row full-height">
        <div class="col-lg-4 col-md-5 col-sm-6 col-xs-12 full-height">
            <div class="byPassHorizontalMargins explorePlaceListContainer full-height" id="placeList" itemscope
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
                        <link itemprop="itemListOrder" href="http://schema.org/ItemListOrderAscending"/>
                        <meta itemprop="numberOfItems" content="${places?.size()}"/>
                    </g:if>
                    <g:if test="${request["query"]}">
                        <g:message code="place.search.result.heading"
                                   args="${[request["query"], params.city ?: params.address ?: message(code: 'search.location.nearMe')]}"/>
                    </g:if>
                    <g:else>
                        <g:message code="place.search.result.noQueryHeading"
                                   args="${[params.city ?: params.address ?: message(code: 'search.location.nearMe')]}"/>
                    </g:else>

                    <g:render template="explore/currentTags" model="${[tags: currentTags]}"/>
                </div>

                <div class="sortSelectionContainer">
                    <g:set var="searchParams" value="${params?.clone()}"/>
                    <g:set var="temp" value="${searchParams?.remove('sort')}"/>
                    <div class="btn-group" data-toggle="buttons">
                        <a class="btn btn-default ${params.sort != 'distance' ? 'active' : ''}"
                           href="${createLink(action: 'explore', params: searchParams)}">
                            <input type="radio" name="options" id="option1"
                                   autocomplete="off" ${params.sort != 'distance' ? 'checked' : ''}>
                            <g:message code="place.search.sort.default"/>
                        </a>
                        <a class="btn btn-default ${params.sort == 'distance' ? 'active' : ''}"
                           href="${createLink(action: 'explore', params: searchParams + [sort: 'distance'])}">
                            <input type="radio" name="options" id="option2"
                                   autocomplete="off" ${params.sort == 'distance' ? 'checked' : ''}>
                            <g:message code="place.search.sort.distance"/>
                        </a>
                    </div>
                    <span>
                        <g:message code="place.search.sort.label"/>
                    </span>

                    <div class="clearfix"></div>
                </div>
                <ul class="exploreList" itemscope itemtype="http://schema.org/ItemList">
                    <g:each in="${places}" var="place" status="index">
                        <place:exploreItem place="${place}" index="${index}"/>
                    </g:each>
                </ul>
            </div>
        </div>

        <div class="col-lg-8 col-md-7 col-sm-6 col-xs-12 full-height">
            <div class="byPassHorizontalMargins full-height" id="placeListMap">
                <map:explore visitorLocation="0" places="${places}"
                             center="${params.near ? [session['location']?.lat, session['location']?.lon] : null}"/>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    function resizeExploreLayout() {
        var windowWidth = $(window).width();
        var container = $('#placeList').parent().parent().parent();
        if (windowWidth > 768) {
            container.css('padding-top', '97px');
            container.height($(window).height() - 97);
        }
        else {
            container.css('padding-top', '0');
            container.css('height', 'auto');
        }
        //var navbarHeight = $('.navbar-fixed-top').height() + $('.tagBar').height() + 21;
        //navbarHeight = 97;
        ////console.log(navbarHeight);
        ////console.log($(window).height());
        //$('#placeList').height($(window).height() - navbarHeight);
        //var mapContainer = $('#map_explore');
        //mapContainer.height($(window).height() - navbarHeight);
        //mapContainer.css('width', '100%');
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

        $('.sortSelectionContainer .btn').click(function () {
            if (!$(this).hasClass('active'))
                window.location.href = $(this).attr('href');
        })
    });
</g:javascript>
</body>
</html>