<%--
  Created by IntelliJ IDEA.
  User: Farzin
  Date: 5/23/2016
  Time: 3:13 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>${user?.fullName}</title>
</head>

<body>
<div class="container padding-top-15">
    <div class="userInfo row ">
        <div class="userPic col-lg-2 col-md-2 col-sm-3 col-xs-4">
            <img title="${user?.fullName}" class="avatar " alt="${user?.fullName}"
                 src="${createLink(controller: 'image', action: 'profile', id: user?.id, params: [size: 150])}"/>
        </div>

        <div class="userStats col-lg-6 col-md-6 col-sm-4 col-xs-8">
            <div class="userStatsTitle">
                <h1 class="name">${user?.fullName}</h1>
                %{--<a onmousedown="fourSq.ui.OutgoingLink.mousedown($(this), event)" data-sig="SFOeNo4QPEfBnU1cFoAFfxPKfeA=" class="fbLink iconLink" target="_blank" rel="nofollow" href="http://www.facebook.com/100000635342052">--}%
                %{--<img width="30" height="30" alt="Facebook" src="https://ss0.4sqi.net/img/facebook_round-6d3d548ebc5dcbabc953fd82d764e7d0.png"/>--}%
                %{--</a>--}%
                %{--<a onmousedown="fourSq.ui.OutgoingLink.mousedown($(this), event)" data-sig="XeGhR0RMGpyB62MlOWlpr80H/yM=" class="twLink iconLink" target="_blank" rel="nofollow" href="http://twitter.com/burity">--}%
                %{--<img width="30" height="30" alt="Twitter" src="https://ss1.4sqi.net/img/twitter_round-5cfdfa9a819517f6b35661a70ac8ed76.png"/>--}%
                %{--</a>--}%
                %{--<a onmousedown="fourSq.ui.OutgoingLink.mousedown($(this), event)" data-sig="Auwd4r/BTS6DobvflpjXBJOY7c8=" class="igLink iconLink" target="_blank" rel="nofollow" href="https://instagram.com/burity_">--}%
                %{--<img width="30" height="30" alt="Instagram" src="https://ss1.4sqi.net/img/instagram_round-16b7ca32cc2877a6398aaae2d14c748b.png"/>--}%
                %{--</a>--}%
            </div>

            <p class="userBio">${user?.bio}</p>

            <div class="userContact">
                <span class="userLocation">${user?.province} - ${user?.city}</span>
            </div>
        </div>

        <div class="userStatsInfo col-lg-4 col-md-4 col-sm-5 col-xs-12">
            <ul class="stats">
                <li class="tips">
                    <span class="stat">
                        <strong>${tips?.size()}</strong>
                        <g:message code="tip"/>
                    </span>
                </li>
                %{--<li class="followers">--}%
                %{--<span class="stat">--}%
                %{--<strong>147</strong>--}%
                %{--Followers--}%
                %{--</span>--}%
                %{--</li>--}%
                %{--<li class="following">--}%
                %{--<span class="stat">--}%
                %{--<strong>71</strong>--}%
                %{--Following--}%
                %{--</span>--}%
                %{--</li>--}%
                %{--<li class="lists">--}%
                %{--<span class="stat">--}%
                %{--<strong>4</strong>--}%
                %{--Lists--}%
                %{--</span>--}%
                %{--</li>--}%
            </ul>
        </div>
    </div>

    <div class="userTips" style="display: block;">
        <div class="panel-header">
            <hr>

            <div class="container-fluid"><h2><g:message code="user.recentTips" args="${[user?.fullName]}"/></h2></div>
        </div>

        <div class="tipsContainer">
            <div class="row">
                <g:each in="${tips}" var="place" status="i">
                    <div class="col-lg-3 col-md-4 col-sm-6 col-xs-12">
                        <div data-id="573fa93bcd107d041803e940" class="tipCard">
                            <div class="tipPhoto"
                                 style="background-image: url(${createLink(controller: 'image', action: 'tip', params: [id: place?.tips?.id, placeId: place?._id, size: 200])});">
                                %{--<div style="background-image: url('https://irs1.4sqi.net/img/general/113x170/102146082_nufQ3MxXDwKW6ZMvLx1cKDJnzkbRQ2OzXmqnJWc4R4c.jpg')"--}%
                                %{--class="tipSubPhoto"></div>--}%

                                %{--<div style="background-image: url('https://irs3.4sqi.net/img/general/113x170/102146082_BNSQ0PqcBb7k6xs4Lh5t3PohtGCXBf6k4XzpTmJhkuw.jpg')"--}%
                                %{--class="tipSubPhoto"></div>--}%

                                %{--<div style="background-image: url('https://irs1.4sqi.net/img/general/114x170/26153793_edufeWR3LZCHitYZXMEVIXvAJWGho7QFdG91gpjt0eQ.jpg')"--}%
                                %{--class="tipSubPhoto"></div>--}%
                            </div>

                            <div class="tipContent">${place?.tips?.body}</div>

                            <div class="tipUser">
                                <a href="${createLink(controller: 'user', action: 'info', id: place?.tips?.userId)}">
                                    <img width="32" height="32"
                                         data-retina-url="https://irs3.4sqi.net/img/user/64x64/64817910-WWRHOECVMX42HK1M.jpg"
                                         title="${place?.tips?.fullName}" class="avatar " alt="${place?.tips?.fullName}"
                                         src="${createLink(controller: 'image', action: 'profile', id: place?.tips?.userId, params: [size: 32])}">
                                </a>
                                <span class="tipUserName">
                                    <a href="${createLink(controller: 'user', action: 'info', id: place?.tips?.userId)}">${place?.tips?.fullName}</a>
                                </span> ·
                                <span class="tipDate link">
                                    <format:prettyDate date="${place?.tips?.date}"/>
                                </span>
                            </div>

                            <div class="tipVenue">
                                <div class="tipVenueDetails">
                                    <div class="tipVenueInfo">
                                        <a href="${createLink(controller: 'place', action: 'view', params: [id: place?._id, name: place?.name])}">${place?.name}</a>
                                    </div>

                                    <span class="category">${place?.category?.name}</span> · ${place?.city}
                                </div>
                                %{--<span style="background-color: #FFC800;"--}%
                                %{--title="6.2/10 - People have mixed feelings about this place"--}%
                                %{--class="venueScore neutral">--}%
                                %{--<span>6.2</span>--}%
                                %{--</span>--}%
                                <place:aggregateRatingFlag place="${place}" cssClass="venueScore neutral"/>
                            </div>
                        </div>
                    </div>
                </g:each>
            </div>
        </div>
    </div>

</div>
</body>
</html>