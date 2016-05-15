<r:require module="maps"/>

<div class="mapViewer ${cssClass ?: ''}"
     style="${style ?: ''};width:${width ? (width.toString().toInteger() + 10) + 'px' : 'auto'};height:${height.toString().toInteger() + 10}px;">

    <div id="map_${place.id}" style="width:${width ? width + 'px' : 'auto'};height:${height}px;"></div>
    <div id="mapTip_${place.id}" class="mapTip"></div>
</div>
<script type="text/javascript">

    var map_${place.id};
    $(document).ready(function () {

        //setup map
        var center = ol.proj.transform([${place.location[1]}, ${place.location[0]}], 'EPSG:4326', 'EPSG:3857');
        map_${place.id} = new ol.Map({
            target: 'map_${place.id}',
            layers: [
                new ol.layer.Tile({
                    source: new ol.source.OSM()
                })
            ],
            view: new ol.View({
                center: center,
                zoom: 15
            })
        });

        //show flag
        var iconFeatures = [];
        var iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(center),
            type: 'flag',
            name: '${place?.name}',
            address: '${place?.address?.replace('\n', '')}'
        });
        iconFeatures.push(iconFeature);
        var vectorSource = new ol.source.Vector({
            features: iconFeatures //add an array of features
        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                anchor: [0.5, 66],
                anchorXUnits: 'fraction',
                anchorYUnits: 'pixels',
                opacity: 0.9,
                src: '${resource(dir:'images', file: 'ping-single.png')}'
            }))
        });
        var pinLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map_${place.id}.addLayer(pinLayer);

        //show icon
        iconFeatures = [];
        iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(center),
            type: 'icon',
            name: '${place?.name}',
            address: '${place?.address?.replace('\n', '')}'
        });
        iconFeatures.push(iconFeature);
        vectorSource = new ol.source.Vector({
            features: iconFeatures //add an array of features
        });
        iconStyle = new ol.style.Style({
            image: new ol.style.Icon(/** @type {olx.style.IconOptions} */ ({
                anchor: [17, 62],
                anchorXUnits: 'pixels',
                anchorYUnits: 'pixels',
                opacity: 1,
                src: '${createLink(controller: 'image', action: 'category', params: [id: place?.categoryId, size:32])}'
            }))
        });
        var iconLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map_${place.id}.addLayer(iconLayer);

        map_${place.id}.on('pointermove', function (evt) {
            if (evt.dragging) {
                return;
            }
            var pixel = map_${place.id}.getEventPixel(evt.originalEvent);
            var feature = map_${place.id}.forEachFeatureAtPixel(pixel, function (feature, layer) {
                return feature;
            });
            if (feature) {
                var geometry = feature.getGeometry();
                var coordinate = geometry.getCoordinates();
                pixel = map_${place.id}.getPixelFromCoordinate(coordinate);
                var tip = $('#mapTip_${place.id}').show().html("<b>" + feature.get('name') + "</b><br/><span>" + feature.get('address') + "</span>");
                tip.css('left', Math.round(pixel[0] - tip.width() / 2 - 12) + 'px').css('top', Math.round(pixel[1] - tip.height() - 80) + 'px');
            }
            else {
                $("#mapTip_${place.id}").hide()
            }
        });
    });
</script>