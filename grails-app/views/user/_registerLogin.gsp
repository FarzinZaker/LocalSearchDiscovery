<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<bootstrap:modal id="registerLoginModal">
    <h3 class="text-center">
        <g:message code="user.register.activate.header"/>
    </h3>

    <p class="text-center subtitle">
        <g:message code="register.success"/>
    </p>

    <form id="registerLoginForm" action="${request.contextPath}${ org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.securityConfig.apf.filterProcessesUrl}" method="POST" autocomplete='off'>
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input type="hidden" name="j_username" value=""/>
                        <input type="password" name="j_password" class="form-control text-left"
                               data-validation="required"
                               placeholder="${message(code: 'user.password')}">
                    </div>
                </div>
            </div>
        </div>
    </form>
    <bootstrap:modalFooter>
        <button type="button" class="btn btn-primary" onclick="registerLogin(this)"><g:message
                code="user.register.activate.button.label"/></button>
        <bootstrap:loading size="48" id="registerLoginLoading"/>
    </bootstrap:modalFooter>
</bootstrap:modal>
<g:javascript>
    $.validate();
</g:javascript>