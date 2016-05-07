<span title="" class="placeScore ${description}">
    <span itemprop="ratingValue">${rate == null ? '?' : rate}</span>
    <sup>
        /<span itemprop="bestRating">10</span>
    </sup>
</span>

<div class="ratingJustification">
    <p class="ratingCountDesc">

        <g:if test="${description}">
            <g:message code="place.rate.value.part1"/> <span itemprop="ratingCount">${votesCout}</span> <g:message
                code="place.rate.value.part2"/>
        </g:if>
        <g:else>
            <g:message code="place.rate.noRate"/>
        </g:else>

    </p>

    <g:if test="${description}">
        <p><g:message code="place.rate.value.description.${description}"/></p>
    </g:if>
</div>