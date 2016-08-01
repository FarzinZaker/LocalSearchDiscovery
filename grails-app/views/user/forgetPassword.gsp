<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.forgetPassword.title"/></title>
</head>

<body>
<div class="container">
<div id='login' class="row">
    <div class="col-lg-3 col-md-3 col-sm-2 col-xs-12"></div>
    <div class='floatingPanel col-lg-6 col-md-6 col-sm-8 col-xs-12'>
        <h2><g:message code="user.forgetPassword.title"/></h2>

        <g:if test='${flash.error}'>
            <div class='alert alert-danger'>${flash.error}</div>
        </g:if>

        <form action='${createLink(action: 'resetPassword')}' id="forgetPasswordForm" method='POST' autocomplete='off'>
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input name="username" class="form-control text-left"
                               id="username" data-validation="required"
                               placeholder="${message(code: 'springSecurity.login.username.label')}">
                    </div>
                </div>
            </div>

            <div class="row">

                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding:0">
                    <bootstrap:captcha name="captcha" id="forgetPasswordCaptcha" type="forgetPassword"/>
                </div>
            </div>


            <div>
                <input type='submit' id="submit" value='${message(code: "user.forgetPassword.button")}'
                       class="btn btn-primary block"/>
            </div>
        </form>
    </div>
    <div class="col-lg-3 col-md-3 col-sm-2 col-xs-12"></div>
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
