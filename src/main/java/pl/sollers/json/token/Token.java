package pl.sollers.json.token;

public class Token {
    public static final Token EOF = new Token(Token.EOF_CHAR);
    public static final Token LEFT_BRACE = new Token(Token.LEFT_BRACE_CHAR, 1);
    public static final Token LEFT_SQUARE = new Token(Token.LEFT_SQUARE_CHAR, 1);
    public static final Token RIGHT_BRACE = new Token(Token.RIGHT_SQUARE_CHAR, -1);
    public static final Token RIGHT_SQUARE = new Token(Token.RIGHT_SQUARE_CHAR, -1);;
    public static final Token COMMA = new Token(Token.COMMA_CHAR);
    public static final Token COLON = new Token(Token.COLON_CHAR);
    private static final byte EOF_CHAR = 0; //end of file
    // TODO: make this private
    public static final byte UNQUOTED = 1;
    public static final byte STRING = 2;
    private static final byte LEFT_BRACE_CHAR = '{';
    private static final byte RIGHT_BRACE_CHAR = '}';
    private static final byte LEFT_SQUARE_CHAR = '[';
    private static final byte RIGHT_SQUARE_CHAR = ']';
    private static final byte COMMA_CHAR = ',';
    private static final byte COLON_CHAR = ':';
    public static final Token[] tokens =  new Token[255];

    static {
        tokens[LEFT_BRACE_CHAR] = LEFT_BRACE;
        tokens[LEFT_SQUARE_CHAR] = LEFT_SQUARE;
        tokens[RIGHT_BRACE_CHAR] = RIGHT_BRACE;
        tokens[RIGHT_SQUARE_CHAR] = RIGHT_SQUARE;
        tokens[COMMA_CHAR] = COMMA;
        tokens[COLON_CHAR] = COLON;
    }


    public byte type = 0;
    public int levelIncrement = 0;
    private byte[] buffer;
    private int start;
    private int length;

    public Token(byte type) {
        this.type = type;
    }

    public Token(byte type, int increment) {
        this.type = type;
        this.levelIncrement = increment;
    }

    public Token(byte type, byte[] buffer, int start, int length) {
        this.type = type;
        this.buffer = buffer;
        this.start = start;
        this.length = length;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case UNQUOTED:
                sb.append("UNQUOTED(").append(getValue()).append(")");
                break;
            case STRING:
                sb.append("STRING(").append(getValue()).append(")");
                break;
            case EOF_CHAR:
                sb.append("END OF FILE");
                break;
            default:
                sb.append("TOKEN(").append(type).append(")");
        }
        return sb.toString();
    }

    public String getValue() {
        return new String(buffer, start, length);
    }

    public boolean isString() {
        return type == STRING;
    }
}
