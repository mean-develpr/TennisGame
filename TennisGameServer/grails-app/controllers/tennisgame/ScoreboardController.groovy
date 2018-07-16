package TennisGame

import RaquetGame.Match

class ScoreboardController {

    Scoreboard service = new TennisGameService()

    def score() {
        println "GET ScoreboardController/points"
        Match match = getMatchFromSession()
        println match
        respond(match: match)
    }

    def point(Integer player) {
        println "PUT ScoreboardController/point Player ${player}"
        try {
            Match match = service.point(getMatchFromSession(), player)
            session["match"] = match
            respond(message: match.matchEvent)
        } catch (RaquetException ex) {
            respond(message: ex.getCode())
        }
    }

    private Match getMatchFromSession() {
        Match match = (Match) session["match"]
        if (!match) {
            newMatch()
        }
        (Match) session["match"]
    }

    private void newMatch() {
        session["match"] = new TennisMatch()
        (Match) session["match"]
    }

}
