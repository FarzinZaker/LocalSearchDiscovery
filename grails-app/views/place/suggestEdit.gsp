<div class="alert alert-danger softHidden" id="editSuggestionError">
</div>
<form id="editSuggestionForm" autocomplete="off">
    <input type="hidden" name="placeId" value="${placeId}"/>
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                       aria-expanded="true" aria-controls="collapseOne">
                        <g:message code="place.suggestEdit.baseInfo"/>
                    </a>
                </h4>
            </div>

            <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <g:render template="suggestEdit/baseInfo" model="${[place: place]}"/>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4 class="panel-title">
                    <a class="" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        <g:message code="place.suggestEdit.report"/>
                    </a>
                </h4>
            </div>

            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                <div class="panel-body">
                    <g:render template="suggestEdit/report" model="${[place: place]}"/>
                </div>
            </div>
        </div>
    </div>
</form>
<script language="javascript">

    $('.accordion').on('show', function (e) {
        $(e.target).prev('.accordion-heading').find('.accordion-toggle').addClass('active');
    }).on('hide', function (e) {
        $(this).find('.accordion-toggle').not($(e.target)).removeClass('active');
    });
</script>