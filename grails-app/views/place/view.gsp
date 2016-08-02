<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 4/23/2016
  Time: 3:33 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>${place?.name}</title>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-md-7 col-sm-6 col-xs-12">
            <div class="whitePanel spaceFromTop placeInfoSection" itemtype="http://schema.org/LocalBusiness"
                 itemscope="">
                <div class="alert alert-info softHidden" id="placeMessage"></div>
                <span class="categoryIcon" itemprop="photo" itemscope itemtype="http://schema.org/ImageObject">
                    <img src="${createLink(controller: 'image', action: 'placeLogo', params: [id: place?.id, size: 88])}"
                         itemprop="contentUrl"/>
                </span>

                <div class="placeBaseInfo">
                    <h1 itemprop="name">${place?.name}</h1>

                    <g:if test="${!place?.approved}">
                        <div class="notApprovedFlag">
                            <i class="glyphicon glyphicon-alert"></i>
                            <span><g:message code="place.notApproved.label"/></span>
                        </div>
                    </g:if>

                    <div><span itemprop="additionalType">${place?.category?.name}</span> - <span
                            itemprop="location">${place?.city}</span></div>

                    <div itemprop="address">${place?.address}</div>

                    <g:render template="tags"
                              model="${[tags: place?.tags, province: place?.province, city: place?.city]}"/>

                    <sec:ifLoggedIn>
                        <div>
                            <input type="button" class="btn btn-link"
                                   value="${message(code: 'plage.suggestEdit.button')}" data-toggle="modal"
                                   data-target="#suggestEditModal"/>
                        </div>
                    </sec:ifLoggedIn>
                </div>

                <div class="clearfix"></div>

                <div class="linkBar">
                    <map:directionLink place="${place}"/>
                    <format:phoneNumber id="placePhone" value="${place?.phone}"/>
                    <div class="clearfix"></div>
                </div>

                <div class="attrBar row">
                    <div itemtype="http://schema.org/AggregateRating" itemscope="" itemprop="aggregateRating"
                         class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <place:aggregateRating place="${place}"/>
                    </div>

                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"></div>
                </div>

                <div class="placeActions" id="actionBar">
                    <place:rate place="${place}"/>
                </div>

                <div class="clearfix"></div>
            </div>

            <div class="tipsSections" style="position: relative;">
                <place:addTip/>
            </div>

            <ul id="tipsList" class="whitePanel">
                <place:tipList place="${place}"/>
            </ul>
        </div>

        <div class="col-lg-4 col-md-5 col-sm-6 col-xs-12 spaceFromTop">
            <map:locationViewer place="${place}" height="300"/>
            <div class="similarList">
                <g:each in="${similarPlaces}" var="similarPlace">
                    <place:similarItem place="${similarPlace}"/>
                </g:each>
            </div>
        </div>
    </div>
</div>
<g:render template="suggestEditModal" model="${[place: place]}"/>
</body>
</html>