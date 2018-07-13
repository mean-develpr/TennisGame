package tennisgame

class Match {

    TennisScore score
    String matchEvent
    List<TennisGames> sets = new ArrayList<TennisGames>()
    static hasMany = [sets: TennisGames]
//    TennisPlayer servicePlayer

    Match() {
        score = new TennisScore()
        sets.add(new TennisGames())
//        servicePlayer = TennisPlayer.PLAYER0
        matchEvent = "New match created"
    }

    static constraints = {
    }

}