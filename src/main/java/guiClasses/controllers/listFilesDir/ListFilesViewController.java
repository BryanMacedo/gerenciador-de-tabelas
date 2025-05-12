package guiClasses.controllers.listFilesDir;

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
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ListFilesViewController implements Initializable {
    public static String fileToAccess;

    private AudioClip clickSound;
    private AudioClip hoverSound;


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
    private HBox hbListFiles;

    @FXML
    private HBox hbStatistics;

    @FXML
    private void onHbStatistics() {
        clickSound.play();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/StatisticsDir/StatisticsView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void onHbNewFileClick() {
        clickSound.play();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/newFileDir/NewFileView.fxml"));
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
        loadSounds();

        setInitialsSounds();

        File path = new File("C:\\tabelas-GT");
        File[] files = path.listFiles((dir, name) -> name.endsWith(".xlsx"));

        int count = 1;

        if (files != null) {
            for (File file : files) {
                String fileWithOutFinal = file.getName().substring(0, file.getName().length() - 5);
                Label newLabel = new Label(fileWithOutFinal);

                newLabel.setStyle("-fx-border-color: #ffffff; -fx-border-width: 4px; " +
                        "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #ffffff; -fx-max-width: infinity;" +
                        "-fx-alignment: center; -fx-background-color: transparent;");

                vb01.setSpacing(15);
                vb02.setSpacing(15);
                vb03.setSpacing(15);
                vb04.setSpacing(15);

                // efeito de hover
                newLabel.setOnMouseEntered(e -> {
                    newLabel.setStyle(" -fx-border-color: #ffffff; -fx-border-width: 4px;" +
                            "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #ffffff; -fx-max-width: infinity;" +
                            "-fx-alignment: center; -fx-background-color: #272727;");

                    hoverSound.play();
                });

                newLabel.setOnMouseExited(e -> newLabel.setStyle("-fx-border-color: #ffffff; -fx-border-width: 4px;" +
                        "-fx-padding: 15px; -fx-cursor: hand; -fx-text-fill: #ffffff; -fx-max-width: infinity;" +
                        "-fx-alignment: center; -fx-background-color: transparent;"));


                newLabel.setOnMouseClicked(e -> {
                    clickSound.play();
                    System.out.println("Arquivo clicado: " + fileWithOutFinal);

                    //ir para a tela que mostra os dados do arquivo
                    try {
                        fileToAccess = fileWithOutFinal;
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/ListFileDataView.fxml"));
                        Parent root = loader.load();
                        Scene scene = imgvClose.getScene();
                        scene.setRoot(root);
                    } catch (IOException exc) {
                        //System.out.println(e.getMessage());
                        exc.printStackTrace();
                    }
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

    private void setInitialsSounds() {
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<HBox> hBoxViews = new ArrayList<>(Arrays.asList(hbListFiles, hbNewFile, hbStatistics));

        for (ImageView imgv : imageViews) {
            imgv.setOnMouseEntered(event -> {
                hoverSound.play();
            });
        }

        for (HBox hbv : hBoxViews) {
            hbv.setOnMouseEntered(event -> {
                hoverSound.play();
            });
        }
    }

    private void loadSounds() {
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        String hoverPath = getClass().getResource("/sounds/hover_sound_01.mp3").toString();
        this.hoverSound = new AudioClip(hoverPath);
        this.hoverSound.setVolume(0.1);
        hoverSound.setPriority(1);
    }

    // fechar e minimizar
    @FXML
    private void onImgvCloseClick() {
        clickSound.play();
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick() {
        clickSound.play();
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
