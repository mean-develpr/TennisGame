import tennisgame.Match

interface TennisGameServiceI {

    /**
     * Calculates resulting match after a point is won by player
     * @param match The current match
     * @param player Integer representing player 0,1
     * @return Modified match updating player scores, games and a lastEvent with last action.
     */
    Match point(Match match, Integer player)
}