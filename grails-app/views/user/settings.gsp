<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.settings.title"/></title>
</head>

<body>
<div class="container">
    <bootstrap:verticalTabStrip>
        <bootstrap:tabPage id="profile" title="${message(code: 'user.profile.title')}">
            <g:render template="profile"/>
        </bootstrap:tabPage>
    </bootstrap:verticalTabStrip>
</div>
</body>
</html>
