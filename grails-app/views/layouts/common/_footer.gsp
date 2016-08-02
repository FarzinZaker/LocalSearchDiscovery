<div class="userFooter">
    <div id="footer1">
        <div class="container">
            <ul class="notrans">
                <li>
                    <a href="${createLink(controller: 'info', action: 'about')}"><g:message code="footer.about"/></a>
                </li>
                <li>
                    <a href="http://blog.agahisaz.com"><g:message code="footer.blog"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'help')}"><g:message code="footer.help"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'oldUsersHelp')}"><g:message code="footer.oldUsersHelp"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'cookie')}"><g:message code="footer.cookies"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'privacy')}"><g:message code="footer.privacy"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'terms')}"><g:message code="footer.terms"/></a>
                </li>
                <li>
                    <a href="${createLink(controller: 'info', action: 'contact')}"><g:message code="footer.contact"/></a>
                </li>
            </ul>
        </div>
    </div>

    <div id="footer2">

        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12 text-center">
                    <a href="${createLink(controller: 'place', action: 'add')}" class="addPlace">
                        <i class="icon"></i>
                        <span><g:message code="footer.addPlace"/></span>
                    </a>
                </div>
                <div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
                    <place:topCities/>
                </div>
                %{--<div class="col-sm-2 text-center">--}%
                    %{--<img class="enamad" src="${resource(dir:'/images', file: 'enamad.png')}"/>--}%
                    %{--<div class="clearfix"></div>--}%
                %{--</div>--}%
            </div>
        </div>
    </div>

    <div id="footer3">
        <div class="container">
            <g:message code="site.title"/> &copy; 2016 - <g:message code="parsSystem"/>
        </div>

    </div>
</div>