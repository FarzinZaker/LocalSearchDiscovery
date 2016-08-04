<g:form class="reviewForm row" controller="place" action="saveReview">
    <h3><g:message code="place.review.title"/></h3>
    <g:if test="${lastReportType}">

        <div class="alert alert-danger">
            <g:message code="place.lastReport"/>:
            <b>${lastReportType}</b>
        </div>
    </g:if>

    <input type="hidden" value="${placeId}" name="placeId"/>

    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="input-group">
            <select name="reason">
                <option value="nothing"><g:message code="place.report.type.nothing"/></option>
                <option value="address"><g:message code="place.report.type.address"/></option>
                <option value="notAPlace"><g:message code="place.report.type.notAPlace"/></option>
                <option value="wrongPlaceTitle"><g:message code="place.report.type.wrongPlaceTitle"/></option>
                <option value="closed"><g:message code="place.report.type.closed"/></option>
                <option value="inappropriate"><g:message code="place.report.type.inappropriate"/></option>
                <option value="duplicate"><g:message code="place.report.type.duplicate"/></option>
                <option value="home"><g:message code="place.report.type.home"/></option>
                <option value="rules"><g:message code="place.report.type.rules"/></option>
                <option value="private"><g:message code="place.report.type.private"/></option>
            </select>
        </div>
    </div>

    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <textarea name="additionalInfo" class="form-control" rows="5"
                  placeholder="${message(code: 'place.report.additionalComment')}"></textarea>
    </div>

    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
        <input name="btn" type="submit" value="${message(code: 'place.review.saveAndNext')}" class="btn btn-success"/>
    </div>

    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
        <input name="btn" type="submit" value="${message(code: 'place.review.save')}" class="btn btn-primary"/>
    </div>

    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
        <input name="btn" type="submit" value="${message(code: 'place.review.next')}" class="btn btn-warning"/>
    </div>
</g:form>
<script language="javascript">
    $(document).ready(function () {
        $('[name=reason]').selectize();
    })
</script>

<div class="categorySuggestionPanel row">
    <h3><g:message code="place.review.changeCategory"/></h3>
    <g:if test="${suggestedCategories?.size()}">
    <ul>
        <g:each in="${suggestedCategories?.sort { -it.value }}" var="category">
            <li>
                <a href="${createLink(controller: 'place', action: 'changeCategory', id: placeId, params: [category:category?.key?.id])}">${category.key.name} <span>${category.value}</span></a>
            </li>
        </g:each>
    </ul>
    </g:if>
    <g:else>
        <div class="text-center">
            <g:message code="list.noItem"/>
        </div>
    </g:else>
</div>