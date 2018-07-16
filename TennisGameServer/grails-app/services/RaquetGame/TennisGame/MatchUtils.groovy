package RaquetGame.TennisGame

/**
 * Utility functions which work with maps<Integer, Integer>
 */

class MatchUtils {

    /**
     * Gets the difference between the values in a map
     * @param whatever map of integers
     * @return abs value of difference
     */
    static Integer getDiff(Map<Integer, Integer> games) {
        Integer diff = 0
        for (Integer _player : games.keySet()) {
            diff = Math.abs(diff - games.get(_player))
        }
        return diff
    }

    /**
     * Gets the max between the values in a map
     * @param whatever array of integers
     * @return max or the first value
     */
    static Integer getMax(Map<Integer, Integer> games) {
        Integer max = 0
        for (Integer _player : games.keySet()) {
            if (games.get(_player) >= max) {
                max = games.get(_player)
            }
        }
        return max
    }

    /**
     * Returns the index which contains the max value in an array of 2
     * @param whatever
     * @return index containing the player
     */
    static Integer getMaxIndex(Integer[] whatever) {
        Integer max = 0
        Integer _player = 0
        for (int i = 0; i < whatever.length; i++) {
            if (max < whatever[i]) {
                max = whatever[i]
                _player = i
            }
        }
        return _player
    }
}
