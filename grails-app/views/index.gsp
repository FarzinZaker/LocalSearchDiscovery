<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="home"/>
</head>

<body>
<div class="mapBGContainer">
    <div class="mapBG">
    </div>

    <div class="mapBGWrapper">

    </div>

    <div class="contentOnMapBG">

        <div class="searchBoxContainer">
            <div class="searchBox">

                <div class="home-logo">
                    <a href="${createLink(uri: '/')}" alt="${message(code: 'site.title')}">
                        <img src="${resource(dir: 'images', file: 'logo-lg.png')}"/>
                    </a>
                </div>
                <place:searchBox/>
                <place:topCategories iconSize="44"/>

            </div>
        </div>

        <div class="home-action-bar">
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

        <div class="home-footer">
            <div class="copyright">
                <g:message code="copyright"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
