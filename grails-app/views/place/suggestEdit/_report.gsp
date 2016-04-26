<div class="venueEditContent removePane">
    <div class="removeOptions">
        <div class="introText">
            <g:message code="place.report.pickOne"/>

            <div>
                <div class="validatedHiddenFieldContainer">
                    <input type="text" name="reportReason" id="reportReason" value=""/>
                </div>
            </div>
        </div>

        <div class="removeReasonButtons">
            <div class="removeReasonButton closed" data-reason="closed">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.closed"/></div>
            </div>

            <div class="removeReasonButton not_closed hidden" data-reason="not_closed">
                <div class="image"></div>

                <div class="label">Not closed</div>
            </div>

            <div class="removeReasonButton un_delete hidden" data-reason="un_delete">
                <div class="image"></div>

                <div class="label">Real / Not Spam</div>
            </div>

            <div class="removeReasonButton inappropriate" data-reason="inappropriate">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.inappropriate"/></div>
            </div>

            <div class="removeReasonButton duplicate" data-reason="duplicate">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.duplicate"/></div>
            </div>

            <div class="removeReasonButton home" data-reason="home">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.home"/></div>
            </div>

            <div class="removeReasonButton rule" data-reason="rules">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.rules"/></div>
            </div>

            <div class="removeReasonButton private" data-reason="private">
                <div class="image"></div>

                <div class="label"><g:message code="place.report.type.private"/></div>
            </div>
        </div>
    </div>

    <div>
        <textarea name="additionalInfo" placeholder="${message(code: 'place.report.additionalComment')}"
                  class="form-control" rows="5"></textarea>
    </div>

    <div class="removeSecondaryPanel"></div>
</div>
<script language="javascript" type="text/javascript">
    $(document).ready(function () {
        $('.removeOptions .removeReasonButton').click(function () {
            $('.removeOptions .removeReasonButton').removeClass('selected');
            $(this).addClass('selected');
            $('#reportReason').val($(this).attr('data-reason'));
        });
    });
</script>