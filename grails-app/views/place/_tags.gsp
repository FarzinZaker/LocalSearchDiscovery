<ul class="tags">
    <g:each in="${tags}" var="tag">
        <li>
            <a href="${createLink(controller: 'place', action: 'explore', params: [id: tag, province: province, city: city])}">${tag}</a>
        </li>
    </g:each>
</ul>