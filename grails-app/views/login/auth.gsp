<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.login.title"/></title>
</head>

<body>
<div class="container">
    <div id='login'>
        <div class='floatingPanel'>
            <h2><g:message code="springSecurity.login.header"/></h2>

            <g:if test='${flash.message}'>
                <div class='alert alert-danger'>${flash.message}</div>
            </g:if>
            <g:if test='${flash.info}'>
                <div class='alert alert-info'>${flash.info}</div>
            </g:if>

            <form action='${postUrl}' method='POST' id='loginForm' autocomplete='off'>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                            <input name="j_username" class="form-control text-left"
                                   id="username" data-validation="required"
                                   placeholder="${message(code: 'springSecurity.login.username.label')}">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                            <input type="password" name="j_password" class="form-control text-left"
                                   id="password" data-validation="required"
                                   placeholder="${message(code: 'springSecurity.login.password.label')}">
                        </div>

                        <div class="text-left fieldComment">
                            <a href="${createLink(controller: 'user', action: 'forgetPassword')}"><g:message
                                    code="forgetPassword.link"/></a>
                        </div>
                    </div>
                </div>

                <div class="row hidden">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon">
                                <input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
                                       <g:if test='${true || hasCookie}'>checked='checked'</g:if>/>
                            </span>
                            <label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
                        </div>
                    </div>
                </div>

                <g:if test="${loginErrorsCount > 0}">
                    <div class="row">

                        <div class="col-sm-12">
                            <bootstrap:captcha name="captcha" id="loginCaptcha" type="login"/>
                        </div>
                    </div>
                </g:if>

                <div>
                    <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'
                           class="btn btn-primary"/>
                    <span class="separator"><g:message code="or"/></span>
                    <a data-toggle="modal" data-target="#registerModal"><g:message code="user.register.header"/></a>
                </div>
            </form>
        </div>
    </div>
</div>
<script type='text/javascript'>
    <!--
    (function () {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>
