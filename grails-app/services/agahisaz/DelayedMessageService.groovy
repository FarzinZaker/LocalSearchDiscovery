package agahisaz

import com.pars.agahisaz.User
import utils.SmsHelper

class DelayedMessageService {

    def mailService

    def schedule(User receiver, String title, String body, String type, Date sendDate) {
        if (!DelayedMessage.findByReceiverAndType(receiver, type))
            new DelayedMessage(receiver: receiver, type: type, title: title, body: body, sendDate: sendDate)?.save(flush: true)
    }

    def sendMessage() {
        def message = DelayedMessage.findAllBySendDateLessThan(new Date(), [sort: 'sendDate', order: 'asc', max: 1])?.find()
        if (message) {

            if (message?.receiver?.mobile)
                SmsHelper.sendSms(message?.receiver?.mobile as String, "آگاهی ساز" + "\n" + message?.body)

            if (message?.receiver?.email) {
                mailService.sendMail {
                    to message?.receiver?.email
                    subject message?.title
                    html(view: "/messageTemplates/email_template",
                            model: [message: message?.body?.replace('\n', '<br/>'),
                                    source : 'delayedMessage',
                                    email  : message?.receiver?.email])
                }
            }

            message.delete(flush: true)
        }
    }
}
