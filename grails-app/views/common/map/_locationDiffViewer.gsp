<r:require module="maps"/>

<div class="mapViewer" style="height:200px;">

    <div id="map_${id}" style="height:${200}px;"></div>
</div>
<script type="text/javascript">

    var map_${id};
    $(document).ready(function () {

        //setup map
        var center = ol.proj.transform([${location[1]}, ${location[0]}], 'EPSG:4326', 'EPSG:3857');
        map_${id} = new ol.Map({
            target: 'map_${id}',
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
            name: '${name}'
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
                src: '${resource(dir:'images', file: 'pin-select.png')}'
            }))
        });
        var pinLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map_${id}.addLayer(pinLayer);

        //show icon
        iconFeatures = [];
        iconFeature = new ol.Feature({
            geometry: new ol.geom.Point(center),
            type: 'icon',
            name: '${name}'
        });
        iconFeatures.push(iconFeature);
        vectorSource = new ol.source.Vector({
            features: iconFeatures //add an array of features
        });
    });
</script>