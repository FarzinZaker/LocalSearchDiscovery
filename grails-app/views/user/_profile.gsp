<g:form controller="user" action="saveBasicInfo">
    <bootstrap:cropie name="profileImage" cssClass="profileImage text-center" src="${createLink(controller: 'image', action: 'profile', params: [size:200])}"/>

    <g:if test="${flash.error}">
        <div class="alert alert-danger">
            ${flash.error}
        </div>
    </g:if>
    <h3 class="fieldSet-title">
        <g:message code="user.profile.title"/>
    </h3>


    <div class="row">
        <div class="col-sm-6">
            <label for="mobile">
                <span class="glyphicon glyphicon-phone"></span>
                <g:message code="user.mobile.label"/>
            </label>

            <div class="input-group">
                <input name="mobile" id="mobile" class="form-control text-left" data-validation="required mobile"
                       value="${user.mobile}">
            </div>
        </div>

        <div class="col-sm-6">
            <label for="email">
                <span class="glyphicon glyphicon-envelope"></span>
                <g:message code="user.email.label"/>
            </label>

            <div class="input-group">
                <input name="email" id="email" class="form-control text-left" data-validation="required email"
                       value="${user.email}">
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-1">
            <label for="firstName">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="user.firstName.label"/>
            </label>

            <div>
                <input name="male" id="male" data-toggle="toggle" data-on="${message(code: 'male')}"
                       data-off="${message(code: 'female')}" data-onstyle="male" data-offstyle="female"
                       type="checkbox" ${user.gender == 'male' ? 'checked' : ''}/>
            </div>
        </div>

        <div class="col-sm-5">
            <label for="firstName">&nbsp;
            </label>

            <div class="input-group">
                <input name="firstName" id="firstName" class="form-control" data-validation="required"
                       value="${user.firstName}">
            </div>
        </div>

        <div class="col-sm-6">
            <label for="lastName">
                <span class="glyphicon glyphicon-user"></span>
                <g:message code="user.lastName.label"/>
            </label>

            <div class="input-group">
                <input name="lastName" id="lastName" class="form-control" data-validation="required"
                       value="${user.lastName}">
            </div>
        </div>
    </div>

    <div class="btn-toolbar">
        <input class="btn btn-primary" type="submit" value="${message(code: 'user.profile.button')}"/>
        <g:link class="btn" controller="user" action="changePassword"><g:message code="user.changePassword.menu"/></g:link>
    </div>
</g:form>