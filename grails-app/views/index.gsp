<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="home"/>
</head>

<body>
<div class="mapBGContainer">

    <div class="mapBGWrapper">

    </div>

    <div class="mapBG">
    </div>
</div>

<div class="contentOnMapBG">

    <div class="searchBoxContainer">
        <div class="searchBox">
            <place:searchBox/>
            <place:topCategories iconSize="44"/>

        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-sm-3 text-center">
                <a class="mobile-download-button">
                    <img src="${resource(dir: 'images/icons', file: 'icon-apple.png')}"/>
                    <span class="title">
                        <span><g:message code="application.downloadFrom"/></span>
                        <b>APP STORE</b>
                    </span>
                    <span class="clearfix"></span>
                </a>
            </div>

            <div class="col-sm-3 text-center">
                <a class="mobile-download-button">
                    <img src="${resource(dir: 'images/icons', file: 'icon-android.png')}"/>
                    <span class="title">
                        <span><g:message code="application.downloadFrom"/></span>
                        <b>ANDROID MARKET</b>
                    </span>
                    <span class="clearfix"></span>
                </a>
            </div>

            <div class="col-sm-3 text-center">
                <a class="mobile-download-button">
                    <img src="${resource(dir: 'images/icons', file: 'icon-bazar.png')}"/>
                    <span class="title">
                        <span><g:message code="application.downloadFrom"/></span>
                        <b>CAFE BAZAAR</b>
                    </span>
                    <span class="clearfix"></span>
                </a>
            </div>

            <div class="col-sm-3 text-center">
                <a class="mobile-download-button">
                    <img src="${resource(dir: 'images/icons', file: 'icon-windows.png')}"/>
                    <span class="title">
                        <span><g:message code="application.downloadFrom"/></span>
                        <b>WINDOWS PHONE</b>
                    </span>
                    <span class="clearfix"></span>
                </a>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <div class="panel-header">
                    <hr/>

                    <div class="container-fluid"><h2><g:message code="topPlaces.list"/></h2></div>
                </div>
            </div>
        </div>
    </div>


    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12">
                <div class="owl-carousel place-carousel owl-theme">
                    <place:topPlaces/>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row">

            <div class="col-sm-6">
                <div class="panel-header">
                    <hr/>

                    <div class="container"><h2><g:message code="topUsers.title"/></h2></div>
                </div>

                <div class="owl-carousel user-carousel owl-theme">
                    <points:topUsers/>
                </div>
            </div>

            <div class="col-sm-6">
                <div class="panel-header">
                    <hr/>

                    <div class="container"><h2><g:message code="weekTopUsers.title"/></h2></div>
                </div>

                <div class="owl-carousel user-carousel owl-theme">
                    <points:weekTopUsers/>
                </div>
            </div>
        </div>
    </div>
    <g:render template="/layouts/common/footer"/>
</div>
<g:javascript>
    $(document).ready(function () {
        $('.owl-carousel.place-carousel').owlCarousel({
            rtl: true,
            margin: 10,
            responsive: {
                0: {
                    items: 1
                },
                450: {
                    items: 2
                },
                660: {
                    items: 3
                },
                870: {
                    items: 4
                },
                1080: {
                    items: 5
                },
                1290: {
                    items: 6
                },
                1500: {
                    items: 7
                },
                1710: {
                    items: 8
                },
                1920: {
                    items: 9
                },
                2130: {
                    items: 10
                }
            }
        });
        $('.owl-carousel.user-carousel').owlCarousel({
            rtl: true,
            margin: 10,
            responsive: {
                0: {
                    items: 1
                },
                670: {
                    items: 2
                },
                990: {
                    items: 3
                },
                1310: {
                    items: 4
                },
                1630: {
                    items: 5
                },
                1950: {
                    items: 6
                },
                2270: {
                    items: 7
                }
            }
        });
    })
</g:javascript>
</body>
</html>
