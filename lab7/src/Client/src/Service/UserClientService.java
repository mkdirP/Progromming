package Service;

import Common.Message;
import Common.MessageType;
import Common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * 该类完成用户登录验证
 */
public class UserClientService {

    //可能我们在其他地方需要使用User信息和Socket，所以设置为一个属性
    private User u = new User();
    private Socket socket;
    int port = 19600;

    //根据用户名和密码到服务器验证该用户是否合法
    public boolean checkUser(String userId, String pwd){

        // 如果进入if则说明登陆成功
        boolean b = false;
        // 创建User对象
        u.setUserId(userId);
        u.setPasswd(pwd);

        // 连接到服务器
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), port);
            // 得到序列化对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);// 将User对象发送给服务端

            //读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();


            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                //如果成功则启动一个线程，让此线程持有socket，跟服务器端进行通讯
                // 创建一个和服务器端保持通信的线程 - 类 ClientConnectServerThread
                //管理客户端连接到服务器端的线程,将线程放入到集合管理(可能有多个客户端) -> 线程池

                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                //启动客户端线程
                ccst.start();
                ManageClientThread.addTread(userId,ccst);

                b = true;

            }if (ms.getMesType().equals(MessageType.MESSAGE_REGISTER_SUCCEED)){
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                ManageClientThread.addTread(userId,ccst);
                b = true;
            }
            else {
                // 如果登录失败，则不能启动和服务器通信的线程，关闭socket
//                socket.close();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return b;

    }
    /**
     * 退出客户端并给服务端发送退出系统的message对象
     */
    public void logout(){
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());//指定是哪个客户端关闭
        ClientConnectServerThread ccst = null;
        
        //发送message
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            System.out.println("\""+u.getUserId() + "\" exit");
//            ManageClientThread.removeThread();
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
