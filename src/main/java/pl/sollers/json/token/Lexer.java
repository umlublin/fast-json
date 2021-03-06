package pl.sollers.json.token;

import pl.sollers.json.ParseException;

import java.util.Set;

public class Lexer {
    private static final char ESCAPE_CHAR = '\\';
    private static final char SPACE_QUOTED = '"';
    private static final char SPACE_UNQUOTED = '-';
    public static final char SPACE_GLOBAL = 0;
    private final int length;
    private byte[] text;
    private byte space;
    private byte thisByte;
    private int position;
    private int level = 0;
    private Set<Byte> startUnquoted = Set.of((byte) '-', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4',
            (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 't', (byte) 'f', (byte) 'n');
    private Set<Byte> endUnquoted = Set.of((byte) ',', (byte) ']', (byte) '}', (byte) ' ', (byte) '\t', (byte) '\r',
            (byte) '\n', (byte) '\b', (byte) '\f');

    public Lexer(byte[] input) {
        this.text = input;
        this.length = input.length;
        this.space = 0;
        this.position = 0;
    }

    private void goBack() {
        if (position > 1) position--;
    }

    private boolean readChar() {
        if (position == length) {
            return false;
        }
        thisByte = text[position];
        position++;
        return true;
    }

    public int getPosition() {
        return position;
    }

    public Token getNextToken() throws ParseException {
        boolean escape = false;
        int start = 0;
        while (readChar()) {
            if (space == SPACE_GLOBAL) {
                Token token = Token.tokens[thisByte];
                if (token != null) {
                    level += token.levelIncrement;
                    return token;
                }
                if (startUnquoted.contains(thisByte)) {
                    space = SPACE_UNQUOTED;
                    start = getPosition();
                    continue;
                }
                if (thisByte == SPACE_QUOTED) {
                    space = SPACE_QUOTED;
                    start = getPosition();
                }
            } else if (space == SPACE_UNQUOTED) {
                if (endUnquoted.contains(thisByte)) {
                    space = SPACE_GLOBAL;
                    goBack();
                    return new Token(Token.UNQUOTED, text, start - 1, getPosition() - start + 1);
                }
            } else if (space == SPACE_QUOTED) {
                if (escape) {
                    escape = false;
                    continue;
                }
                if (thisByte == ESCAPE_CHAR) {
                    escape = true;
                    continue;
                }
                if (thisByte == SPACE_QUOTED) {
                    space = SPACE_GLOBAL;
                    return new Token(Token.STRING, text, start, getPosition() - start - 1);
                }
            }
        }
        return Token.EOF;
    }

    public int getLevel() {
        return level;
    }

    public void skipTo(int targetLevel) {
        //System.out.println("Level "+level+", Skip to " + targetLevel + "pos: "+ position);
        int space = SPACE_GLOBAL;
        boolean escape = false;
        while (level > targetLevel) {
            thisByte = text[position]; //out of array
            position++;
            if (space == SPACE_GLOBAL) {
                Token token = Token.tokens[thisByte];
                if (token != null) {
                    level += token.levelIncrement;
                } else if (thisByte == SPACE_QUOTED) {
                    space = SPACE_QUOTED;
                    escape = false;
                }
            } else {
                if (escape) {
                    escape = false;
                } else if (thisByte == ESCAPE_CHAR) {
                    escape = true;
                } else if (thisByte == SPACE_QUOTED) {
                    space = SPACE_GLOBAL;
                }
            }
        }
    }
}
