package ztc.HTTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by voidhug on 16/8/19.
 */
public class Main {
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", "001");
        params.put("name", "Andy");
//        StringBuffer postResult = null;
//        postResult = HTTPUtils.getRequestData(params, "utf-8");
//        System.out.println(postResult);
        try {
            File file = new File("./src/ztc/HTTP/test.txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            System.out.println(HTTPUtils.dealResponseResult(fileInputStream));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
