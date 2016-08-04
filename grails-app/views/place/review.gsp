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
        <div class="col-lg-6 col-md-4 col-sm-12 col-xs-12">
            <div class="whitePanel spaceFromTop placeInfoSection" itemtype="http://schema.org/LocalBusiness"
                 itemscope="">
                <div class="alert alert-info softHidden" id="placeMessage"></div>
                <span class="categoryIcon" itemprop="photo" itemscope itemtype="http://schema.org/ImageObject">
                    <img src="${createLink(controller: 'image', action: 'placeLogo', params: [id: place?.id, size: 88])}"
                         itemprop="contentUrl"/>
                </span>

                <div class="placeBaseInfo">
                    <h3 itemprop="name">${place?.name}</h3>

                    <div><span itemprop="additionalType">${place?.category?.name}</span> - <span
                            itemprop="location">${place?.city}</span></div>

                    <div itemprop="address">${place?.address}</div>

                    <g:render template="tags"
                              model="${[tags: place?.tags, province: place?.province, city: place?.city]}"/>

                    <sec:ifLoggedIn>
                        <div>
                            <input type="button" class="btn btn-link"
                                   value="${message(code: 'place.suggestEdit.button')}" data-toggle="modal"
                                   data-target="#suggestEditModal"/>
                        </div>
                    </sec:ifLoggedIn>
                </div>

                <div class="clearfix"></div>

                <div class="linkBar">
                    <format:phoneNumber id="placePhone" value="${place?.phone}"/>
                    <div class="clearfix"></div>
                </div>

                <div class="clearfix"></div>

                <map:locationViewer place="${place}" height="400"/>
            </div>

            <place:reviewForm place="${place}" suggestedCategories="${suggestedCategories}"/>
        </div>

        <div class="col-lg-3 col-md-4 col-sm-12 col-xs-12 spaceFromTop">
            <div class="panel-header">
                <hr>

                <div class="container">
                    <h2>
                        <g:message code="place.review.otherPlacesOfThisUser"/>
                    </h2>
                </div>
            </div>

            <div class="similarList small">
                <g:if test="${otherPlacesOfThisUser?.size()}">
                    <g:each in="${otherPlacesOfThisUser}" var="similarPlace">
                        <place:similarItem place="${similarPlace}"/>
                    </g:each>
                </g:if>
                <g:else>
                    <div class="text-center">
                        <g:message code="list.noItem"/>
                    </div>
                </g:else>
            </div>
        </div>

        <div class="col-lg-3 col-md-4 col-sm-12 col-xs-12 spaceFromTop">
            <div class="panel-header">
                <hr>

                <div class="container">
                    <h2>
                        <g:message code="place.review.similarInCategory"/>
                    </h2>
                </div>
            </div>

            <div class="similarList small">
                <g:if test="${similarPlaces?.size()}">
                    <g:each in="${similarPlaces}" var="similarPlace">
                        <place:similarItem place="${similarPlace}"/>
                    </g:each>
                </g:if>
                <g:else>
                    <div class="text-center">
                        <g:message code="list.noItem"/>
                    </div>
                </g:else>
            </div>
        </div>
    </div>
</div>
<g:render template="suggestEditModal" model="${[place: place]}"/>
</body>
</html>