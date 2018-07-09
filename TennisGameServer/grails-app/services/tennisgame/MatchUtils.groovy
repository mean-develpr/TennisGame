package tennisgame

/**
 * Utility functions which work with arrays within the Match Class
 */

class MatchUtils {

    /**
     * Utility method that checks if all items in array are equal
     * @param myArray
     * @param value
     * @return True if all items in array are equal, false elsewhere
     */
    static Boolean isSameValueInArray(Object[] myArray, Object value) {
        for (Object item : myArray) {
            if (item != value) {
                return false
            }
        }
        true
    }

    /**
     * Gets the difference between the values of an array of 2
     * @param whatever array of integers
     * @return abs value of difference
     */
    static Integer getDiff(Integer[] whatever) {
        Integer abs = Math.abs(whatever[0] - whatever[1])
        return abs
    }

    /**
     * Gets the mac between the values of an array of 2
     * @param whatever array of integers
     * @return max or the first value
     */
    static Integer getMax(Integer[] whatever) {
        if (whatever[0] >= whatever[1]) {
            return whatever[0]
        } else {
            return whatever[1]
        }
    }

    /**
     * Returns the index which contains the max value in an array of 2
     * @param whatever
     * @return index containing the player
     */
    static Integer getMaxIndex(Integer[] whatever) {
        if (whatever[0] >= whatever[1]) {
            return 0
        } else {
            return 1
        }
    }
}
