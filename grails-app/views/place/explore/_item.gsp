<li data-id="${place?._id}">
    <span class="number">${index + 1}.</span>

    <div class="body">
        <a class="name" target="_blank"
           href="${createLink(controller: 'place', action: 'view', id: place?._id, params: [name: place?.name])}">
            ${place?.name}
        </a>

        <div>
            <place:aggregateRatingFlag place="${place}"/>

            <div class="contactInfo">
                <g:if test="${place?.address}">
                    <div class="address">
                        ${place?.address}
                    </div>
                </g:if>

                <g:if test="${place?.phone}">
                    <div class="phone">
                        ${place?.phone}
                    </div>
                </g:if>
                <div class="clearfix"></div>
                <a class="category"
                   href="${createLink(controller: 'place', action: 'explore', params: [category: place?.category?.name])}">
                    %{--<span class="glyphicon glyphicon-folder-open"></span>--}%
                    ${place?.category?.name}
                </a>
            </div>

            <div class="insightTexts">
                <ul class="texts">
                    <g:if test="${tip}">
                        <li class="insight">
                            ${tip}
                            <img src="${resource(dir:'images/icons', file: 'tip.png')}"/>
                        </li>
                    </g:if>
                </ul>
            </div>
        </div>

    </div>

    <img class="categoryIcon"
         src="${createLink(controller: 'image', action: 'placeSearch', params: [id: place?._id, size: 88])}"/>

    <div class="clearfix"></div>
</li>