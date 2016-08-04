<li data-id="${place?.id ?: place?._id}">
    <div class="body">
        <img class="categoryIcon"
             src="${createLink(controller: 'image', action: 'placeSearch', params: [id: place?.id ?: place?._id, size: 88])}"/>

        <div class="details">

            <place:aggregateRatingFlag place="${place}" cssClass="badge"/>
            <a class="name" target="_blank"
               href="${createLink(controller: 'place', action: 'view', id: place?.id ?: place?._id, params: [name: place?.name])}">
                ${place?.name}
            </a>

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
                <a class="category"
                   href="${createLink(controller: 'place', action: 'explore', params: [category: place?.category?.name])}">
                    %{--<span class="glyphicon glyphicon-folder-open"></span>--}%
                    ${place?.category?.name}
                </a>
            </div>

            <g:if test="${place.approved && place.reportType}">
                <p class="reason">
                    <b><i class="glyphicon glyphicon-flag"></i><g:message code="place.report.type.${place?.reportType}"/></b>
                    ${place?.reportComment?.replace('\n', '<br/>')}
                </p>
                <a href="${createLink(controller: 'place', action: 'edit', id: place?.id ?: place?._id)}" class="btn btn-primary">
                    <g:message code="place.suggestEdit.button"/>
                </a>
            </g:if>
        </div>

    </div>

    <div class="clearfix"></div>
</li>