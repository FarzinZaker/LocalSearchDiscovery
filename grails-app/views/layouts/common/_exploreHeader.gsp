<nav class="navbar navbar-green navbar-fixed-top">
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-9">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${createLink(uri: '/')}" alt="${message(code: 'site.title')}">
                        <img src="${resource(dir: 'images', file: 'logo.png')}"/>
                    </a>
                </div>
                <place:searchBox/>
            </div>

            <div class="col-sm-3">
                <div class="navbar-left">
                    <g:render template="/layouts/common/userMenu"/>
                </div>
            </div>
        </div>
    </div>
</nav>