package Service;

import Commands.Command;
import Common.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * 客户端引导程序界面
 */
public class View {
    //控制是否显示主菜单
    private boolean loop = true;
    private String key = ""; //接收用户键盘输入
    // 该对象用于登录服务器或注册用户
    private UserClientService ucs = new UserClientService();
    private RegisterUser ru = new RegisterUser();
    String host = "127.0.0.1";
    int port = 19600;
    Command c = new Command();

    //显示主菜单
    public void mainMenu() {
        System.out.println("连接到主机：" + host + " ，端口号：" + port);

        while (loop) {
            System.out.println("Welcome to the Network Communication System!");
            System.out.println("    1 - log in to the system;\n    9 - log out of the system\n    r - register first ");
            System.out.print("Please enter the options: ");
            key = Utility.readString(1);

            switch (key) {
                case "1":
                    System.out.print("Please enter user name: ");
                    String userId = Utility.readString(50);//限制字符在50个之内
                    System.out.print("Please enter password: ");
                    String pwd = Utility.readString(50);
                    User u1 = new User(userId,pwd);
                    c.getUser(u1);

                    //到服务器端验证该用户是否合法 -- 类UserClientService
                    if (ucs.checkUser(userId, pwd)) {
                        System.out.println("Hello, my dear user \"" + userId + "\" login!");
                        SocketChannel channel = null;
                        try {
                            channel = SocketChannel.open(new InetSocketAddress(host, port));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        while (loop) {
                            System.out.print("> ");
                            Scanner scanner = new Scanner(System.in);
                            String line = scanner.nextLine();//command

                            try {
                                SerializableComArg sca = new SerializableComArg(line);
                                ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                                channel.configureBlocking(false);
                                ObjectOutputStream out = new ObjectOutputStream(bOut);
                                out.writeObject(sca);
                                out.close();

                                byte[] arr = bOut.toByteArray();
                                ByteBuffer bb = ByteBuffer.wrap(arr);
                                channel.write(bb);
                                System.out.println(c.exec(line));
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                    else {
                        System.out.println("Username or password is incorrect or User does not exist, please register first!");
                        System.out.println("    r - register first \n    e - re-enter your username and password");
                        System.out.print("Please enter the options: ");
                        String key = Utility.readString(1);


                        ObjectOutputStream oos = null;
                        try {
                            SerializableComArg sca1 = new SerializableComArg(key);
                            Socket socket = new Socket(InetAddress.getByName(host), port);
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(sca1);
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                        switch (key) {

                            case "r":
                                System.out.print("Please enter user name: ");
                                String rId = Utility.readString(50);//限制字符在50个之内
                                System.out.print("Please enter password: ");
                                String rpwd = Utility.readString(50);

                                try {
                                    User u = new User(rId,rpwd);
                                    Socket socket = new Socket(InetAddress.getByName(host), port);
                                    oos = new ObjectOutputStream(socket.getOutputStream());
                                    oos.writeObject(u);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                                System.out.println("\"" + rId + "\" register successfully!");
                                break;

                            case "e":
                                break;
                        }
                    }
                    break;
                case "r":
                    System.out.print("Please enter user name: ");
                    String rId = Utility.readString(50);//限制字符在50个之内
                    System.out.print("Please enter password: ");
                    String rpwd = Utility.readString(50);

                    ObjectOutputStream oos1 = null;
                    try {
                        User u = new User(rId,rpwd);
                        Socket socket = new Socket(InetAddress.getByName(host), port);
                        oos1 = new ObjectOutputStream(socket.getOutputStream());
                        oos1.writeObject(u);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    System.out.println("\"" + rId + "\" register successfully!");
                    break;

                case "9":
//                    ucs.logout();
                    loop = false;
                    break;
            }
        }

    }

}
