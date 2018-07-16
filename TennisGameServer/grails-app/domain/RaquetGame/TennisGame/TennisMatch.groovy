package RaquetGame.TennisGame

import RaquetGame.Match
import RaquetGame.Set

class TennisMatch extends Match {

    TennisMatch() {
        this.matchEvent = "New match"
        score = new TennisScore()
        sets.add(new Set())

    }
}
