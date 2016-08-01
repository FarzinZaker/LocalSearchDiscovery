<g:set var="oldValue" value="${field(place)}"/>
<g:set var="newValue" value="${field(editSuggestion)}"/>
<g:if test="true"/>
<g:set var="changed" value="${newValue != oldValue}"/>
<g:if test="${(oldValue && oldValue?.trim() != '') || (newValue && newValue?.trim() != '')}">
    <h2><g:message code="place.${fieldName}.label"/></h2>

    <div class="row ${changed ? 'changed' : ''}">

        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 current">
            <g:if test="${template}">
                <g:render template="${template}" model="${model(place)}"/>
            </g:if>
            <g:else>
                ${oldValue}
            </g:else>
        </div>

        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12 text-center arrow">
            <g:if test="${changed}">
                <img src="${resource(dir: 'images', file: 'diff-changed.png')}"/>
            </g:if>
        </div>

        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 suggestion">
            <g:if test="${changed}">
                <g:if test="${template}">
                    <g:render template="${template}" model="${model(editSuggestion)}"/>
                </g:if>
                <g:else>
                    ${newValue}
                </g:else>
            </g:if>
        </div>

        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12 text-center">
            <g:if test="${changed}">
                <input type="button" class="btn btn-accept" value="${message(code: 'accept')}" data-place="${place?.id}" data-editSuggestion="${editSuggestion?._id}" data-field="${fieldName}"/>
                <input type="button" class="btn btn-reject" value="${message(code: 'reject')}" data-place="${place?.id}" data-editSuggestion="${editSuggestion?._id}" data-field="${fieldName}"/>
                <bootstrap:loading id="loading_diff_${fieldName}" size="48"/>
            </g:if>
        </div>
    </div>
</g:if>