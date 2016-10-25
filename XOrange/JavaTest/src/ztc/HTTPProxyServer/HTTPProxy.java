package ztc.HTTPProxyServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;

public class HTTPProxy extends Thread {
    public static int CONNECT_RETRIES = 5; // 在放弃之前尝试连接远程主机的次数。
    public static int CONNECT_PAUSE = 5; // 在两次连接尝试之间的暂停时间。
    public static int TIMEOUT = 50; // 等待 Socket 输入的等待时间。
    public static int BUFSIZE = 1024; // Socket 输入的缓冲大小。
    public static boolean logging = false; // 是否要求代理服务器在日志中记录所有已传输的数据。
    public static OutputStream log = null; // 一个 OutputStream 对象, 默认日志例程将向 OutputStream 对象输出日志信息。
    // 传入数据用的 socket。
    protected Socket socket;
    // 上级代理服务器, 可选。
    private static String parent = null;
    private static int parentPort = -1;
    /**
     * 用来把一个代理服务器链接到另一个代理服务器（需要指明另一个服务器的名称和端口）。
     *
     * @param name
     * @param pport
     */
    public static void setParentProxy(String name, int pport) {
        parent = name;
        parentPort = pport;
    }

    // 在给定 Socket 上创建一个代理线程。
    public HTTPProxy(Socket s) {
        socket = s;
        start();
    }

    public void writeLog(int c, boolean browser) throws IOException {
        log.write(c);
    }

    public void writeLog(byte[] bytes, int offset, int len, boolean browser) throws IOException {
        for (int i = 0; i < len; i++) {
            writeLog((int) bytes[offset + i], browser);
        }
    }

    // 默认情况下, 日志信息输出到
    // 标准输出设备
    // 派生类可以覆盖它
    public String processHostName(String url, String host, int port, Socket sock) {
        DateFormat cal = DateFormat.getDateInstance();
        System.out.println(cal.format(new java.util.Date()) + " - " + url + " " + sock.getInetAddress() + "\n");
        return host;
    }

    public void run() {
        String line;
        String host;
        int port = 80;
        Socket outbound = null;
        try {
            socket.setSoTimeout(TIMEOUT);
            try {
                InputStream is = socket.getInputStream();
//                byte b[] = new byte[2000];
//                is.read(b);
//                System.out.print(new String(b));
                OutputStream os = null;
                // 获取请求行的内容
                line = "";
                host = "";
                int state = 0;
                boolean space;
                while (true) {
                    int c = is.read();
                    if (c == -1) {
                        break;
                    }
                    if (logging) {
                        writeLog(c, true);
                    }
                    space = Character.isWhitespace((char) c);
                    switch (state) {
                        case 0:
                            if (space) {
                                continue;
                            }
                            state = 1;
                        case 1:
                            if (space) {
                                state = 2;
                                continue;
                            }
                            line = line + (char) c;
                            break;
                        case 2:
                            if (space) {
                                continue; // 跳过多个空白符。
                            }
                            state = 3;
                        case 3:
                            if (space) {
                                state = 4;
                                // 只分析主机 host 名称。
                                String host0 = host;
                                int n;
                                n = host.indexOf("//");
                                if (n != -1) {
                                    host = host.substring(n + 2);
                                }
                                n = host.indexOf('/');
                                if (n != -1) {
                                    host = host.substring(0, n);
                                }
                                n = host.indexOf(":");
                                if (n != -1) {
                                    port = Integer.parseInt(host.substring(n + 1));
                                    host = host.substring(0, n);
                                }
                                host = processHostName(host0, host, port, socket);
                                if (parent != null) {
                                    host = parent;
                                    port = parentPort;
                                }
                                int retry = CONNECT_RETRIES;
                                while (retry-- != 0) {
                                    System.out.println(host + "  " + port);
                                    outbound = new Socket(host, port);
                                    break;
                                }
                                try {
                                    Thread.sleep(CONNECT_PAUSE);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if (outbound == null) {
                                    break;
                                }
                                outbound.setSoTimeout(TIMEOUT);
                                os = outbound.getOutputStream();
                                os.write(line.getBytes());
                                os.write(' ');
                                os.write(host0.getBytes());
                                os.write(' ');

                                pipe(is, outbound.getInputStream(), os, socket.getOutputStream());
                                break;
                            }
                            host = host + (char) c;
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outbound.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void pipe(InputStream is0, InputStream is1, OutputStream os0,  OutputStream os1) throws IOException {
        try {
            int ir;
            byte bytes[]=new byte[BUFSIZE];
            while (true) {
                try {
                    if ((ir = is0.read(bytes)) > 0) {
                        os0.write(bytes, 0, ir);
                        if (logging) {
                            writeLog(bytes, 0, ir, true);
                        }
                    } else if (ir < 0) {
                        break;
                    }
                } catch (InterruptedIOException e) {

                }
                try {
                    if ((ir = is1.read(bytes)) > 0) {
                        os1.write(bytes, 0, ir);
                        if (logging) {
                            writeLog(bytes, 0, ir, false);
                        }
                    } else if (ir < 0) {
                        break;
                    }
                } catch (InterruptedIOException e) {

                }
            }
        } catch (Exception e0) {
            System.out.println("Pipe异常: " + e0);
        }
    }

    static public void startProxy(int port, Class clobj) {
        ServerSocket ssock;
        Socket sock;
        try {
            ssock = new ServerSocket(port); // 绑定端口。
            while (true) {
                Class[] sarg = new Class[1];
                Object[] arg = new Object[1];
                sarg[0] = Socket.class;
                try {
                    Constructor cons = clobj.getDeclaredConstructor(sarg);
                    arg[0] = ssock.accept(); // 从请求队列中取出一个客户的连接请求, 然后创建与客户连接的 Socket 对象。
                    cons.newInstance(arg); // 创建 HTTPProxy 或其派生类的实例。
                } catch (Exception e) {
                    Socket esock = (Socket) arg[0];
                    try {
                        esock.close();
                    } catch (Exception ec) {

                    }
                }
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        System.out.println("在端口 8080 启动代理服务器\n");
        HTTPProxy.log = System.out;
        HTTPProxy.logging = false;
        HTTPProxy.startProxy(8080, HTTPProxy.class);
    }
}
