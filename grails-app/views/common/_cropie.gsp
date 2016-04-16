<bootstrap:modal id="${name}CroppieDialog">
    <div id="${name}Cropper"></div>

    <div class="text-center">
        <a class="btn btn-primary btn-upload">
            <span><g:message code="uploadImage"/></span>
            <input id="${name}Uploader" type="file" accept="image/*" value="Choose a file">
        </a>
    </div>

    <div class="btn upload-result"></div>
    <bootstrap:modalFooter>
        <button class="btn btn-default" onclick="return false;" data-toggle="modal" data-target="#${name}CroppieDialog"><g:message
                code="cancel"/></button>
        <button class="btn btn-primary" onclick="return false;" id='${name}GetResult' data-toggle="modal"
                data-target="#${name}CroppieDialog"><g:message code="select"/></button>
    </bootstrap:modalFooter>
</bootstrap:modal>
<div class="${cssClass}">
    <div>
        <img data-src="${src ?: resource(dir: 'images', file: 'blank_boy.png')}" id="${name}Image" class="croppie-result"/>
    </div>

    <div>
        <input type="hidden" name="${name}"/>
        <button class="btn btn-primary" onclick="return false;" data-toggle="modal" data-target="#${name}CroppieDialog"><g:message
                code="cropie.change.image"/></button>
    </div>

</div>

<g:javascript>

    function ${name}SetupCroppie() {
        var $uploadCrop;

        function readFile(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $uploadCrop.croppie('bind', {
                        url: e.target.result
                    });
                    $('#${name}Cropper').addClass('ready');
                };

                reader.readAsDataURL(input.files[0]);
            }
            else {
                swal("Sorry - you're browser doesn't support the FileReader API");
            }
        }

        $uploadCrop = $('#${name}Cropper').croppie({
            viewport: {
                width: 200,
                height: 200,
                type: 'square'
            },
            boundary: {
                width: 300,
                height: 300
            },
            exif: true
        });

        $('#${name}Uploader').on('change', function () {
            readFile(this);
        });
        $('#${name}GetResult').on('click', function (ev) {
            $uploadCrop.croppie('result', {
                type: 'canvas',
                size: 'viewport'
            }).then(function (resp) {
            $('#${name}Image').attr('src', resp);
            $('input[name=${name}]').val(resp);
            });
        });
    }
    ${name}SetupCroppie()
</g:javascript>



