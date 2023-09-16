package Service;

import java.net.Socket;
import java.util.concurrent.ForkJoinPool;

/**
 * 用于处理请求的线程 -- ForkJoinPool
 * 处理用户登录和注册
 */
public class ProcessRequest extends Thread{
    private Socket socket;
    private String userId;
    ForkJoinPool fjp = new ForkJoinPool();

    public static void addRequest(){

    }

    public ProcessRequest() {
    }

    public ProcessRequest(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public void run(){

    }
}

