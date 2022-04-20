package pl.sollers.json;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import junit.framework.TestCase;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileTest extends TestCase {

    public static final String FILE_JSON = "large-file.json";

    public void testDecode() throws Exception {
        byte[] bytes = Files.readAllBytes(Path.of(FILE_JSON));
        long start = System.nanoTime();
        JSONParser jsonParser = new JSONParser(bytes);
        int count=0;
        for (JSONEntry e : jsonParser) {
            count++;
            //System.out.println(e.toString());
        }
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
        System.out.println(count);
    }

    public void testDecodeJsoniter() throws Exception {
        byte[] bytes = Files.readAllBytes(Path.of(FILE_JSON));
        long start = System.nanoTime();
        Any any = JsonIterator.deserialize(bytes);
        int count = 0;
        for (Any element : any) {
            count++;
        }
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
        System.out.println(count);
    }
}