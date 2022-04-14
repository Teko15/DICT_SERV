package Client;

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
        int port = 0;
        try {
            Socket clientSocket = new Socket ("192.168.0.129", port);
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String data = inFromClient.readLine();
            System.out.println(data);
        }
        catch (IOException ignored) {

        }
    }
}