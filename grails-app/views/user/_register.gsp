<bootstrap:modal id="registerModal">
    <h3 class="text-center">
        <g:message code="user.register.header"/>
    </h3>

    <p class="text-center subtitle">
        <g:message code="user.register.description"/>
    </p>

    <form id="registerForm">
        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-6">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-envelope"></span></span>
                        <input type="email" name="email" class="form-control text-left"
                               data-validation="oneOfTwo emptyEmail serverResponseError" data-validation-other="mobile"
                               placeholder="${message(code: 'email')}">
                    </div>
                </div>

                <div class="col-sm-6">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
                        <input type="text" name="mobile" class="form-control  text-left"
                               data-validation="oneOfTwo mobile serverResponseError" data-validation-other="email"
                               placeholder="${message(code: 'mobile')}">
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-sm-2">
                    <input name="male" data-toggle="toggle" data-on="${message(code: 'male')}"
                           data-off="${message(code: 'female')}" data-onstyle="male" data-offstyle="female"
                           type="checkbox">
                </div>

                <div class="col-sm-10">
                    <div class="table-row">
                        <div class="table-cell align-top">
                            <div class="input-group" style="width:240px">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="text" name="firstName" class="form-control inline-block"
                                       data-validation="required"
                                       placeholder="${message(code: 'firstName')}">
                            </div>
                        </div>

                        <div class="table-cell align-top">

                            <div class="input-group" style="width:203px;">
                                <input type="text" name="lastName" class="form-control inline-block"
                                       data-validation="required"
                                       placeholder="${message(code: 'lastName')}">

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">

                <div class="col-sm-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input type="text" name="captcha" class="form-control text-center"
                               data-validation="required serverResponseError" placeholder="${message(code: 'captcha')}">
                        <span class="input-group-addon">
                            <img src="${createLink(controller: 'login', action: 'captcha', id: new Date().time)}" id="registerCaptchaImage"/>
                        </span>
                        <span class="input-group-btn">
                            <button class="btn btn-default" type="button"
                                    onclick="$('#registerCaptchaImage').attr('src', '${createLink(controller: 'login', action: 'captcha')}/' + new Date().getTime());">
                                <span class="glyphicon glyphicon-refresh"></span>
                            </button>
                        </span>
                    </div>
                </div>

                <div class="col-sm-1">
                </div>
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
<g:javascript>
    $.validate();
</g:javascript>