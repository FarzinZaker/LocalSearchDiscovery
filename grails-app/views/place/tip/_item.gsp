<li data-id="${tip.id}" class="tip tipWithLogging ${slideDown ? 'slideDown' : ''} ${cssClass}">
    <div class="authorImage">
        <a href="${createLink(controller: 'user', action: 'info', id: tip.userId)}">
            <img width="32" height="32" title="${tip.fullName}" class="avatar " alt="${tip.fullName}"
            ${lazyLoadImages ? 'data-src' : 'src'}="${createLink(controller: 'image', action: 'profile', id: tip.userId, params: [size: 32])}">
        </a>
    </div>

    <div class="tipContents">
        <g:if test="${image}">
            <img width="100" height="100" alt="" photo-id="${image?.id}"
            ${lazyLoadImages ? 'data-src' : 'src'}="${createLink(controller: 'image', action: 'get', id: image?.id)}"
                 class="tipPhoto"/>
        </g:if>
        <p class="tipText">${tip.body}</p>

        <div class="tipInfo">
            <span class="userName">
                <a href="${createLink(controller: 'user', action: 'info', id: tip.userId)}">${tip.fullName}</a>
            </span> ·
            <span class="tipDate"><format:prettyDate date="${tip.date}"/></span>
        </div>

        <g:if test="${!hideActions}">
            <div class="actionButtons">
                <span class="likeButtonContainer ${alreadyLiked ? 'active' : ''}"
                      onclick="likeTip('${placeId}', '${tip?.id}', this)">
                    <span class="like">
                        <span class="link">
                            <span class="icon"></span>
                            <span class="label"><g:message code="place.tip.like.button"/> <span>
                                <g:if test="${tip?.likes && tip?.likes?.size() > 0}">
                                    (${tip?.likes?.size()})
                                </g:if>
                            </span></span>
                        </span>
                    </span>
                </span>
                <sec:ifLoggedIn>
                    <span class="flagTipLink ${alreadyReported ? 'active' : ''}"
                          onclick="reportTip('${placeId}', '${tip?.id}', this)">
                        <span class="icon"></span>
                        <g:message code="place.tip.report"/>
                        <span>
                            <g:if test="${tip?.reports && tip?.reports?.size() > 0}">
                                (${tip?.reports?.size()})
                            </g:if>
                        </span>
                    </span>
                </sec:ifLoggedIn>
            </div>
        </g:if>
    </div>
</li>