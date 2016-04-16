<div class="input-group">
    <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
    <input type="text" name="${name}" id="${id}" class="form-control text-center"
           data-validation="required serverResponseError" placeholder="${message(code: 'captcha')}">
    <span class="input-group-addon">
        <img src="${createLink(controller: 'login', action: 'captcha', id: new Date().time, params: [type: type])}"
             id="${id}Image"/>
    </span>
    <span class="input-group-btn">
        <button class="btn btn-default" type="button"
                onclick="$('#${id}Image').attr('src', '${createLink(controller: 'login', action: 'captcha')}/' + new Date().getTime() + '?type=${type}');">
            <span class="glyphicon glyphicon-refresh"></span>
        </button>
    </span>
</div>