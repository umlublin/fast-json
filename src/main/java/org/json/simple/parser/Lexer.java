package org.json.simple.parser;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class Lexer {
    private static final char ESCAPE_CHAR = '\\';
    private static final char DOUBLE_QUOTES = '"';
    private static final char UNQUOTED = '-';
    public static final char GLOBAL = 0;
    private final int length;
    private byte[] text;
    private byte space;
    private byte thisByte;
    private int position;
    private int level = 0;
    private Map<Byte, Boolean> tokens = Map.of(Token.TYPE_LEFT_BRACE, true, Token.TYPE_LEFT_SQUARE, true,
            Token.TYPE_RIGHT_BRACE, true, Token.TYPE_RIGHT_SQUARE, true, Token.TYPE_COLON, true,
            Token.TYPE_COMMA, true);
    private Set<Byte> startUnquoted = Set.of((byte) '-', (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5', (byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 't', (byte) 'f', (byte) 'n');
    private Set<Byte> endUnquoted = Set.of((byte) ',', (byte) ']', (byte) '}', (byte) ' ', (byte) '\t', (byte) '\r', (byte) '\n', (byte) '\b', (byte) '\f');

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
            if (space == GLOBAL) {
                if (tokens.containsKey(thisByte)) {
                    Token token = new Token(thisByte);
                    if (token.isOpening()) level++;
                    if (token.isClosing()) level--;
                    return token;
                }
                if (startUnquoted.contains(thisByte)) {
                    space = UNQUOTED;
                    start = getPosition();
                    continue;
                }
                if (thisByte == DOUBLE_QUOTES) {
                    space = DOUBLE_QUOTES;
                    start = getPosition();
                }
            } else if (space == UNQUOTED) {
                if (endUnquoted.contains(thisByte)) {
                    space = GLOBAL;
                    goBack();
                    return new Token(Token.TYPE_UNQUOTED, text, start-1, getPosition() - start + 1);
                }
            } else if (space == DOUBLE_QUOTES) {
                if (escape) {
                    escape = false;
                    continue;
                }
                if (thisByte == ESCAPE_CHAR) {
                    escape = true;
                    continue;
                }
                if (thisByte == DOUBLE_QUOTES) {
                    space = GLOBAL;
                    return new Token(Token.TYPE_STRING, text, start, getPosition() - start - 1);
                }
            }
        }
        return new Token(Token.TYPE_EOF);
    }

    public int getLevel() {
        return level;
    }
}
