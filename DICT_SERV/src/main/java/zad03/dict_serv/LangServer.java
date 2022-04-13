package zad03.dict_serv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class LangServer {
    Map<String, String> dictionary = new HashMap<>();

    public LangServer(String nameFile) throws IOException {
        addToMapDictionary(System.getProperty("user.dir") + "\\Languages\\" + nameFile.toUpperCase() + ".txt");
        Socket clientSocket = new Socket();
        ServerSocket serverSocket = new ServerSocket();
    }

    private void addToMapDictionary(String path) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            String line = bufferedReader.readLine();
            while (line != null) {
                dictionary.put(line.substring(0, line.indexOf(9)), line.substring(line.indexOf(9) + 1));
                line = bufferedReader.readLine();
            }
        } catch (IOException ignored) {
            System.out.println("Language code is invalid");
        }
    }

    public String translate(String polishWord) {
        String translatedWord = dictionary.get(polishWord.toLowerCase());
        return translatedWord == null ? "Podane slowo nie istnieje w slowniku" : translatedWord;
    }

}
