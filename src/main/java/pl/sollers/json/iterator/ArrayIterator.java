package pl.sollers.json.iterator;

import pl.sollers.json.JSONParser;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.ParseException;

import java.util.Iterator;

public class ArrayIterator implements Iterator<JSONParser> {
    private Lexer lexer;
    private JSONParser entry;
    private Long count = 0L;

    public ArrayIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            if (entry != null) {
                entry.skip();
            }
            entry = new JSONParser(lexer, count.toString());
            if (entry.endOfIteration()) {
                return false;
            }
            count++;
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
