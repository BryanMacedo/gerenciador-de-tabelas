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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InitialViewController implements Initializable {
    private AudioClip clickSound;
    private AudioClip hoverSound;
    private AudioClip errorSound;

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
    private Label lbWarning;

    @FXML
    private void onLbStatisticsClick(){
        // verificar se tem arquivos para entrar na pegar os dados

        File folder = new File("C:\\tabelas-GT");
        String[] files = folder.list();

        if (files.length == 0){
            errorSound.play();
            lbWarning.setText("No momento, você não tem dados suficientes para visualizar suas estatísticas. Por favor, clique na opção \"Criar uma tabela\" para criar sua primeira tabela e fornecer os dados que serão analisados.");
        }else{
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
    }

    @FXML
    private void onLbListFilesClick(){
        // verificar se tem arquivos para listar
        File folder = new File("C:\\tabelas-GT");
        String[] files = folder.list();

        if (files.length == 0){
            errorSound.play();
            lbWarning.setText("No momento não há tabelas a serem listadas. Por favor, clique na opção \"Criar uma tabela\" para poder criar sua primeira tabela.");
        }else{
            clickSound.play();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFilesDir/ListFilesView.fxml"));
                Parent root = loader.load();
                Scene scene = imgvClose.getScene();
                scene.setRoot(root);
            } catch (IOException e) {
                //System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
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

        setInitialsSounds();

        Path path = Paths.get("C://tabelas-GT");

        if (!Files.exists(path) && !Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Pasta não criada." + e.getMessage());
            }
        }
    }

    private void setInitialsSounds(){
        List<Label> labelList = new ArrayList<>(Arrays.asList(lbListFiles, lbNewFile, lbStatistics));
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));

        for (Label label : labelList) {
            label.setOnMouseEntered(event -> {
                hoverSound.play();
            });
        }

        for (ImageView imageView : imageViews) {
            imageView.setOnMouseEntered(event -> {
                hoverSound.play();
            });
        }
    }

    private void loadSounds(){
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        String hoverPath = getClass().getResource("/sounds/hover_sound_01.mp3").toString();
        this.hoverSound = new AudioClip(hoverPath);
        this.hoverSound.setVolume(0.1);
        hoverSound.setPriority(1);

        String errorPath = getClass().getResource("/sounds/error_sound_01.mp3").toString();
        this.errorSound = new AudioClip(errorPath);
        this.errorSound.setVolume(0.5);
        errorSound.setPriority(1);
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
