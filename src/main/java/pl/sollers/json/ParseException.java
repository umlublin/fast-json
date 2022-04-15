package pl.sollers.json;

public class ParseException extends Exception {

    public static final int ERROR_KEY_NOT_STRING = 1;
    public static final int COLON_EXPECTED = 2;
    public static final int END_OF_MAP = 3;
    public static final int END_OF_FILE = 4;

    private int errorType;

    public ParseException(int errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return "";
    }
}
