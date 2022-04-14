package org.json.simple.parser;

import java.util.Iterator;
import java.util.Map;

public class MapIterator implements Iterator<JSONMapEntry> {

    private final Lexer lexer;
    private JSONMapEntry entry;

    public MapIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            entry = new JSONMapEntry(lexer);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public JSONMapEntry next() {
        return entry;
    }
}
