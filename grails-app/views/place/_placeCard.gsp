<div data-id="${place?._id}" class="placeCard">
    <div class="body">
        <img class="categoryIcon"
             src="${createLink(controller: 'image', action: 'placeSearch', params: [id: place?._id, size: 88])}"/>

        <place:aggregateRatingFlag place="${place}" cssClass="badge"/>
        <div class="details">

            <a class="name" target="_blank"
               href="${createLink(controller: 'place', action: 'view', id: place?._id, params: [name: place?.name])}">
                ${place?.name}
            </a>

            <div class="contactInfo">
                <g:if test="${place?.city}">
                    <div class="city">
                        ${place?.province} - ${place?.city}
                    </div>
                </g:if>

                <g:if test="${place?.phone}">
                    <div class="phone">
                        ${place?.phone}
                    </div>
                </g:if>
                <a class="category"
                   href="${createLink(controller: 'place', action: 'explore', params: [id: place?.category?.name])}">
                    %{--<span class="glyphicon glyphicon-folder-open"></span>--}%
                    ${place?.category?.name}
                </a>
            </div>
        </div>

    </div>

    <div class="clearfix"></div>
</div>