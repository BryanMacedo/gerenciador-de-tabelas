module org.bryanmacedo.projetogerenciadordetabelas {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires javafx.media;


//    opens guiClasses.controllers to javafx.fxml, javafx.graphics;
//    exports guiClasses.controllers to javafx.fxml;

    opens org.bryanmacedo.projetogerenciadordetabelas to javafx.fxml;
    exports org.bryanmacedo.projetogerenciadordetabelas;

    exports guiClasses.controllers.newFileDir to javafx.fxml;
    opens guiClasses.controllers.newFileDir to javafx.fxml, javafx.graphics;
    exports guiClasses.controllers.listFilesDir to javafx.fxml;
    opens guiClasses.controllers.listFilesDir to javafx.fxml, javafx.graphics;
    exports guiClasses.controllers.listFileDataDir to javafx.fxml;
    opens guiClasses.controllers.listFileDataDir to javafx.fxml, javafx.graphics;
    exports guiClasses.controllers.StatisticsDir to javafx.fxml;
    opens guiClasses.controllers.StatisticsDir to javafx.fxml, javafx.graphics;
}