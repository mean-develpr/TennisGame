package RaquetGame.TennisGame

import RaquetGame.Match
import RaquetGame.Player
import RaquetGame.Set
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class TennisGameServiceSpec extends Specification implements ServiceUnitTest<TennisGameService> {

    Match match
    Player player0
    Player player1

    def setup() {
        match = new TennisMatch()
        player0 = new Player(Player.PLAYER0)
        player1 = new Player(Player.PLAYER1)
    }

    def cleanup() {
    }

    def "Player 1 wins Point"() {
        when: "First point of match won"
        Match modMatch = service.calculateScoreForPlayer(match, player0)
        then: "Score is 15-0"
        modMatch.score.points.get(Player.PLAYER0) == TennisPoint.FIFTEEN
        modMatch.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        when: "Second point of match won"
        modMatch = service.calculateScoreForPlayer(match, player0)
        then: "Score is 30-0"
        modMatch.score.points.get(Player.PLAYER0) == TennisPoint.THIRTY
        modMatch.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        when: "Third point of match won"
        modMatch = service.calculateScoreForPlayer(match, player0)
        then: "Score is 40-0"
        modMatch.score.points.get(Player.PLAYER0) == TennisPoint.FORTY
        modMatch.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        when: "Fourth point of match won"
        modMatch = service.calculateScoreForPlayer(match, player0)
        then: "Score is WIN-0"
        modMatch.score.points.get(Player.PLAYER0) == TennisPoint.WIN
        modMatch.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
    }

    def "Player 1 point is DEUCE"() {
        match = new TennisMatch()

        match.score.points.put(Player.PLAYER0, TennisPoint.THIRTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.FORTY)
        when: "Player 1 wins point 30-40"
        match = service.calculateScoreForPlayer(match, player0)
        then: "Score is deuce"
        match.score.points.get(Player.PLAYER0) == TennisPoint.DEUCE
        match.score.points.get(Player.PLAYER1) == TennisPoint.DEUCE

        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        when: "Player 1 wins point DEUCE-AD"
        match = service.calculateScoreForPlayer(match, player0)
        then: "Score is deuce again"
        match.score.points.get(Player.PLAYER0) == TennisPoint.DEUCE
        match.score.points.get(Player.PLAYER1) == TennisPoint.DEUCE

    }

    def "Player 1 wins Game"() {
        match = new TennisMatch()
        match.score.points.put(Player.PLAYER0, TennisPoint.FORTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.THIRTY)

        when: "Player 1 wins point"
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 1
        match.sets.last().games.get(Player.PLAYER1) == 0
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        match.score.points.put(Player.PLAYER0, TennisPoint.FORTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.LOVE)
        when: "Player 1 wins point"
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 2
        match.sets.last().games.get(Player.PLAYER1) == 0
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        match.score.points.put(Player.PLAYER0, TennisPoint.AD)
        match.score.points.put(Player.PLAYER1, TennisPoint.LOVE)
        when: "Player 1 wins point"
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 3
        match.sets.last().games.get(Player.PLAYER1) == 0
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
    }

    def "Player 1 wins Set"() {
        match = new TennisMatch()

        match.sets.add(new Set(6, 3))
        match.score.points.put(Player.PLAYER0, TennisPoint.FORTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.THIRTY)
        when: "Player 1 wins point 40-30"
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins set"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
        Integer[] setsWon = service.getSetsWonByPlayer(match)
        setsWon[Player.PLAYER0] == 1
        setsWon[Player.PLAYER1] == 0

        when: "Player 1 wins point AD-DEUCE"
        match.sets.add(new Set(5, 0))
        match.score.points.put(Player.PLAYER0, TennisPoint.AD)
        match.score.points.put(Player.PLAYER1, TennisPoint.DEUCE)
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins set"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
        service.getSetsWonByPlayer(match)[Player.PLAYER0] == 2
        service.getSetsWonByPlayer(match)[Player.PLAYER1] == 0

        when: "Player 2 wins point and set"
        match.sets.add(new Set(4, 5))
        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins set"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
        service.getSetsWonByPlayer(match)[Player.PLAYER0] == 2
        service.getSetsWonByPlayer(match)[Player.PLAYER1] == 1
    }

    def "Player 1 wins Match"() {
        match = new TennisMatch()
        when: "Player 1 wins game"
        match.sets.add(new Set(6, 3))
        match.sets.add(new Set(4, 6))
        match.sets.add(new Set(6, 3))
        match.sets.add(new Set(6, 5))
        match.score.points.put(Player.PLAYER0, TennisPoint.FORTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.FIFTEEN)
        match = service.point(match, Player.PLAYER0)
        then: "Player 1 wins match"
        service.isMatchWon(match)
        service.getWinner(match) == Player.PLAYER0

        when: "Player 2 wins point"
        match = new TennisMatch()
        match.sets.add(new Set(6, 3))
        match.sets.add(new Set(4, 6))
        match.sets.add(new Set(6, 3))
        match.sets.add(new Set(2, 6))
        match.sets.add(new Set(5, 6))
        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins match"
        service.isMatchWon(match)
        service.getWinner(match) == Player.PLAYER1
    }

    def "Player 2 wins Point"() {
        when: "First point of match won"
        match = service.point(match, Player.PLAYER1)
        then: "Score is 15-0"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.FIFTEEN

        when: "Second point of match won"
        match = service.point(match, Player.PLAYER1)
        then: "Score is 30-0"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.THIRTY

        when: "Third point of match won"
        match = service.point(match, Player.PLAYER1)
        then: "Score is 40-0"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.FORTY
    }

    def "Player 2 point is DEUCE"() {
        match = new TennisMatch()
        match.score.points.put(Player.PLAYER0, TennisPoint.FORTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.THIRTY)

        when: "Player 1 wins point 30-40"
        match = service.point(match, Player.PLAYER1)
        then: "Score is deuce"
        match.score.points.get(Player.PLAYER0) == TennisPoint.DEUCE
        match.score.points.get(Player.PLAYER1) == TennisPoint.DEUCE

        match.score.points.put(Player.PLAYER0, TennisPoint.AD)
        match.score.points.put(Player.PLAYER1, TennisPoint.DEUCE)
        when: "Player 1 wins point DEUCE-AS"
        match = service.point(match, Player.PLAYER1)
        then: "Score is deuce again"
        match.score.points.get(Player.PLAYER0) == TennisPoint.DEUCE
        match.score.points.get(Player.PLAYER1) == TennisPoint.DEUCE

    }

    def "Player 2 wins Game"() {
        match = new TennisMatch()
        match.score.points.put(Player.PLAYER0, TennisPoint.THIRTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.FORTY)

        when: "Player 2 wins point"
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 0
        match.sets.last().games.get(Player.PLAYER1) == 1
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        match.score.points.put(Player.PLAYER0, TennisPoint.LOVE)
        match.score.points.put(Player.PLAYER1, TennisPoint.FORTY)
        when: "Player 2 wins point"
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 0
        match.sets.last().games.get(Player.PLAYER1) == 2
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE

        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        when: "Player 2 wins point"
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins game"
        match.sets.last().games.get(Player.PLAYER0) == 0
        match.sets.last().games.get(Player.PLAYER1) == 3
        //Score is initialized for new game
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
    }

    def "Player 2 wins Set"() {
        match = new TennisMatch()

        match.sets.add(new Set(4, 5))
        match.score.points.put(Player.PLAYER0, TennisPoint.THIRTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.FORTY)
        when: "Player 2 wins point 30-40"
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins set"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
        Integer[] setsWon = service.getSetsWonByPlayer(match)
        setsWon[Player.PLAYER0] == 0
        setsWon[Player.PLAYER1] == 1

        when: "Player 1 wins point AD-DEUCE"

        match.sets.add(new Set(0, 5))
        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        match = service.point(match, Player.PLAYER1)
        then: "Player 1 wins set"
        match.score.points.get(Player.PLAYER0) == TennisPoint.LOVE
        match.score.points.get(Player.PLAYER1) == TennisPoint.LOVE
        service.getSetsWonByPlayer(match)[Player.PLAYER0] == 0
        service.getSetsWonByPlayer(match)[Player.PLAYER1] == 2
    }

    def "Player 2 wins Match"() {
        match = new TennisMatch()
        match.sets.add(new Set(3, 6))
        match.sets.add(new Set(6, 4))
        match.sets.add(new Set(0, 6))
        match.sets.add(new Set(5, 6))
        when: "Player 2 wins game"
        match.score.points.put(Player.PLAYER0, TennisPoint.THIRTY)
        match.score.points.put(Player.PLAYER1, TennisPoint.FORTY)
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins match"
        service.isMatchWon(match)
        service.getWinner(match) == Player.PLAYER1

        when: "Player 2 wins point"
        match = new TennisMatch()
        match.score.points.put(Player.PLAYER0, TennisPoint.DEUCE)
        match.score.points.put(Player.PLAYER1, TennisPoint.AD)
        match.sets.add(new Set(3, 6))
        match.sets.add(new Set(6, 4))
        match.sets.add(new Set(8, 6))
        match.sets.add(new Set(4, 6))
        match.sets.add(new Set(5, 6))
        match = service.point(match, Player.PLAYER1)
        then: "Player 2 wins match"
        service.isMatchWon(match)
        service.getWinner(match) == Player.PLAYER1
    }
}

