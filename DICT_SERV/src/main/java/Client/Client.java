package Client;

import _Misc.ImportantData;
import _Misc.ProxyInfo;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application {

    @Override
    public void start(Stage stage) {
    }

    public static void main(String[] args) throws IOException {
        /* */
        int port = 13;
        Scanner scanner = new Scanner(System.in);
        String newText = "dom,TUR";



        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = new Socket("localhost", ImportantData.PROXY_PORT);
        PrintWriter toProxyServer = new PrintWriter(clientSocket.getOutputStream(), true);
        ProxyInfo proxyInfo = ProxyInfo.Client;

        toProxyServer.println(proxyInfo + "," + port + "," + newText);

        sendInfo(serverSocket);

        while (!newText.equals("果て")) {
            newText = scanner.nextLine();
            String toSend = proxyInfo + "," + port + "," + newText;
            System.out.println(toSend);
            toProxyServer.println(toSend);
            sendInfo(serverSocket);
        }
        System.exit(Integer.MIN_VALUE);
        //launch
    }

    private static void sendInfo(ServerSocket serverSocket) throws IOException {
        Socket fromProxySocket = serverSocket.accept();
        new Thread(new ClientHandler(fromProxySocket)).start();
    }
}

