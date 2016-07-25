<li role="separator" class="divider"></li>
<li>
    <g:link controller="oldAds" action="list">
        <span class="label label-warning">${count}</span> <g:message code="user.oldAdds.menu"/>
    </g:link>
</li>

<div class="alert alert-warning alert-bottom hidden">
    <i class="glyphicon glyphicon-remove"></i>
    <p>
        <g:message code="oldAds.remaining.alert" args="${count}"/>
    </p>
    <br/>

    <p>
        <a class="btn btn-warning float-left" href="${createLink(controller: 'oldAds', action: 'list')}">
            <g:message code="oldAds.manage.button"/>
        </a>
    </p>

    <div class="clearfix"></div>
</div>
<script language="javascript">
    $(document).ready(function () {
        var alert = $('.alert-bottom').removeClass('hidden').slideUp(10, function(){
            $(this).stop().slideDown(500)
        });
        $('body').append(alert);
        alert.find('i').click(function(){
            $(this).parent().stop().slideUp(500);
        });
    });
</script>