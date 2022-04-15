package pl.sollers.json.iterator;

import pl.sollers.json.JSONEntry;
import pl.sollers.json.JSONParser;
import pl.sollers.json.token.Lexer;
import pl.sollers.json.ParseException;

import java.util.Iterator;

public class ArrayIterator implements Iterator<JSONEntry> {
    private Lexer lexer;
    private JSONEntry entry;
    private Long count = 0L;

    public ArrayIterator(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        try {
            if (entry != null) {
                entry.getValue().skip();
            }
            entry = new JSONEntry(count.toString(), new JSONParser(lexer));
            count++;
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
