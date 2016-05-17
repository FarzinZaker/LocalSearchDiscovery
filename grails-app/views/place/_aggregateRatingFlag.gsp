<span class="placeScore ${cssClass?:'flag'} ${rate == null ? 'noRate' : ''} ${description}"
      original-title="${rate == null ? '?' : rate}/10 - ${description ? message(code: "place.rate.value.description.${description}") : message(code: 'place.rate.noRate')}">
    <span itemprop="ratingValue">${rate == null ? '' : rate}</span>
</span>