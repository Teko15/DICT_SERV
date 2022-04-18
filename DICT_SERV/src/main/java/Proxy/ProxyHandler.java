package Proxy;

import _Misc.ProxyInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ProxyHandler implements Runnable {
    Socket fromClientSocket;

    public ProxyHandler(Socket fromClientSocket) {
        this.fromClientSocket = fromClientSocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(fromClientSocket.getInputStream()));
                String line = fromClient.readLine();
                String[] receivedInfo = line.split(",");
                ProxyInfo receivedProxyInfo = ProxyInfo.valueOf(receivedInfo[0]);
                int receivedPort = Integer.parseInt(receivedInfo[1]);
                String receivedData = receivedInfo[2];
                String langCode = "";
                if (receivedInfo.length == 4)
                    langCode = receivedInfo[3];
                switch (receivedProxyInfo) {
                    case Client -> clientService(receivedPort, receivedData, langCode);
                    case LangServer -> langServerService(receivedPort, receivedData);
                    default -> {
                        System.out.print("Greetings! Thanks for not stealing our data ❤");
                        System.exit(-1163383400);
                    }
                }

            } catch (IOException ignored) {
                System.out.print("Connection failed - Proxy went home or something");
                System.exit(-106254429);
            }
        }
    }

    private void clientService(int clientPort, String data, String langCode) throws IOException {
        System.out.println("Greetings mr. " + clientPort + ".Client");
        if (Proxy.langServerCodeAndPort.get(langCode) == null) {
            Socket toClientSocket = new Socket("localhost", clientPort);
            PrintWriter toProxyServer = new PrintWriter(toClientSocket.getOutputStream(), true);
            if (langCode.equals(""))
                toProxyServer.println("Unknown command");
            else
                toProxyServer.println("Invalid server (" + langCode + ")");
        } else {
            Socket toLangServerSocket = new Socket("localhost", Proxy.langServerCodeAndPort.get(langCode));
            PrintWriter toLangServer = new PrintWriter(toLangServerSocket.getOutputStream(), true);
            toLangServer.println(data + "," + clientPort);
        }
    }

    private void langServerService(int langServerPort, String langCode) {
        System.out.println("Greetings mr. " + langCode + ".LangServ");
        Proxy.langServerCodeAndPort.put(langCode, langServerPort);
    }
}
