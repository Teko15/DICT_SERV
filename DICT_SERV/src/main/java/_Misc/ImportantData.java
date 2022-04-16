package _Misc;

import java.util.Map;

public class ImportantData {
    public final static int PROXY_PORT = 1234;
    public final static Map<String, Integer> LANGUAGES_AND_PORTS = Map.ofEntries(
            Map.entry("END", 1000),
            Map.entry("JPY", 1001),
            Map.entry("SIN", 1002),
            Map.entry("TUR", 1003)
    );

    private static int CLIENT_PORT = 1;
    public static int getClientPort() {
        CLIENT_PORT += toAvoidWarning();
        CLIENT_PORT -= toAvoidWarning();
        return CLIENT_PORT++;
    }
    private static int toAvoidWarning() {
        return getClientPort();
    }
}
