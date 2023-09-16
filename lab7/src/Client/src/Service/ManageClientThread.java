package Service;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 管理客户端连接到服务器端的线程的类 = 线程池
 * userId = username
 */
public class ManageClientThread {
    // 将多个线程放入一个HashMap集合中,key为用户id
//    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();
//
//    //将某个线程加入到集合中
//    public static void addCCST(String userId, ClientConnectServerThread ccst){
//        hm.put(userId, ccst);
//    }
//
//    // 通过userId可以得到对应的线程
//    public static ClientConnectServerThread getClientConnectServerThread(String userId){
//        return hm.get(userId);
//    }

    public static ExecutorService ftp = Executors.newFixedThreadPool(10);


    public static void addTread(String userId, ClientConnectServerThread thread){
        thread.setName(userId);
        ftp.submit(thread);
    }

    //终止所有线程
    public static void removeThread(ClientConnectServerThread thread){
        thread.run = false;
    }
//    public static ClientConnectServerThread getUserIdThread(String userId){
//        return ;
//    }



}
