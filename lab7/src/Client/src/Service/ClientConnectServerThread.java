package Service;

import Common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Request
 * 创建一个和服务器端保持通信的线程
 * 接收服务器端数据 -- Fixed thread pool
 *      该线程池适用于为了满足管理资源的需求，而需要限制当前线程数量的应用场景，
 *      它适用于负载比较重的服务器。没有空闲线程时, 新的任务将被暂存在一个任务队列中。
 */
public class ClientConnectServerThread extends Thread{
    // 该线程需要持有Socket才能和服务器端通讯
    private Socket socket;
    private Message message;
    boolean run = true;

    // 构造器可以接收一个Socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // 因为需要在后台和服务器通信，因此我们while循环
        while (true){

            if (run) {
                ObjectInputStream ois = null;
                try {
                    System.out.println("客户端线程，等待从读取服务器端发送的消息");
                    ois = new ObjectInputStream(socket.getInputStream());
                    // 如果服务器没有发送Message对象，线程会阻塞在这里
                    Message message = (Message) ois.readObject();
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
