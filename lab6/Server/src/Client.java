import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class Client {
    public static void main(String[] args) {
        Student stu = new Student();
        stu.setId(849);
        stu.setName("Squall");
        try {
            write(stu);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void write(Object obj) throws IOException {
        SocketChannel channel = null;
        try {
            channel = SocketChannel.open(new InetSocketAddress(
                    "127.0.0.1", 8234));
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
