<r:require module="maps"/>

<div>
    <div id="map_explore"></div>

    <div id="mapTip_explore" class="mapTip"></div>
</div>
<script type="text/javascript">

    function hideFeatureOnMap(id) {
        deselectAllFeatures();
//        var features = pinLayer.getSource().getFeatures();
//        var feature = null;
//        var i = 0;
//        while (i++ < features.length && !feature)
//            if (features[i].get('id') == id)
//                feature = features[i];
//        if (feature)
//            feature.setStyle(iconStyle(feature));
//        $("#mapTip_explore").hide()
    }

    function showFeatureOnMap(id) {
        var features = pinLayer.getSource().getFeatures();
        var feature = null;
        var i = 0;
        while (i++ < features.length && !feature)
            if (features[i].get('id') == id)
                feature = features[i];
        if (feature) {
            selectFeature(feature, false);
        }
        else {
            deselectAllFeatures();
        }
    }

    var map_explore;
    var pinLayer;
    var highlightedIconStyle;
    var iconStyle;
    $(document).ready(function () {
        //setup map
        var mapLayer = new ol.layer.Tile({
            source: new ol.source.OSM()
        });
        map_explore = new ol.Map({
            target: 'map_explore',
            layers: [mapLayer],
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
        var mapTop = ol.proj.transform([180, 90], 'EPSG:4326', 'EPSG:3857');
        var extent = map_explore.getView().calculateExtent(map_explore.getSize());
        var topCenter = [(extent[0] + extent[2]) / 2, extent[3]];
        <g:each in="${places}" var="place" status="i">
        var placeLocation = ol.proj.transform([${place?.location[1]}, ${place?.location[0]}], 'EPSG:4326', 'EPSG:3857');
        var pinFeature = new ol.Feature({
            geometry: new ol.geom.Point([placeLocation[0], mapTop[1]]),
            speed : ${Math.ceil((i + 1) / 4D)},// Math.round(Math.random() * (17 - 3) + 3),
            location: placeLocation,
            type: 'flag',
            index: '${i + 1}',
            id: '${place._id}',
            name: '${place.name}',
            address: '${place.address.replace('\n', ' ')}',
            icon: '${resource(dir: "images/categories/${place?.category?.iconDirectory}", file: "${place?.category?.iconFile}44.png")}'
        });
        pinFeatures.push(pinFeature);
        </g:each>
        var vectorSource = new ol.source.Vector({
//            features: pinFeatures //add an array of features
        });
        iconStyle = (function () {
            return function (feature, resolution) {
                var style = new ol.style.Style({
                    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                        anchor: [0.5, 66],
                        anchorXUnits: 'fraction',
                        anchorYUnits: 'pixels',
                        opacity: 1,
                        src: '${resource(dir:'images', file: 'pin-list.png')}'
                    })),
                    text: new ol.style.Text({
                        offsetY: -45,
                        text: '1',
                        scale: 2,
                        fill: new ol.style.Fill({
                            color: '#EE5921'
                        })
                    })
                });
                var styles = [style];
                style.getText().setText(feature.get("index"));
                style.setZIndex(50 - parseInt(feature.get("index")));
                return styles;
            };
        })();
        highlightedIconStyle = (function () {
            return function (feature, resolution) {
                var style = new ol.style.Style({
                    image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                        anchor: [0.5, 66],
                        anchorXUnits: 'fraction',
                        anchorYUnits: 'pixels',
                        opacity: 1,
                        src: '${resource(dir:'images', file: 'pin-list-highlight.png')}'
                    })),
                    text: new ol.style.Text({
                        offsetY: -45,
                        text: '1',
                        scale: 2,
                        fill: new ol.style.Fill({
                            color: '#96C950'
                        })
                    }),
                    zIndex: 100
                });
                var styles = [style];
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
                selectFeature(feature, true);
            }
            else {
                deselectAllFeatures();
            }
        });

        //animation
        function animate(feature) {

            var location = feature.get('location');
            var geometry = feature.getGeometry();
            var coordinate = geometry.getCoordinates();
            var speed = parseInt(feature.get('speed'));
            if (coordinate[1] != location[1]) {
                var distance = location[1] - coordinate[1];
                if (Math.abs(distance) > 10)
                    coordinate[1] += distance / (speed + 1);
                else
                    coordinate[1] = location[1];
                geometry.setCoordinates(coordinate);
                setTimeout(function () {
                    animate(feature);
                }, speed);
            }
        }

        vectorSource.on('addfeature', function (e) {
            animate(e.feature);
        });

        var initialLoading = true;
        var totalTilesCount = 0;
        var loadedTilesCount = 0;
        mapLayer.getSource().on("tileloadstart", function() {
            totalTilesCount++;
        });

        mapLayer.getSource().on("tileloadend", function() {
            loadedTilesCount++;
            if(initialLoading && loadedTilesCount == totalTilesCount){
                initialLoading = true;

                //add features
                for (var i = 0; i < pinFeatures.length; i++)
                    vectorSource.addFeature(pinFeatures[i]);
            }
        });
    });

    var lastSelectedFeature;
    function selectFeature(feature, scroll) {
        deselectAllFeatures();
        lastSelectedFeature = feature;
        var geometry = feature.getGeometry();
        var coordinate = geometry.getCoordinates();
        var pixel = map_explore.getPixelFromCoordinate(coordinate);
        var tip = $('#mapTip_explore').html("<div class='tipIcon'><img src='" + feature.get('icon') + "' class='categoryIcon'><i>" + feature.get('index') + "</i></div><div class='tipText'><b>" + feature.get('name') + "</b><br/><span>" + feature.get('address') + "</span></div><div class='clearfix'></div>");
        tip.css('left', Math.round(pixel[0] - tip.width() / 2 - 2) + 'px').css('top', Math.round(pixel[1] - tip.height() - 97) + 'px').show();
        feature.setStyle(highlightedIconStyle(feature));

        var list = $('#placeList');
        var item = list.find('[data-id=' + feature.get('id') + ']').addClass('active');
        if (scroll)
            list.stop().scrollTo('[data-id=' + feature.get('id') + ']', 400, {offset: -(list.height() - item.height()) / 2 + item.height()});

    }

    function deselectAllFeatures() {
        $('#placeList li').removeClass('active');
        if (lastSelectedFeature) {
            lastSelectedFeature.setStyle(iconStyle(lastSelectedFeature));
            lastSelectedFeature = null;
        }
        $("#mapTip_explore").hide()
    }
</script>