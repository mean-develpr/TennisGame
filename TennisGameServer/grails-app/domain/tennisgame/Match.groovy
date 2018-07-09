package tennisgame

class Match {

    TennisPoint[] score = new TennisPoint[2]
    Integer[][] sets = new Integer[5][2]
    TennisPlayer servicePlayer
    Integer setPlaying
    String message

    Match() {
        score = [TennisPoint.LOVE, TennisPoint.LOVE]
        sets = [[0, 0], [0, 0], [0, 0], [0, 0], [0, 0]]
        servicePlayer = TennisPlayer.PLAYER0
        setPlaying = 0
        message = "New match created"
    }

    static constraints = {
    }

    TennisPoint[] getScore() {
        score
    }

    TennisPoint getScore(Integer player) {
        score[player]
    }

    void setScore(Integer player, TennisPoint point) {
        score[player] = point
    }

    void setScores(TennisPoint point) {
        score[0] = point
        score[1] = point
    }

    Integer[] getCurrentSet() {
        sets[setPlaying]
    }

    Integer getCurrentSet(Integer player) {
        sets[setPlaying][player]
    }

    void addGame(Integer player) {
        sets[setPlaying][player]++
    }

    Integer getSetPlaying() {
        setPlaying
    }

    @Override
    String toString() {
        StringBuffer buff = new StringBuffer()
        buff.append("SET     1  2  3  4  5  SCORE\n")
        for (int player = 0; player < 2; player++) {
            buff.append("PLYR"+player+"   ")
            for (int i = 0; i < 5; i++) {
                buff.append(sets[i][player])
                buff.append("  ")
            }
            buff.append(score[player])
            buff.append("\n")
        }
        buff.toString()
    }

}