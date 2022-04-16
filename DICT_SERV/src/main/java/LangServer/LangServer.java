package LangServer;

import _Misc.ProxyInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class LangServer {
    final static int PROXY_PORT = 1234;

    private static Map<String, String> dictionary = new HashMap<>();

    public static Map<String, String> getDictionary() {
        return dictionary;
    }

    public static void setDictionary(Map<String, String> dictionary) {
        LangServer.dictionary = dictionary;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2)
            System.exit(13);
        int port = Integer.parseInt(args[0]);
        String langCode = args[1];
        ProxyInfo proxyInfo = ProxyInfo.LangServer;


        addToMapDictionary(langCode);
        setDictionary(dictionary);
        Socket toProxySocket = new Socket("localhost", PROXY_PORT);
        PrintWriter toProxyServer = new PrintWriter(toProxySocket.getOutputStream(), true);
        String toSend = proxyInfo + "," + port + "," + langCode;
        System.out.println(toSend);
        toProxyServer.println(toSend);

        ServerSocket serverSocket = new ServerSocket(port);
        Socket toClientSocket = serverSocket.accept();
        new Thread(new LangServerHandler(toClientSocket)).start();
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


}
/*
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LangServer {
    Map<String, String> dictionary = new HashMap<>();

    public LangServer(String nameFile, int port) throws IOException {
        addToMapDictionary(System.getProperty("user.dir") + "\\Languages\\" + nameFile.toUpperCase() + ".txt");
        Socket clientSocket = new Socket();
        ServerSocket serverSocket = new ServerSocket(101);
        System.out.println(serverSocket.getInetAddress());

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        while (true) {
            threadPool.submit(new TCPHandler(serverSocket.accept(), 2));
        }
    }



    public String translate(String polishWord) {
        String translatedWord = dictionary.get(polishWord.toLowerCase());
        return translatedWord == null ? "Podane slowo nie istnieje w slowniku" : translatedWord;
    }

}

* */