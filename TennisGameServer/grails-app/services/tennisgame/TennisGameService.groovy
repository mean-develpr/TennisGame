package tennisgame

//import grails.gorm.transactions.Transactional

//@Transactional
class TennisGameService {

    /**
     * Calculates resulting match after a point is won by player
     * @param match The current match
     * @param player Integer representing player 0,1
     * @return Modified match updating player scores, games and a message with last action.
     */
    Match point(Match match, Integer player) {
        try {
            updateScore(match, player)
            match.message = "Point won by player ${player}"
            if (isGameWon(match, player)) {
                match.message = "Game won by player ${player}"
                match.addGame(player)
                match.setScores(TennisPoint.LOVE)
                if (isSetWon(match)) {
                    match.message = "Set won by player ${player}"
                    if (isMatchWon(match)) {
                        Integer winner = getWinner(match)
                        match.message = "Match won by player ${winner}"
                    } else {
                        //Begin playing a new set
                        match.setPlaying++
                    }
                }
            }
        } catch (Exception e) {
            match.message = e.getMessage()
        }

        // Return modified match to the controller
        // TODO use GORM persistence instead: match.save()
        match
    }

    /**
     * Updates score of the current game
     * @param match
     * @param Integer 0,1 being the player who won the point
     */
    private static void updateScore(Match match, Integer player) {
        TennisPoint[] score = match.getScore()

        if (isMatchWon(match)) {
            throw new Exception("Match already finished!")
        }

        // Add point to player
        switch (score[player]) {
            case TennisPoint.LOVE:
                match.setScore(player, TennisPoint.FIFTEEN)
                break
            case TennisPoint.FIFTEEN:
                match.setScore(player, TennisPoint.THIRTY)
                break
            case TennisPoint.THIRTY:
                match.setScore(player, TennisPoint.FORTY)
                if (MatchUtils.isSameValueInArray(match.getScore(), TennisPoint.FORTY)) {
                    match.setScores(TennisPoint.DEUCE)
                }
                break
            case TennisPoint.FORTY:
                match.setScore(player, TennisPoint.WIN)
                break
            case TennisPoint.DEUCE:
                match.setScore(player, TennisPoint.AD)
                if (MatchUtils.isSameValueInArray(match.getScore(), TennisPoint.AD)) {
                    match.setScores(TennisPoint.DEUCE)
                }
                break
            case TennisPoint.AD:
                match.setScore(player, TennisPoint.WIN)
                break
        }
    }

    /**
     * Returns whether game is won by player winner
     * @param match Current match
     * @param winner integer for player 1 or 2 = [0,1]
     * @return Boolean if winner won the game
     */
    private static Boolean isGameWon(Match match, Integer player) {
        return (match.getScore(player) == TennisPoint.WIN)
    }

    /**
     *
     * @param match
     * @return
     */
    private static Boolean isSetWon(Match match) {
        Integer[] currentSet = match.getCurrentSet()
        return isSetWon(currentSet)
    }

    /**
     * Checks if the set has been won by any player
     * @param currentSet Array with the games played
     * @return If the set has a winner
     */
    private static Boolean isSetWon(Integer[] currentSet) {
        return (MatchUtils.getMax(currentSet) > 5) && (MatchUtils.getDiff(currentSet) > 1)
    }

    /**
     * Returns whether the match has a winner
     * @param match
     * @return
     */
    private static Boolean isMatchWon(Match match) {
        Integer[] setsWon = calculateSetsWonByPlayer(match)
        return (setsWon[TennisPlayer.PLAYER0.value] > 2 || setsWon[TennisPlayer.PLAYER1.value] > 2)
    }

    /**
     * Returns the number of sets won by each player
     * @param match
     * @return Integer[2] with sets won
     */
    static Integer[] calculateSetsWonByPlayer(Match match) {
        Integer[] setsWon = [0, 0]
        for (Integer[] set : match.sets) {
            if (isSetWon(set)) {
                //Check who won the set
                if (set[TennisPlayer.PLAYER0.value] > set[TennisPlayer.PLAYER1.value]) {
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
        Integer[] setsWon = calculateSetsWonByPlayer(match)
        Integer winner = MatchUtils.getMaxIndex(setsWon)
        return winner
    }
}

