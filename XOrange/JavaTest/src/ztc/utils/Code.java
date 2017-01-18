package ztc.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Code {
    public static String base64Encode(String str) {
        return new String(new Base64().encode(str.getBytes()));
    }

    public static String base64DecodeToString(String str) {
        return new String(new Base64().decode(str.getBytes()));
    }

    public static byte[] base64Decode(String str) {
        return new Base64().decode(str);
    }

    public static void main(String[] args) {
//        String str = "eyJycyI6IjEiLCJlcnIiOiIiLCJ2YWx1ZSI6eyJyZXZhbHVlIjoiTVFBQUFJRVFxZzRWQVFIcnlvc2JBZ0ttZmdFQS93PT1cclxuIn19";
//        String str = "eyJycyI6IjEiLCJlcnIiOiIiLCJ2YWx1ZSI6eyJyZXZhbHVlIjoiTVFBQUFJRVBxZzBWQVFIcnlvWWJBZ0ttZmdIL1xyXG4ifX0=";
        String str = "eyJycyI6IjEiLCJlcnIiOiIiLCJ2YWx1ZSI6eyJyZXZhbHVlIjoiTVFBQUFJRVBxZzBWQVFIcnlvWWJBZ0ttZmdEL1xyXG4ifX0=";
        String[] array = hitprdecode(str);
        for (String s : array) {
            System.out.print(s + " ");
        }
    }

    public static int getUnsignedByte (byte data){
        return data & 0x0FF ;
    }

    public static Map<?, ?> jsonStrToMap(String jsonStr) {
        Map<?, ?> objectMap = null;
        Gson gson = new Gson();
        Type type = new TypeToken<Map<?, ?>>() {}.getType();
        objectMap = gson.fromJson(jsonStr, type);
        return objectMap;
    }

    public static String[] hitprdecode(String str) {
        HashMap<?, ?> map = (HashMap<?, ?>) jsonStrToMap(base64DecodeToString(str));
        str = map.get("value").toString().replace("{revalue=", "").replace("\r\n}", "");
        byte[] bytes = base64Decode(str);
        String[] array = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            array[i] = Integer.toHexString(getUnsignedByte(bytes[i]));
        }
        return array;
    }
}
