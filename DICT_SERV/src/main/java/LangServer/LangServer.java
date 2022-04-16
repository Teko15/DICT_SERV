package LangServer;

import _Misc.ImportantData;
import _Misc.ProxyInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LangServer {

    private static Map<String, String> dictionary = new HashMap<>();

    public static Map<String, String> getDictionary() {
        return dictionary;
    }

    public static void setDictionary(Map<String, String> dictionary) {
        LangServer.dictionary = dictionary;
    }

    public static void main(String[] args) throws IOException {
        checkErrors(args);

        String langCode = args[0];
        int port = ImportantData.LANGUAGES_AND_PORTS.get(langCode);
        ProxyInfo proxyInfo = ProxyInfo.LangServer;
        addToMapDictionary(langCode);
        setDictionary(dictionary);

        Socket toProxySocket = new Socket("localhost", ImportantData.PROXY_PORT);
        PrintWriter toProxyServer = new PrintWriter(toProxySocket.getOutputStream(), true);
        String toSend = proxyInfo + "," + port + "," + langCode;
        System.out.println(toSend);
        toProxyServer.println(toSend);

        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket toClientSocket = serverSocket.accept();
            new Thread(new LangServerHandler(toClientSocket)).start();
        }
    }

    private static void addToMapDictionary(String langCode) {
        String path = System.getProperty("user.dir") + "\\Languages\\" + langCode.toUpperCase() + ".txt";
        try {
            java.io.BufferedReader bufferedReader = new java.io.BufferedReader(new java.io.FileReader(path));
            String line = bufferedReader.readLine();
            while (line != null) {
                dictionary.put(line.substring(0, line.indexOf(9)), line.substring(line.indexOf(9) + 1));
                line = bufferedReader.readLine();
            }
        } catch (java.io.IOException ignored) {
            System.out.println("Language code [" + langCode + "]is invalid");
        }
    }

    private static void checkErrors(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of parameters!");
            System.exit(255);
        }
        if (ImportantData.LANGUAGES_AND_PORTS.get(args[0]) == null) {
            System.out.println("Unknown language code!");
            System.exit(args[0].length());
        }
    }
}