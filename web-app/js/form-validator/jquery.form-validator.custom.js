/**
 * Created by Farzin on 4/13/2016.
 */
$.formUtils.addValidator({
    name: 'emptyEmail',
    validatorFunction: function (email) {
        if (!email || email == '')
            return true;
        var emailParts = email.toLowerCase().split('@'),
            localPart = emailParts[0],
            domain = emailParts[1];

        if (localPart && domain) {

            if (localPart.indexOf('"') === 0) {
                var len = localPart.length;
                localPart = localPart.replace(/\"/g, '');
                if (localPart.length !== (len - 2)) {
                    return false; // It was not allowed to have more than two apostrophes
                }
            }

            return $.formUtils.validators.validate_domain.validatorFunction(emailParts[1]) &&
                localPart.indexOf('.') !== 0 &&
                localPart.substring(localPart.length - 1, localPart.length) !== '.' &&
                localPart.indexOf('..') === -1 && !(/[^\w\+\.\-\#\-\_\~\!\$\&\'\(\)\*\+\,\;\=\:]/.test(localPart));
        }

        return false;
    },
    errorMessage: '',
    errorMessageKey: 'badEmail'
});
$.formUtils.addValidator({
    name: 'serverResponseError',
    validatorFunction: function (value, $el, config, language, $form) {
        if (serverError && serverError.field && serverError.field == $el.attr('name')) {
            return false;
        }
        return true;
    },
    errorMessage: function (config) {
        if (serverError && serverError.error)
            return serverError.error;
        return '';
    }
});
$.formUtils.addValidator({
    name: 'mobile',
    validatorFunction: function (value, $el, config, language, $form) {
        var mobileNumber = value;
        if (!mobileNumber || mobileNumber == '')
            return true;

        if (mobileNumber.length != 11)
            return false;

        if (/[^0-9]/g.test(mobileNumber))
            return false;

        return mobileNumber.indexOf('09') == 0
    },
    errorMessage: 'تلفن همراه وارد شده اشتباه است',
    errorMessageKey: 'wrongNationalCode'
});
$.formUtils.addValidator({
    name: 'oneOfTwo',
    validatorFunction: function (value, $el, config, language, $form) {
        if (value && value != '')
            return true;
        var otherFieldName = $el.valAttr("other");
        var otherField = $form.find('[name="' + otherFieldName + '"]');
        var otherVal = otherField.val();
        if (otherVal && otherVal != '')
            return true;
        return false;
    },
    errorMessage: 'لطفا یکی از فیلدهای تلفن همراه یا ایمیل را وارد نمایید',
    errorMessageKey: 'wrongNationalCode'
});
$.formUtils.addValidator({
    name: 'national-code',
    validatorFunction: function (value, $el, config, language, $form) {
        var nationalCode = value;
        if (!nationalCode || nationalCode == '')
            return false;

        if (nationalCode.length != 10)
            return false;

        if (/[^0-9]/g.test(nationalCode))
            return false;

        if (nationalCode == '0000000000' ||
            nationalCode == '1111111111' ||
            nationalCode == '2222222222' ||
            nationalCode == '3333333333' ||
            nationalCode == '4444444444' ||
            nationalCode == '5555555555' ||
            nationalCode == '6666666666' ||
            nationalCode == '7777777777' ||
            nationalCode == '8888888888' ||
            nationalCode == '9999999999')
            return false;


        var num0 = parseInt(nationalCode.charAt(0)) * 10;
        var num2 = parseInt(nationalCode.charAt(1)) * 9;
        var num3 = parseInt(nationalCode.charAt(2)) * 8;
        var num4 = parseInt(nationalCode.charAt(3)) * 7;
        var num5 = parseInt(nationalCode.charAt(4)) * 6;
        var num6 = parseInt(nationalCode.charAt(5)) * 5;
        var num7 = parseInt(nationalCode.charAt(6)) * 4;
        var num8 = parseInt(nationalCode.charAt(7)) * 3;
        var num9 = parseInt(nationalCode.charAt(8)) * 2;
        var a = parseInt(nationalCode.charAt(9));

        var b = (((((((num0 + num2) + num3) + num4) + num5) + num6) + num7) + num8) + num9;
        var c = b % 11;

        return (((c < 2) && (a == c)) || ((c >= 2) && ((11 - c) == a)));
    },
    errorMessage: 'کد ملی وارد شده اشتباه است',
    errorMessageKey: 'wrongNationalCode'
});