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
        while (true) {
            try {
                BufferedReader fromProxyServer = new BufferedReader(new InputStreamReader(fromProxySocket.getInputStream()));
                String line = fromProxyServer.readLine();
                Client.receiveData(line);
            } catch (java.io.IOException ignored) {
                System.out.print("ERROR! Wrong connection with Proxy (again)");
                System.exit(-16510459);
            }
        }
    }
}