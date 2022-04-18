package Client;

import _Misc.ImportantData;
import _Misc.ProxyInfo;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client extends Application {

    final static int HEIGHT = 800;
    final static int WIDTH = 200;
    static Group root = new Group();
    static Scene scene = new Scene(root, HEIGHT, WIDTH);
    static TextArea gottenTextArea;

    @Override
    public void start(Stage primaryStage) {
        KeyCombination sendTextKeyCombination = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_ANY);

        gottenTextArea = new TextArea("Received data");
        gottenTextArea.setFocusTraversable(false);
        gottenTextArea.setEditable(false);
        gottenTextArea.setPrefHeight(0.5 * HEIGHT);
        gottenTextArea.setPrefWidth(WIDTH);
        gottenTextArea.setTranslateX(0.5 * HEIGHT);
        gottenTextArea.setTranslateY(0);

        TextArea sentTextArea = new TextArea();
        sentTextArea.setPrefHeight(0.5 * HEIGHT);
        sentTextArea.setPrefWidth(WIDTH);
        sentTextArea.setTranslateX(0);
        sentTextArea.setTranslateY(0);
        sentTextArea.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && !sendTextKeyCombination.match(e)) {
                sendData(proxyInfo + "," + port + "," + sentTextArea.getText());
                sentTextArea.setText("");
            }
        });

        root.getChildren().add(gottenTextArea);
        root.getChildren().add(sentTextArea);
        Stage stage = new Stage();
        stage.setTitle("DICT_SERV");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private static void sendData(String data) {
        toProxyServer.println(data);
        sendInfo(serverSocket);
    }

    public static void receiveData(String data) {
        gottenTextArea.setText(data);
    }

    private static PrintWriter toProxyServer;
    private static ServerSocket serverSocket;
    private static final ProxyInfo proxyInfo = ProxyInfo.Client;
    private static final int port = ImportantData.getClientPort();

    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", ImportantData.PROXY_PORT);
        serverSocket = new ServerSocket(port);
        toProxyServer = new PrintWriter(clientSocket.getOutputStream(), true);
        launch();
    }

    private static void sendInfo(ServerSocket serverSocket) {
        try {
            Socket fromProxySocket = serverSocket.accept();
            new Thread(new ClientHandler(fromProxySocket)).start();
        } catch (IOException ignored) {
            System.out.print("ERROR!!! Something went wrong with Proxy connection!!!");
            System.exit(-229454516);
        }
    }
}

