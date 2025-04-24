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

    private String getStrYesOrNo(){
        if (rbYes.isSelected()){
            return "Sim";
        } else if (rbNo.isSelected()) {
            return "Não";
        }
        return "";
    }


    @FXML
    private void onBtAddGameClick() {
        strRbYesOrNo = getStrYesOrNo();
        Game newGame = new Game(tfName.getText(), cbPlataforms.getValue(),
                spnRating.getValue(), TypeDLC.E_DLC, strRbYesOrNo, dpFinish.getValue());

        System.out.println(newGame);

        // usar o obj para salvar no arquivo .xlsx
    }

    @FXML
    private void onRbYesClick(){
        dpFinish.setDisable(false);
    }

    @FXML
    private void onRbNoClick(){
        dpFinish.setDisable(true);
        dpFinish.getEditor().clear();
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
