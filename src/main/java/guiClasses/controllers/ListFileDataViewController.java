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
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private Label lbTableName;

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

    @FXML void onHbInsertNewGameClick(){
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
    private void onHbListFilesClick(){
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

                    if (row.getCell(2).toString().equals("Jogo não finalizado")){
                        String textDate = row.getCell(2).toString();
                        newGame = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                rating, dlc, row.getCell(5).toString(), textDate);
                    }else{
                        LocalDate date = LocalDate.parse(row.getCell(2).toString());
                        newGame = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                rating, dlc, row.getCell(5).toString(), date);
                    }

                    Label labelName = new Label(newGame.getName());
                    labelName.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 363; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelPlataform = new Label(newGame.getPlatform());
                    labelPlataform.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 5px; -fx-pref-width: 142; -fx-pref-height: 37; -fx-alignment: center;");

                    Label labelDate;

                    if (newGame.getFinishDate() == null){
                        labelDate = new Label(newGame.getTextDate());
                    }else {
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
