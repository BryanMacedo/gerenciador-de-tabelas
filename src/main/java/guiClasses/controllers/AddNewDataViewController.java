package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewDataViewController implements Initializable {
    private List<String> plataforms = new ArrayList<>(Arrays.asList
            ("PS5", "PS4", "PS3", "PS2", "PS1", "PSP", "PSVITA", "PC", "XBOX SX"
                    , "XBOX SS", "XBOX ONE", "XBOX 360", "XBOX"));

    String strRbYesOrNo = null;
    TypeDLC typeDLC = null;

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
    private HBox hbNewFile;

    @FXML
    private HBox hbListFiles;

    private String getStrYesOrNo(){
        if (rbYes.isSelected()){
            return "Sim";
        } else if (rbNo.isSelected()) {
            return "Não";
        }
        return "";
    }

    private TypeDLC getTypeDLC(){
        if (rbE_DLC.isSelected()){
            return TypeDLC.E_DLC;
        } else if (rbTERMINEI.isSelected()) {
            return TypeDLC.TERMINEI;
        }else if (rbNAO_TERMINEI.isSelected()) {
            return TypeDLC.NAO_TERMINEI;
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

        System.out.println(newGame);

        // adicionar na tabela

        writeData(newGame);
    }

    private void writeData(Game game){
        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + ListFileDataViewController.fileNameToAccessFromListData + ".xlsx");
             Workbook workbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream("C:\\tabelas-GT\\" + ListFileDataViewController.fileNameToAccessFromListData + ".xlsx")){

            Sheet sheet = workbook.getSheetAt(0);

            // Encontra a última linha preenchida
            int lastRow = sheet.getLastRowNum();

            // Cria uma nova linha após a última
            Row row = sheet.createRow(lastRow + 1);

            // Preenche os dados nas células
            row.createCell(0).setCellValue(game.getName());
            row.createCell(1).setCellValue(game.getPlatform());
            if (strRbYesOrNo.equals("Não")){
                row.createCell(2).setCellValue("Jogo não finalizado");
            }else{
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

        try {
            ListFilesViewController.fileToAccess = ListFileDataViewController.fileNameToAccessFromListData;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/ListFileDataView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException exc) {
            //System.out.println(e.getMessage());
            exc.printStackTrace();
        }
    }

    @FXML
    private void onRbYesClick(){
        dpFinish.setDisable(false);
    }

    @FXML
    private void onRbNoClick(){
        dpFinish.getEditor().clear();
        dpFinish.setDisable(true);
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
        cbPlataforms.getItems().addAll(plataforms);

        // spinner config
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        valueFactory.setValue(0);

        spnRating.setValueFactory(valueFactory);
        spnRating.getEditor().setDisable(true);
        spnRating.getEditor().setOpacity(1.0);

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
