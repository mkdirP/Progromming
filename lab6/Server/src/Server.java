import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
        ServerThread server = new ServerThread();
        new Thread(server).start();
    }

    static class ServerThread implements Runnable {
        public void run() {
            try {
                ServerSocketChannel sc = ServerSocketChannel.open();
                ServerSocket s = sc.socket();
                s.bind(new InetSocketAddress(8234));
                while (true) {
                    Socket incoming = s.accept();
                    Runnable r = new GetObjThread(incoming);
                    Thread t = new Thread(r);
                    t.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class GetObjThread implements Runnable {
        public GetObjThread(Socket s) {
            incoming = s;
        }

        public void run() {
            try {
                SocketChannel sc = incoming.getChannel();
                ByteBuffer bbIn = ByteBuffer.allocate(85); //根据实际情况分配你需要的字节数，我用的85是从cli打印出来的字节数目
                sc.read(bbIn);
                sc.close();
                ByteArrayInputStream bIn = new ByteArrayInputStream(bbIn
                        .array());
                ObjectInputStream in = new ObjectInputStream(bIn);
                Student nStu = (Student) in.readObject();
                System.out.println("student id is " + nStu.getId() + "\n" + "student name is " + nStu.getName());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private Socket incoming;
    }
}
