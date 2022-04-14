package org.json.simple.parser;

import java.util.Iterator;

public class JSONArrayParser extends JSONParser {

    public JSONArrayParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Iterator<JSONParser> getIterator() {
        return new ArrayIterator(lexer);
    }
}
