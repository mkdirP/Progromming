import Commands.Command;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        // 创建客户端Socket对象
        // Socket(InetAddress adr, int port) 创建流套接字并将其连接到指定IP地址的指定端口号
//        Socket s = new Socket(InetAddress.getByName("127.0.0.1"),19500);
        // Socket(String host, int port) 创建流套接字并将其连接到指定IP地址的指定端口号

        String host = "127.0.0.1";
        int port = 19501;
        System.out.println("连接到主机：" + host + " ，端口号：" + port);
        Command c = new Command();
        try {
            System.out.println("Hello, my dear user!");
            // 1. 获取通道
            SocketChannel channel = SocketChannel.open(new InetSocketAddress(host, port));

             while (true){
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

//                     //跳出循环表示接收到数据
//                     while (channel.write(bb) <= 0){
//                         System.out.println("No data received yet");
//                     }

//                     // 1.1切换成非阻塞模式
//                     client.configureBlocking(false);
//                     // 2. 发送一张图片给服务端
//                     FileChannel fileChannel = FileChannel.open(Paths.get("D:\\A_itmo\\iProgram\\lab6s\\scas"), StandardOpenOption.READ);
//                     // 3.要使用NIO，有了Channel，就必然要有Buffer，Buffer是与数据打交道的
//                     ByteBuffer buffer = ByteBuffer.allocate(1024);
//                     // 4.读取本地文件(图片)，发送到服务器
//                     while (fileChannel.read(buffer) != -1) {
//                         // 在读之前都要切换成读模式
//                         buffer.flip();
//                         client.write(buffer);
//                         // 读完切换成写模式，能让管道继续读取文件的数据
//                         buffer.clear();
//                     }

//                     ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\A_itmo\\iProgram\\lab6s\\scas"));
//                     oos.writeObject(sca);
                     if (line.equals("exit")){
//                         fileChannel.close();
                        channel.close();
                        break;
                     }
                     //nextLine(): 以Enter为结束符,也就是说 nextLine()方法返回的是输入回车之前的所有字符
                     System.out.println(c.exec(line));
//                     oos.flush();
                 }catch (Exception e){
                     System.out.println(e.getMessage());
                 }

            }

        } catch (IOException  e) {//| ClassNotFoundException
            System.out.println(e.getMessage());
        }

    }
    public static void write(Object obj) throws IOException{
        SocketChannel channel = null;
        try {
//            channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 19501));
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bOut);
            out.writeObject(obj);
            out.flush();
            byte[] arr = bOut.toByteArray();
            System.out.println("Object in " + arr.length + " bytes");
            ByteBuffer bb = ByteBuffer.wrap(arr);
            out.close();
            //Socket socket = channel.socket();
            // SocketChannel sc = socket.getChannel();
            channel.write(bb);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.close();
        }
    }
}
