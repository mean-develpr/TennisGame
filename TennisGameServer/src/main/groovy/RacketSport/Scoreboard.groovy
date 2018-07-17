package RacketSport

interface Scoreboard {

    /**
     * Updates the match for the point won for player
     * @param match Current Match
     * @param player Integer as player [0,1] as for now
     * @return The updated match
     * @throws RacketException
     */
    Match point(Match match, Integer player) throws RacketException
}