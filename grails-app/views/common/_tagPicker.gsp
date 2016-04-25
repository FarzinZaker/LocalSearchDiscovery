<div class="row">
    <div class="col-sm-12">
        <label for="tags">
            <span class="glyphicon glyphicon-road"></span>
            <g:message code="place.tags.label"/>
        </label>

        <div class="input-group">
            <select id="tags"></select>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#tags').selectize({
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