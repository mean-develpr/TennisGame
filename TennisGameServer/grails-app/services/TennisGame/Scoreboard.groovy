package TennisGame

import RaquetGame.Match

interface Scoreboard {

    Match point(Match match, Integer player)
}