package zad03.dict_serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class TCPHandler implements Runnable {
    private Socket socket;

    public TCPHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outToClient = new PrintWriter(socket.getOutputStream(), true);
            while (!inFromClient.readLine().isEmpty()) {
                outToClient.println("Test");
                socket.close();
            }
        }
        catch (IOException ignored) {

        }
    }
}