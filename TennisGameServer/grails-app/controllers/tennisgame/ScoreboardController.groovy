package tennisgame

class ScoreboardController {

//    static scope = "session"
//    static responseFormats = ['json']
    TennisGameService tennisGameService

    def score() {
        println "GET ScoreboardController/score"
        Match match = getMatchFromSession()
        println match
        respond(match: match)
    }

    def point(Integer player) {
        println "PUT ScoreboardController/point Player ${player}"
        if (player == 0 || player == 1) {
            Match match = tennisGameService.point(getMatchFromSession(), player)
            session["match"] = match
            println("${session["match"]}")
            respond(message: match.message)
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

}
