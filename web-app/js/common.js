/**
 * Created by Farzin on 4/13/2016.
 */

$(document).ready(function () {

    $("img").unveil();
});

var serverError = {};

function hideLoading(id) {
    var loading = $('#' + id);
    if (loading.parent().hasClass('modal-footer')) {
        loading.stop().fadeOut(200, function () {
            loading.parent().find('.btn').stop().fadeIn(200, function () {
                $(this).css('opacity', '1');
            });
        });
    }
    else
        loading.stop().fadeOut();
}

function showLoading(id) {
    var loading = $('#' + id);
    if (loading.parent().hasClass('modal-footer')) {
        loading.parent().find('.btn').stop().fadeOut(200, function () {
            loading.stop().fadeIn();
        });
    }
    else
        loading.stop().fadeIn();
}

function register(sender) {
    var form = $('#registerForm');
    serverError = {};
    if (form.isValid()) {
        $(sender).attr('disabled', 'disabled');
        showLoading('registerLoading');
        $.ajax({
            type: "POST",
            url: '/user/register',
            data: form.serialize()
        }).done(function (response) {
            hideLoading('registerLoading');
            $(sender).removeAttr('disabled');
            if (response.error) {
                serverError = response;
                form.isValid();
            }
            else {
                $('#registerModal').modal('hide');
                $('#registerLoginModal').modal('show');
                var username = form.find('[name=mobile]').val();
                if (!username)
                    username = form.find('[name=email]').val();
                $('#registerLoginForm [name=j_username]').val(username);
            }
        }).error(function () {
            $(sender).removeAttr('disabled');
            hideLoading('registerLoading');
        });
    }
}
function registerLogin(sender) {
    var form = $('#registerLoginForm');
    if (form.isValid()) {
        $(sender).attr('disabled', 'disabled');
        showLoading('registerLoginLoading');
        form.submit();
    }
}

function loginOnEnter(event) {
    if (event.which == 13) {
        event.preventDefault();
        login();
    }
}

function login() {
    $('#loginFormModal').submit();
}


function moveMap(id, latitude, longitude) {
    var map = eval("map_" + id);
    map.setView(new ol.View({
        center: ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857'),
        zoom: 12
    }));
}