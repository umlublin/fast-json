package org.json.simple.parser;

import java.util.Map;

public class JSONMapEntry implements Map.Entry<String, JSONParser> {
    private Lexer lexer;
    private String key;
    private JSONParser value;

    public JSONMapEntry(Lexer lexer) throws ParseException {
        this.lexer = lexer;
        Token nextToken = lexer.getNextToken();
        if (nextToken.type == Token.TYPE_COMMA) {
            nextToken = lexer.getNextToken(); //eat next
        }
        if (nextToken.type == Token.TYPE_RIGHT_BRACE) { //end of map
            throw new ParseException(ParseException.END_OF_MAP);
        }
        if (nextToken.type != Token.TYPE_STRING) {
            System.out.println(nextToken);
            throw new ParseException(ParseException.ERROR_KEY_NOT_STRING);
        }
        this.key = nextToken.getValue();
        nextToken = lexer.getNextToken();
        if (nextToken.type != Token.TYPE_COLON) {
            throw new ParseException(ParseException.COLON_EXPECTED);
        }
        value = new JSONParser(lexer);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public JSONParser getValue() {
        return value;
    }

    @Override
    public JSONParser setValue(JSONParser value) {
        return null;
    }

}
