<nav class="navbar navbar-green navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-9">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${createLink(uri: '/')}"><g:message code="site.title"/></a>
                </div>
                <place:searchBox/>
            </div>

            <div class="col-sm-3">
                <div class="navbar-left">
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
                        <!-- Split button -->
                        <div class="btn-group">
                            %{--<button type="button" class="btn btn-warning">Action</button>--}%
                            <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown"
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
                                    </g:link></li>
                                <li>
                                    <g:link controller="user" action="changePassword">
                                        <g:message code="user.changePassword.menu"/>
                                    </g:link>
                                </li>
                                <li role="separator" class="divider"></li>
                                <li>
                                    <g:link controller="logout">
                                        <g:message code="user.logout.button.label"/>
                                    </g:link>
                                </li>
                            </ul>
                        </div>
                    </sec:ifLoggedIn>
                </div>
            </div>
        </div>
    </div>
</nav>