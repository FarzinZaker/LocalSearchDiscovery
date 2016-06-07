package agahisaz

class CommonTagLib {

    static namespace = "common"

    def actionService

    def recordBrowse = {attrs, body ->
        actionService.doAction(Action.BROWSE)
    }
}
