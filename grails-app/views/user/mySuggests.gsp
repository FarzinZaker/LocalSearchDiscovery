<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 8/4/2016
  Time: 5:59 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="place.my.list"/></title>
</head>

<body>
<div class="container padding-top-15">
    <div class="row">
        <div class="col-lg-12 cols-md-12 col-sm-12 col-xs-12">
            <h1><g:message code="place.my.list"/></h1>
        </div>
    </div>

    <div class="row">
        <div class="cols-lg-4 col-md-4 col-sm-12 col-xs-12">
            <div class="panel-header failed">
                <hr>

                <div class="container">
                    <h2>
                        <g:message code="place.my.rejected"/>
                    </h2>
                </div>
            </div>
            <g:if test="${rejectedPlaces?.size()}">
                <ul class="myPlaces">
                    <g:each in="${rejectedPlaces}" var="place">
                        <place:mySuggestedPlace place="${place}"/>
                    </g:each>
                </ul>
            </g:if>
            <g:else>
                <div class="text-center">
                    <g:message code="list.noItem"/>
                </div>
            </g:else>
        </div>

        <div class="cols-lg-4 col-md-4 col-sm-12 col-xs-12">
            <div class="panel-header">
                <hr>

                <div class="container">
                    <h2>
                        <g:message code="place.my.waiting"/>
                    </h2>
                </div>
            </div>
            <g:if test="${waitingPlaces?.size()}">
                <ul class="myPlaces">
                    <g:each in="${waitingPlaces}" var="place">
                        <place:mySuggestedPlace place="${place}"/>
                    </g:each>
                </ul>
            </g:if>
            <g:else>
                <div class="text-center">
                    <g:message code="list.noItem"/>
                </div>
            </g:else>
        </div>

        <div class="cols-lg-4 col-md-4 col-sm-12 col-xs-12">
            <div class="panel-header succeed">
                <hr>

                <div class="container">
                    <h2>
                        <g:message code="place.my.approved"/>
                    </h2>
                </div>
            </div>
            <g:if test="${approvedPlaces?.size()}">
                <ul class="myPlaces">
                    <g:each in="${approvedPlaces}" var="place">
                        <place:mySuggestedPlace place="${place}"/>
                    </g:each>
                </ul>
            </g:if>
            <g:else>
                <div class="text-center">
                    <g:message code="list.noItem"/>
                </div>
            </g:else>
        </div>
    </div>
</div>
</body>
</html>