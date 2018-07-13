package tennisgame

//import grails.gorm.transactions.Transactional

//@Transactional
class TennisMatchService {

    /**
     * Updates points of the current game
     * @param match
     * @param Integer 0,1 being the player who won the point
     */
    static Match updateScore(Match match, TennisPlayer player) {
        println "calling updateScore"
        if (isMatchWon(match)) {
            throw new Exception("Match already finished!")
        }

        // Add point to player
        switch (match.score.points[player.value]) {
            case TennisPoint.LOVE:
                match.score.points[player.value] = TennisPoint.FIFTEEN
                break
            case TennisPoint.FIFTEEN:
                match.score.points[player.value] = TennisPoint.THIRTY
                break
            case TennisPoint.THIRTY:
                match.score.points[player.value] = TennisPoint.FORTY
                if (match.score.points[otherPlayer(player).value] == TennisPoint.FORTY) {
                    match.score.points[player.value] = TennisPoint.DEUCE
                    match.score.points[otherPlayer(player).value] = TennisPoint.DEUCE
                }
                break
            case TennisPoint.FORTY:
                match.score.points[player.value] = TennisPoint.WIN
                break
            case TennisPoint.DEUCE:
                match.score.points[player.value] = TennisPoint.AD
                if (match.score.points[otherPlayer(player).value] == TennisPoint.AD) {
                    match.score.points[player.value] = TennisPoint.DEUCE
                    match.score.points[otherPlayer(player).value] = TennisPoint.DEUCE
                }
                break
            case TennisPoint.AD:
                match.score.points[player.value] = TennisPoint.WIN
                break
        }
        match.lastEvent = "Point won by player ${player.value}"
        match
    }

    static Match gameWonByPlayer(Match match, TennisPlayer player) {
        match.sets.last().games[player.value]++
        match.score = new TennisScore()
        match
    }

/**
 * Returns whether game is won by player winner
 * @param match Current match
 * @param winner integer for player 1 or 2 = [0,1]
 * @return Boolean if winner won the game
 */
    static Boolean isGameWon(Match match, TennisPlayer player) {
        if (match.score.points[player.value] == TennisPoint.WIN) {
            match.lastEvent = "Game won by player ${player}"
            return true
        } else {
            return false
        }
    }

/**
 *
 * @param match
 * @return
 */
/**
 * Checks if the set has been won by any player
 * @param tennisGames Array with the games played
 * @return If the set has a winner
 */

    static Boolean isSetWon(TennisGames tennisGames) {
        println("isSetWon")
        (MatchUtils.getMax(tennisGames.games) > 5) && (MatchUtils.getDiff(tennisGames.games) > 1)
    }
/**
 * Checks if the set has been won by any player
 * @param currentSet Array with the games played
 * @return If the set has a winner
 */
    static Boolean isLastSetWon(Match match) {
        println("isLastSetWon")
        if (isSetWon(match.sets[-1])) {
            match.lastEvent = "Set won!"
            true
        } else {
            false
        }
    }

/**
 * Returns whether the match has a winner
 * @param match
 * @return
 */
    static Boolean isMatchWon(Match match) {
        println("isMatchWon")
        Integer[] setsWon = setsWonByPlayer(match)
        if (setsWon[TennisPlayer.PLAYER0.value] > 2 || setsWon[TennisPlayer.PLAYER1.value] > 2) {
            match.lastEvent = "Match won by player ${getWinner(match)}"
            return true
        } else {
            return false
        }
    }

/**
 * Returns the number of sets won by each player
 * @param match
 * @return Integer[2] with sets won
 */
    static Integer[] setsWonByPlayer(Match match) {
        Integer[] setsWon = [0, 0]
        for (TennisGames tennisGames : match.sets) {
            println("setsWonByPlayer")
            if (isSetWon(tennisGames)) {
                //Check who won the set
                if (tennisGames.games[TennisPlayer.PLAYER0.value] > tennisGames.games[TennisPlayer.PLAYER1.value]) {
                    setsWon[TennisPlayer.PLAYER0.value]++
                } else {
                    setsWon[TennisPlayer.PLAYER1.value]++
                }
            }
        }
        setsWon
    }

/**
 * Returns the winner of match
 * @param match
 * @return Integer 0 or 1 for player0 and player1
 */
    static Integer getWinner(Match match) {
        Integer[] setsWon = setsWonByPlayer(match)
        Integer winner = MatchUtils.getMaxIndex(setsWon)
        return winner
    }

    static TennisPlayer otherPlayer(TennisPlayer player) {
        if (player.value == TennisPlayer.PLAYER0.value) {
            return TennisPlayer.PLAYER1
        } else {
            return TennisPlayer.PLAYER0
        }
    }

    static Match newSet(Match match) {
        match.sets.add(new TennisGames())
        match.score = new TennisScore()
        match
    }
}

