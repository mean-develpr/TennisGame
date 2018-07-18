package RacketSport

/**
 * Methods to calculate the 'any' racket score
 */
interface RacketGame {

    /**
     * Validates player and match status
     * @param match Match to apply operation
     * @param player PlayerEnum who wins the point
     * @throws Exception containing error message
     */
    void validateOperation(Match match, Integer player) throws RacketException

    /**
     * Updates points of the current game
     * @param match Current match
     * @param player Integer 0,1 being the player who won the point
     * @return Updated match
     */
     Match calculateScoreForPlayer(Match match, Player player)

    /**
     * Returns whether game is won by player winner
     * @param match Current match
     * @param winner integer for player 1 or 2 = [0,1]
     * @return Boolean if winner won the game
     */
    Boolean isGameWonByPlayer(Match match, Player player)

    /**
     * Updates match adding a game for player
     * @param match Current match
     * @param winner integer for player 1 or 2 = [0,1]
     * @return Modified Match
     */
    Match updateGamesForPlayer(Match match, Player player)

    /**
     * Checks if the set has been won by any player
     * @param tennisGames Array with the games played
     * @return If the set has a winner
     */
    Boolean isSetWon(Set tennisGames)

    /**
     * Checks if the set has been won by any player
     * @param currentSet Array with the games played
     * @return If the set has a winner
     */
    Boolean isLastSetWon(Match match)

    /**
     * Returns whether the match has a winner
     * @param match
     * @return
     */
    Boolean isMatchWon(Match match)

    /**
     * Returns the winner of match
     * @param match
     * @return Integer 0 or 1 for player0 and player1
     */
    Integer getWinner(Match match)

    /**
     * Returns the number of sets won by each player
     * @param match
     * @return Map with sets won
     */
    Map<Integer, Integer> getSetsWonByPlayer(Match match)

    /**
     * Returns the other player
     * @param player
     * @return
     */
    Player otherPlayer(Player player)

    /**
     * Adds a new set to match
     * @param match
     * @return Updated match
     */
    Match addNewSet(Match match)
}