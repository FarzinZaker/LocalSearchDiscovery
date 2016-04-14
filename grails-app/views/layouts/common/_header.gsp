<nav class="navbar navbar-green navbar-fixed-top">
    <div class="container">
        <div class="row">
            <div class="col-sm-3">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${createLink(uri: '/')}"><g:message code="site.title"/></a>
                </div>
            </div>

            <div class="col-sm-6" role="search">
                <div class="navbar-form">
                    <div class="form-group">
                        <input type="text" class="form-control"/>
                        <input type="text" class="form-control"/>
                        <span class="glyphicon glyphicon-search"></span>
                    </div>
                </div>
            </div>

            <div class="col-sm-3">
                <div class="navbar-left">
                    <sec:ifNotLoggedIn>
                        <input type="button" class="btn btn-transparent navbar-btn"
                               value="${message(code: 'user.login.button.label')}" data-toggle="modal"
                               data-target="#loginModal"/>
                        <input type="button" class="btn btn-warning navbar-btn"
                               value="${message(code: 'user.register.button.label')}" data-toggle="modal"
                               data-target="#registerModal"/>
                    </sec:ifNotLoggedIn>
                    <sec:ifLoggedIn>
                        <g:link controller="logout"  class="btn btn-transparent navbar-btn"><g:message code="user.logout.button.label" /></g:link>
                    </sec:ifLoggedIn>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="tagBar">
    <div class="container">
        <ul>
            <li><a href="#">Top Picks</a></li>
            <li><a href="#">Trending</a></li>
            <li><a href="#">فست فود</a></li>
            <li><a href="#">کافی شاپ</a></li>
            <li><a href="#">تفریح</a></li>
            <li><a href="#">فروشگاه</a></li>
        </ul>
    </div>
</div>