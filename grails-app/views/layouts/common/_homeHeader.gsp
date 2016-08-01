<nav class="navbar navbar-green navbar-fixed-top navbar-home">
    <div class="container">
        <div class="row">
            <div class="col-lg-2 col-md-2 col-sm-5 col-xs-12">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${createLink(uri: '/')}" alt="${message(code: 'site.title')}">
                        <img src="${resource(dir: 'images', file: 'logo.png')}"/>
                    </a>
                </div>
            </div>

            <div class="col-lg-7 col-md-7 col-sm-1 col-xs-12">
            </div>

            <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
                <div class="navbar-left">
                    <g:render template="/layouts/common/userMenu"/>
                </div>
            </div>
        </div>
    </div>
</nav>