package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

class ClientHandler implements Runnable {

    Socket fromProxySocket;
    public ClientHandler(Socket fromProxySocket) {
        this.fromProxySocket = fromProxySocket;
    }

    @Override
    public void run() {
        while(true) {
            try {
                BufferedReader fromProxyServer = new BufferedReader(new InputStreamReader(fromProxySocket.getInputStream()));
                String line = fromProxyServer.readLine();
                System.out.println(line);
            } catch (java.io.IOException ignored) {
                System.out.println("ERROR CLIENT HANDLER");
            }
        }
    }
}