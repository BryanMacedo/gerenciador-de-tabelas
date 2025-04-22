package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddFirstDataViewController implements Initializable {
    private final Image imgSelected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Selected.png"));
    private final Image imgUnselected = new Image(getClass().getResourceAsStream("/imgs/imgAddData/ic_button_Unselected.png"));

    List<String> plataforms = new ArrayList<>(Arrays.asList
            ("PS5", "PS4", "PS3", "PS2", "PS1", "PSP", "PSVITA", "PC", "XBOX SX"
                    , "XBOX SS", "XBOX ONE", "XBOX 360", "XBOX"));

    private List<String> dlc = new ArrayList<>();
    private List<ImageView> imageViewClicked = new ArrayList<>();

    private List<String>finish = new ArrayList<>();
    private List<ImageView> imageViewFinishClicked = new ArrayList<>();

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

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

    private void clickFinish(String finishStr, ImageView imageView){
        if (finish.isEmpty()){
            finish.add(finishStr);
            imageViewFinishClicked.add(imageView);
            imageViewFinishClicked.get(0).setImage(imgSelected);
        } else if (!finish.get(0).equals(finishStr)) {
            finish.clear();
            finish.add(finishStr);

            imageViewFinishClicked.get(0).setImage(imgUnselected);
            imageViewFinishClicked.clear();

            imageViewFinishClicked.add(imageView);
            imageViewFinishClicked.get(0).setImage(imgSelected);
        } else if (finish.get(0).equals(finishStr)) {
            imageViewFinishClicked.get(0).setImage(imgUnselected);
            imageViewFinishClicked.clear();
            finish.clear();
        }
    }

    private void clickDLC(String dlcStr, ImageView imageView) {
        if (dlc.isEmpty()) {
            dlc.add(dlcStr);
            imageViewClicked.add(imageView);
            imageViewClicked.get(0).setImage(imgSelected);
        } else if (!dlc.get(0).equals(dlcStr)) {
            dlc.clear();
            dlc.add(dlcStr);

            imageViewClicked.get(0).setImage(imgUnselected);
            imageViewClicked.clear();

            imageViewClicked.add(imageView);
            imageViewClicked.get(0).setImage(imgSelected);
        } else if (dlc.get(0).equals(dlcStr)) {
            imageViewClicked.get(0).setImage(imgUnselected);
            imageViewClicked.clear();
            dlc.clear();
        }
    }

    @FXML
    private void onBtAddGameClick(){
        // criar um obj com os dados dos campos preenchidos pelo user
        // usar o obj para salvar no arquivo .xlsx
    }

    @FXML
    private void onHbFinishYesClick() {
        clickFinish("Sim",imgvRbFinishYes);
        if (!finish.isEmpty() && finish.get(0).equals("Sim")){
            tfDate.setDisable(false);
        }else {
            tfDate.clear();
            tfDate.setDisable(true);
        }
    }

    @FXML
    private void onHbFinishNoClick() {
        clickFinish("Não",imgvRbFinishNo);
        tfDate.clear();
        tfDate.setDisable(true);
    }

    @FXML
    private void onHbfinishDLC_Click() {
        clickDLC("Terminei", imgvRbfinishDLC);
    }

    @FXML
    private void onHbUnfinishDLC_Click() {
        clickDLC("Não terminei", imgvRbunfinishDLC);
    }

    @FXML
    private void onHbIsDLC_Click() {
        clickDLC("É DLC", imgvRbisDLC);
    }

    @FXML
    private void onHbDontHaveDLC_Click() {
        clickDLC("Não tem", imgvRbDontHaveDLC);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbPlataforms.getItems().addAll(plataforms);

        // spinner config
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,10);
        valueFactory.setValue(0);

        spnRating.setValueFactory(valueFactory);
        spnRating.getEditor().setDisable(true);
        spnRating.getEditor().setOpacity(1.0);;
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
