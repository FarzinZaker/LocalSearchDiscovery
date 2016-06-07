package agahisaz


class ScoreResetJob {
    def timeout = 5000l // execute job once in 5 seconds
    def cronExpression = "0 1 0 ? * SAT *"
//    def cronExpression = "0 0/1 * 1/1 * ? *"

    def mongoService

    def execute() {
        mongoService.getCollection('user').update(
                [:],
                [$set: [weekScore: 0]],
                false, true
        )
    }
}
