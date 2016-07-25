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
    <title><g:message code="oldAds.add.title"/></title>

</head>

<body>
<div class="container">
    <bootstrap:helpedPanel>
        <h3 class="fieldSet-title">
            <g:message code="oldAds.add.title"/>
        </h3>
        <g:if test="${flash.error}">
            <div class="alert alert-danger">
                ${flash.error}
            </div>
        </g:if>
        <g:form controller="oldAds" action="save">
            <input type="hidden" name="adId" value="${ad.id}"/>
            <g:render template="/place/form"/>
        </g:form>
        <bootstrap:help>
            help
        </bootstrap:help>
    </bootstrap:helpedPanel>
</div>
</body>
</html>