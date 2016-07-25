<nav class="navbar navbar-green navbar-fixed-top">
    <div class="container">
        <div class="row">
            <div class="col-sm-2">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${createLink(uri: '/')}" alt="${message(code: 'site.title')}">
                        <img src="${resource(dir: 'images', file: 'logo.png')}"/>
                    </a>
                </div>
            </div>

            <div class="col-sm-7">
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

<div class="tagBar skipHeader">
    <div class="container">
        <place:topCategories iconSize="32"/>
        <sec:ifLoggedIn>
            <place:waitingEditSuggestions/>
        </sec:ifLoggedIn>

    </div>
</div>