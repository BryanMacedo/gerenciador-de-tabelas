module org.bryanmacedo.projetogerenciadordetabelas {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.bryanmacedo.projetogerenciadordetabelas to javafx.fxml;
    exports org.bryanmacedo.projetogerenciadordetabelas;
}