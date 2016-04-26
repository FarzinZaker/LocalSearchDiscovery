<li data-id="${place?._id}">
    <span class="number">${index + 1}.</span>

    <div class="body">
        <a class="name" target="_blank"
           href="${createLink(controller: 'place', action: 'view', id: place?._id, params: [name: place?.name])}">
            ${place?.name}
        </a>

        <div class="address">
            ${place?.address}
        </div>

        <div class="phone">
            <span class="glyphicon glyphicon-phone-alt"></span>
            ${place?.phone}
        </div>

        <a class="category"
           href="${createLink(controller: 'place', action: 'explore', params: [category: place?.category?.name])}">
            <span class="glyphicon glyphicon-folder-open"></span>
            ${place?.category?.name}
        </a>
    </div>

    <img class="categoryIcon"
         src="${resource(dir: "images/categories/${place?.category?.iconDirectory}", file: "${place?.category?.iconFile}88.png")}"/>

    <div class="clearfix"></div>
</li>