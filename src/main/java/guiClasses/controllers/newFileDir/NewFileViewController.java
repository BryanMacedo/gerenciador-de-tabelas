package guiClasses.controllers.newFileDir;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NewFileViewController implements Initializable {
    private List<String> columns = new ArrayList<>(Arrays.asList("Nome", "Plataforma", "Data de termino", "Nota", "DLC", "Finalizado"));
    public static String nameNewFile;

    private AudioClip clickSound;
    private MediaPlayer hover;
    private AudioClip errorSound;
    private AudioClip typingSound;
    private AudioClip typingDeleteSound;
    private Media mediaHover;

    @FXML
    private Button btCreate;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private TextField tfFileName;

    @FXML
    private HBox hbListFiles;

    @FXML
    private HBox hbNewFile;

    @FXML
    private HBox hbStatistics;

    @FXML
    private Label lbWarning;

    @FXML
    private void onHbStatistics() {
        clickSound.play();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/StatisticsDir/StatisticsView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHbListFilesClick() {
        clickSound.play();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFilesDir/ListFilesView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onBtCreateAction() {
        Path path = Paths.get("C:\\tabelas-GT");
        String fileName = tfFileName.getText().trim();

        if (fileName.isEmpty()) {
            lbWarning.setText("Por favor, digite um nome para o arquivo.");
            errorSound.play();
            return;
        }

        //verificando se já existe um arquivo com o nome informado
        String fileNameXlsx = fileName + ".xlsx";
        nameNewFile = fileNameXlsx;

        File checkFile = new File(path.toString(), fileNameXlsx);
        if (checkFile.exists()) {
            errorSound.play();
            lbWarning.setText("Já existe uma tabela com este nome, por favor digite outro.");
            tfFileName.clear();
            return;
        } else {
            clickSound.play();

            // criando o arquivo
            Path fullPath = Paths.get(path.toString(), fileName + ".xlsx");

            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOutputStream = new FileOutputStream(fullPath.toString())) {

                // cria a planilha
                XSSFSheet sheet = workbook.createSheet(fileName);

                //cria a primeira linha que será usada como cabeçalho/colunas
                Row headerRow = sheet.createRow(0);

                for (int i = 0; i < columns.size(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns.get(i));
                }

                //Deixa o tamanho da célula igual ao do seu conteúdo
                for (int i = 0; i < columns.size(); i++) {
                    sheet.autoSizeColumn(i);
                }

                workbook.write(fileOutputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/newFileDir/AddFirstDataView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSounds();

        setInitialsSounds();

        tfFileName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btCreate.fire();
            }
        });
    }

    private void setInitialsSounds() {
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<HBox> hBoxViews = new ArrayList<>(Arrays.asList(hbListFiles, hbNewFile, hbStatistics));

        for (ImageView imgv : imageViews) {
            imgv.setOnMouseEntered(event -> {
                MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
                newHoverPlayer.setVolume(0.1);
                newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
                newHoverPlayer.play();
            });
        }

        for (HBox hbv : hBoxViews) {
            hbv.setOnMouseEntered(event -> {
                MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
                newHoverPlayer.setVolume(0.1);
                newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
                newHoverPlayer.play();
            });
        }

        btCreate.setOnMouseEntered(event -> {
            MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
            newHoverPlayer.setVolume(0.1);
            newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
            newHoverPlayer.play();
        });

        tfFileName.setOnMouseClicked(event -> {
            clickSound.play();
        });

        tfFileName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > oldValue.length()) {
                typingSound.play();
            } else {
                typingDeleteSound.play();
            }
        });
    }

    private void loadSounds() {
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        String audioHover = getClass().getResource("/sounds/hover_sound_01.mp3").toString();
        this.mediaHover = new Media(audioHover);
        hover = new MediaPlayer(mediaHover);
        hover.setVolume(0.1);

        String errorPath = getClass().getResource("/sounds/error_sound_01.mp3").toString();
        this.errorSound = new AudioClip(errorPath);
        this.errorSound.setVolume(0.5);
        errorSound.setPriority(1);

        String typingPath = getClass().getResource("/sounds/typing_sound_01.mp3").toString();
        this.typingSound = new AudioClip(typingPath);
        this.typingSound.setVolume(0.3);
        typingSound.setPriority(1);

        String typingDeletePath = getClass().getResource("/sounds/delete_typing_sound_01.mp3").toString();
        this.typingDeleteSound = new AudioClip(typingDeletePath);
        this.typingDeleteSound.setVolume(0.3);
        typingDeleteSound.setPriority(1);
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
