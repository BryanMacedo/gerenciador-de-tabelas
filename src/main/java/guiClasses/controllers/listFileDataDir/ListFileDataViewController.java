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
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ListFileDataViewController implements Initializable {
    public static String fileNameToAccessFromListData;
    public static List<Game> games = new ArrayList<>();
    private List<HBox> hBoxList = new ArrayList<>();
    static Game gameToEdit;
    private int optionFunction;
    private List<String> columns = new ArrayList<>(Arrays.asList("Nome", "Plataforma", "Data de termino", "Nota", "DLC", "Finalizado"));

    DateTimeFormatter dateTimeFormatterBR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private AudioClip clickSound;
    private MediaPlayer hover;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private VBox vbList;

    @FXML
    private HBox hbListFiles;

    @FXML
    private HBox hbInsertNewGame;

    @FXML
    private HBox hbNewFile;

    @FXML
    private HBox hbDeleteFile;

    @FXML
    private HBox hbEditGameLine;

    @FXML
    private HBox hbDeleteLine;

    @FXML
    private Label lbTableName;

    @FXML
    private Label lbTip;

    @FXML
    private HBox hbStatistics;

    @FXML
    private void onHbStatistics(){
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


    private void warningDelete(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText(msg);

        // tira a imagem do Alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setGraphic(null);

        dialogPane.setStyle(
                "-fx-background-color: #272727; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-border-color: #FFFFFF; " +
                        "-fx-border-width: 5px; " +
                        "-fx-border-radius: 20px;"
        );

        Stage stage = (Stage) dialogPane.getScene().getWindow();

        Stage mainStage = (Stage) imgvMinimize.getScene().getWindow();
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();

        alertStage.setX(mainStage.getX() + mainStage.getWidth() - 810);
        alertStage.setY(mainStage.getY() + 390);

        stage.initStyle(StageStyle.TRANSPARENT);
        dialogPane.getScene().setFill(Color.TRANSPARENT);

        dialogPane.getStyleClass().add("custom-dialog");
        dialogPane.setStyle(dialogPane.getStyle() +
                " -fx-text-fill: white; ");

        Label contentLabel = (Label) dialogPane.lookup(".content.label");
        if (contentLabel != null) {
            contentLabel.setStyle(
                    "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-weight: bold;"
            );
        }

        ButtonType btYes = new ButtonType("Sim");
        ButtonType btNo = new ButtonType("Não");

        alert.getButtonTypes().setAll(btYes, btNo);

        Button btnYes = (Button) dialogPane.lookupButton(btYes);
        Button btnNo = (Button) dialogPane.lookupButton(btNo);


        btnYes.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");

        btnYes.setOnMouseEntered(e -> {
            btnYes.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-radius: 20px; -fx-border-width: 2px;");
            hover.seek(javafx.util.Duration.ZERO);
            hover.play();
        });
        btnYes.setOnMouseExited(e -> {
            btnYes.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");

        });

        btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");

        btnNo.setOnMouseEntered(e -> {
            btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-radius: 20px; -fx-border-width: 2px;");
            hover.seek(javafx.util.Duration.ZERO);
            hover.play();
        });

        btnNo.setOnMouseExited(e -> {
            btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");
        });

        Optional<ButtonType> result = alert.showAndWait();


        if (result.isPresent() && result.get() == btYes) {
            clickSound.play();
            switch (optionFunction){
                case 2->{
                    //excluir linha
                    List<Game> listAux = new ArrayList<>();

                    for (Game game : games) {
                        listAux.add(game);
                    }
                    games.clear();

                    for (Game aux : listAux) {
                        if (!aux.equals(gameToEdit)) {
                            games.add(aux);
                        }
                    }

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
                        for(int i = 0; i < columns.size(); i++) {
                            sheet.autoSizeColumn(i);
                        }

                        workbook.write(fileOutputStream);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    for(Game game : games){
                        writeData(game);
                    }

                    // abre novamente a view atual

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/ListFileDataView.fxml"));
                        Parent root = loader.load();
                        Scene scene = imgvClose.getScene();
                        scene.setRoot(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                case 3 ->{
                    File fileToDelete = new File("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx");
                    fileToDelete.delete();

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFilesDir/ListFilesView.fxml"));
                        Parent root = loader.load();
                        Scene scene = imgvClose.getScene();
                        scene.setRoot(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }else {
            clickSound.play();
        }
    }

    private void writeData(Game game) {
        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx");
             Workbook workbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx")) {

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

    @FXML
    private void onHbDeleteFileClick() {
        clickSound.play();
        lbTip.setText("");
        optionFunction = 3;
        warningDelete("Tem certeza que deseja excluir a tabela " + ListFilesViewController.fileToAccess + "?");
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
    private void onHbDeleteLineClick(){
        clickSound.play();
        lbTip.setText("Clique na linha que deseja excluir.");

        optionFunction = 2;

        for (HBox hBox : hBoxList) {
            hBox.setMouseTransparent(false);
            hBox.setStyle("-fx-cursor: hand; -fx-alignment: top_center;");
        }
    }

    @FXML
    private void onHbEditGameLineClick(){
        clickSound.play();
        lbTip.setText("Clique na linha que deseja editar.");

        optionFunction = 1;

        for (HBox hBox : hBoxList) {
            hBox.setMouseTransparent(false);
            hBox.setStyle("-fx-cursor: hand; -fx-alignment: top_center;");
        }
    }

    @FXML
    private void onHbInsertNewGameClick() {
        clickSound.play();
        try {
            fileNameToAccessFromListData = ListFilesViewController.fileToAccess;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/AddNewDataView.fxml"));
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

        games.clear();

        lbTableName.setText(ListFilesViewController.fileToAccess);

        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    int rating = (int) row.getCell(3).getNumericCellValue();
                    TypeDLC dlc = TypeDLC.valueOf(row.getCell(4).toString());
                    Game newGame;

                    if (row.getCell(2).toString().equals("Jogo não finalizado")) {
                        String textDate = row.getCell(2).toString();
                        newGame = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                rating, dlc, row.getCell(5).toString(), textDate);
                    } else {
                        LocalDate date = LocalDate.parse(row.getCell(2).toString());
                        newGame = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                rating, dlc, row.getCell(5).toString(), date);
                    }

                   games.add(newGame);

                    Label labelName = new Label(newGame.getName());
                    labelName.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 363; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    Label labelPlataform = new Label(newGame.getPlatform());
                    labelPlataform.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 142; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    Label labelDate;

                    if (newGame.getFinishDate() == null) {
                        labelDate = new Label(newGame.getTextDate());
                    } else {
                        labelDate = new Label(newGame.getFinishDate().format(dateTimeFormatterBR));
                    }

                    labelDate.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 192; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    Label labelRating = new Label(String.valueOf(newGame.getRating()));
                    labelRating.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 72; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    Label labelDLC = new Label(newGame.getDlc().getStrDLC());
                    labelDLC.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 134; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    Label labelFinish = new Label(newGame.getFinish());
                    labelFinish.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 132; -fx-pref-height: 37; -fx-alignment: center; -fx-background-color: #272727;");

                    HBox newHbox = new HBox(labelName, labelPlataform, labelRating, labelDLC, labelFinish, labelDate);
                    newHbox.setStyle("-fx-alignment: top_center;");

                    newHbox.setMouseTransparent(true);

                    newHbox.setOnMouseClicked(e -> {
                        clickSound.play();
                        gameToEdit = newGame;

                        switch (optionFunction){
                            case 1 ->{
                                try {
                                    fileNameToAccessFromListData = ListFilesViewController.fileToAccess;
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFileDataDir/EditDataView.fxml"));
                                    Parent root = loader.load();
                                    Scene scene = imgvClose.getScene();
                                    scene.setRoot(root);
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            case 2 ->{
                                warningDelete("Tem certeza que deseja excluir esta Linha?");
                            }
                        }
                    });
                    vbList.getChildren().add(newHbox);
                    hBoxList.add(newHbox);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    private void setInitialsSounds(){
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<HBox> hBoxeBtns = new ArrayList<>(Arrays.asList(hbInsertNewGame, hbEditGameLine, hbDeleteLine, hbDeleteFile));
        List<HBox> hBoxViews = new ArrayList<>(Arrays.asList(hbListFiles, hbNewFile, hbStatistics));

        for (HBox hBox : hBoxeBtns) {
            hBox.setOnMouseEntered(event -> {
                //hoverSound.stop();
                hover.seek(javafx.util.Duration.ZERO);
                hover.play();
            });
        }

        for (ImageView imgv : imageViews) {
            imgv.setOnMouseEntered(event -> {
                hover.seek(javafx.util.Duration.ZERO);
                hover.play();
            });
        }

        for (HBox hbv : hBoxViews) {
            hbv.setOnMouseEntered(event -> {
                hover.seek(javafx.util.Duration.ZERO);
                hover.play();
            });
        }
    }

    private void loadSounds(){
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        String audioHover = getClass().getResource("/sounds/hover_sound_01.mp3").toString();
        Media mediaHover = new Media(audioHover);
        hover = new MediaPlayer(mediaHover);
        hover.setVolume(0.1);
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
