<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 7/21/2016
  Time: 4:56 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title><g:message code="oldAdds.list.title"/></title>
</head>

<body>
<div class="container">
    <bootstrap:helpedPanel>
        <h3 class="fieldSet-title">
            <g:message code="oldAdds.list.title"/>
        </h3>

        <g:if test='${flash.info}'>
            <div class='alert alert-info'>${flash.info}</div>
        </g:if>

        <ul class="oldAdList">
            <g:each in="${ads}" var="ad">
                <div class="row">
                    <div class="image col-lg-2 col-md-2 col-sm-2 col-xs-12">
                        <g:if test="${ad?.pictureUrl1?.contains('Spacer')}">
                            <img src="http://old.agahisaz.com/Images/120/Spacer.gif"/>
                        </g:if>
                        <g:else>
                            <img src="http://old.agahisaz.com/UserData/Images/Original/${ad.pictureUrl1?.split('/')?.reverse()?.find()}"/>
                        </g:else>
                    </div>

                    <div class="col-lg-8 col-md-7 col-sm-6 col-xs-12">
                        <h4>${ad.title}</h4>

                        <p>
                            <old:adBody ad="${ad}"/>
                        </p>
                    </div>

                    <div class="actions col-lg-2 col-md-3 col-sm-4 col-xs-12">
                        <a class="btn btn-success" href="${createLink(controller: 'oldAds', action: 'add', id: ad.id)}">
                            <g:message code="oldAds.add.label"/>
                        </a>
                        <a class="btn btn-danger"
                           href="${createLink(controller: 'oldAds', action: 'delete', id: ad.id)}">
                            <g:message code="default.button.delete.label"/>
                        </a>
                    </div>

                    <div class="clearfix"></div>
                </div>
            </g:each>
        </ul>

        <div class="pagination-container">
            <ul class="pagination">
                <g:each in="${1..total}" var="page">
                    <li class="${page == currentPage ? 'active' : ''}"><a
                            href="${createLink(action: 'list', params: [page: page])}">${page}</a></li>
                </g:each>
            </ul>

            <div class="clearfix"></div>
        </div>

        <bootstrap:help>
            <h4><g:message code="oldAdds.list.help.title"/></h4>

            <p><g:message code="oldAdds.list.help.description"/></p>
            <ol>
                <li>
                    <g:message code="oldAdds.list.help.item1"/>
                </li>
                <li>
                    <g:message code="oldAdds.list.help.item2"/>
                </li>
            </ol>
        </bootstrap:help>
    </bootstrap:helpedPanel>
</div>

</body>
</html>