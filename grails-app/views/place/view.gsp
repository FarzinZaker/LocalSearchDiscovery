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
        <div class="col-sm-8 whitePanel spaceFromTop placeInfoSection">
            <div class="alert alert-info softHidden" id="placeMessage"></div>
            <span class="categoryIcon">
                <img src="${resource(dir: "images/categories/${place?.category?.iconDirectory}", file: "${place?.category?.iconFile}88.png")}"/>
            </span>

            <div class="placeBaseInfo">
                <h1>${place?.name}</h1>

                <div>${place?.category?.name} - ${place?.city}</div>

                <div>${place?.address}</div>

                <div>
                    <input type="button" class="btn btn-link"
                           value="${message(code: 'plage.suggestEdit.button')}" data-toggle="modal"
                           data-target="#suggestEditModal"/>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="linkBar">
                <map:directionLink place="${place}"/>
                <format:phoneNumber id="placePhone" value="${place?.phone}"/>
                <div class="clearfix"></div>
            </div>
        </div>

        <div class="col-sm-4 spaceFromTop">
            <map:locationViewer place="${place}" height="300"/>
        </div>
    </div>
</div>
<g:render template="suggestEditModal" model="${[place: place]}"/>
</body>
</html>