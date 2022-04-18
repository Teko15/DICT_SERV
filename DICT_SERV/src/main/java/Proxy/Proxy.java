package Proxy;

import _Misc.ImportantData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Proxy {
    static Map<String, Integer> langServerCodeAndPort = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(ImportantData.PROXY_PORT);
            ExecutorService executorService = Executors.newFixedThreadPool(Integer.MAX_VALUE);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ProxyHandler(clientSocket));
            }
        } catch (IOException ignored) {
            System.out.print("ERROR! Could not created Proxy Server");
            System.exit(-1753756);
        }
    }
}