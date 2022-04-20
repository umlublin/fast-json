package pl.sollers.json;


import pl.sollers.json.iterator.ArrayIterator;
import pl.sollers.json.iterator.MapIterator;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.token.Token;

import java.util.Iterator;

public class JSONParser implements Iterable<JSONEntry> {

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
            if (token == Token.EOF) {
                throw new ParseException(ParseException.END_OF_FILE);
            }
        } while (token == Token.COMMA);
    }

    public JSONParser() {
    }

    public void skip() {
        lexer.skipTo(level);
    }

    @Override
    public String toString() {
        return "JSONParser{" +
                "level=" + level + ", pos=" + lexer.getPosition() + ", token=" + token + '}';
    }


    public boolean isArray() {
        return token == Token.LEFT_SQUARE;
    }

    public boolean isMap() {
        return token == Token.LEFT_BRACE;
    }

    @Override
    public Iterator<JSONEntry> iterator() {
        if (isMap())
            return new MapIterator(lexer);
        if (isArray())
            return new ArrayIterator(lexer);
        return null;
    }

}
