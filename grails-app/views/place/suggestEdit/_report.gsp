<div class="venueEditContent removePane">
    <div class="removeOptions">
        <div class="introText">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <g:message code="place.report.pickOne"/>

                        <div>
                            <div class="validatedHiddenFieldContainer">
                                <input type="text" name="reportReason" id="reportReason" value=""/>
                            </div>
                        </div>
                    </div>
                </div>
        </div>

        <div class="removeReasonButtons">
            <div class="row">
                <div class="removeReasonButton closed col-lg-2 col-md-2 col-sm-4 col-xs-12" data-reason="closed">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.closed"/></div>
                </div>

                <div class="removeReasonButton not_closed hidden col-lg-2 col-md-2 col-sm-4 col-xs-12"
                     data-reason="not_closed">
                    <div class="image"></div>

                    <div class="label">Not closed</div>
                </div>

                <div class="removeReasonButton un_delete hidden col-lg-2 col-md-2 col-sm-4 col-xs-12"
                     data-reason="un_delete">
                    <div class="image"></div>

                    <div class="label">Real / Not Spam</div>
                </div>

                <div class="removeReasonButton inappropriate col-lg-2 col-md-2 col-sm-4 col-xs-12"
                     data-reason="inappropriate">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.inappropriate"/></div>
                </div>

                <div class="removeReasonButton duplicate col-lg-2 col-md-2 col-sm-4 col-xs-12" data-reason="duplicate">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.duplicate"/></div>
                </div>

                <div class="removeReasonButton home col-lg-2 col-md-2 col-sm-4 col-xs-12" data-reason="home">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.home"/></div>
                </div>

                <div class="removeReasonButton rule col-lg-2 col-md-2 col-sm-4 col-xs-12" data-reason="rules">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.rules"/></div>
                </div>

                <div class="removeReasonButton private col-lg-2 col-md-2 col-sm-4 col-xs-12" data-reason="private">
                    <div class="image"></div>

                    <div class="label"><g:message code="place.report.type.private"/></div>
                </div>
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