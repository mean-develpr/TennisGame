# The Server

This is the server which has the TennisGame API with following urls.

    http://localhost:8080/point/1
    
To record a point with player 1.
    
    http://localhost:8080/score

To retrieve the match in json format.

## HowTo Start Server

It is straightforward, just as a normal grails application, open grails console and type:

    run-app
    
There are also some unit test which can be run with:

    test-app
    
You can also test with __curl__ (https://curl.haxx.se/download.html). Here are some examples:

    curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/score"  | jq .
    curl -X PUT -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/1"  | jq .
    curl -X PUT -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/0"  | jq .
    
## The Wiring

* **TennisGameService**: Here is where all calculations are done. It exposes only one method:

    *  **Match point(Match match, Integer player)** Calculates resulting match after a point is won by player, updating player scores, games and a message with last action.

* **ScoreboardController**: Very simple controller, delegates logic into TennisGameService. 
It stores the match in the session BUT there is no session management implemented. 

1. **CONTROLLERS** Here follows the package structure of the domain classes


        └── RacketSport
            ├── TennisSport
            ├── ScoreboardController.groovy
            └── UrlMappings.groovy
    
1. **SERVICES** Here follows the package structure of the domain classes


        └── RacketSport
            ├── TennisSport
            │   └── TennisGameService.groovy    (1)
            ├── MapUtils.groovy                 (2)
            └── RacketGameService.groovy        (3)


1. TennisGameService
    
1. MapUtils
    
1. RacketGameService

1. **DOMAIN** Here follows the package structure of the domain classes


        └── RacketSport
            ├── TennisSport
            │   ├── TennisMatch.groovy
            │   ├── TennisPoint.groovy
            │   └── TennisScore.groovy
            ├── Match.groovy
            ├── Player.groovy
            ├── Point.groovy
            ├── Score.groovy
            └── Set.groovy
            
    Again, the idea is to be able to represent any racket match, only a Tennis Match is implemented now
     
        
## The TODOs

* Internationalization (i18n) 

* Persistence 

* Session management

* Error handling

<!--
    http-server -c-1 -o --cors
--->

In order to test the app, you will need to have installed grails in your host. 

