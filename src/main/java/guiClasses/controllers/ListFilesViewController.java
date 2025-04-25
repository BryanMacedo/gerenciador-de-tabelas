package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ListFilesViewController implements Initializable {
    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private VBox vb01;

    @FXML
    private VBox vb02;

    @FXML
    private HBox hbCentralContent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File path = new File("C:\\tabelas-GT");
        File[] files = path.listFiles((dir, nome) -> nome.endsWith(".xlsx"));

        int count = 1;

        if (files != null){
            for (File file : files) {
                Label newLabel = new Label(file.getName());

                newLabel.setStyle("-fx-border-color: #ffffff; -fx-border-width: 2px; " +
                        "-fx-padding: 5px; -fx-cursor: hand;");

                vb01.setSpacing(15);
                vb02.setSpacing(15);

                newLabel.setOnMouseEntered(e -> newLabel.setStyle("-fx-background-color: #272727; -fx-border-color: #ffffff; -fx-border-width: 2px;" +
                        "-fx-padding: 5px; -fx-cursor: hand; -fx-font-size: 19px;"));

                newLabel.setOnMouseExited(e -> newLabel.setStyle("-fx-background-color: #161616; -fx-border-color: #ffffff; -fx-border-width: 2px;" +
                        "-fx-padding: 5px; -fx-cursor: hand; -fx-font-size: 18px;"));


                newLabel.setOnMouseClicked(e -> {
                    System.out.println("Arquivo clicado: " + file.getName());

                    //ir para a tela que mostra os dados do arquivo
                });

                switch (count){
                    case 1 ->{
                        vb01.getChildren().add(newLabel);
                        count += 1;
                    }
                    case 2 ->{
                        vb02.getChildren().add(newLabel);
                        count = 1;
                    }
                }

            }
        }


    }

    // fechar e minimizar
    @FXML
    private void onImgvCloseClick() {
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick() {
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
