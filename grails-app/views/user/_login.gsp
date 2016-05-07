<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils" %>
<bootstrap:modal id="loginModal" size="sm">
    <h3 class="text-center">
        <img src="${resource(dir: 'images', file: 'logo-lg.png')}"/>
        %{--<g:message code="user.login.header"/>--}%
    </h3>

    <p class="text-center subtitle">
        <g:message code="user.login.description"/>
    </p>
    <form action='${request.contextPath}${ org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.securityConfig.apf.filterProcessesUrl}' method='POST' id='loginFormModal' autocomplete='off'>
        <div class="row">
            <div class="col-sm-12">
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                    <input name="j_username" class="form-control text-left"
                           data-validation="required" onkeypress="loginOnEnter(event)"
                           placeholder="${message(code: 'springSecurity.login.username.label')}">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-sm-12">
                <div class="input-group">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                    <input type="password" name="j_password" class="form-control text-left" onkeypress="loginOnEnter(event)"
                           data-validation="required"
                           placeholder="${message(code: 'springSecurity.login.password.label')}">
                </div>
            </div>
        </div>
    </form>
    <bootstrap:modalFooter>
        <button type="button" class="btn btn-default" data-dismiss="modal"><g:message code="cancel"/></button>
        <button type="button" class="btn btn-primary" onclick="login()"><g:message code="user.login.button.label" /></button>
    </bootstrap:modalFooter>
</bootstrap:modal>