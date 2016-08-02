<ul>
    <g:each in="${tags}" var="tag">
        <li><a href="${place.addTagToSearchLink(tag: tag._id)}" class="hasCounter"><span class="text">${tag?._id}</span><span class="badge"><g:formatNumber number="${tag?.count}" type="number"/></span><span class="clearfix"></span></a></li>
    </g:each>
</ul>