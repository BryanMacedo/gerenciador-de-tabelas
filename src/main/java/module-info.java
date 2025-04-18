module org.bryanmacedo.projetogerenciadordetabelas {
    requires javafx.controls;
    requires javafx.fxml;


    opens guiClasses.controllers to javafx.fxml, javafx.graphics;
    exports guiClasses.controllers to javafx.fxml;

    opens org.bryanmacedo.projetogerenciadordetabelas to javafx.fxml;
    exports org.bryanmacedo.projetogerenciadordetabelas;
}