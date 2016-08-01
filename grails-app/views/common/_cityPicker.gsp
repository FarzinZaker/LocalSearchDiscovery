<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
        <label for="province">
            <span class="glyphicon glyphicon-th-large"></span>
            <g:message code="place.province.label"/>
        </label>

        <div class="input-group">
            <select name="province" id="province" class="form-control text-right" data-validation="required"></select>
        </div>
    </div>

    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
        <label for="city">
            <span class="glyphicon glyphicon-th"></span>
            <g:message code="place.city.label"/>
        </label>

        <div class="input-group">
            <select name="city" id="city" class="form-control text-right" data-validation="required"></select>
        </div>
    </div>
</div>
<script language="javascript" type="text/javascript">

    var cascadeSelect = true;
    var curProvince;
    var cities;
    var city;
    var province;
    function loadCities(value) {
        var proviences = $.grep(cities, function (n, i) {
            return n.name == value
        });
        if (proviences.length) {
            curProvince = proviences[0];
            city[0].selectize.clearOptions();
            city[0].selectize.load(function (callback) {
                callback(proviences[0].cities);
            });
        }
    }

    function selectByGeoLocation(latitude, longitude, update) {
        var nearestProvince = null;
        var nearestCity = null;
        var lastDistance = -1;
        for (var i in cities) {
            var p = cities[i];
            for (var j in p.cities) {
                var c = p.cities[j];
                var d = Math.pow(c.location.lat - latitude, 2) + Math.pow(c.location.long - longitude, 2);
                if (!nearestCity || d < lastDistance) {
                    nearestProvince = p;
                    nearestCity = c;
                    lastDistance = d;
                }
            }
        }

        if (!update && city[0].selectize.getValue() == "تهران" && nearestCity.name == "شهریار")
            return;
        cascadeSelect = false;
        province[0].selectize.setValue(nearestProvince.name);
        loadCities(nearestProvince.name);
        city[0].selectize.setValue(nearestCity.name);
        cascadeSelect = true;
    }

    $(document).ready(function () {
        city = $('#city').selectize({
            onChange: function (value) {
                if (!cascadeSelect)
                    return;
                var selCities = $.grep(curProvince.cities, function (n, i) {
                    return n.name == value
                });
                if (selCities.length && $.isFunction(showCityOnMap)) {
                    showCityOnMap(selCities[0].location.lat, selCities[0].location.long);
                }
            },
            valueField: 'name',
            labelField: 'name',
            searchField: ['name']
        });
        province = $('#province').selectize({
            onChange: function (value) {
                if (!cascadeSelect)
                    return;
                loadCities(value);
            },
            valueField: 'name',
            labelField: 'name',
            searchField: ['name']
        });
        $.get('<g:createLink uri="/data/cities.json"/>').success(function (data) {
            cities = data;
            province[0].selectize.clearOptions();
            province[0].selectize.load(function (callback) {
                callback(cities);

                <g:if test="${province}">
                province[0].selectize.setValue('${province}');
                </g:if>

                <g:if test="${city}">
                city[0].selectize.setValue('${city}');
                </g:if>
            });
        });
    });
</script>