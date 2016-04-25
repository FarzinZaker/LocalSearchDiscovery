<a id="directionLink_${place?.id}" href="http://maps.google.com/maps?daddr=${place?.location[0]},${place?.location[1]}"
   target="_blank">
    <img src="${resource(dir: 'images/icons', file: 'direction.png')}"/>
    <span>
        <g:message code="place.direction.link"/>
    </span>
</a>
<g:javascript>

    $(document).ready(function () {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function (position) {
                $('#directionLink_${place?.id}').attr('href', "http://maps.google.com/maps?daddr=${place?.location[0]},${place?.location[1]}&saddr=" + position.coords.latitude + "," + position.coords.longitude);
            });
        }
    })

</g:javascript>