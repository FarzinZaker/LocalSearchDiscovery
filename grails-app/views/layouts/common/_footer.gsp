<div class="userFooter">
    <div id="footer1">
        <div class="container">
            <ul class="notrans">
                <li>
                    <a href="#"><g:message code="footer.about"/></a>
                </li>
                <li>
                    <a href="#"><g:message code="footer.blog"/></a>
                </li>
                <li>
                    <a href="#"><g:message code="footer.help"/></a>
                </li>
                <li>
                    <a href="#"><g:message code="footer.cookies"/></a>
                </li>
                <li>
                    <a href="#"><g:message code="footer.privacy"/></a>
                </li>
                <li>
                    <a href="#"><g:message code="footer.terms"/></a>
                </li>
            </ul>
        </div>
    </div>

    <div id="footer2">

        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <place:topCities/>
                </div>
                <div class="col-sm-3">
                    <a href="${createLink(controller: 'place', action: 'add')}" class="btn">
                        <i class="glyphicon glyphicon-plus"></i>
                        <g:message code="footer.addPlace"/>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div id="footer3">
        <div class="container">
            <g:message code="site.title"/> &copy; 2016 - <g:message code="parsSystem"/>
        </div>

    </div>
</div>