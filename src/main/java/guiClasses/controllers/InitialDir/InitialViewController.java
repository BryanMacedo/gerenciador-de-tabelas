package guiClasses.controllers.InitialDir;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialViewController implements Initializable {
    private AudioClip clickSound;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private Label lbListFiles;

    @FXML
    private Label lbNewFile;

    @FXML
    private Label lbStatistics;

    @FXML
    private void onLbStatisticsClick(){
        clickSound.play();
        // verificar se tem arquivos para entrar na pegar os dados
    }

    @FXML
    private void onLbListFilesClick(){
        clickSound.play();
        // verificar se tem arquivos para listar
    }

    @FXML
    private void onLbNewFileClick(){
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
    }

    private void loadSounds(){
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);
    }

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
