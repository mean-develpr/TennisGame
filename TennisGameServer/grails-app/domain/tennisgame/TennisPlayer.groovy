package tennisgame

enum TennisPlayer {

    PLAYER0(0), PLAYER1(1)

    TennisPlayer(int value) {
        this.value = value
    }

    private final int value

    int getValue() {
        value
    }

    String toString() {
        return name()
    }

    static TennisPlayer valueOf(Integer player) {
        if (player == 0) {
            return TennisPlayer.PLAYER0
        } else if (player == 1) {
            return  TennisPlayer.PLAYER1
        }
    }

}
