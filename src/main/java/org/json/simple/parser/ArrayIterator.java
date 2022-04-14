package org.json.simple.parser;

import java.util.Iterator;

public class ArrayIterator implements Iterator<JSONParser> {
    private Lexer lexer;
    private JSONParser entry;

    public ArrayIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            if (entry != null) {
                entry.skip();
            }
            entry = new JSONParser(lexer);
            //System.out.println("hasnext " + entry);
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
