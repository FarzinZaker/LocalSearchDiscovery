<div class="panel-header">
    <hr/>
    <div class="container"><h2><g:message code="topCities.title"/></h2></div>
</div>
<div class="citiesList">
<g:each in="${cities}" var="city" status="index">
    <div class="col-sm-3">
        <a href="${createLink(controller: 'place', action: 'explore', params: [city:city?._id, province: city?.province])}">
            ${city?._id} <span class="counter">(<g:formatNumber number="${city?.count}" type="number"/>)</span>
        </a>
    </div>
</g:each>
    <div class="clearfix"></div>
</div>