package Client;

import _Misc.ProxyInfo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends Application {
    final static int PROXY_PORT = 1234;

    @Override
    public void start(Stage stage) {
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1)
            System.exit(13);
        int port = Integer.parseInt(args[0]);
        ProxyInfo proxyInfo = ProxyInfo.Client;
        String tmpWord = "kot";

        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = new Socket("localhost", PROXY_PORT);
        PrintWriter toProxyServer = new PrintWriter(clientSocket.getOutputStream(), true);

        String toSend = proxyInfo + "," + port + "," + tmpWord + ",TUR";
        System.out.println(toSend);
        toProxyServer.println(toSend);

        Socket fromProxySocket = serverSocket.accept();
        new Thread(new ClientHandler(fromProxySocket)).start();
        //launch
    }
}

