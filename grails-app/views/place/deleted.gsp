<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 5/21/2016
  Time: 12:56 PM
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
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="whitePanel spaceFromTop placeInfoSection">

                <h1 itemprop="name">${place?.name}</h1>

                <div class="alert alert-danger removed">
                    <g:message code="place.reported.message" args="${[message(code:"place.report.type.${place.reportType}")]}"/>
                </div>
            </div>
        </div>
    </div>
</body>
</html>