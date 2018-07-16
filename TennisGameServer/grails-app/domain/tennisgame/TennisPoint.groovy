package TennisGame

import RaquetGame.Point


class TennisPoint extends Point {

//    static final TennisPoint LOVE = new TennisPoint('LOVE')
//    static final TennisPoint FIFTEEN = new TennisPoint('FIFTEEN')
//    static final TennisPoint THIRTY = new TennisPoint('THIRTY')
//    static final TennisPoint FORTY = new TennisPoint('FORTY')
//    static final TennisPoint DEUCE = new TennisPoint('DEUCE')
//    static final TennisPoint AD = new TennisPoint('AD')
//    static final TennisPoint WIN = new TennisPoint('WIN')

//    enum score {
//        LOVE,  FIFTEEN, THIRTY, FORTY, DEUCE, AD, WIN
//    }
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