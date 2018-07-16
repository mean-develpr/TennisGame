package RaquetGame

class Player {

    static final Integer PLAYER0 = 0
    static final Integer PLAYER1 = 1

    Integer value

    Player(Integer value) {
        if (value == 0 || value == 1) {
            this.value = value
        } else {
            throw new Exception("Invalid player number")
        }

    }
    static constraints = {
    }
}
