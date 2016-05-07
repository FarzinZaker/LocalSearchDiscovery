<div class="likeDislikeControls comboRatingButton">
    <div title="" class="comboRatingButtonDislike dislike ${currentRate == 0 ? 'active' : ''}" data-rate="0"
         original-title="${message(code: 'place.rate.tip.dislike')}">
        <span class="icon"></span>
    </div>

    <div title="" class="comboRatingButtonOk ok ${currentRate == 5 ? 'active' : ''}" data-rate="5"
         original-title="${message(code: 'place.rate.tip.mix')}">
        <span class="icon"></span>
    </div>

    <div title="" class="comboRatingButtonLike like ${currentRate == 10 ? 'active' : ''}" data-rate="10"
         original-title="${message(code: 'place.rate.tip.like')}">
        <span class="icon"></span>
    </div>
</div>
<g:javascript>
    $(document).ready(function () {
        $('.likeDislikeControls div').tipsy({gravity:'se'}).click(function () {
            var container = $('.likeDislikeControls');
            var button = $(this);
            if (button.hasClass('active'))
                return;
            var value = button.attr('data-rate');
            var allButtons = $('.likeDislikeControls div');
            container.addClass('loading');
                $.ajax({
                    url: '${createLink(controller: 'rate', action: 'save')}',
                    type: 'POST',
                    data: {value:value, id:'${placeId}'},
                    error: function () {
                        container.removeClass('loading');
                    },
                    success: function (res) {
                        if(res == '1'){
                            allButtons.removeClass('active');
                            button.addClass('active');
                        }
                        container.removeClass('loading');
                    }
                });
        });
    })
</g:javascript>