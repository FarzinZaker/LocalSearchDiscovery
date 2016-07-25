<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Agahisaz"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon"/>
    <r:require module="application"/>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>
<g:render template="/layouts/common/header"/>
<g:layoutBody/>
<g:render template="/layouts/common/footer"/>
<sec:ifNotLoggedIn>
    <g:render template="/user/register"/>
    <g:render template="/user/registerLogin"/>
    <g:render template="/user/login"/>
</sec:ifNotLoggedIn>
<g:javascript>
    $.validate();
    $(document).ready(function(){
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-11319424-1', 'auto');
        ga('send', 'pageview');
    });
</g:javascript>
<common:recordBrowse/>
<r:layoutResources/>
</body>
</html>
