package RaquetGame

import RaquetGame.Match
import RaquetGame.TennisGame.RaquetException

interface Scoreboard {

    Match point(Match match, Integer player) throws RaquetException
}