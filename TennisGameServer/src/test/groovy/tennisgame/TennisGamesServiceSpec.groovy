package tennisgame

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class TennisGamesServiceSpec extends Specification implements ServiceUnitTest<TennisMatchService> {

    Match match

    def setup() {
        match = new Match()
    }

    def cleanup() {
    }

    def "Player 1 wins Point"() {
        when: "First point of match won"
        Match modMatch = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Score is 15-0"
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.FIFTEEN
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE

        when: "Second point of match won"
        modMatch = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Score is 30-0"
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.THIRTY
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE

        when: "Third point of match won"
        modMatch = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Score is 40-0"
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.FORTY
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
    }

    def "Player 1 point is DEUCE"() {
        match = new Match()

        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.THIRTY)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        when: "Player 1 wins point 30-40"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Score is deuce"
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.DEUCE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.DEUCE

        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.DEUCE)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.AD)
        when: "Player 1 wins point DEUCE-AS"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Score is deuce again"
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.DEUCE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.DEUCE

    }

    def "Player 1 wins Game"() {
        match = new Match()
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.THIRTY)

        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 1
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0

        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.LOVE)
        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 2
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0

        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.AD)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.DEUCE)
        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 3
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0
    }

    def "Player 1 wins Set"() {
        match = new Match()

        match.sets = [[5, 3], [0, 0], [0, 0], [0, 0], [0, 0]]
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.THIRTY)
        when: "Player 1 wins point 40-30"
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins set"
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        Integer[] setsWon = service.calculateSetsWonByPlayer(match)
        setsWon[TennisPlayer.PLAYER0.value] == 1
        setsWon[TennisPlayer.PLAYER1.value] == 0

        when: "Player 1 wins point AD-DEUCE"
        match.sets = [[6, 3], [5, 0], [0, 0], [0, 0], [0, 0]]
        match.currentSet = 1
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.AD)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.DEUCE)
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins set"
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        Integer[] sets = service.calculateSetsWonByPlayer(match)
        sets[TennisPlayer.PLAYER0.value] == 2
        sets[TennisPlayer.PLAYER1.value] == 0

        when: "Player 2 wins point and set"
        match.sets[match.getCurrentSet()] = [4, 5]
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.DEUCE)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.AD)
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 2 wins set"
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        Integer[] setsWonByPlayer = service.calculateSetsWonByPlayer(match)
        setsWonByPlayer[TennisPlayer.PLAYER0.value] == 2
        setsWonByPlayer[TennisPlayer.PLAYER1.value] == 1
    }

    def "Player 1 wins Match"() {
        match = new Match()
        when: "Player 1 wins game"
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FIFTEEN)
        match.sets = [[6, 3], [4, 6], [6, 0], [6, 5], [0, 0]]
        match.currentSet = 3
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins match"
        service.isMatchWon(match) == true
        service.getWinner(match) == TennisPlayer.PLAYER0.value

        when: "Player 2 wins point"
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.LOVE)
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        match.sets = [[6, 3], [4, 6], [6, 0], [2, 6], [5, 6]]
        match.currentSet = 4
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 2 wins match"
        service.isMatchWon(match) == true
        service.getWinner(match) == TennisPlayer.PLAYER1.value
    }
    
    def "Player 2 wins Point"() {
        when: "First point of match won"
        Match modMatch = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Score is 15-0"
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.FIFTEEN
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE

        when: "Second point of match won"
        modMatch = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Score is 30-0"
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.THIRTY
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE

        when: "Third point of match won"
        modMatch = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Score is 40-0"
        modMatch.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.FORTY
        modMatch.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
    }

    def "Player 2 point is DEUCE"() {
        match = new Match()

        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.THIRTY)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        when: "Player 1 wins point 30-40"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Score is deuce"
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.DEUCE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.DEUCE

        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.DEUCE)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.AD)
        when: "Player 1 wins point DEUCE-AS"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Score is deuce again"
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.DEUCE
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.DEUCE

    }

    def "Player 2 wins Game"() {
        match = new Match()
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.THIRTY)

        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 1
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0

        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.LOVE)
        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 2
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0

        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.AD)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.DEUCE)
        when: "Player 1 wins point"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 1 wins game"
        match.getCurrentSet(TennisPlayer.PLAYER1.value) == 3
        match.getCurrentSet(TennisPlayer.PLAYER0.value) == 0
        //Score is initialized for new game
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        //Current set is still first
        match.getCurrentSet() == 0
    }

    def "Player 2 wins Set"() {
        match = new Match()

        match.sets = [[4, 5], [0, 0], [0, 0], [0, 0], [0, 0]]
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.THIRTY)
        when: "Player 2 wins point 30-40"
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 2 wins set"
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        Integer[] setsWon = service.calculateSetsWonByPlayer(match)
        setsWon[TennisPlayer.PLAYER1.value] == 1
        setsWon[TennisPlayer.PLAYER0.value] == 0

        when: "Player 1 wins point AD-DEUCE"
        match.sets = [[3, 6], [0, 5], [0, 0], [0, 0], [0, 0]]
        match.currentSet = 1
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.AD)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.DEUCE)
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 1 wins set"
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        Integer[] sets = service.calculateSetsWonByPlayer(match)
        sets[TennisPlayer.PLAYER1.value] == 2
        sets[TennisPlayer.PLAYER0.value] == 0

        when: "Player 2 wins point and set"
        match.sets[match.getCurrentSet()] = [5, 4]
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.DEUCE)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.AD)
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 2 wins set"
        match.getScore(TennisPlayer.PLAYER1.value) == TennisPoint.LOVE
        match.getScore(TennisPlayer.PLAYER0.value) == TennisPoint.LOVE
        Integer[] setsWonByPlayer = service.calculateSetsWonByPlayer(match)
        setsWonByPlayer[TennisPlayer.PLAYER1.value] == 2
        setsWonByPlayer[TennisPlayer.PLAYER0.value] == 1
    }

    def "Player 2 wins Match"() {
        match = new Match()
        when: "Player 2 wins game"
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.FORTY)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FIFTEEN)
        match.sets = [[3, 6], [6, 4], [0, 6], [5, 6], [0, 0]]
        match.currentSet = 3
        match = service.point(match, TennisPlayer.PLAYER1.value)
        then: "Player 2 wins match"
        service.isMatchWon(match) == true
        service.getWinner(match) == TennisPlayer.PLAYER1.value

        when: "Player 1 wins point"
        match.setScore(TennisPlayer.PLAYER1.value, TennisPoint.LOVE)
        match.setScore(TennisPlayer.PLAYER0.value, TennisPoint.FORTY)
        match.sets = [[3, 6], [6, 4], [0, 6], [6, 2], [6, 5]]
        match.currentSet = 4
        match = service.point(match, TennisPlayer.PLAYER0.value)
        then: "Player 1 wins match"
        service.isMatchWon(match) == true
        service.getWinner(match) == TennisPlayer.PLAYER0.value
    }
}
