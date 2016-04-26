<r:require module="maps"/>

<div class="mapContainer ${cssClass ?: ''}"
     style="${style ?: ''};width:${width ? (width.toString().toInteger() + 6) + 'px' : 'auto'};height:${height.toString().toInteger() + 38}px;">
    <div class="mapHeader"><g:message code="locationPicker.title"/></div>

    <div id="map_${id}" style="width:${width ? width + 'px' : 'auto'};height:${height}px;"></div>
    <input type="hidden" name="${name}" id="${id}" data-validation="${validation}" value="${center? "${center[0]},${center[1]}" : ''}"/>
</div>
<script type="text/javascript">

    var pinLayer_${id} = null;
    var map_${id};
    $(document).ready(function () {
        //setup map
        map_${id} = new ol.Map({
            target: 'map_${id}',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                <g:if test="${!center}">
                center: ol.proj.transform([51.3890, 35.6892], 'EPSG:4326', 'EPSG:3857'),
                zoom: 13
                </g:if>
                <g:else>
                center: ol.proj.transform([${center[1]}, ${center[0]}], 'EPSG:4326', 'EPSG:3857'),
                zoom: 15
                </g:else>
            })
        });

        <g:if test="${!center && visitorLocation}">
        //show visitor position
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;
                map_${id}.setView(new ol.View({
                    center: ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857'),
                    zoom: 15
                }));
                if ($.isFunction(selectByGeoLocation)) {
                    try {
                        selectByGeoLocation(latitude, longitude, true)
                    } catch (ex) {
                    }
                }
            });
        }
        </g:if>

        <g:if test="${center}">
        //show flag
        var iconFeatures = [];
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(ol.proj.transform([${center[1]}, ${center[0]}], 'EPSG:4326', 'EPSG:3857')),
            type: 'flag'
        });
        iconFeatures.push(iconFeature);
        var vectorSource = new ol.source.Vector({
            features: iconFeatures //add an array of features
        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                anchor: [0.5, 41],
                anchorXUnits: 'fraction',
                anchorYUnits: 'pixels',
                opacity: 0.9,
                src: '${resource(dir:'images', file: 'pin-select.png')}'
            }))
        });
        var pinLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map_${id}.addLayer(pinLayer);
        </g:if>

        //show flag
        map_${id}.on('click', function (evt) {
            var coordinate = evt.coordinate;
            var latLong = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
            if ($.isFunction(selectByGeoLocation)) {
                    try {
                        selectByGeoLocation(latLong[1], latLong[0], false)
                    } catch (ex) {
                    }
                }
            $('#${id}').val(latLong[1] + ',' + latLong[0]);
            var iconFeatures = [];

            var iconFeature = new ol.Feature({
                geometry: new ol.geom.Point(coordinate),
                name: 'selected'
            });

            iconFeatures.push(iconFeature);

            var vectorSource = new ol.source.Vector({
                features: iconFeatures //add an array of features
            });

            var iconStyle = new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                    anchor: [0.5, 41],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    opacity: 0.9,
                    src: '${resource(dir:'images', file: 'pin-select.png')}'
                }))
            });


            if (pinLayer_${id})
                map_${id}.removeLayer(pinLayer_${id});
            pinLayer_${id} = new ol.layer.Vector({
                source: vectorSource,
                style: iconStyle
            });
            map_${id}.addLayer(pinLayer_${id});
        });
    });
</script>