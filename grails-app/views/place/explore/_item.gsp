<li data-id="${place?._id}" itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
    <span class="number"><span itemprop="position">${index + 1}</span>.</span>

    <div class="body" itemprop="item" itemscope itemtype="http://schema.org/LocalBusiness">
        <a class="name" target="_blank"
           href="${createLink(controller: 'place', action: 'view', id: place?._id, params: [name: place?.name])}" itemprop="name">
            ${place?.name}
        </a>

        <div>
            <place:aggregateRatingFlag place="${place}"/>

            <div class="contactInfo">
                <g:if test="${place?.address}">
                    <div class="address" itemprop="address">
                        ${place?.address}
                    </div>
                </g:if>

                <g:if test="${place?.phone}">
                    <div class="phone" itemprop="telephone">
                        ${place?.phone}
                    </div>
                </g:if>
                <div class="clearfix"></div>
                <a class="category"
                   href="${createLink(controller: 'place', action: 'explore', params: [category: place?.category?.name])}" itemprop="additionalType">
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
    <span class="image"  itemprop="photo" itemscope itemtype="http://schema.org/ImageObject">
        <img class="categoryIcon" itemprop="contentUrl"
            src="${createLink(controller: 'image', action: 'placeSearch', params: [id: place?._id, size: 88])}"/>
    </span>
    <div class="clearfix"></div>
</li>