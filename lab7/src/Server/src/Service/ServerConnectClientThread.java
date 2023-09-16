package Service;

import Common.Message;
import Common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 该类的一个对象和某个客户端保持通信
 * 读取请求，读取客户端数据 -- Fixed thread pool
 * 对读取到的请求的处理 - ForkJoinPool
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    boolean run = true;
    private String userId;//链接到服务端的用户名,区分此socket来自于哪个用户

    public ServerConnectClientThread() {
    }

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }
    ServerConnectClientThread scct = null;
    /**
     * 可以发送或接收消息
     */
    @Override
    public void run() {


        while (true){
            ObjectInputStream ois = null;

            if (run) {
                try {
                    // 服务端和客户端保持通信，读取数据 -- Fixed thread pool
                    System.out.println("服务端和客户端 " + userId + " 保持通信，读取数据");
                    ois = new ObjectInputStream(socket.getInputStream());
                    Message message = (Message) ois.readObject();

//                    if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
//                        System.out.println(message.getSender() + " exit");
//                        //将这个客户端对应的线程从我们集合中删除
//                        ManageServerThread.removeThread(scct);
//                        socket.close();
//                    }

                } catch (Exception e) {
                    try {
                        if (ois != null){
                            ois.close();
                        }else {
                            System.out.println("退出");
                            System.exit(0);
                        }
                    } catch (IOException ex) {
                        e.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }else {

                System.out.println("终止线程成功");
                return;
            }
        }

    }
}
