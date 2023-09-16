import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import Commands.Command;

public class Main {
    public static void main(String[] args) throws IOException{
        // 创建服务器Socket对象
//        ServerSocket server = new ServerSocket(19500);
        // 监听客户端的连接，返回一个对应的Socket对象
//        Socket s = server.accept();
        // 获取输入流
//        InputStream is = s.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
//        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        Command c = new Command();
        String host = "127.0.0.1";
        int port = 19501;
        System.out.println("连接到主机：" + host + " ，端口号：" + port);
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Already connected");
            Socket s = server.accept();
            boolean flug = false;
            do {
//                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//                String line = br.readLine();

                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                SerializableComArg sca = (SerializableComArg) ois.readObject();
                String s1 = sca.getComArg();
//                System.out.println(sca.getComArg());
//                SerializableComArg sca = (SerializableComArg) ois.readObject();
//                System.out.println(sca.getComArg());
//                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
//                System.out.println(rLines);

                String[] commands = {"help","info","show","remove_by_id","clear",
                        "exit" ,"history"};
                for (int i = 0; i < commands.length; i++){
                    if (s1.equals(commands[i])){
                        flug = true;
                        System.out.println("You entered the command: "+commands[i]);
                    }
                }
                String[] commands2 = {"add","update_id","remove_by_id","execute_script", "remove_greater",
                        "remove_lower", "remove_all_by_genre","filter_contains_name","filter_starts_with_name"};
                for (int i = 0; i < commands2.length; i++){
                    if (s1.split(" ")[0].equals(commands2[i])){
                        flug=true;
                        System.out.println("You entered the command: "+commands2[i]);
                    }
                }

                if (s1.equals("exit")){
                    System.out.println("Disconnect and end the program");
                    s.close();
                    System.exit(0);
                }

                if (!flug){
                    System.out.println("Wrong input value!");
                }

                try {
                    c.exec(s1);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }

//                System.out.println(!str.equals("You entered the command: "));

            }while (true);
//            BufferedReader cbr = new BufferedReader(new InputStreamReader(s.getInputStream()));
//            String lline = cbr.readLine();
////            Scanner scanner = new Scanner(System.in);
//            if (lline.equals("end")){
//                System.out.println("1111111111");
//                try {
//                    c.save();
//                    System.out.println("Collections are saved");
//                    c.exit();
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//
//            } else if (lline.equals("save")) {
//                System.out.println("2222222222");
//                try {
//                    c.save();
//                    System.out.println("Collections are saved");
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }

        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
//        } catch (ClassNotFoundException e){
//            e.printStackTrace();
        } catch (IOException e){
            System.out.println("??????????????");
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        String line;


//        Scanner scanner = new Scanner(System.in);
//        if (scanner.nextLine().equals("end")){
//            c.save();
//            c.exit();
//        } else if (scanner.nextLine().equals("save")) {
//            c.save();
//        }
//        do {
//            try {
//                System.out.println(c.exec(scanner.nextLine()));
//
//            }catch (Exception e){
//                System.out.println(e.getMessage());
//            }
//        } while (true);

//        while ((line= br.readLine())!=null){
//            System.out.println("You entered the command: " + line);
//        }

//        server.close();

    }
    public static void scanner(){
        Command c = new Command();
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("end")){
            System.out.println("1111111111");
            try {
                c.save();
                System.out.println("Collections are saved");
                c.exit();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else if (scanner.nextLine().equals("save")) {
            System.out.println("2222222222");
            try {
                c.save();
                System.out.println("Collections are saved");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
