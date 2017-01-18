package ztc.hitpr;

import org.apache.commons.codec.binary.Base64;
import ztc.utils.Code;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class Test {
    public static void main(String[] args) {
        StringBuilder buf = new StringBuilder();
        try {
            buf.append("yhdh=" + URLEncoder.encode(Code.base64Encode("xch"), "UTF-8") + "&");
            buf.append("pwd=" + URLEncoder.encode(Code.base64Encode("pwd"), "UTF-8"));
            byte[] data = buf.toString().getBytes("UTF-8");
            try {
                URL url = new URL("http://222.171.203.220:8090/route/smarthome.do?method=dologinforsmarthome");
                try {
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(30000);
                    conn.setDoInput(true); // 打开输入流，以便从服务器获取数据。
                    conn.setDoOutput(true); // 打开输出流，以便向服务器提交数据。
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(data);
                    if (conn.getResponseCode() == 200) {
                        System.out.println("登录成功");
                    } else {
                        System.out.println("登录失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder buf1 = new StringBuilder();
        try {
            buf1.append("yhdh=" + URLEncoder.encode(Code.base64Encode("xch"), "UTF-8") + "&");
            buf1.append("pwd=" + URLEncoder.encode(Code.base64Encode("888888"), "UTF-8") + "&");
            buf1.append("routeid=" + URLEncoder.encode(Code.base64Encode("0014223585446888"), "UTF-8") + "&");
            buf1.append("value=" + URLEncoder.encode(new String(new Base64().encode(code())), "UTF-8"));
            System.out.println(Code.base64Encode("xch"));
            System.out.println(Code.base64Encode("888888"));
            System.out.println(Code.base64Encode("0014223585446888"));
            System.out.println(new String(new Base64().encode(code())));
            byte[] data = buf1.toString().getBytes();
            try {
                URL url = new URL("http://222.171.203.220:8090/route/smarthome.do?method=ctrlforsmarthome");
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(30000);
                    conn.setDoInput(true); // 打开输入流，以便从服务器获取数据。
                    conn.setDoOutput(true); // 打开输出流，以便向服务器提交数据。
                    conn.setRequestMethod("POST");
                    OutputStream out = conn.getOutputStream();
                    out.write(data);
                    out.flush();
                    out.close();
                    InputStream is = conn.getInputStream();
                    String str = new String(getBytesByInputStream(is));
                    System.out.println(Code.base64Decode(str));
//                    System.out.println(new String(data));
//                    System.out.println(Code.base64DecodeToString(new String(data)));
                    if (conn.getResponseCode() == 200) {
                        System.out.println("登录成功");
                    } else {
                        System.out.println("登录失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    conn.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static byte[] code() {
//        String input = "31 00 00 00 81 0F AA 0D 15 01 01 47 41 8A 00 00 00 00 00 FB FF";
        String input1 = "31 00 00 00 81 0F AA 0D 15 01 01 47 41 85 1B 02 02 A6 7E 00 FF"; // 开关一 关
//        String input2 = "31 00 00 00 81 0F AA 0D 15 01 01 47 41 85 1B 02 02 A6 7E 00 FF";
        StringBuilder buf = new StringBuilder();
        String[] array = input1.split(" ");

        for (int i = 0; i < array.length; i++) {
            buf.append((char) Integer.parseInt(array[i], 16));
        }
        char data[] = {0x31,0x00,0x00,0x00,0x81,0x0F,0xAA,0x0D,0x15,0x01,0x01,0x47,0x41,0x85,0x1B,0x02,0x02,0xA6,0x7E,0x01,0xFF};
        return buf.toString().getBytes();
    }
//    private static String getBytesByInputStream(InputStream is) {
//        String str = "";
//        BufferedReader in = new BufferedReader(new InputStreamReader(is));
//        StringBuffer buffer = new StringBuffer();
//        String line = "";
//        try {
//            while ((line = in.readLine()) != null) {
//                buffer.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        str = buffer.toString();
//        return str;
//    }

    private static byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0 ,length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
}
