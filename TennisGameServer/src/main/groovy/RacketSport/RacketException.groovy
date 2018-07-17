package RacketSport

/**
 * Own exception just to get going..
 */
class RacketException extends Exception {
    protected String code

    String getCode() {
        return code
    }

    RacketException(String code, String message) {
        super(message)
        this.code = code
    }
}