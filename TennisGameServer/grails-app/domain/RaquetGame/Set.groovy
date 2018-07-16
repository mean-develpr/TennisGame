package RaquetGame

class Set {
    Map<Integer, Integer> games = new HashMap<Integer, Integer>()

    Set() {
        games.put(Player.PLAYER0, 0)
        games.put(Player.PLAYER1, 0)
    }

    Set(Integer games0, Integer games1) {
        games.put(Player.PLAYER0, games0)
        games.put(Player.PLAYER1, games1)
    }
}
