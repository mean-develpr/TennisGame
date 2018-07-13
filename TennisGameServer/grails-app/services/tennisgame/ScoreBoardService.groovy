package tennisgame
//@Transactional
class ScoreBoardService {

    TennisScoreBoardService service = new TennisScoreBoardService()

    /**
     * Calculates resulting match after a point is won by player
     * @param match The current match
     * @param player Integer representing player 0,1
     * @return Modified match updating player scores, games and a matchEvent with last action.
     */
    Match point(Match match, Integer player) {
//        try {
        TennisPlayer tennisPlayer = TennisPlayer.valueOf(player)
        println "calling ScoreBoardService"
        match = service.updateScore(match, tennisPlayer)
        if (service.isGameWon(match, tennisPlayer)) {
            match = service.gameWonByPlayer(match, tennisPlayer)
            if (service.isLastSetWon(match)) {
                if (!service.isMatchWon(match)) {
                    //Begin playing a new set
                    match = service.newSet(match)
                }
            }
        }
//        } catch (Exception e) {
//            match.matchEvent = e.getMessage()
//        }

        // Return modified match to the controller
        // TODO use GORM persistence instead: match.save()
        match
    }
}
