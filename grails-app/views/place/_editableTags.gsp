<ul class="tags editable">
    <g:each in="${tags}" var="tag">
        <li>
            <a class="deleteButton"
               href="${createLink(controller: 'place', action: 'removeTag', id: place?.id, params: [tag: tag])}">X</a>
            <a href="${createLink(controller: 'place', action: 'explore', params: [tags: tag, province: province, city: city])}">${tag}</a>

            <div class="clearfix"></div>
        </li>
    </g:each>
</ul>