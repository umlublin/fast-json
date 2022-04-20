package pl.sollers.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import junit.framework.TestCase;

import java.nio.file.Files;
import java.nio.file.Path;

public class ParseTest extends TestCase {


    public void testDecodeMap() throws Exception {
        String input = "[{'id':1},{'id':2}]".replace("'", "\"");
        long start = System.nanoTime();
        JSONParser jsonParser = new JSONParser(input.getBytes());
        int count=0;
        for (JSONParser element : jsonParser) {
            count++;
            for (JSONParser element2 : element) {
                System.out.println(element2);
            }
            System.out.println(element.toString());
        }
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
        System.out.println(count);
    }

    public void testDecodeArray() throws Exception {
        String input = "[['test'],['test2']".replace("'", "\"");
        long start = System.nanoTime();
        JSONParser jsonParser = new JSONParser(input.getBytes());
        int count=0;
        for (JSONParser element : jsonParser) {
            count++;
            System.out.println(element.toString());
            for (JSONParser element2 : element) {
                System.out.println(element2);
            }
        }
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
        System.out.println(count);
    }

}