package Service;

import Commands.Command;
import Common.Message;
import Common.MessageType;
import Common.User;
import Common.ValidUsers;
import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * 这是服务器，在监听19500，等待客户端的连接，并保持通信
 */
public class Server {
    private ServerSocket ss = null;
    Command c = new Command();
    String host = "127.0.0.1";
    int port = 19600;
    UserData ud = new UserData();

    public Server() {
        // 端口可以写在配置文件中
        try {
            System.out.println("The server is listening on port: "+port);
            ss = new ServerSocket(port);
            boolean flug = false;

            // 当和某个客户端建立连接后，会继续监听，因为可能不止一个客户端
            while (true) {

                Socket socket = ss.accept();//如果没有客户端连接，就会阻塞在这里
                // 通过socket读取客户端发送的user对象，得到socket关联的对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                // 将Message对象回复,需要输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User u = (User) ois.readObject();
                c.getUser(u);

                // 构建一个Message对象，准备回复客户端
                Message message = new Message();

                //验证!!!!!!!!!!!!
                if (ud.UserCheckData(u)) {//checkUser(u.getUserId(), u.getPasswd())
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    // 将Message对象回复
                    oos.writeObject(message);
                    // 将线程对象放到线程池中进行管理
                    // 创建一个线程，和客户端通信，该线程需要持有socket对象

                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
                    // 启动该线程
                    scct.start();
                    ManageServerThread.addClientThread(u.getUserId(), scct);
                    Socket socket1 = ss.accept();
                    do {
                        // 读取序列化后的命令
                        ObjectInputStream ois1 = new ObjectInputStream(socket1.getInputStream());
                        SerializableComArg sca = (SerializableComArg) ois1.readObject();
                        String s1 = sca.getComArg();

                        String[] commands = {"help", "info", "show", "clear",
                                "exit", "history"};
                        for (int i = 0; i < commands.length; i++) {
                            if (s1.equals(commands[i])) {
                                flug = true;
                                System.out.println("You entered the command: " + commands[i]);
                            }
                        }
                        String[] commands2 = {"add", "update_id", "remove_by_id", "execute_script", "remove_greater",
                                "remove_lower", "remove_all_by_genre", "filter_contains_name", "filter_starts_with_name"};
                        for (int i = 0; i < commands2.length; i++) {
                            if (s1.split(" ")[0].equals(commands2[i])) {
                                flug = true;
                                System.out.println("You entered the command: " + commands2[i]);
                            }
                        }

                        if (s1.equals("exit")) {
                            System.out.println("Disconnect and end the program");
                            socket.close();
                            System.exit(0);
                        }

                        if (!flug) {
                            System.out.println("Wrong input value!");
                        }

                        try {
                            c.exec(s1);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } while (true);
//                    validUsers.put(validUsers.size(), u);
                } else {
                    System.out.println("User \"" + u.getUserId() + "\" authentication failed");

                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);

//
                    Socket socket2 = ss.accept();
//                    ForkJoinPool fjp = new ForkJoinPool();
//                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, u.getUserId());
//                    fjp.submit(scct);
//                    fjp.shutdown();

                    ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());
                    SerializableComArg sca = (SerializableComArg) ois2.readObject();
                    String s2 = sca.getComArg();

                    switch (s2) {
                        //用户重新登录
                        case "e":
                            System.out.println("User will re-enter username or password");
                            break;
                        //用户注册
                        case "r":
                            //获取客户端传入的user对象
                            Socket socket3 = ss.accept();
                            ObjectInputStream ois3 = new ObjectInputStream(socket3.getInputStream());
                            User user = (User) ois3.readObject();

                            ud.userPutData(user);
                            SHA.putData(user);

                            System.out.println("User \"" + user.getUserId() +"\" register");
                            break;
                    }


//                    ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
//                    SerializableComArg sca = (SerializableComArg) ois.readObject();
//                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
//                    oos.writeObject(message);
//                    socket.close();

                }
            }

        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            // 如果服务端退出了while循环，说明服务器端不在监听，因此关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
