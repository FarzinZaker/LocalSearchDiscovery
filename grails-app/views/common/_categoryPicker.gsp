<div class="row">
    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 dynamicColumn">
        <label for="category1">
            <span class="glyphicon glyphicon-folder-open"></span>
            ${title}
        </label>

        <div class="input-group">
            <select name="category1" id="category1" class="form-control" data-validation="required"></select>
        </div>
    </div>

    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 dynamicColumn">
        <label for="category2">&nbsp;</label>

        <div class="input-group">
            <select name="category2" id="category2" class="form-control" data-validation="required"></select>
        </div>
    </div>

    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 dynamicColumn hidden">
        <label for="category3">&nbsp;</label>

        <div class="input-group">
            <select name="category3" id="category3" class="form-control"></select>
        </div>
    </div>
</div>
<g:set var="category1" value="${category}"/>
<g:if test="${category1?.parent}">
    <g:set var="category2" value="${category1}"/>
    <g:set var="category1" value="${category1.parent}"/>
</g:if>
<g:if test="${category1?.parent}">
    <g:set var="category3" value="${category2}"/>
    <g:set var="category2" value="${category2.parent}"/>
    <g:set var="category1" value="${category1.parent}"/>
</g:if>
<script language="javascript" type="text/javascript">
    $(document).ready(function () {
        var categories;
        var selCategory1;
        var selCategory2;
        var category1 = $('#category1').selectize({
            onChange: function (value) {
                var selCategories = $.grep(categories, function (n, i) {
                    return n.name == value
                });
                if (selCategories.length) {
                    category3.parent().parent().addClass('hidden').parent().find('.dynamicColumn')
                            .removeClass('col-lg-4')
                            .removeClass('col-md-4')
                            .addClass('col-lg-6')
                            .addClass('col-md-6');
                    category3[0].selectize.clearOptions();
                    selCategory1 = selCategories[0];
                    category2[0].selectize.clearOptions();
                    category2[0].selectize.load(function (callback) {
                        callback(selCategories[0].children);
                    });
                }
            },
            valueField: 'name',
            labelField: 'name',
            searchField: ['name']
        });
        var category2 = $('#category2').selectize({
            onChange: function (value) {
                var selCategories = $.grep(selCategory1.children, function (n, i) {
                    return n.name == value
                });
                if (selCategories.length) {
                    if (selCategories[0].children.length) {
                        category3.parent().parent().removeClass('hidden').parent().find('.dynamicColumn')
                                .removeClass('col-lg-6')
                                .removeClass('col-md-6')
                                .addClass('col-lg-4')
                                .addClass('col-md-4');
                    }
                    else {
                        category3.parent().parent().addClass('hidden').parent().find('.dynamicColumn')
                                .removeClass('col-lg-4')
                                .removeClass('col-md-4')
                                .addClass('col-lg-6')
                                .addClass('col-md-6');
                    }
                    selCategory2 = selCategories[0];
                    category3[0].selectize.clearOptions();
                    category3[0].selectize.load(function (callback) {
                        callback(selCategories[0].children);
                    });
                }
            },
            valueField: 'name',
            labelField: 'name',
            searchField: ['name']
        });
        var category3 = $('#category3').selectize({
            valueField: 'name',
            labelField: 'name',
            searchField: ['name']
        });
        $.get('<g:createLink uri="/data/categories.json"/>').success(function (data) {
            categories = data;
            category1[0].selectize.clearOptions();
            category1[0].selectize.load(function (callback) {
                callback(categories);

                <g:if test="${category1}">
                category1[0].selectize.setValue('${category1.name}');
                </g:if>

                <g:if test="${category2}">
                category2[0].selectize.setValue('${category2.name}');
                </g:if>

                <g:if test="${category3}">
                category3[0].selectize.setValue('${category3.name}');
                </g:if>
            });
        });
    });
</script>