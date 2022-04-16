module zad03.dict_serv {
    requires javafx.controls;
    requires javafx.fxml;


    opens Client to javafx.fxml;
    exports Client;
}