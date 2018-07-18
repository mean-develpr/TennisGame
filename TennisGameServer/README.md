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
    
> In order to test the app, you will need to have installed grails in your host. 
    
## THE WIRING

The main idea was to use a design which would make easier to extend the behaviour of the application to calculate another 
racket sport, such as ping-pong or similar.

With this in mind, the RacketGameService contains overall methods to calculate scores in any racket sport.
As the counting score differ from t.ex. tennis and ping-pong, each sport must implement its own Service, 
and in this example, the TennisGameService contains methods to calculate the scores(LOVE,FIFTEEN..) for each player,
 as well as the calculation of new games, sets and match.
RacketGameService has been declared as abstract, forcing any class which extends it to implement these methods.

The Domain classes also follow this idea, as the Match class is abstract and only TennisMatch can be instantiated.


###  CONTROLLERS

        └── RacketSport
            ├── ScoreboardController.groovy (1)
            └── UrlMappings.groovy 
            
1. **ScoreboardController**: Delegates logic calculation into TennisGameService, which is injected by convention, 
using underlying Spring Framework. The it renders the response to appropriate view. 

> The match is stored in the session BUT there is no session management implemented. 
    
###  SERVICES



        └── RacketSport
            ├── TennisSport
            │   └── TennisGameService.groovy    (1)
            └── RacketGameService.groovy        (2)


1.  **TennisGameService** Calculates the score and the progress of a tennis match. It extends RacketGameService.
1.  **RacketGameService** Abstract, implements the RacketGame and ScoreBoard interfaces which are used to keep track of 
the scores. 

As mentioned before, the idea is that each sport (Ping-Pong, Paddle...) will implement its own methods as in the 
TennisGameService to calculate the score


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
           │   ├── RacketGame.groovy        (1)
           │   └── Scoreboard.groovy        (2)
           │   ├── RacketException.groovy   (3)
           └── utils
               └── MapUtils.groovy          (4)
        
1. RacketGame: Interface with methods to calculate score progress in 'any' racket game
1. Scoreboard: Interface which only contains a method _point()_ which is called within the controller
1. RacketException: Exception to handle own errors and not to delegate in GORM configuration, just something simple
1. MapUtils: Utility class which works with maps<Integer, Integer>
  
### SRC/TEST

Contains SOME unit test.
 
       groovy
           └── RacketSport
               ├── TennisSport
               │   └── TennisGameServiceSpec.groovy (1)
               ├── PlayerSpec.groovy
               ├── PointSpec.groovy
               └── ScoreboardControllerSpec.groovy
               
1. TennisGameServiceSpec: to ensure correctness of the TennisGameService score calculations. 
           
## The TODOs

* Create an example, as a  PingPongService, using same principle as the TennisGameService. New controller and UI for this.

* Persistence 

* Session management

* Internationalization (i18n) 

* Improve Error handling


...

<!--
    http-server -c-1 -o --cors
--->



