<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.settings.title"/></title>
</head>

<body>
<div class="container silverPanel">
    <div class="row">

        <div class="col-xs-9">
            <!-- Tab panes -->
            <div class="tab-content tab-content-left whitePanel">
                <div class="tab-pane ${currentTab == 'profile' ? 'active' : ''}" id="profile">
                    <g:render template="profile"/>
                </div>

                %{--<div class="tab-pane" id="otherTab">other tab</div>--}%
            </div>
        </div>
        <div class="col-xs-3"><!-- required for floating -->
        <!-- Nav tabs -->
            <ul class="nav nav-tabs tabs-left">
                <li class="${currentTab == 'profile' ? 'active' : ''}"><a href="#profile" data-toggle="tab"><g:message code="user.profile.title"/></a></li>
                %{--<li><a href="#otherTab" data-toggle="tab">Other Tabs</a></li>--}%
            </ul>
        </div>

        <div class="clearfix"></div>

    </div>
</div>
</body>
</html>
