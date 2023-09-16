package Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManageServerThread {

    public static ExecutorService ftp = Executors.newFixedThreadPool(10);

    public static void addClientThread(String userId, ServerConnectClientThread scct){
        scct.setName(userId);
        ftp.submit(scct);
    }
    public static void removeThread(ServerConnectClientThread scct){
        scct.run = false;
    }
}
