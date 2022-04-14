package zad03.dict_serv;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class Client extends Application {

    @Override
    public void start(Stage stage) {
    }

    public static void main(String[] args) throws IOException {

        Socket clientSocket = new Socket("192.168.0.129", 1);
        //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
        String clientPort = String.valueOf(clientSocket.getLocalPort());
        String[] toServer = new String[]{"dom", "sin", clientPort};


        outToServer.println(Arrays.toString(toServer));

        clientSocket.close();

     //   ServerSocket serverSocket = new ServerSocket();

      //  ExecutorService threadPool = Executors.newFixedThreadPool(3);
        /*while (true) {
            threadPool.submit(new TCPHandler(serverSocket.accept()));
        }*/
    }
}

