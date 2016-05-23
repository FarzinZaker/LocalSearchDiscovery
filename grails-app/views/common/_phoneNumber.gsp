<g:if test="${value}">
    <span class="phoneNumber" id="${id}">
        <img src="${resource(dir: 'images/icons', file: 'phone.png')}"/>
        <span class="ltr" itemprop="telephone">
            ${value}
        </span>
    </span>

%{--<g:javascript>--}%
%{--$(document).ready(function(){--}%
%{--$('#${id}').html(formatLocal('IR', '${value}') + '<img src="${resource(dir: 'images/icons', file: 'phone.png')}"/>');--}%
%{--});--}%
%{--</g:javascript>--}%
</g:if>