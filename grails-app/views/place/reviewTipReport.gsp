<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 5/21/2016
  Time: 11:52 AM
--%>

<%@ page import="agahisaz.Image" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="editSuggestion.reviewEnded.title"/></title>
</head>

<body>
<div class="container padding-top-15">
    <div class="free-toolbar row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <h1><g:message code="tipReport.review"/> ${place?.name}</h1>
            <input type="button" class="btn btn-warning btn-skip" value="${message(code: 'skip')}"/>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 tipReportReviewPane">
            <ul id="tipsList">
                <g:render template="tip/item" model="${[
                        placeId       : place?._id,
                        tip           : place?.tips,
                        hideActions   : true,
                        image         : Image.findByTypeAndOwnerIdAndSize('tip', place?.tips?.id, 100),
                        lazyLoadImages: false,
                        cssClass      : 'standalone']}"/>
            </ul>

            <div class="tipReportReviewFooter">
                <h3><g:message code="tipReport.reporters"/></h3>
                ${
                    users.collect {
                        "<a href='${createLink(controller: 'user', action: 'info', id: it.id)}'>${it.fullName}</a>"
                    }.join(' - ')
                }
            </div>
        </div>
    </div>

    <div class="free-toolbar row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="float-right">
                <a class="btn btn-accept" href="${createLink(controller: 'place', action: 'acceptTipReport', params: [id:place?._id, tip: place?.tips?.id])}"><g:message code="accept"/></a>
                <a class="btn btn-reject" href="${createLink(controller: 'place', action: 'rejectTipReport', params: [id:place?._id, tip: place?.tips?.id])}"><g:message code="reject"/></a>
            </div>
            <input type="button" class="btn btn-warning btn-skip" value="${message(code: 'skip')}"/>
        </div>
    </div>
</div>

</body>
</html>