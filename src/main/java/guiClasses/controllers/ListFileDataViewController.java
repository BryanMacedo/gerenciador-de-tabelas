package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListFileDataViewController implements Initializable {
    public static String fileNameToAccessFromListData;
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
    private Label lbTableName;


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
                        "-fx-background-radius: 20px; " +
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
        });
        btnYes.setOnMouseExited(e -> {
            btnYes.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");

        });



        btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");

        btnNo.setOnMouseEntered(e -> {
            btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: #ffffff; -fx-border-radius: 20px; -fx-border-width: 2px;");
        });

        btnNo.setOnMouseExited(e -> {
            btnNo.setStyle("-fx-background-color: transparent; -fx-background-radius: 15px; -fx-text-fill: white; -fx-font-size: 18px; -fx-cursor: hand; -fx-font-weight: bold; -fx-border-color: transparent; -fx-border-radius: 20px; -fx-border-width: 2px;");
        });

        Optional<ButtonType> result = alert.showAndWait();


        if (result.isPresent() && result.get() == btYes) {
            File fileToDelete = new File("C:\\tabelas-GT\\" + ListFilesViewController.fileToAccess + ".xlsx");
            fileToDelete.delete();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/ListFilesView.fxml"));
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
    private void onHbDeleteFileClick() {
        warningDelete("Tem certeza que deseja excluir a tabela " + ListFilesViewController.fileToAccess + "?");
    }

    @FXML
    private void onHbNewFileClick() {
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

    @FXML
    void onHbInsertNewGameClick() {
        try {
            fileNameToAccessFromListData = ListFilesViewController.fileToAccess;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/AddNewDataView.fxml"));
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/ListFilesView.fxml"));
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
        System.out.println(ListFilesViewController.fileToAccess);

        lbTableName.setText(" Nome da tabela: " + ListFilesViewController.fileToAccess);

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

                    Label labelName = new Label(newGame.getName());
                    labelName.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 363; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelPlataform = new Label(newGame.getPlatform());
                    labelPlataform.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 142; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelDate;

                    if (newGame.getFinishDate() == null) {
                        labelDate = new Label(newGame.getTextDate());
                    } else {
                        labelDate = new Label(newGame.getFinishDate().toString());
                    }

                    labelDate.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 182; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelRating = new Label(String.valueOf(newGame.getRating()));
                    labelRating.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 72; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelDLC = new Label(newGame.getDlc().getStrDLC());
                    labelDLC.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 134; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelFinish = new Label(newGame.getFinish());
                    labelFinish.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 122; -fx-pref-height: 37; -fx-alignment: center;");

                    HBox newHbox = new HBox(labelName, labelPlataform, labelRating, labelDLC, labelFinish, labelDate
                    );
                    newHbox.setStyle("-fx-alignment: top_center;");

                    vbList.getChildren().add(newHbox);
                }
            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }

    }

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
