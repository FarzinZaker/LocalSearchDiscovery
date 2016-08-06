package agahisaz


class DelayedMessageJob {

    static startDelay = 60000
    def timeout = 10 * 60 * 1000l
//    def timeout = 1000l
    def concurrent = false


    def delayedMessageService

    def execute() {

        def calendar = Calendar.instance
        calendar.setTime(new Date())
        def dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        def hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        if(dayOfWeek == 5 || hourOfDay < 10 || hourOfDay > 19)
        {
            Thread.sleep(1000l * 60 * 30)
            return
        }

        delayedMessageService.sendMessage()
    }
}
