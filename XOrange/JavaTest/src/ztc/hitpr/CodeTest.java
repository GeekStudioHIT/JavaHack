package ztc.hitpr;

import org.apache.commons.codec.binary.Base64;

public class CodeTest {
    public static void main(String[] args) {
        Base64 base64 = new Base64();
        String hex = "";
        byte[] result = null;
        hex = ("31 00 00 00 81 0F AA 0D 15 01 01 47 41 85 1B 02 02 A6 7E 01 FF").replace(" ", "");
//        hex = ("31 00 00 00 81 0F AA 0D 15 01 01 47 41 8A 00 00 00 00 00 FB FF").replace(" ", "");
        int len = (hex.length() / 2);
        result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        String vv= base64.encodeToString(result); //你给我发的
        System.out.println(vv);
    }
    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
}
