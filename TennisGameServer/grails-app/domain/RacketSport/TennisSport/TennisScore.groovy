package RacketSport.TennisSport

import RacketSport.Player
import RacketSport.Score

class TennisScore extends Score {

    TennisScore() {
        points.put(Player.PLAYER0, new TennisPoint().toString())
        points.put(Player.PLAYER1, new TennisPoint().toString())
    }

    static constraints = {
    }
}
