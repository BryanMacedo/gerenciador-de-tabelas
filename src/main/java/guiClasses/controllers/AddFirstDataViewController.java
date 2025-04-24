package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.entities.Game;
import model.entities.TypeDLC;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddFirstDataViewController implements Initializable {
    private final Image imgSelected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Selected.png"));
    private final Image imgUnselected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Unselected.png"));

    private List<String> plataforms = new ArrayList<>(Arrays.asList
            ("PS5", "PS4", "PS3", "PS2", "PS1", "PSP", "PSVITA", "PC", "XBOX SX"
                    , "XBOX SS", "XBOX ONE", "XBOX 360", "XBOX"));

    private List<String> dlc = new ArrayList<>();
    private List<ImageView> imageViewClicked = new ArrayList<>();

    private List<String> finish = new ArrayList<>();
    private List<ImageView> imageViewFinishClicked = new ArrayList<>();

    private List<Boolean> yesOrNo = new ArrayList<>();
    private List<TypeDLC> typeDLCChoice = new ArrayList<>();

    String strRbYesOrNo = null;
    TypeDLC typeDLC = null;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private TextField tfName;

    @FXML
    private Label finishDLC;

    @FXML
    private Label unfinishDLC;

    @FXML
    private Label isDLC;

    @FXML
    private ImageView imgvRbfinishDLC;
    @FXML
    private ImageView imgvRbunfinishDLC;
    @FXML
    private ImageView imgvRbisDLC;
    @FXML
    private ImageView imgvRbDontHaveDLC;

    @FXML
    private ImageView imgvRbFinishYes;

    @FXML
    private ImageView imgvRbFinishNo;


    @FXML
    private HBox hbfinishDLC;

    @FXML
    private HBox hbUnfinishDLC;

    @FXML
    private HBox hbUnIsDLC;

    @FXML
    private HBox hbDontHaveDLC;

    @FXML
    private ChoiceBox<String> cbPlataforms;

    @FXML
    private HBox hbFinishYes;

    @FXML
    private HBox hbFinishNo;

    @FXML
    private TextField tfDate;

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
    private RadioButton rbNAO_TERMINEI;

    @FXML
    private RadioButton rbE_DLC;

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

        writeFirstData(newGame);

    }

    private void writeFirstData(Game game){
        try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\jogos2025.xlsx");
             Workbook workbook = WorkbookFactory.create(fis);
             FileOutputStream fos = new FileOutputStream("C:\\tabelas-GT\\jogos2025.xlsx")) {

            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha

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
        }catch (IOException e){
            e.printStackTrace();
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
        ;
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
