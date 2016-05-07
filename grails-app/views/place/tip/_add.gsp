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

<div class="addTipsSection">
    <img width="16" height="10" class="addTipCarat" alt="" src="${resource(dir: 'images', file: 'icon-tipcarat.png')}"/>

    <div class="addTipBlock">
        <div class="addItemArea addTipArea">
            <span class="input-holder">
                <textarea rows="4" tabindex="1" class="formStyle"
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
                 data-src="${createLink(controller: 'image', params: [type: 'profile'])}">
        </a>

        <div class="buttonArea">
            <button tabindex="2" class="shareTipButton greenButton btn btn-primary"><g:message
                    code="tip.publish"/></button>
        </div>

        <div id="tipLink">
            <input type="text" value="" maxlength="255" class="formStyle">
        </div>

        <div class="photoPreview"></div>
    </div>
</div>

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
                var newImageSrc = $('<input type="hidden"/>');
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
                $('.photoPreview').append(newImageContainer);
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

        addTipSetupCroppie();
    });
</g:javascript>