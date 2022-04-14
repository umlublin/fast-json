package org.json.simple.parser;

import java.util.Iterator;

public class JSONMapParser extends JSONParser {

    public JSONMapParser(Lexer lexer) {
        this.lexer = lexer;
    }

    public Iterator<JSONMapEntry> getIterator() {
        return new MapIterator(lexer);
    }
}
