/*
 * $Id: JSONParser.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple.parser;


/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 *
 * @author FangYidong<fangyidong @ yahoo.com.cn>
 */
public class JSONParser {

    protected int level;
    protected Lexer lexer;
    Token token = null;

    public JSONParser(byte[] bytes) throws ParseException {
        this(new Lexer(bytes));
    }

    public JSONParser(Lexer lexer) throws ParseException {
        this.lexer = lexer;
        //System.out.println("LVL:"+lexer.getLevel());
        level = lexer.getLevel();
        do {
            this.token = lexer.getNextToken();
            if (token.type == Token.TYPE_EOF) {
                throw new ParseException(ParseException.END_OF_FILE);
            }
        } while (token.type == Token.TYPE_COMMA);
    }

    public JSONParser() {
    }

    public void skip() {
        while(lexer.getLevel() > level) {
            try {
                lexer.getNextToken();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isArray() {
        return token.type == Token.TYPE_LEFT_SQUARE;
    }

    public JSONArrayParser asArray() throws ParseException {
        if (isArray())
            return new JSONArrayParser(lexer);
        return null;
    }

    public boolean isMap() {
        return token.type == Token.TYPE_LEFT_BRACE;
    }

    public JSONMapParser asMap() throws ParseException {
        if (isMap())
            return new JSONMapParser(lexer);
        return null;
    }

    public boolean isValue() {
        return token.type == Token.TYPE_STRING || token.type == Token.TYPE_UNQUOTED;
    }

    public String getAsString() {
        if (isValue()) {
            return token.getValue();
        }
        return null;
    }

    public int getPosition() {
        return lexer.getPosition();
    }

    @Override
    public String toString() {
        return "JSONParser{" +
                "level=" + level + ", pos=" + lexer.getPosition() + ", token=" + token + '}';
    }
}
