<html>
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="user.changePassword.title"/></title>
</head>

<body>
<div class="container">
    <div id='login'>
        <div class='floatingPanel'>
            <h2><g:message code="user.changePassword.title"/></h2>

            <g:if test='${flash.error}'>
                <div class='alert alert-danger'>${flash.error}</div>
            </g:if>

            <g:if test='${flash.info}'>
                <div class='alert alert-info'>${flash.info}</div>
            </g:if>


            <form action='${createLink(action: 'saveNewPassword')}' id="changePasswordForm" method='POST'
                  autocomplete='off'>
                <g:if test="${!skipOldPassword}">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                <input type="password" name="oldPassword" class="form-control text-left"
                                       data-validation="required"
                                       placeholder="${message(code: 'user.changePassword.oldPassword')}">
                            </div>
                        </div>
                    </div>
                </g:if>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                            <input type="password" name="newPasswordConfirm" class="form-control text-left"
                                   data-validation="required"
                                   placeholder="${message(code: 'user.changePassword.newPassword')}">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                            <input type="password" name="newPassword" class="form-control text-left"
                                   data-validation="required confirmation" data-validation-confirm="newPasswordConfirm"
                                   placeholder="${message(code: 'user.changePassword.newPasswordConfirm')}">
                        </div>
                    </div>
                </div>


                <div>
                    <input type='submit' id="submit" value='${message(code: "user.changePassword.button")}'
                           class="btn btn-primary"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
