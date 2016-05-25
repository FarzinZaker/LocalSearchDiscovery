package utils

import sms1000.SmsLocator

/**
 * Created by roohi on 4/14/2016.
 */
class SmsHelper {
    private static def messageService = new SmsLocator().getsmsSoap()
    private static parameters = [
            agah: [
                    userName    : "ofogh",
                    userPassword: "f@n@v@r@n",
                    senderNumber: '500012001048551'
            ]
    ]

    static def sendSms(String mobile, String text) {
        def result
        try {
            result = messageService.doSendSMS(
                    parameters.agah.userName,
                    parameters.agah.userPassword,
                    parameters.agah.senderNumber,
                    mobile,
                    text,
                    true, false, false, '')
        }
        catch (Exception x) {
            result = "ERROR SMS: ${x.message}"
//            println result
        }
        return result
    }
}
