package Service;

import Common.Message;
import Common.MessageType;
import Common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将注册的用户发送给服务器端 -- FixedThreadPool
 */
public class RegisterUser {
    private User u = new User();
    private Socket socket;

    public void registerUser(String userId, String pwd){
        u.setUserId(userId);
        u.setPasswd(pwd);
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 19550);
            // 得到序列化对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);// 将User对象发送给服务端

            //读取从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMesType().equals(MessageType.MESSAGE_REGISTER_SUCCEED)){
                ExecutorService ftp = Executors.newFixedThreadPool(10);
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ftp.submit(ccst);
                ftp.shutdown();
            }else {
                socket.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
