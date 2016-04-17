<r:require module="maps"/>

<div class="mapContainer ${cssClass ?: ''}" style="${style ?: ''};">
    <div id="map_${id}" style="width:${width};height:${height};"></div>
    <input type="hidden" name="${name}" id="${id}"/>

    <div id="mapTip" class="mapTip"></div></div>
<script type="text/javascript">


    var pinLayer_${id} = null;
    $(document).ready(function () {

        //cursor position
        var currentMousePos = {x: -1, y: -1};
        $(document).mousemove(function (event) {
            currentMousePos.x = event.pageX;
            currentMousePos.y = event.pageY;
        });

        //setup map
        var map_${id} = new ol.Map({
            target: 'map_${id}',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                <g:if test="${!center}">
                center: ol.proj.transform([51.3890, 35.6892], 'EPSG:4326', 'EPSG:3857'),
                </g:if>
                <g:else>
                center: ol.proj.transform([${center[1]}, ${center[0]}], 'EPSG:4326', 'EPSG:3857'),
                </g:else>
                zoom: 13
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
            });
        }
        </g:if>

        //show flag
        map_${id}.on('click', function (evt) {
            var coordinate = evt.coordinate;
            var latLong = ol.proj.transform(evt.coordinate, 'EPSG:3857', 'EPSG:4326');
            $('#${id}').val(latLong[1] + ',' + latLong[0]);
            var iconFeatures = [];

            var iconFeature = new ol.Feature({
                geometry: new ol.geom.Point(coordinate),
                name: 'شیرینی بی بی',
                type: 'confectionery',
                price: '2'
            });

            iconFeatures.push(iconFeature);

            var vectorSource = new ol.source.Vector({
                features: iconFeatures //add an array of features
            });

            var iconStyle = new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                    anchor: [0.5, 46],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    opacity: 0.9,
                    src: '${resource(dir:'images', file: 'pin.png')}'
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

        map_${id}.on('pointermove', function (evt) {
            if (evt.dragging) {
                return;
            }
            var pixel = map_${id}.getEventPixel(evt.originalEvent);
            var feature = map_${id}.forEachFeatureAtPixel(pixel, function (feature, layer) {
                return feature;
            });
            if (feature) {
                var geometry = feature.getGeometry();
                var coordinate = geometry.getCoordinates();
                pixel = map_${id}.getPixelFromCoordinate(coordinate);
                var tip = $('#mapTip').show().html(feature.get('name'));
                tip.css('left', Math.round(pixel[0] - tip.width() / 2 - 12) + 'px').css('top', Math.round(pixel[1] - tip.height() - 50) + 'px');
            }
            else {
                $('#mapTip').hide()
            }
        });
    });
</script>