package RacketSport.TennisSport

import RacketSport.Match
import RacketSport.Set

class TennisMatch extends Match {

    TennisMatch() {
        this.matchEvent = "New match"
        score = new TennisScore()
        sets.add(new Set())

    }
}
