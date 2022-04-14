package zad03.dict_serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class TCPHandler implements Runnable {
    private Socket socket;
    private int clientServerServer;

    public TCPHandler(Socket socket, int clientServerServer) {
        this.socket = socket;
        this.clientServerServer = clientServerServer;
    }

    @Override
    public void run() {
        int port;
        try {
            switch (clientServerServer) {
                case 1 -> port = 1;
                case 2 -> port = 10;
                case 3 -> port = 100;
                default -> port = -1;
            }
            Socket clientSocket = new Socket ("192.168.0.129", port);

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = inFromClient.readLine();
            System.out.println(data);

            switch (clientServerServer) {
                case 1 -> System.out.println("Lang Server -> Client");
                case 2 -> System.out.println("Client -> Main Server");
                case 3 -> System.out.println("Main Server -> Lang Server");
            }
        }
        catch (IOException ignored) {

        }
    }
}