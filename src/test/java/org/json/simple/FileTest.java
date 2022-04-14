/*
 * $Id: Test.java,v 1.1 2006/04/15 14:40:06 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple;

import com.dslplatform.json.DslJson;
import com.dslplatform.json.JsonReader;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import junit.framework.TestCase;
import org.json.simple.parser.JSONArrayParser;
import org.json.simple.parser.JSONMapParser;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileTest extends TestCase {

    public void testDecode() throws Exception {
        File file = new File("large-file.json");
        System.out.println(file.getAbsoluteFile());
        byte[] bytes = Files.readAllBytes(Path.of("large-file.json"));
        long start = System.nanoTime();
        JSONParser jsonParser = new JSONParser(bytes);
        final int[] count = {0};
        jsonParser.asArray().getIterator().forEachRemaining(it -> {
            System.out.println(it.toString());
//            count[0]++;
//            if (count[0] > 20) System.exit(1);
        });
//        System.out.println("Map:"+asArray.isMap());
//        System.out.println("asMap()");
//        JSONMapParser asMap = asArray.asMap();
//        System.out.println("przed getIterator");
//        asMap.getIterator().forEachRemaining(it -> {
//            JSONParser value = it.getValue();
//            if (value.isValue()) {
//                System.out.println("key: " + it.getKey() + " = " + it.getValue().getAsString());
//            } else value.skip();
//        });
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
    }

    public void testDecodeDSL() throws Exception {
        File file = new File("large-file.json");
        System.out.println(file.getAbsoluteFile());
        long start = System.nanoTime();
        DslJson<Object> json = new DslJson<Object>();
        JsonReader<Object> reader = json.newReader().process(new FileInputStream(file));
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
    }

    public void testDecodeJsoniter() throws Exception {
        //File file = new File("large-file.json");
        //System.out.println(file.getAbsoluteFile());
        byte[] bytes = Files.readAllBytes(Path.of("large-file.json"));
        long start = System.nanoTime();
        Any any = JsonIterator.deserialize(bytes);
        int z;
        for (Any element : any) {
            Map<String, Any> map = element.asMap();
            System.out.println(element.asMap());
            //System.out.println("."+z);
        }
//
        long stop = System.nanoTime();
        long x = stop - start;
        System.out.println(x/1000/1000 + " ms");
    }
}