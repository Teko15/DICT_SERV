module zad03.dict_serv {
    requires javafx.controls;
    requires javafx.fxml;


    opens zad03.dict_serv to javafx.fxml;
    exports zad03.dict_serv;
}