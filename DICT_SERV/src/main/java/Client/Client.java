package Client;

import _Misc.ImportantData;
import _Misc.ProxyInfo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends Application {

    @Override
    public void start(Stage stage) {
    }

    public static void main(String[] args) throws IOException {
        /* */
        int port = 12;
        String tmpWord = "kot";

        ProxyInfo proxyInfo = ProxyInfo.Client;

        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = new Socket("localhost", ImportantData.PROXY_PORT);
        PrintWriter toProxyServer = new PrintWriter(clientSocket.getOutputStream(), true);

        String toSend = proxyInfo + "," + port + "," + tmpWord + ",TUR";
        System.out.println(toSend);
        toProxyServer.println(toSend);

        Socket fromProxySocket = serverSocket.accept();
        new Thread(new ClientHandler(fromProxySocket)).start();
        //launch
    }
}

