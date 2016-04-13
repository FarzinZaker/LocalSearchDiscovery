<bootstrap:modal id="registerModal">
    <h4>
        <g:message code="user.register.header"/>
    </h4>
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-6">
                <div class="text-center">
                    <g:message code="email"/>
                    <input type="email" name="email" class="form-control text-left">
                </div>
            </div>
            <div class="col-sm-6">
                <div class="text-center">
                    <g:message code="mobile"/>
                    <input type="text" name="mobile" class="form-control  text-left">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-sm-3">
                <div class="text-center">
                    <g:message code="firstName"/>
                    <input type="text" name="firstName" class="form-control  text-left">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="text-center">
                    <g:message code="lastName"/>
                    <input type="text" name="lastName" class="form-control  text-left">
                </div>
            </div>
            <div class="col-sm-3">
                <div class="text-center">
                    <input name="male" data-toggle="toggle" data-on="${message(code: 'male')}" data-off="${message(code: 'female')}" data-offstyle="success" type="checkbox">
                </div>
            </div>
        </div>
    </div>
    %{--<bootstrap:modalFooter>--}%
        %{--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>--}%
        %{--<button type="button" class="btn btn-primary">Save changes</button>--}%
    %{--</bootstrap:modalFooter>--}%
</bootstrap:modal>