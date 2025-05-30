package guiClasses.controllers.newFileDir;

import guiClasses.controllers.listFilesDir.ListFilesViewController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddFirstDataViewController implements Initializable {
    private final Image imgSelected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Selected.png"));
    private final Image imgUnselected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Unselected.png"));

    private List<String> plataforms = new ArrayList<>(Arrays.asList
            ("PS5", "PS4", "PS3", "PS2", "PS1", "PSP", "PSVITA", "STEAM DECK", "PC", "XBOX SX"
                    , "XBOX SS", "XBOX ONE", "XBOX 360", "XBOX", "SWITCH 2", "SWITCH"));

    private List<String> dlc = new ArrayList<>();
    private List<ImageView> imageViewClicked = new ArrayList<>();

    private List<String> finish = new ArrayList<>();
    private List<ImageView> imageViewFinishClicked = new ArrayList<>();

    private List<Boolean> yesOrNo = new ArrayList<>();
    private List<TypeDLC> typeDLCChoice = new ArrayList<>();

    NewFileViewController controller = new NewFileViewController();

    String strRbYesOrNo = null;
    TypeDLC typeDLC = null;

    private AudioClip clickSound;
    private MediaPlayer hover;
    private AudioClip errorSound;
    private AudioClip typingSound;
    private AudioClip typingDeleteSound;
    private Media mediaHover;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private TextField tfName;

    @FXML
    private ChoiceBox<String> cbPlataforms;

    @FXML
    private HBox hbFinishNo;

    @FXML
    private Spinner<Integer> spnRating;

    @FXML
    private Button btAddGame;

    @FXML
    private DatePicker dpFinish;

    @FXML
    private ToggleGroup tgYesOrNo;

    @FXML
    private ToggleGroup tgDLC;

    @FXML
    private RadioButton rbYes;

    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbNAO_TEM;

    @FXML
    private RadioButton rbTERMINEI;

    @FXML
    private RadioButton rbNAO_JOGUEI;

    @FXML
    private RadioButton rbE_DLC;

    @FXML
    private Label lbWarning;

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
            e.printStackTrace();
        }
    }

    private String getStrYesOrNo() {
        if (rbYes.isSelected()) {
            return "Sim";
        } else if (rbNo.isSelected()) {
            return "Não";
        }
        return "";
    }

    private TypeDLC getTypeDLC() {
        if (rbE_DLC.isSelected()) {
            return TypeDLC.E_DLC;
        } else if (rbTERMINEI.isSelected()) {
            return TypeDLC.TERMINEI;
        } else if (rbNAO_JOGUEI.isSelected()) {
            return TypeDLC.NAO_JOGUEI;
        } else if (rbNAO_TEM.isSelected()) {
            return TypeDLC.NAO_TEM;
        }
        return null;
    }


    @FXML
    private void onBtAddGameClick() {
        strRbYesOrNo = getStrYesOrNo();
        typeDLC = getTypeDLC();

        Game newGame = new Game(tfName.getText(), cbPlataforms.getValue(),
                spnRating.getValue(), typeDLC, strRbYesOrNo, dpFinish.getValue());

        if (tfName.getText().isEmpty() || cbPlataforms.getValue() == null ||
                typeDLC == null || strRbYesOrNo.isEmpty()) {
            lbWarning.setText("Preencha todos os campos para adicionar um jogo.");
            lbWarning.setStyle("-fx-text-fill: #ffffff;");
            errorSound.play();
        } else {
            writeFirstData(newGame);
            clickSound.play();
        }
    }

    private void writeFirstData(Game game) {
        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + NewFileViewController.nameNewFile);
             Workbook workbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream("C:\\tabelas-GT\\" + NewFileViewController.nameNewFile)) {

            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha

            // Encontra a última linha preenchida
            int lastRow = sheet.getLastRowNum();

            // Cria uma nova linha após a última
            Row row = sheet.createRow(lastRow + 1);

            // Preenche os dados nas células
            row.createCell(0).setCellValue(game.getName());
            row.createCell(1).setCellValue(game.getPlatform());
            if (strRbYesOrNo.equals("Não")) {
                row.createCell(2).setCellValue("Jogo não finalizado");
            } else {
                row.createCell(2).setCellValue(game.getFinishDate().toString());
            }
            row.createCell(3).setCellValue(game.getRating());
            row.createCell(4).setCellValue(game.getDlc().toString());
            row.createCell(5).setCellValue(game.getFinish());

            // salva no arquivo
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ListFilesViewController.fileToAccess = NewFileViewController.nameNewFile.replaceAll(".xlsx", "");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/ListFileDataView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRbYesClick() {
        dpFinish.setDisable(false);
        LocalDate DateNow = LocalDate.now();
        dpFinish.setValue(DateNow);
    }

    @FXML
    private void onRbNoClick() {
        dpFinish.getEditor().clear();
        dpFinish.setDisable(true);
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSounds();

        setInitialsSounds();

        cbPlataforms.getItems().addAll(plataforms);

        // spinner config
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        valueFactory.setValue(0);

        spnRating.setValueFactory(valueFactory);
        spnRating.getEditor().setDisable(true);
        spnRating.getEditor().setOpacity(1.0);

        ObservableList<Node> form = FXCollections.observableArrayList(
                tfName, cbPlataforms, spnRating,
                rbNo,rbYes,rbTERMINEI,rbNAO_JOGUEI,rbNAO_TEM,rbE_DLC, dpFinish);

        for (Node node : form) {
            node.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    btAddGame.fire();
                }
            });
        }
    }

    private void setInitialsSounds() {
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<RadioButton> rbs = new ArrayList<>(Arrays.asList(rbE_DLC, rbNAO_TEM, rbNAO_JOGUEI, rbTERMINEI, rbYes, rbNo));
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

        btAddGame.setOnMouseEntered(event -> {
            MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
            newHoverPlayer.setVolume(0.1);
            newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
            newHoverPlayer.play();
        });

        tfName.setOnMouseClicked(event -> {
            clickSound.play();
        });

        cbPlataforms.setOnMouseClicked(event -> {
            clickSound.play();
        });

        cbPlataforms.setOnAction(event -> {
            if (cbPlataforms.getValue() != null) {
                clickSound.play();
            }
        });

        spnRating.setOnMouseClicked(event -> {
            clickSound.play();
        });

        for (RadioButton rb : rbs) {
            rb.setOnMouseClicked(event -> {
                clickSound.play();
            });
        }

        dpFinish.setOnMouseClicked(event -> {
            clickSound.play();
        });

        dpFinish.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                clickSound.play();
            }
        });

        tfName.textProperty().addListener((observable, oldValue, newValue) -> {
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
