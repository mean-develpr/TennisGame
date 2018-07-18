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


###  CONTROLLERS

        └── RacketSport
            ├── TennisSport
            ├── ScoreboardController.groovy
            └── UrlMappings.groovy
            
1. **ScoreboardController**: Delegates logic into TennisGameService. The match is stored in the session BUT there is no session management implemented. 
    
###  SERVICES



        └── RacketSport
            ├── TennisSport
            │   └── TennisGameService.groovy    (1)
            └── RacketGameService.groovy        (3)


1.  **TennisGameService** Calculates the score and the progress of a tennis match. It extends RacketGameService.
1.  **RacketGameService** Abstract, implements the RacketGame interface which is used to keep track of the scores. The idea is that each sport (PingPong, Paddle...) will implement its own methods as in the TennisGameService to calculate the score


###  DOMAIN

Here follows the package structure of the domain classes


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
     
### SRC/MAIN
   
   
       groovy
           ├── RacketSport
           │   ├── RacketException.groovy
           │   ├── RacketGame.groovy
           │   └── Scoreboard.groovy
           └── utils
               └── MapUtils.groovy
        
1. RacketGame: Interface with methods to calculate score progress in 'any' racket game
1. Scoreboard: Interface which only contains a method _point()_ which is called within the controller
1. MapUtils: Utility class which work with maps<Integer, Integer>
   
           
## The TODOs

* Internationalization (i18n) 

* Persistence 

* Session management

* Improve Error handling

* Create a PingPong from TennisGameService

<!--
    http-server -c-1 -o --cors
--->

In order to test the app, you will need to have installed grails in your host. 

