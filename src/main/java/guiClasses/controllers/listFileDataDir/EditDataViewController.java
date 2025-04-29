package guiClasses.controllers.listFileDataDir;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.entities.Game;
import model.entities.TypeDLC;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditDataViewController implements Initializable {
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
    private Label lbWarning;

    @FXML
    private ToggleGroup tgDLC;

    @FXML
    private Button btEditLine;

    private void onBtEditLineClick(){
        // criar um obj gameEdited e verificar se é iguial ao gameToEdit
        //se for informar o usuário q nenhum dado foi alterado
        // se for diferente percorrer a list até encontrar o obj igual ao gameToEdit e editar ele com os dados do gameEdited
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
        cbPlataforms.getItems().addAll(plataforms);

        // spinner config
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10);
        valueFactory.setValue(0);

        spnRating.setValueFactory(valueFactory);
        spnRating.getEditor().setDisable(true);
        spnRating.getEditor().setOpacity(1.0);

        // adicionado os valores do obj no formulario para facilitar a edição
        Game gameToEdit = ListFileDataViewController.gameToEdit;
        tfName.setText(gameToEdit.getName());
        cbPlataforms.setValue(gameToEdit.getPlatform());
        spnRating.getValueFactory().setValue(gameToEdit.getRating());

        switch (gameToEdit.getDlc().getStrDLC()) {
            case "Não tem" -> rbNAO_TEM.setSelected(true);
            case "Terminei" -> rbTERMINEI.setSelected(true);
            case "Não terminei" -> rbNAO_TERMINEI.setSelected(true);
            case "É DLC" -> rbE_DLC.setSelected(true);
        }

        switch (gameToEdit.getFinish()){
            case "Sim" -> rbYes.setSelected(true);
            case "Não" -> rbNo.setSelected(true);
        }

        if (gameToEdit.getTextDate() == null){
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
