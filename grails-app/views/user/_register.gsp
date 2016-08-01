<bootstrap:modal id="registerModal">
    <h3 class="text-center">
        <img src="${resource(dir: 'images', file: 'logo-lg.png')}"/>
        %{--<div><g:message code="user.register.header"/></div>--}%
    </h3>

    <p class="text-center subtitle">
        <g:message code="user.register.description"/>
    </p>

    <div class='alert alert-warning text-center'>
        <g:message code="oldUsers.notification.part1"/>
        <a href="${createLink(controller: 'user', action: 'forgetPassword')}">
            <g:message code="user.forgetPassword.title"/>
        </a><br/>
        <g:message code="oldUsers.notification.part2"/>
    </div>


    <form id="registerForm" autocomplete="off">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                        <input type="email" name="email" class="form-control text-left"
                               data-validation="oneOfTwo emptyEmail serverResponseError" data-validation-other="mobile"
                               placeholder="${message(code: 'email')}">
                    </div>
                </div>

                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
                        <input type="text" name="mobile" class="form-control  text-left"
                               data-validation="oneOfTwo mobile serverResponseError" data-validation-other="email"
                               placeholder="${message(code: 'mobile')}">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-3">
                    <input name="male" data-toggle="toggle" data-on="${message(code: 'male')}"
                           data-off="${message(code: 'female')}" data-onstyle="male" data-offstyle="female"
                           type="checkbox">
                </div>

                <div class="col-lg-5 col-md-5 col-sm-10 col-xs-9">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input type="text" name="firstName" class="form-control inline-block"
                               data-validation="required"
                               placeholder="${message(code: 'firstName')}">
                    </div>
                </div>

                <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input type="text" name="lastName" class="form-control inline-block"
                               data-validation="required"
                               placeholder="${message(code: 'lastName')}">

                    </div>
                </div>
            </div>

            <div class="row">
                <bootstrap:captcha name="captcha" id="registerCaptcha" type="register"/>
            </div>
        </div>
    </form>
    <bootstrap:modalFooter>
        <button type="button" class="btn btn-default" data-dismiss="modal"><g:message code="cancel"/></button>
        <button type="button" class="btn btn-primary" onclick="register(this)"><g:message
                code="user.register.button.label"/></button>
        <bootstrap:loading size="48" id="registerLoading"/>
    </bootstrap:modalFooter>
</bootstrap:modal>