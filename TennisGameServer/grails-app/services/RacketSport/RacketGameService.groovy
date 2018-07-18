package RacketSport

import grails.validation.ValidationException
import org.springframework.validation.Errors
import utils.MapUtils

//import grails.gorm.transactions.Transactional

//@Transactional
/**
 * This class implements the RacketGame interface which is used to keep track of the scores
 * Is an abstract class because each Racket Sport will implement its own methods to calculate the score
 * which are different in each racket sport
 */
abstract class RacketGameService implements RacketGame, Scoreboard {

    Match point(Match match, Integer player) throws RacketException {
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

    void validateOperation(Match match, Integer player) throws RacketException {
        if (player != 0 && player != 1) {
            throw new RacketException("Player number not valid", "Player number not valid")
        }
        if (isMatchWon(match)) {
            throw new RacketException("Match finished", "Match finished")
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

    Map<Integer, Integer> getSetsWonByPlayer(Match match) {
        Map<Integer, Integer> setsWon = new HashMap<Integer, Integer>()
        setsWon.put(Player.PLAYER0, 0)
        setsWon.put(Player.PLAYER1, 0)
        for (Set set : match.sets) {
            if (isSetWon(set)) {
                //Check who won the set
                if (set.games[Player.PLAYER0] > set.games[Player.PLAYER1]) {
                    setsWon.put(Player.PLAYER0, ++setsWon.get(Player.PLAYER0))
                } else {
                    setsWon.put(Player.PLAYER1, ++setsWon.get(Player.PLAYER1))
                }
            }
        }
        setsWon
    }

    Integer getWinner(Match match) {
        Map<Integer, Integer> setsWon = getSetsWonByPlayer(match)
        Integer winner = MapUtils.getMaxIndex(setsWon)
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
