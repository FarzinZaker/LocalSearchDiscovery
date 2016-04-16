<div class="modal fade" tabindex="-1" role="dialog" id="${id}">
    <div class="modal-dialog ${size ? "modal-${size}" : ''}">
        <div class="modal-content">
            %{--<div class="modal-header">--}%
            %{--<h4 class="modal-title">${title}</h4>--}%
            %{--</div>--}%

            <div class="modal-body">
                <format:html value="${body}"/>
            </div>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>

            <g:if test="${footer}">
                <div class="modal-footer">
                    <format:html value="${footer}"/>
                </div>
            </g:if>
        </div>
    </div>
</div>