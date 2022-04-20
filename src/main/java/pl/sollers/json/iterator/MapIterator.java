package pl.sollers.json.iterator;

import pl.sollers.json.*;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.token.Token;

import java.util.Iterator;

public class MapIterator implements Iterator<JSONEntry> {

    private final Lexer lexer;
    private JSONEntry entry;

    public MapIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            Token nextToken = lexer.getNextToken();
            if (nextToken.type == Token.TYPE_COMMA) {
                nextToken = lexer.getNextToken(); //eat next
            }
            if (nextToken.type == Token.RIGHT_BRACE) { //end of map
                throw new ParseException(ParseException.END_OF_MAP);
            }
            if (nextToken.type != Token.TYPE_STRING) {
                System.out.println(nextToken);
                throw new ParseException(ParseException.ERROR_KEY_NOT_STRING);
            }
            String key = nextToken.getValue();
            nextToken = lexer.getNextToken();
            if (nextToken.type != Token.TYPE_COLON) {
                throw new ParseException(ParseException.COLON_EXPECTED);
            }
            JSONParser value = new JSONParser(lexer);
            entry = new JSONEntry(key, value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public JSONEntry next() {
        return entry;
    }
}
