package pl.sollers.json.iterator;

import pl.sollers.json.*;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.token.Token;

import java.util.Iterator;

public class MapIterator implements Iterator<JSONParser> {

    private final Lexer lexer;
    private JSONParser entry;

    public MapIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            Token nextToken = lexer.getNextToken();
            if (nextToken == Token.COMMA) {
                nextToken = lexer.getNextToken(); //eat next
            }
            if (nextToken == Token.RIGHT_BRACE) { //end of map
                throw new ParseException(ParseException.END_OF_MAP);
            }
            if (!nextToken.isString()) {
                System.out.println(nextToken);
                throw new ParseException(ParseException.ERROR_KEY_NOT_STRING);
            }
            String key = nextToken.getValue();
            nextToken = lexer.getNextToken();
            if (nextToken != Token.COLON) {
                throw new ParseException(ParseException.COLON_EXPECTED);
            }
            entry = new JSONParser(lexer, key);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public JSONParser next() {
        return entry;
    }
}
