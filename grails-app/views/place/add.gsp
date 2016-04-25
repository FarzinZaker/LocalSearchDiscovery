<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 4/16/2016
  Time: 6:59 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="place.add.title"/></title>

</head>

<body>
<bootstrap:helpedPanel>
    <h3 class="fieldSet-title">
        <g:message code="place.add.title"/>
    </h3>
    <g:if test="${flash.error}">
        <div class="alert alert-danger">
            ${flash.error}
        </div>
    </g:if>
    <g:form controller="place" action="save">
        <div class="row">
            <div class="col-sm-12">
                <label for="name">
                    <span class="glyphicon glyphicon-home"></span>
                    <g:message code="place.name.label"/>
                </label>

                <div class="input-group">
                    <input name="name" id="name" class="form-control" data-validation="required">
                </div>
            </div>
        </div>

        <g:render template="/common/cityPicker"/>
        <g:javascript>
            function showCityOnMap(latitude, longitude) {
                moveMap('location', latitude, longitude);
            }
        </g:javascript>
        <div class="row">
            <div class="col-sm-12">
                <label for="address">
                    <span class="glyphicon glyphicon-road"></span>
                    <g:message code="place.address.label"/>
                </label>

                <div class="input-group">
                    <input name="address" id="address" class="form-control">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <map:locationPicker
                        name="location"
                        visitorLocation="1"
                        height="400"
                        validation="required"/>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-6">
                <label for="phone">
                    <span class="glyphicon glyphicon-phone-alt"></span>
                    <g:message code="place.phone.label"/>
                </label>

                <div class="input-group">
                    <input name="phone" id="phone" class="form-control text-left">
                </div>
            </div>

            <div class="col-sm-6">
                <label for="postalCode">
                    <span class="glyphicon glyphicon-barcode"></span>
                    <g:message code="place.postalCode.label"/>
                </label>

                <div class="input-group">
                    <input name="postalCode" id="postalCode" class="form-control text-left">
                </div>
            </div>
        </div>

        <g:render template="/common/categoryPicker" model="${[title:message(code:'place.category.label')]}"/>

        <g:render template="/common/tagPicker"/>

        <div class="btn-toolbar">
            <input class="btn btn-primary" type="submit" value="${message(code: 'place.add.button')}"/>
        </div>
    </g:form>
    <bootstrap:help>
        help
    </bootstrap:help>
</bootstrap:helpedPanel>
</body>
</html>