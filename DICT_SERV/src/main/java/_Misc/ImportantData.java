package _Misc;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ImportantData {
    public final static int PROXY_PORT = 9000;
    public final static Map<String, Integer> LANGUAGES_AND_PORTS = Map.ofEntries(
            Map.entry("END", 1000),
            Map.entry("JPY", 1001),
            Map.entry("SIN", 1002),
            Map.entry("TUR", 1003)
    );

    private static int CLIENT_PORT = 0;

    public static int getClientPort() {
        try {
            String fileName = System.getProperty("user.dir") + "\\ClientPort.MP0";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = bufferedReader.readLine();
            if (line == null)
                CLIENT_PORT = 0;
            else
                CLIENT_PORT = Integer.parseInt(line);

            if (CLIENT_PORT == 1000) {
                System.out.print("Too many clients! I don't know what to do as Proxy Server *PANIC*");
                System.exit(-1241568642);
            }
            PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
            printWriter.print(++CLIENT_PORT);
            printWriter.close();
            return CLIENT_PORT;
        } catch (IOException ignored) {
            System.out.print("File ClientPort.MP0 not found!");
            System.exit(-41437);
            return CLIENT_PORT;
        }
    }
}
