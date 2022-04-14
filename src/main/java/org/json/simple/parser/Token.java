/*
 * $Id: Yytoken.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple.parser;

public class Token {
    public static final byte TYPE_UNQUOTED = 0;
    public static final byte TYPE_STRING = 1;
    public static final byte TYPE_LEFT_BRACE = '{';
    public static final byte TYPE_RIGHT_BRACE = '}';
    public static final byte TYPE_LEFT_SQUARE = '[';
    public static final byte TYPE_RIGHT_SQUARE = ']';
    public static final byte TYPE_COMMA = ',';
    public static final byte TYPE_COLON = ':';
    public static final byte TYPE_EOF = -1;//end of file

    public int type = 0;
    private byte[] buffer;
    private int start;
    private int length;

    public Token(int type) {
        this.type = type;
    }

    public Token(int type, byte[] buffer, int start, int length) {
        this.type = type;
        this.buffer = buffer;
        this.start = start;
        this.length = length;
    }

    public boolean isOpening() {
        return (type == TYPE_LEFT_BRACE || type == TYPE_LEFT_SQUARE);
    }

    public boolean isClosing() {
        return (type == TYPE_RIGHT_BRACE || type == TYPE_RIGHT_SQUARE);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        switch (type) {
            case TYPE_UNQUOTED:
                sb.append("UNQUOTED(").append(getValue()).append(")");
                break;
            case TYPE_STRING:
                sb.append("STRING(").append(getValue()).append(")");
                break;
            case TYPE_LEFT_BRACE:
                sb.append("LEFT BRACE({)");
                break;
            case TYPE_RIGHT_BRACE:
                sb.append("RIGHT BRACE(})");
                break;
            case TYPE_LEFT_SQUARE:
                sb.append("LEFT SQUARE([)");
                break;
            case TYPE_RIGHT_SQUARE:
                sb.append("RIGHT SQUARE(])");
                break;
            case TYPE_COMMA:
                sb.append("COMMA(,)");
                break;
            case TYPE_COLON:
                sb.append("COLON(:)");
                break;
            case TYPE_EOF:
                sb.append("END OF FILE");
                break;
        }
        return sb.toString();
    }

    public String getValue() {
        return new String(buffer, start, length);
    }
}
