
<div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <label for="tags">
            <span class="glyphicon glyphicon-tags"></span>
            <g:message code="place.tags.label"/>
        </label>

        <div class="input-group">
            <input type="text" id="tags" name="tags" value="${tags?.join(',')}"/>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#tags').selectize({
            plugins: ['remove_button'],
            valueField: 'name',
            labelField: 'name',
            searchField: 'name',
            create: false,
            maxItems: 5,
            dropdownDirection: 'up',
            load: function (query, callback) {
                if (!query.length) return callback();
                $.ajax({
                    url: '${createLink(controller: 'tag', action: 'search')}/' + encodeURIComponent(query),
                    type: 'GET',
                    error: function () {
                        callback();
                    },
                    success: function (res) {
                        callback(res);
                    }
                });
            }
        });
    });
</script>