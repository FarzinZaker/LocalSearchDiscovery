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
<div class="container">
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
            <g:render template="form"/>
        </g:form>
        <bootstrap:help>

            <h4><g:message code="place.add.help.title"/></h4>

            <p><g:message code="place.add.help.description"/></p>
            <ol>
                <li>
                    <g:message code="place.add.help.item1"/>
                </li>
                <li>
                    <g:message code="place.add.help.item2"/>
                </li>
                <li>
                    <g:message code="place.add.help.item3"/>
                </li>
                <li>
                    <g:message code="place.add.help.item4"/>
                </li>
                <li>
                    <g:message code="place.add.help.item5"/>
                </li>
            </ol>
        </bootstrap:help>
    </bootstrap:helpedPanel>
</div>
</body>
</html>