package tennisgame

class ScoreboardController {

    ScoreBoardService service = new ScoreBoardService()

    def score() {
        println "GET ScoreboardController/points"
        Match match = getMatchFromSession()
        println match
        respond(match: match)
    }

    def point(Integer player) {
        println "PUT ScoreboardController/point Player ${player}"
        if (player == 0 || player == 1) {
            println "calling service"
            Match match = service.point(getMatchFromSession(), player)
            session["match"] = match
            println("${session["match"]}")
            respond(message: match.matchEvent)
        } else {
            String str = "Unknown player ${player}"
            println str
            //TODO fix error response
            respond(message: str)
        }

    }

    private Match getMatchFromSession() {
        Match match = (Match) session["match"]
        if (!match) {
            println("Match not found in session")
            session["match"] = new Match()
        }
        (Match) session["match"]
    }

    def newMatch() {
        session["match"] = new Match()
        (Match) session["match"]
    }

}
