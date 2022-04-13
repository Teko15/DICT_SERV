package zad03.dict_serv;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends Application {

    @Override
    public void start(Stage stage) {
    }

    public static void main(String[] args) throws IOException {
        LangServer lang = new LangServer("sIN");
        System.out.println(lang.translate("miAsTo"));

        Socket clientSocket = new Socket("192.168.0.214", 0);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);

        //O tutaj bedzie okienko itp
        Scanner input = new Scanner(System.in);

        outToServer.println(input);

        String line;
        while ((line = inFromServer.readLine()) != null) {
            System.out.println(line);
        }

        clientSocket.close();

        ServerSocket serverSocket = new ServerSocket();

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        while (true) {
            threadPool.submit(new TCPHandler(serverSocket.accept()));
        }
    }
}

