<div class="navbar-form" role="search">
    <div class="input-group">
        <select id="searchQuery" class="form-control no-caret"
                placeholder="${message(code: 'search.query.placeHolder')}">
        </select>
        <select id="searchLocation" class="form-control no-caret"
                placeholder="${message(code: 'search.location.placeHolder')}">
        </select>
        <span class="glyphicon glyphicon-search" id="btnSearch"></span>
    </div>
</div>

<g:javascript>

    function search(){
        var url = '${createLink(controller: 'place', action: 'explore')}/';
        url += $('#searchQuery').val();
        var city = $('#searchLocation').val();
        if(city.contains('-')){
            var location = city.split(' - ');
            url += '?province=' + location[0];
            url += '&city=' + location[1];
            window.location.href = url;
        }
        else if (navigator.geolocation)
            navigator.geolocation.getCurrentPosition(function (position) {
                url += '?near=' + position.coords.latitude + ',' + position.coords.longitude;
                window.location.href = url;
            });
        else
            window.location.href = url;
    }

    $(document).ready(function(){
        var searchQuery = $('#searchQuery').selectize({
            valueField: 'name',
            labelField: 'name',
            searchField: ['name'],
            create: true,
            createOnBlur:true,
            selectOnTab:true,
            sortField:'name',
            options: [
            <g:if test="${query}">
                {name: '${query}', icon: '${queryIcon}'}
            </g:if>
            ],
            load: function (query, callback) {
                if (!query.length) return callback();
                $.ajax({
                    url: '${createLink(controller: 'category', action: 'search')}/' + encodeURIComponent(query),
                    type: 'GET',
                    error: function () {
                        callback();
                    },
                    success: function (res) {
                        callback(res);
                    }
                });
            },
            render: {
                option: function(item, escape) {
                    return '<div>' +
'<img class="icon" src="' + item.icon +'" />'+
'<span class="text">' + item.name + '</span>' +
'</div>';
                }
            }
        });
        var searchLocation = $('#searchLocation').selectize({
            valueField: 'name',
            labelField: 'name',
            searchField: ['name'],
            create: false,
            options: [
            <g:if test="${city && province}">
                {name: '${province} - ${city}', cityName: '${city}'}
            </g:if>
            ],
            load: function (query, callback) {
                if (!query.length) return callback();
                $.ajax({
                    url: '${createLink(controller: 'city', action: 'search')}/' + encodeURIComponent(query),
                    type: 'GET',
                    error: function () {
                        callback();
                    },
                    success: function (res) {
                        callback(res);
                    }
                });
            },
            render: {
                item: function(item, escape) {
                    return '<div>' + item.cityName + '</div>';
                }
            }
        });
         <g:if test="${query}">
             searchQuery[0].selectize.setValue('${query}');
         </g:if>
         <g:if test="${city && province}">
             searchLocation[0].selectize.setValue('${province} - ${city}');
         </g:if>
        $('#btnSearch').click(function(){
            search();
        });
    });
</g:javascript>