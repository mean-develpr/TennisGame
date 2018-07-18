package RacketSport

import RacketSport.TennisSport.TennisGameService
import RacketSport.TennisSport.TennisMatch

class ScoreboardController {

    //Inject Tennis implementation
    //Scoreboard service = new TennisGameService()
    def tennisGameService

    def score() {
        println "GET ScoreboardController/score"
        Match match = getMatchFromSession()
        respond(match: match)
    }

    def point(Integer player) {
        println "PUT ScoreboardController/point Player ${player}"
        try {
            Match match = tennisGameService.point(getMatchFromSession(), player)
            session["match"] = match
            respond(message: match.matchEvent)
        } catch (RacketException ex) {
            respond(message: ex.getMessage())
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
