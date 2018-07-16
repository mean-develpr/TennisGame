package TennisGame

import RaquetGame.Match
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MatchSpec extends Specification implements DomainUnitTest<Match> {

    def setup() {
    }

    def cleanup() {
    }

    def "Match test hello"() {
        when: "Nothing is added"
        Integer count = Match.count()
        then: "Nothing is returned"
        count == 0
    }
}
