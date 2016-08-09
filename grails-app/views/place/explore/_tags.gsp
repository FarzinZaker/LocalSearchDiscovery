<ul>
    <g:each in="${tags}" var="tag">
        <li><a href="${place.addTagToSearchLink(tag: tag.name)}" class="hasCounter"><span class="text">${tag?.name}</span><span class="badge"><g:formatNumber number="${tag?.count}" type="number"/></span><span class="clearfix"></span></a></li>
    </g:each>
</ul>