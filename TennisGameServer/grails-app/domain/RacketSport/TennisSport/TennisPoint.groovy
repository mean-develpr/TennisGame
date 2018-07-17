package RacketSport.TennisSport

import RacketSport.Point


class TennisPoint extends Point {

    static final String LOVE = 'LOVE'
    static final String FIFTEEN = 'FIFTEEN'
    static final String THIRTY = 'THIRTY'
    static final String FORTY = 'FORTY'
    static final String DEUCE = 'DEUCE'
    static final String AD = 'AD'
    static final String WIN = 'WIN'

    List<String> validValues = Arrays.asList('LOVE', 'FIFTEEN', 'THIRTY', 'FORTY', 'DEUCE', 'AD', 'WIN')

    TennisPoint() {
        value = LOVE
    }

    TennisPoint(String value) {
        if (validValues.contains(value)) {
            this.value = value
        } else {
            throw new Exception("Invalid value")
        }
    }

    @Override
    String toString() {
        value
    }

}