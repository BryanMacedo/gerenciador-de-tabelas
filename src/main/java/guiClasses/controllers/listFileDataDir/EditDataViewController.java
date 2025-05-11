package guiClasses.controllers.listFileDataDir;

import guiClasses.controllers.listFilesDir.ListFilesViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditDataViewController implements Initializable {
    private List<String> plataforms = new ArrayList<>(Arrays.asList
            ("PS5", "PS4", "PS3", "PS2", "PS1", "PSP", "PSVITA", "STEAM DECK", "PC", "XBOX SX"
                    , "XBOX SS", "XBOX ONE", "XBOX 360", "XBOX", "SWITCH 2", "SWITCH"));
    private List<String> columns = new ArrayList<>(Arrays.asList("Nome", "Plataforma", "Data de termino", "Nota", "DLC", "Finalizado"));


    String strRbYesOrNo = null;
    TypeDLC typeDLC = null;
    Game gameToEdit;
    Game gameEdited;

    private AudioClip clickSound;
    private AudioClip hoverSound;
    private AudioClip errorSound;
    private AudioClip typingSound;
    private AudioClip typingDeleteSound;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private TextField tfName;

    @FXML
    private ChoiceBox<String> cbPlataforms;

    @FXML
    private Spinner<Integer> spnRating;

    @FXML
    private DatePicker dpFinish;

    @FXML
    private RadioButton rbYes;

    @FXML
    private RadioButton rbNo;

    @FXML
    private RadioButton rbNAO_TEM;

    @FXML
    private RadioButton rbTERMINEI;

    @FXML
    private RadioButton rbNAO_TERMINEI;

    @FXML
    private RadioButton rbE_DLC;

    @FXML
    private Label lbWarning;

    @FXML
    private ToggleGroup tgDLC;

    @FXML
    private Button btEditLine;

    @FXML
    private HBox hbListFiles;

    @FXML
    private HBox hbNewFile;

    @FXML
    private HBox hbProfile;

    @FXML
    private void onBtEditLineClick() {
        strRbYesOrNo = getStrYesOrNo();
        typeDLC = getTypeDLC();

        gameEdited = new Game(tfName.getText(), cbPlataforms.getValue(),
                spnRating.getValue(), typeDLC, strRbYesOrNo, dpFinish.getValue());

        System.out.println("gameToEdit: " + gameToEdit);
        System.out.println("gameEdited: " + gameEdited);

        if (tfName.getText().isEmpty() || cbPlataforms.getValue() == null ||
                typeDLC == null || strRbYesOrNo.isEmpty()) {
            lbWarning.setStyle("-fx-text-fill: #ffffff;");
            errorSound.play();
        } else if (gameToEdit.equals(gameEdited)) {
            lbWarning.setText("Edite os dados para alterar a linha.");
            lbWarning.setStyle("-fx-text-fill: #ffffff;");
            errorSound.play();
        } else {
            clickSound.play();
            List<Game> listAux = new ArrayList<>();

            for (Game game : ListFileDataViewController.games) {
                listAux.add(game);
            }
            ListFileDataViewController.games.clear();

            for (Game aux : listAux) {
                if (!aux.equals(gameToEdit)) {
                    ListFileDataViewController.games.add(aux);
                } else {
                    ListFileDataViewController.games.add(gameEdited);
                }
            }

            System.out.println(ListFileDataViewController.games);

            //excluir a tabela e criar outra com o mesmo nome
            File fileToDelete = new File("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx");
            fileToDelete.delete();

            Path path = Paths.get("C:\\tabelas-GT");

            Path fullPath = Paths.get(path.toString(), ListFilesViewController.fileToAccess + ".xlsx");

            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOutputStream = new FileOutputStream(fullPath.toString())) {

                // cria a planilha
                XSSFSheet sheet = workbook.createSheet(ListFilesViewController.fileToAccess);

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

            for (Game game : ListFileDataViewController.games) {
                writeData(game);
            }

            try {
                ListFilesViewController.fileToAccess = ListFileDataViewController.fileNameToAccessFromListData;

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/ListFileDataView.fxml"));
                Parent root = loader.load();
                Scene scene = imgvClose.getScene();
                scene.setRoot(root);
            } catch (IOException exc) {
                //System.out.println(e.getMessage());
                exc.printStackTrace();
            }


        }

    }


    private void writeData(Game game) {
        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + ListFileDataViewController.fileNameToAccessFromListData + ".xlsx");
             Workbook workbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream("C:\\tabelas-GT\\" + ListFileDataViewController.fileNameToAccessFromListData + ".xlsx")) {

            Sheet sheet = workbook.getSheetAt(0);

            // Encontra a última linha preenchida
            int lastRow = sheet.getLastRowNum();

            // Cria uma nova linha após a última
            Row row = sheet.createRow(lastRow + 1);

            // Preenche os dados nas células
            row.createCell(0).setCellValue(game.getName());
            row.createCell(1).setCellValue(game.getPlatform());

            if (game.getFinish().equals("Não")) {
                row.createCell(2).setCellValue("Jogo não finalizado");
            } else {
                row.createCell(2).setCellValue(game.getFinishDate().toString());
            }
            row.createCell(3).setCellValue(game.getRating());
            row.createCell(4).setCellValue(game.getDlc().toString());
            row.createCell(5).setCellValue(game.getFinish());

            // salva no arquivo
            workbook.write(fos);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        } else if (rbNAO_TERMINEI.isSelected()) {
            return TypeDLC.NAO_TERMINEI;
        } else if (rbNAO_TEM.isSelected()) {
            return TypeDLC.NAO_TEM;
        }
        return null;
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
            //System.out.println(e.getMessage());
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
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<RadioButton> rbs = new ArrayList<>(Arrays.asList(rbE_DLC, rbNAO_TEM, rbNAO_TERMINEI, rbTERMINEI, rbYes, rbNo));
        List<HBox> hBoxViews = new ArrayList<>(Arrays.asList(hbListFiles, hbNewFile, hbProfile));

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

        String typingPath = getClass().getResource("/sounds/typing_sound_01.mp3").toString();
        this.typingSound = new AudioClip(typingPath);
        this.typingSound.setVolume(0.3);
        typingSound.setPriority(1);

        String typingDeletePath = getClass().getResource("/sounds/delete_typing_sound_01.mp3").toString();
        this.typingDeleteSound = new AudioClip(typingDeletePath);
        this.typingDeleteSound.setVolume(0.3);
        typingDeleteSound.setPriority(1);

        //audios
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


        cbPlataforms.getItems().addAll(plataforms);

        // spinner config
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        valueFactory.setValue(0);

        spnRating.setValueFactory(valueFactory);
        spnRating.getEditor().setDisable(true);
        spnRating.getEditor().setOpacity(1.0);

        // adicionado os valores do obj no formulario para facilitar a edição
        gameToEdit = ListFileDataViewController.gameToEdit;
        tfName.setText(gameToEdit.getName());
        cbPlataforms.setValue(gameToEdit.getPlatform());
        spnRating.getValueFactory().setValue(gameToEdit.getRating());

        switch (gameToEdit.getDlc().getStrDLC()) {
            case "Não tem" -> rbNAO_TEM.setSelected(true);
            case "Terminei" -> rbTERMINEI.setSelected(true);
            case "Não terminei" -> rbNAO_TERMINEI.setSelected(true);
            case "É DLC" -> rbE_DLC.setSelected(true);
        }

        switch (gameToEdit.getFinish()) {
            case "Sim" -> rbYes.setSelected(true);
            case "Não" -> rbNo.setSelected(true);
        }

        if (gameToEdit.getTextDate() == null) {
            dpFinish.setValue(gameToEdit.getFinishDate());
            dpFinish.setDisable(false);
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
