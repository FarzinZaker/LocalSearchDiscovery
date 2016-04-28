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
        <ul>
            <li><a href="#">داغ ترین</a></li>
            <li><a href="#">برترین</a></li>
            <li><a href="#">فست فود</a></li>
            <li><a href="#">کافی شاپ</a></li>
            <li><a href="#">تفریح</a></li>
            <li><a href="#">فروشگاه</a></li>
        </ul>
        <sec:ifLoggedIn>
            <place:waitingEditSuggestions/>
        </sec:ifLoggedIn>

    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-4">
            <div class="byPassHorizontalMargins explorePlaceListContainer" id="placeList">
                <ul class="exploreList">
                    <g:each in="${places}" var="place" status="index">
                        <g:render template="explore/item" model="${[place: place, index: index]}"/>
                    </g:each>
                </ul>
            </div>
        </div>

        <div class="col-sm-8">
            <div class="byPassHorizontalMargins" id="placeListMap">
                <map:explore visitorLocation="0" places="${places}"/>
            </div>
        </div>
    </div>
</div>
<g:javascript>
    function resizeExploreLayout() {
        var navbarHeight = $('.navbar-fixed-top').height() + $('.tagBar').height() + 21;
        $('#placeList').height($(window).height() - navbarHeight);
        $('#map_explore').height($(window).height() - navbarHeight);
        map_explore.updateSize();
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