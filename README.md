

TennisGame is an application to display current score of a tennis game. The application will start showing an empty game, and the user will be able to add a point to a player, showing the resulting score.

The application consists of a very simple HTML client and a server:

  1. [The Client](#section_client): containing only a plain HTML/JS file.
  1. [The Server](#section_server): implemented with Grails..
 
<a name='section_client'/>

### The Client

This is a very simple html client with the following files:
  
      ├── README.md
      ├── index.html
      ├── scoreboard.css
      ├── scoreboard.js
      └── tennis-ball.png

One way is with node, if installed, just type following command:
  
  http-server -o 

Options:

* **-o** opens explorer.

> Session management has not been implemented. 
> If you need to create a new match, a possible solution is to open  an incognito window.

<!--
  http-server -c-1 -o --cors
--->
  
<a name='section_server'/>

## The Server

This is the server which has the TennisGame API with following urls.

    http://localhost:8080/point/1
    
To record a point with player 1.
    
    http://localhost:8080/score

To retrieve the match in json format.

### HowTo Start Server

It is straightforward, just as a normal grails application, open grails console and type:

    run-app
    
There are also some unit test which can be run with:

    test-app
    
You can also test with __curl__ (https://curl.haxx.se/download.html). Here are some examples:

    curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/score"  | jq .
    curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/1"  | jq .
    curl -b "cookie.txt" -c "cookie.txt" -H "Accept: application/json" "localhost:8080/point/0"  | jq .
    
### The Logic

* **TennisGameService**: Here is where all calculations are done. It exposes only one method:

    *  **Match point(Match match, Integer player)** Calculates resulting match after a point is won by player, updating player scores, games and a message with last action.

* **ScoreboardController**: Very simple controller, delegates logic into TennisGameService. 
It stores the match in the session BUT there is no session management implemented. 

* **Match**:
        
### The TODOs

* Persistence is not completed

* Session management

* Error handling is not completed

<!--
    http-server -c-1 -o --cors
--->

In order to test the app, you will need to have installed grails in your host. 


