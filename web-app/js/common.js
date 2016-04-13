/**
 * Created by Farzin on 4/13/2016.
 */


function hideLoading(id) {
    var loading = $('#' + id);
    if (loading.parent().hasClass('modal-footer')) {
        loading.stop().fadeOut(200, function () {
            loading.parent().find('.btn').stop().fadeIn(200, function(){
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
        }).error(function () {
            $(sender).removeAttr('disabled');
            hideLoading('registerLoading');
        });
    }
}