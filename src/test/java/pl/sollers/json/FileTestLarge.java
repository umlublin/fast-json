/*
 * $Id: Test.java,v 1.1 2006/04/15 14:40:06 platform Exp $
 * Created on 2006-4-15
 */
package pl.sollers.json;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileTestLarge extends TestCase {

    public static final String FILE_JSON = "generated.json";

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