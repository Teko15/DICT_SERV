package Proxy;

import _Misc.ProxyInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ProxyHandler implements Runnable {
    Socket fromClientSocket;
    Map<String, Integer> langServerCodeAndPort = new HashMap<>();

    public ProxyHandler(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));
                String line = fromClient.readLine();
                System.out.println(line);
                String[] receivedInfo = line.split(",");
                ProxyInfo receivedProxyInfo = ProxyInfo.valueOf(receivedInfo[0]);
                int receivedPort = Integer.parseInt(receivedInfo[1]);
                String receivedData = receivedInfo[2];
                String langCode = "";
                if (receivedInfo.length == 4)
                    langCode = receivedInfo[3];
                switch (receivedProxyInfo) {
                    //Client,1331,dom,ENG
                    case Client -> clientService(receivedPort, receivedData, langCode);
                    //LangServer,12,ENG
                    case LangServer -> langServerService(receivedPort, receivedData);
                    default -> System.out.println("Unknown server kek");
                }

            } catch (IOException ignored) {
                System.out.println("nope");
            }
        }
    }
    //1331,dom,ENG
    private void clientService(int clientPort, String data, String langCode) throws IOException {
        System.out.println("Greetings mr. client!");
        System.out.println(langServerCodeAndPort);
        if (langServerCodeAndPort.get(langCode) == null) {
            Socket toClientSocket = new Socket("localhost", clientPort);
            PrintWriter toProxyServer = new PrintWriter(toClientSocket.getOutputStream(), true);
            toProxyServer.println("Invalid server (" + langCode + ")");
        } else {
            Socket toLangServerSocket = new Socket("localhost", langServerCodeAndPort.get(langCode));
            PrintWriter toLangServer = new PrintWriter(toLangServerSocket.getOutputStream(), true);
            //dom,1331
            toLangServer.println(data + "," + clientPort);
        }
    }

    private void langServerService(int langServerPort, String langCode) {
        System.out.println("Greetings mr. server!");
        langServerCodeAndPort.put(langCode, langServerPort);
    }
}
