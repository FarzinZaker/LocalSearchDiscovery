<div class="userCard">
    <img src="${createLink(controller: 'image', action: 'profile', params: [id: user?._id, size: 150])}"/>

    <div class="details">
        <a class="title">${user?.firstName} ${user?.lastName}</a>

        <span class="city">${user?.province} ${user?.city}</span>

        <span class="score">${score ?: 0}</span>
    </div>

    <div class="clearfix"></div>
</div>