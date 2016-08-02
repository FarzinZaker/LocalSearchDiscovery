<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 8/2/2016
  Time: 12:47 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="empty"/>
    <title><g:message code="error.500.description"/></title>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-lg-3 col-md-3 col-sm-2 col-xs-12"></div>

        <div class='floatingPanel col-lg-6 col-md-6 col-sm-8 col-xs-12 text-center'>
            <a href="${createLink(uri: '/')}">
                <img src="${resource(dir: 'images', file: 'logo-lg.png')}" class="errorLogo"/>
            </a>

            <div>
                <h1 class="errorCode">500</h1>

                <p class="text-center">
                    <g:message code="error.500.description"/>
                </p>

                <p class="text-center">
                    <g:message code="error.500.help" args="${[createLink(uri: '/')?.toString()]}"/>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>