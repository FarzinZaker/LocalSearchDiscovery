<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 4/23/2016
  Time: 3:33 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="editSuggestion.review.title"/> ${place?.name}</title>
</head>

<body>
<div class="container diff">
    <div class="free-toolbar row">
        <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
            <h1><g:message code="editSuggestion.review"/> <a
                    href="${createLink(controller: 'place', action: 'view', params: [id: place?.id, name: place?.name])}">${place?.name}</a>
            </h1>
        </div>

        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
            <input type="button" class="btn btn-warning btn-skip" value="${message(code: 'skip')}"/>
        </div>
    </div>

    <div class="alert alert-info softHidden" id="editSuggestionMessage"><g:message
            code="editSuggestion.reviewCompleted"/></div>
    <g:if test="${editSuggestion.reportType}">
        <h2><g:message code="editSuggestion.report.title"/></h2>

        <div class="row ${changed ? 'changed' : ''}">
            <div class="col-lg-10 col-md-9 col-sm-8 col-xs-12 current">
                <b><g:message code="place.report.type.${editSuggestion.reportType}"/></b>:
            ${editSuggestion.reportComment}
            </div>

            <div class="col-lg-2 col-md-3 col-sm-4 col-xs-12 text-center">
                <input type="button" class="btn btn-accept" value="${message(code: 'accept')}" data-place="${place?.id}"
                       data-editSuggestion="${editSuggestion?._id}" data-field="reportType"/>
                <input type="button" class="btn btn-reject" value="${message(code: 'reject')}" data-place="${place?.id}"
                       data-editSuggestion="${editSuggestion?._id}" data-field="reportType"/>
                <bootstrap:loading id="loading_diff_reportType" size="48"/>
            </div>
        </div>
    </g:if>
    <g:else>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.name }, fieldName: 'name']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.categoryInfo?.name }, fieldName: 'category']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.tags?.join(' , ') }, fieldName: 'tags']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item ->
                      item.location?.collect {
                          it?.toString()?.trim()
                      }?.join(',')
                  }, template    : '/common/map/locationDiffViewer', model: { item -> [id: item.id, name: item.name, location: item.location] }, fieldName: 'location']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.province + ' - ' + item.city }, fieldName: 'city']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.address }, fieldName: 'address']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.postalCode }, fieldName: 'postalCode']}"/>
        <g:render template="suggestEdit/diff"
                  model="${[place: place, editSuggestion: editSuggestion, field: { item -> item.phone }, fieldName: 'phone']}"/>
    </g:else>
    <div class="free-toolbar row">
        <input type="button" class="btn btn-warning btn-skip" value="${message(code: 'skip')}"/>
    </div>
</div>
<g:javascript>
    $(document).ready(function(){
        $('.btn-accept').click(function(){
            var sender = $(this);
            var field = sender.attr('data-field');
            var loading = $('#loading_diff_' + field);
            var buttons = sender.parent().find('input');
            buttons.stop().fadeOut(400, function(){
                loading.stop().fadeIn(400, function(){
                    $.ajax({
                    url: '${createLink(controller: 'place', action: 'acceptEdit')}',
                    data: {
                        place: sender.attr('data-place'),
                        editSuggestion: sender.attr('data-editSuggestion'),
                        field: sender.attr('data-field')
                    },
                    type: 'GET',
                    error: function () {
                        loading.stop().fadeOut(400, function(){
                            buttons.stop().fadeIn(400);
                        });
                    },
                    success: function (res) {
                        loading.stop().fadeOut(400, function(){
                            sender.parent().html(res.message);
                            if(res.next == 1){
                                $('#editSuggestionMessage').slideDown();
                                setTimeout(function(){location.reload();}, 2000);
                            }
                        });
                    }
                });
                });
            });
        });
        $('.btn-reject').click(function(){
            var sender = $(this);
            var field = sender.attr('data-field');
            var loading = $('#loading_diff_' + field);
            var buttons = sender.parent().find('input');
            buttons.stop().fadeOut(400, function(){
                loading.stop().fadeIn(400, function(){
                    $.ajax({
                    url: '${createLink(controller: 'place', action: 'rejectEdit')}',
                    data: {
                        place: sender.attr('data-place'),
                        editSuggestion: sender.attr('data-editSuggestion'),
                        field: sender.attr('data-field')
                    },
                    type: 'GET',
                    error: function () {
                        loading.stop().fadeOut(400, function(){
                            buttons.stop().fadeIn(400);
                        });
                    },
                    success: function (res) {
                        loading.stop().fadeOut(400, function(){
                            sender.parent().html(res.message);
                            if(res.next == 1){
                                $('#editSuggestionMessage').slideDown();
                                setTimeout(function(){location.reload();}, 2000);
                            }
                        });
                    }
                });
                });
            });
        });
        $('.btn-skip').click(function(){
            location.reload();
        });
    });
</g:javascript>
</body>
</html>