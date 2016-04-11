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
                    <input type="button" class="btn btn-transparent navbar-btn"
                           value="${message(code: 'user.login.button.label')}" data-toggle="modal"
                           data-target="#loginModal"/>
                    <input type="button" class="btn btn-warning navbar-btn"
                           value="${message(code: 'user.register.button.label')}" data-toggle="modal"
                           data-target="#registerModal"/>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="tagBar">
    <div class="container">
        Tags
    </div>
</div>