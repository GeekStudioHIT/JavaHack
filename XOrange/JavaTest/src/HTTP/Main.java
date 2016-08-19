package HTTP;

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
        StringBuffer postResult = null;
        postResult = HTTPUtils.getRequestData(params, "utf-8");
        System.out.println(postResult);
    }
}
