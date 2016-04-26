<r:require module="maps"/>

<div>
    <div id="map_explore"></div>

    <div id="mapTip_explore" class="mapTip"></div>
</div>
<script type="text/javascript">

    function hideFeatureOnMap(id) {
        var features = pinLayer.getSource().getFeatures();
        var feature = null;
        var i = 0;
        while (i++ < features.length && !feature)
            if (features[i].get('id') == id)
                feature = features[i];
        if (feature)
            feature.setStyle(iconStyle(feature));
        $("#mapTip_explore").hide()
    }

    function showFeatureOnMap(id) {
        var features = pinLayer.getSource().getFeatures();
        var feature = null;
        var i = 0;
        while (i++ < features.length && !feature)
            if (features[i].get('id') == id)
                feature = features[i];
        if (feature) {
            var geometry = feature.getGeometry();
            var coordinate = geometry.getCoordinates();
            pixel = map_explore.getPixelFromCoordinate(coordinate);
            var tip = $('#mapTip_explore').show().html("<b>" + feature.get('name') + "</b><br/><span>" + feature.get('address') + "</span>");
            tip.css('left', Math.round(pixel[0] - tip.width() / 2 - 12) + 'px').css('top', Math.round(pixel[1] - tip.height() - 80) + 'px');
            feature.setStyle(highlightedIconStyle(feature));
        }
        else {
            $("#mapTip_explore").hide()
        }
    }

    var map_explore;
    var pinLayer;
    var highlightedIconStyle;
    var iconStyle;
    $(document).ready(function () {
        //setup map
        map_explore = new ol.Map({
            target: 'map_explore',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                <g:if test="${!center}">
                center: ol.proj.transform([51.3890, 35.6892], 'EPSG:4326', 'EPSG:3857'),
                zoom: 11
                </g:if>
                <g:else>
                center: ol.proj.transform([${center[1]}, ${center[0]}], 'EPSG:4326', 'EPSG:3857'),
                zoom: 11
                </g:else>
            })
        });

        <g:if test="${!center && visitorLocation}">
        //show visitor position
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;
                map_explore.setView(new ol.View({
                    center: ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857'),
                    zoom: 11
                }));
            });
        }
        </g:if>

        //show flag
        var pinFeatures = [];
        var iconFeatures = [];
        <g:each in="${places}" var="place" status="i">
        var placeLocation = ol.proj.transform([${place?.location[1]}, ${place?.location[0]}], 'EPSG:4326', 'EPSG:3857');
        var pinFeature = new ol.Feature({
            geometry: new ol.geom.Point(placeLocation),
            type: 'flag',
            index: '${i + 1}',
            id: '${place._id}',
            name: '${place.name}',
            address: '${place.address.replace('\n', ' ')}'
        });
        pinFeatures.push(pinFeature);
        </g:each>
        var vectorSource = new ol.source.Vector({
            features: pinFeatures //add an array of features
        });
        iconStyle = (function () {
            var style = new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                    anchor: [0.5, 52],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    opacity: 1,
                    src: '${resource(dir:'images', file: 'pin.png')}'
                })),
                text: new ol.style.Text({
                    offsetY: -30,
                    text: '1',
                    scale: 2,
                    fill: new ol.style.Fill({
                        color: '#FFFFFF'
                    })
                })
            });
            var styles = [style];
            return function (feature, resolution) {
                style.getText().setText(feature.get("index"));
                style.setZIndex(50 - parseInt(feature.get("index")));
                return styles;
            };
        })();
        highlightedIconStyle = (function () {
            var style = new ol.style.Style({
                image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                    anchor: [0.5, 52],
                    anchorXUnits: 'fraction',
                    anchorYUnits: 'pixels',
                    opacity: 1,
                    src: '${resource(dir:'images', file: 'pin-highlight.png')}'
                })),
                text: new ol.style.Text({
                    offsetY: -30,
                    text: '1',
                    scale: 2,
                    fill: new ol.style.Fill({
                        color: '#FFFFFF'
                    })
                }),
                zIndex: 100
            });
            var styles = [style];
            return function (feature, resolution) {
                style.getText().setText(feature.get("index"));
                return styles;
            };
        })();
        pinLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map_explore.addLayer(pinLayer);

        //show tip
        map_explore.on('pointermove', function (evt) {
            if (evt.dragging) {
                return;
            }
            var pixel = map_explore.getEventPixel(evt.originalEvent);
            var feature = map_explore.forEachFeatureAtPixel(pixel, function (feature, layer) {
                return feature;
            });
            if (feature) {
                var geometry = feature.getGeometry();
                var coordinate = geometry.getCoordinates();
                pixel = map_explore.getPixelFromCoordinate(coordinate);
                var tip = $('#mapTip_explore').show().html("<b>" + feature.get('name') + "</b><br/><span>" + feature.get('address') + "</span>");
                tip.css('left', Math.round(pixel[0] - tip.width() / 2 - 12) + 'px').css('top', Math.round(pixel[1] - tip.height() - 80) + 'px');
            }
            else {
                $("#mapTip_explore").hide()
            }
        });
    });
</script>