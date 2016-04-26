<bootstrap:modal id="suggestEditModal" size="lg">
    <div id="suggestEditFormContainer"></div>
    <bootstrap:modalFooter>
        <button type="button" class="btn btn-default" data-dismiss="modal"><g:message code="cancel"/></button>
        <button type="button" class="btn btn-primary" onclick="saveEditSuggestion(this)">
            <g:message code="place.suggestEdit.button.label"/>
        </button>
        <bootstrap:loading size="48" id="suggestEditLoading"/>
    </bootstrap:modalFooter>
</bootstrap:modal>
<div id="suggestEditModalInitialLoading">
    <bootstrap:loading size="64"/>
</div>
<g:javascript>

    function showCityOnMap(latitude, longitude) {
    }

    $(document).ready(function(){
        $("#suggestEditModal").on("show.bs.modal", function(e) {
            $(this).find(".modal-footer .btn-primary").attr('disabled', 'disabled');
            $(this).find(".modal-body").html($('#suggestEditModalInitialLoading').html())
        }).on("shown.bs.modal", function(e) {
            var modal = $(this);
            $.ajax({
                url: '${createLink(controller: 'place', action: 'suggestEdit')}/${params.id}?t=' + new Date().getTime()
            }).done(function (response) {
                modal.find(".modal-body").find('.loading').fadeOut(400, function(){
                    modal.find(".modal-body").html(response).hide().fadeIn(1000).find(".panel-group").css('max-height', '1000px');
                    modal.find(".modal-footer .btn-primary").removeAttr('disabled');
                });
            }).error(function () {});
        });
    });

    function saveEditSuggestion(sender){
        $(sender).attr('disabled', 'disabled');
        $('#editSuggestionError').slideUp();
        showLoading('registerLoading');
        var editSuggestionForm = $('#editSuggestionForm');
        if(editSuggestionForm.isValid()){
            $.ajax({
                type: "POST",
                url: '${createLink(controller: 'place', action: 'saveEditSuggestion')}',
                data: editSuggestionForm.serialize()
            }).done(function (response) {
                if(response == 1){
                    $('#suggestEditModal').modal('hide');
                    $('#placeMessage').html('${message(code: 'editSuggestion.save.succeed')}').slideDown();
                    setTimeout(function(){
                        $('#placeMessage').slideUp();
                    }, 10000);
                }
                else{
                    $('#editSuggestionError').html('${message(code: 'editSuggestion.save.error')}').slideDown();
                }
                $(sender).removeAttr('disabled');
                hideLoading('suggestEditLoading');
            }).error(function () {
                $('#editSuggestionError').html('${message(code: 'editSuggestion.save.error')}').slideDown();
                $(sender).removeAttr('disabled');
                hideLoading('suggestEditLoading');
            });
        }else{
            $(sender).removeAttr('disabled');
            hideLoading('suggestEditLoading');
        }
    }
</g:javascript>