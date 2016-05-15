<bootstrap:modal id="addTipCroppieDialog">
    <div id="addTipCropper"></div>

    <div class="text-center">
        <a class="btn btn-primary btn-upload">
            <span><g:message code="uploadImage"/></span>
            <input id="addTipUploader" type="file" accept="image/*" value="Choose a file">
        </a>
    </div>

    <div class="btn upload-result"></div>
    <bootstrap:modalFooter>
        <button class="btn btn-default" onclick="return false;" data-toggle="modal"
                data-target="#addTipCroppieDialog"><g:message
                code="cancel"/></button>
        <button class="btn btn-primary" onclick="return false;" id='addTipGetResult' data-toggle="modal"
                data-target="#addTipCroppieDialog"><g:message code="select"/></button>
    </bootstrap:modalFooter>
</bootstrap:modal>

<form class="addTipsSection" id="tipForm">
    <input type="hidden" name="id" value="${place?.id}"/>
    <img width="16" height="10" class="addTipCarat" alt="" src="${resource(dir: 'images', file: 'icon-tipcarat.png')}"/>

    <div class="addTipBlock">
        <div class="addItemArea addTipArea">
            <span class="input-holder">
                <textarea rows="4" tabindex="1" class="formStyle" name="body" data-validation="required"
                          placeholder="${message(code: 'tip.placeHolder')}"></textarea>
            </span>
            <span class="charCount"></span>

            <p class="inputError"></p>

            <div class="photoLinkButtons">
                <div title="" class="photoAddButtonHolder">
                    <div class="icon" data-toggle="modal" data-target="#addTipCroppieDialog"></div>
                </div>
            </div>
        </div>
        <a class="userImage" href="${createLink(controller: 'user', action: 'profile')}">
            <img width="32" height="32" original-title="${authorName}" class="avatar blankAvatar " alt="${authorName}"
                 data-src="${createLink(controller: 'image', action: 'profile', params: [size: 32])}">
        </a>

        <div class="buttonArea">
            <input type="button" tabindex="2" class="shareTipButton greenButton btn btn-primary" id="submitTip"
                   value="${message(code: 'tip.publish')}"/>
        </div>

        <div class="photoPreview"></div>
    </div>
</form>

<g:javascript>

    var $addTipUploadCrop;
    function addTipSetupCroppie() {

        function readFile(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $addTipUploadCrop.croppie('bind', {
                        url: e.target.result
                    });
                    $('#addTipCropper').addClass('ready');
                };

                reader.readAsDataURL(input.files[0]);
            }
            else {
                swal("Sorry - you're browser doesn't support the FileReader API");
            }
        }

        $addTipUploadCrop = $('#addTipCropper').croppie({
            viewport: {
                width: 150,
                height: 150,
                type: 'square'
            },
            boundary: {
                width: 300,
                height: 300
            },
            exif: true
        });

        $('#addTipUploader').on('change', function () {
            readFile(this);
        });
        $('#addTipGetResult').on('click', function (ev) {
            $addTipUploadCrop.croppie('result', {
                type: 'canvas',
                size: 'viewport'
            }).then(function (resp) {
                var newImage = $('<img/>');
                newImage.attr('src', resp);
                var newImageSrc = $('<input type="hidden" name="image"/>');
                newImageSrc.val(resp);
                var newImageRemoveButton = $('<span/>');
                newImageRemoveButton.html('${message(code: "tip.image.remove")}');
                newImageRemoveButton.click(function(){
                    $(this).parent().remove();
                });
                var newImageContainer = $('<div/>');
                newImageContainer.addClass('addTipImage');
                newImageContainer.append(newImage);
                newImageContainer.append(newImageSrc);
                newImageContainer.append(newImageRemoveButton);
                $('.photoPreview').append(newImageContainer).slideDown(500);
            });
        });
    }

    $(document).ready(function(){
        $("#addTipCroppieDialog").on("show.bs.modal", function(e) {
            $addTipUploadCrop.croppie('destroy');
            $addTipUploadCrop = $('#addTipCropper').croppie({
            viewport: {
                width: 150,
                height: 150,
                type: 'square'
            },
            boundary: {
                width: 300,
                height: 300
            },
            exif: true
        });
        });

        $('.addItemArea textarea').focus(function(){
            $(this).addClass('selected');
        });
        addTipSetupCroppie();

        $('#submitTip').click(function(){
            var form = $('#tipForm');
            if(form.isValid()){
                $.ajax({
                    url: '${createLink(controller: 'tip', action: 'save')}',
                    type: 'POST',
                    data: form.serialize(),
                    error: function () {
                    },
                    success: function (res) {
                        $('.photoPreview').slideUp(500, function(){
                            $(this).html('');
                            var item = $(res);
                            var list = $('#tipsList');
                            var counter = 0;
                            if(list.find('.tipCount .counter').length)
                                counter = parseInt(list.find('.tipCount .counter').text());
                            list.find('.tipCount').html(
                                '<span class="counter">' +
                                ++counter +
                                '</span> <g:message code="place.tip.count"/>');
                            list.find('.noTipText').remove();
                            list.find('.tipItems').prepend(item);
                            item.css('max-height', '500px');
                        });
                        $('.addItemArea textarea').val('').removeClass('selected');

                    }
                });
            }
        });
    });
</g:javascript>