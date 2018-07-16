package RaquetGame.TennisGame

import RaquetGame.Match
import RaquetGame.Player
import RaquetGame.RacketGameService
import RaquetGame.Set

//import grails.gorm.transactions.Transactional

//@Transactional
class TennisGameService extends RacketGameService {

    Match calculateScoreForPlayer(Match match, Player player) {
        // Add point to player
        switch (getScoreForPlayer(match, player)) {
            case TennisPoint.LOVE:
                updateScoreForPlayer(match, player, TennisPoint.FIFTEEN)
                break

            case TennisPoint.FIFTEEN:
                updateScoreForPlayer(match, player, TennisPoint.THIRTY)
                break

            case TennisPoint.THIRTY:
                updateScoreForPlayer(match, player, TennisPoint.FORTY)
                if (getScoreForPlayer(match, otherPlayer(player)) == TennisPoint.FORTY) {
                    updateScoreForPlayer(match, player, TennisPoint.DEUCE)
                    updateScoreForPlayer(match, otherPlayer(player), TennisPoint.DEUCE)
                }
                break

            case TennisPoint.FORTY:
                updateScoreForPlayer(match, player, TennisPoint.WIN)
                break

            case TennisPoint.DEUCE:
                updateScoreForPlayer(match, player, TennisPoint.AD)
                if (getScoreForPlayer(match, otherPlayer(player)) == TennisPoint.AD) {
                    updateScoreForPlayer(match, player, TennisPoint.DEUCE)
                    updateScoreForPlayer(match, otherPlayer(player), TennisPoint.DEUCE)
                }
                break

            case TennisPoint.AD:
                updateScoreForPlayer(match, player, TennisPoint.WIN)
                break
        }
        match.matchEvent = "Point won by player ${player.value}"
        match
    }

    Boolean isGameWonByPlayer(Match match, Player player) {
        String point = getScoreForPlayer(match, player)
        if (point == TennisPoint.WIN) {
            match.matchEvent = "Game won by player ${player.value}"
            return true
        } else {
            return false
        }
    }

    Boolean isSetWon(Set tennisGames) {
        (MatchUtils.getMax(tennisGames.games) > 5) && (MatchUtils.getDiff(tennisGames.games) > 1)
    }

    Boolean isMatchWon(Match match) {
        Integer[] setsWon = getSetsWonByPlayer(match)
        if (setsWon[Player.PLAYER0] > 2 || setsWon[Player.PLAYER1] > 2) {
            match.matchEvent = "Match won by player ${getWinner(match)}"
            return true
        } else {
            return false
        }
    }

    Match updateGamesForPlayer(Match match, Player player) {
        Integer game = match.sets.last().games.get(player.value)
        match.sets.last().games.put(player.value, ++game)
        match.score = new TennisScore()
        match
    }

    Match addNewSet(Match match) {
        match.sets.add(new Set())
        match.score = new TennisScore()
        match
    }

}
