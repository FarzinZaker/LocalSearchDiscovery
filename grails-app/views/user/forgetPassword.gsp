<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.forgetPassword.title"/></title>
</head>

<body>
<div id='login'>
    <div class='floatingPanel'>
        <h2><g:message code="user.forgetPassword.title"/></h2>

        <g:if test='${flash.error}'>
            <div class='alert alert-danger'>${flash.error}</div>
        </g:if>

        <form action='${createLink(action: 'resetPassword')}' id="forgetPasswordForm" method='POST' autocomplete='off'>
            <div class="row">
                <div class="col-sm-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input name="username" class="form-control text-left"
                               id="username" data-validation="required"
                               placeholder="${message(code: 'springSecurity.login.username.label')}">
                    </div>
                </div>
            </div>

            <div class="row">

                <div class="col-sm-12">
                    <bootstrap:captcha name="captcha" id="forgetPasswordCaptcha" type="forgetPassword"/>
                </div>
            </div>


            <div>
                <input type='submit' id="submit" value='${message(code: "user.forgetPassword.button")}'
                       class="btn btn-primary"/>
            </div>
        </form>
    </div>
</div>
<script type='text/javascript'>
    <!--
    (function () {
        document.forms['forgetPasswordForm'].elements['username'].focus();
    })();
    // -->
</script>
</body>
</html>
