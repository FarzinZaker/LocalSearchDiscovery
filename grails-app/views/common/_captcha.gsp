<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
    <div class="input-group">
        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
        <input type="text" name="${name}" id="${id}" class="form-control text-center"
               data-validation="required serverResponseError" placeholder="${message(code: 'captcha')}">
    </div>
</div>

<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
    <div class="input-group">
        <span class="input-group-addon">
            <img src="${createLink(controller: 'login', action: 'captcha', id: new Date().time, params: [type: type])}"
                 id="${id}Image"/>
        </span>
        <span class="input-group-btn">
            <button class="btn btn-default" type="button" style="float: left;padding-left: 10px;padding-right:0;padding-top: 1px;"
                    onclick="$('#${id}Image').attr('src', '${createLink(controller: 'login', action: 'captcha')}/' + new Date().getTime() + '?type=${type}');">
                <span class="glyphicon glyphicon-refresh"></span>
            </button>
        </span>
    </div>
</div>