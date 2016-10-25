package ztc.GBN;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;

public class GBNClient {
    public static void main(String[] args) throws Exception {
        // 服务器 IP 地址
        InetAddress serverAddress = InetAddress.getByName("localhost");
        // 创建 UDP 的 socket
        DatagramSocket clientSocket = new DatagramSocket(9999);
        byte[] sendData;
        int end_ack;
        Timer[] timers = new Timer[10];
        System.out.println("丢包概率为 0.3, 在服务器端设定。");
        System.out.println("重传定时器为 3 秒, 在客户端设定, 逾期则 GoBack 重新发送。");
        System.out.println("客户端即将发送 10 个数据包。");
        System.out.println("客户端发送数据 0 —— 9");
        for (int i = 0; i < 10; i++) {
            sendData = (i + "seq").getBytes(); // 将发送的字符串转换成字节数组,才能在互联网中传输。
            // 新建数据包, 指明发送数据内容, 发送长度, 服务器地址, 服务器端口号。
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8888);
            clientSocket.send(sendPacket);
            timers[i] = new Timer(3000, new DelayActionListener(clientSocket, i, timers));
            timers[i].start();
        }

        while (true){
            byte[] recvData = new byte[100];
            DatagramPacket recvPacket = new DatagramPacket(recvData, recvData.length);
            clientSocket.receive(recvPacket);
            int ack_seq = new String(recvPacket.getData()).charAt(3) -'0';
            System.out.println("客户端接收 ack=" + ack_seq);
            timers[ack_seq].stop();
            if (ack_seq == 9){
                System.out.println("全部数据已被发送成功！");
                return;
            }
        }
    }
}

class DelayActionListener implements ActionListener {
    DatagramSocket clinetSocket;
    int end_ack;
    Timer[] timers;

    public DelayActionListener(DatagramSocket clinetSocket, int end_ack, Timer[] timers) {
        this.clinetSocket = clinetSocket;
        this.end_ack = end_ack;
        this.timers = timers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("客户端重传数据 " + end_ack + "--9");
        for (int i = end_ack; i < 10; i++) {
            byte[] sendData;
            InetAddress serverAddress = null;
            try {
                serverAddress = InetAddress.getByName("localhost");
                sendData = (i + "seq").getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 8888);
                clinetSocket.send(sendPacket);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            timers[i].stop();
            timers[i].start();
        }
    }
}
