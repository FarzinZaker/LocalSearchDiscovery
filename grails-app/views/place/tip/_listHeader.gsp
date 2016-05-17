<div class="tipsSectionHeader">
    <h3 class="tipCount">
        <g:if test="${tipsCount}">
            <span class="counter">${tipsCount}</span> <g:message code="place.tip.count"/>
        </g:if>
        <g:else>
            <g:message code="place.tip.empty"/>
        </g:else>
    </h3>
</div>

<g:javascript>

function reportTip(placeId, tipId, sender) {
    var button = $(sender);
    if (button.hasClass('active'))
        return;
    $.ajax({
        url: '${createLink(controller: 'report', action: 'save')}',
        type: 'POST',
        data: {placeId: placeId, tipId: tipId},
        error: function () {
        },
        success: function (res) {
            if (res == '1') {
                button.addClass('active');
            }
        }
    });
}

function likeTip(placeId, tipId, sender) {
    var button = $(sender);
    if (button.hasClass('active'))
        return;
    $.ajax({
        url: '${createLink(controller: 'like', action: 'save')}',
        type: 'POST',
        data: {placeId: placeId, tipId: tipId},
        error: function () {
        },
        success: function (res) {
            if (res == '1') {
                button.addClass('active');
            }
        }
    });
}
</g:javascript>