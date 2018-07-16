package RaquetGame

import RaquetGame.ScoreboardController
import RaquetGame.TennisGame.TennisMatch
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ScoreboardControllerSpec extends Specification implements ControllerUnitTest<ScoreboardController> {

    def setup() {
    }

    def cleanup() {
    }

    def "score method"() {
        when: "Score Method is called"
        controller.session.match = new TennisMatch()
        def something = controller.score()
        then: "Nothing is returned"
        something == null
    }
}
