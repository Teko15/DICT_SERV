package LangServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LangServerHandler implements Runnable {

    Socket fromProxySocket;

    public LangServerHandler(Socket fromProxySocket) {
        this.fromProxySocket = fromProxySocket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BufferedReader fromProxyServer = new BufferedReader(new InputStreamReader(fromProxySocket.getInputStream()));
                String line = fromProxyServer.readLine();
                String[] fromProxyInfo = line.split(",");
                String output = LangServer.getDictionary().get(fromProxyInfo[0]);
                if (output == null)
                    output = "unknown word";
                Socket toClientSocket = new Socket("localhost", Integer.parseInt(fromProxyInfo[1]));
                PrintWriter toClient = new PrintWriter(toClientSocket.getOutputStream(), true);
                toClient.println(output);
            } catch (IOException ignored) {
                System.out.print("Well... LangServer do not know what to do... Stupid machine...");
                System.exit(-968023442);
            }
        }
    }
}
