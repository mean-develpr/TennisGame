package RacketSport

abstract class Match {

    Score score
    String matchEvent
    List<Player> players =  new ArrayList<Player>()
    List<Set> sets =  new ArrayList<Set>()
    static hasMany = [sets: Set, players: Player]

}