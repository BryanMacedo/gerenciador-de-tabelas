package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private VBox vb03;

    @FXML
    private VBox vb04;

    @FXML
    private HBox hbCentralContent;

    @FXML
    private HBox hbNewFile;

    @FXML
    private void onHbNewFileClick(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/NewFileView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File path = new File("C:\\tabelas-GT");
        File[] files = path.listFiles((dir, nome) -> nome.endsWith(".xlsx"));

        int count = 1;

        if (files != null) {
            for (File file : files) {
                String fileWithOutFinal = file.getName().substring(0, file.getName().length() - 5);
                Label newLabel = new Label(fileWithOutFinal);

                newLabel.setStyle("-fx-border-color: #A9A9A9; -fx-border-width: 4px; " +
                        "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #A9A9A9; -fx-max-width: infinity;" +
                        "-fx-alignment: center;");

                vb01.setSpacing(15);
                vb02.setSpacing(15);
                vb03.setSpacing(15);
                vb04.setSpacing(15);

                // efeito de hover
                newLabel.setOnMouseEntered(e -> newLabel.setStyle(" -fx-border-color: #ffffff; -fx-border-width: 4px;" +
                        "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #ffffff; -fx-max-width: infinity;" +
                        "-fx-alignment: center;"));

                newLabel.setOnMouseExited(e -> newLabel.setStyle("-fx-border-color: #A9A9A9; -fx-border-width: 4px;" +
                        "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #A9A9A9; -fx-max-width: infinity;" +
                        "-fx-alignment: center;"));


                newLabel.setOnMouseClicked(e -> {
                    System.out.println("Arquivo clicado: " + fileWithOutFinal);

                    //ir para a tela que mostra os dados do arquivo
                });

                switch (count) {
                    case 1 -> {
                        vb01.getChildren().add(newLabel);
                        count += 1;
                    }
                    case 2 -> {
                        vb02.getChildren().add(newLabel);
                        count += 1;
                    }
                    case 3 -> {
                        vb03.getChildren().add(newLabel);
                        count += 1;
                    }
                    case 4 -> {
                        vb04.getChildren().add(newLabel);
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
