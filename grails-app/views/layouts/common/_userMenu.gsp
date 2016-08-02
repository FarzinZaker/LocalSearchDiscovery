<sec:ifNotLoggedIn>
    <g:if test="${controllerName != 'login'}">
        <input type="button" class="btn btn-transparent navbar-btn"
               value="${message(code: 'user.login.button.label')}" data-toggle="modal"
               data-target="#loginModal"/>
    </g:if>
    <input type="button" class="btn btn-warning navbar-btn"
           value="${message(code: 'user.register.button.label')}" data-toggle="modal"
           data-target="#registerModal"/>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <div class="btn-group">
        <button type="button" class="btn btn-warning dropdown-toggle navbar-btn" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false">
            <span class="glyphicon glyphicon-user"></span>
            <security:userFullName/>
            <span class="caret"></span>
            <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu">
            <li>
                <g:link controller="user" action="profile">
                    <g:message code="user.profile.menu"/>
                </g:link>
            </li>
            <li>
                <g:link controller="user" action="changePassword">
                    <g:message code="user.changePassword.menu"/>
                </g:link>
            </li>
            <old:adsMenu/>
            <li role="separator" class="divider"></li>
            <li>
                <g:link controller="logout">
                    <g:message code="user.logout.button.label"/>
                </g:link>
            </li>
        </ul>
    </div>
</sec:ifLoggedIn>