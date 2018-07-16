package RaquetGame

import RaquetGame.TennisGame.MatchUtils
import RaquetGame.TennisGame.RaquetException

//import grails.gorm.transactions.Transactional

//@Transactional
abstract class RacketGameService implements RacketGame, Scoreboard {

    Match point(Match match, Integer player) throws RaquetException {
        validateOperation(match, player)
        Player racketPlayer = new Player(player)
        match = calculateScoreForPlayer(match, racketPlayer)
        if (isGameWonByPlayer(match, racketPlayer)) {
            match = updateGamesForPlayer(match, racketPlayer)
            if (isLastSetWon(match)) {
                if (!isMatchWon(match)) {
                    match = addNewSet(match)
                }
            }
        }
        // Return modified match to the controller
        // TODO use GORM persistence instead: match.save()
        match
    }

    void validateOperation(Match match, Integer player) throws RaquetException {
        if (player != 0 && player != 1) {
            throw new RaquetException("Player number not valid", "Player number not valid")
        }
        if (isMatchWon(match)) {
            throw new RaquetException("Match finished", "Match finished")
        }
    }

    Boolean isLastSetWon(Match match) {
        if (isSetWon(match.sets[-1])) {
            match.matchEvent = "Set won!"
            true
        } else {
            false
        }
    }

    Player otherPlayer(Player player) {
        if (player.value == Player.PLAYER0) {
            return new Player(Player.PLAYER1)
        } else {
            return new Player(Player.PLAYER0)
        }
    }

    Integer[] getSetsWonByPlayer(Match match) {
        Integer[] setsWon = [0, 0]
        for (Set set : match.sets) {
            if (isSetWon(set)) {
                //Check who won the set
                if (set.games[Player.PLAYER0] > set.games[Player.PLAYER1]) {
                    setsWon[Player.PLAYER0]++
                } else {
                    setsWon[Player.PLAYER1]++
                }
            }
        }
        setsWon
    }

    Integer getWinner(Match match) {
        Integer[] setsWon = getSetsWonByPlayer(match)
        Integer winner = MatchUtils.getMaxIndex(setsWon)
        return winner
    }

    Match updateScoreForPlayer(Match match, Player player, String value) {
        match.score.points.put(player.value, value)
        match
    }

    String getScoreForPlayer(Match match, Player player) {
        match.score.points.get(player.value)
    }
}
