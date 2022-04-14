package Proxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 1234;
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        Socket cli = serverSocket.accept();
        new Thread(new ProxyHandler(cli)).start();
        //threadPool.submit(new ProxyHandler(serverSocket.accept()));
        System.out.println("End of Server");
    }
}

/*
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket1 = new ServerSocket(10);
        Socket clientSocket = new Socket("192.168.0.129", 10);

        ServerSocket serverSocket = new ServerSocket(1);
        ExecutorService threadPool = Executors.newCachedThreadPool();

            threadPool.submit(new TCPHandler(serverSocket.accept(), 2));

    }
 */
