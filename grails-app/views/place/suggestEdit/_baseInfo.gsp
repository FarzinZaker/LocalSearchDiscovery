<div class="row">
    <div class="col-lg-7 col-md-7 col-sm-12 col-xs-12">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <label for="name">
                    <span class="glyphicon glyphicon-home"></span>
                    <g:message code="place.name.label"/>
                </label>

                <div class="input-group">
                    <input name="name" id="name" class="form-control" data-validation="required"
                           value="${place?.name}">
                </div>
            </div>
        </div>


        <g:render template="/common/categoryPicker"
                  model="${[title: message(code: 'place.category.label'), category: place?.category]}"/>

        <div class="row">
            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                <label for="phone">
                    <span class="glyphicon glyphicon-phone-alt"></span>
                    <g:message code="place.phone.label"/>
                </label>

                <div class="input-group">
                    <input name="phone" id="phone" class="form-control text-left ltr" value="${place?.phone}">
                </div>
            </div>

            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                <label for="postalCode">
                    <span class="glyphicon glyphicon-barcode"></span>
                    <g:message code="place.postalCode.label"/>
                </label>

                <div class="input-group">
                    <input name="postalCode" id="postalCode" class="form-control text-left ltr"
                           value="${place?.postalCode}">
                </div>
            </div>
        </div>

        <g:render template="/common/cityPicker" model="${[province: place?.province, city: place?.city]}"/>
    </div>

    <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
        <map:locationPicker
                name="location"
                height="285"
                validation="required"
                center="${place?.location}"/>
    </div>
</div>

<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <label for="address">
            <span class="glyphicon glyphicon-road"></span>
            <g:message code="place.address.label"/>
        </label>

        <div class="input-group">
            <input name="address" id="address" class="form-control" value="${place?.address}">
        </div>
    </div>
</div>

<g:render template="/common/tagPicker" model="${[tags: place?.tags]}"/>
