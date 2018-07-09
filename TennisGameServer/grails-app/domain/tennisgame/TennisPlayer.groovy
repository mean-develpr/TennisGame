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

}
