<ul class="currentTags">
    <g:each in="${tags}" var="tag">
        <li><a href="${place.removeTagToSearchLink(tag: tag)}" class="hasCounter"><span class="text">${tag}</span><span class="badge">X</span><span class="clearfix"></span></a></li>
    </g:each>
</ul>