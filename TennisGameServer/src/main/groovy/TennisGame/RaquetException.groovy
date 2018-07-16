package TennisGame

class RaquetException extends Exception {
    protected String code

    String getCode() {
        return code
    }

    RaquetException(String code, String message) {
        super(message)
        this.code = code
    }
}