package ztc.IO.FileIO;

import java.io.*;

public class FileIO {
    private static final String FILE_PATH = "./src/ztc.IO/FileIO/呐喊.txt";

    /**
     * 以字节为单位读取文件内容
     * @param filePath 需要读取的文件路径
     */
    public static void readFileByByte(String filePath) {
        File file = new File(filePath);
        InputStream ins = null;
        try {
            ins = new FileInputStream(file);
            int temp;
            while ((temp = ins.read()) != -1) {
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readFileByBufferedByte(String filePath) {
        File file = new File(filePath);
        InputStream inStream = null;
        BufferedInputStream bis = null;
        try {
            inStream = new FileInputStream(file);
            bis = new BufferedInputStream(inStream);
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = bis.read(bytes)) != -1) {
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 以字符为单位读取文件内容
     * @param filePath 需要读取的文件路径
     */
    public static void readFileByCharacter(String filePath) {
        File file  = new File(filePath);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            int temp;
            while ((temp = reader.read()) != -1) {
                if (((char) temp) != '\r') {
                    System.out.print((char) temp);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readFileByLine(String filePath) {
        File file = new File(filePath);
        BufferedReader buf = null;
        try {
            buf = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = buf.readLine()) != null) {
                System.out.println(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static long getTime() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        long time1 = getTime();
        // readFileByByte(FILE_PATH);
//         readFileByCharacter(FILE_PATH);
//         readFileByLine(FILE_PATH);
//         readFileByBufferedByte(FILE_PATH);
        long time2 = getTime();
        System.out.println(time2 - time1);
    }
}
