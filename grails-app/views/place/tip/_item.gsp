<li data-id="${tip.id}" class="tip tipWithLogging">
    <div class="authorImage">
        <a href="#">
            <img width="32" height="32" title="${tip.fullName}" class="avatar " alt="${tip.fullName}"
                 data-src="${createLink(controller: 'image', action: 'profile', id: tip.userId, params: [size: 32])}">
        </a>
    </div>
    <div class="tipContents">
        <g:if test="${image}">
            <img width="100" height="100" alt="" photo-id="${image?.id}"
                 data-src="${createLink(controller: 'image', action: 'get', id: image?.id)}"
                 class="tipPhoto">
        </g:if>
        <p class="tipText">${tip.body}</p>

        <div class="tipInfo">
            <span class="userName">
                <a href="#">${tip.fullName}</a>
            </span> ·
            <span class="tipDate"><format:prettyDate date="${tip.date}"/></span>
        </div>

        <div class="actionButtons">
            <span class="likeButtonContainer">
                <span class="like">
                    <span class="link">
                        <span class="icon"></span>
                        <span class="label"><g:message code="place.tip.like.button"/> <span>(250)</span></span>
                    </span>
                </span>
            </span>
            <span class="flagTipLink">
                <span class="icon"></span>
                <g:message code="place.tip.report"/>
            </span>
        </div>
    </div>
</li>