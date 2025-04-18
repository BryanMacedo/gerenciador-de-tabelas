package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFileViewController implements Initializable {
    @FXML
    private Button btCreate;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private void onImgvCloseClick(){
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick(){
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onBtCreateAction(){
        /*
        * ao clciar veirificar se tem uma psata chamada tables-GT no disco C
        * Caso n tenha criar pelo java
        * após isso verificar se n existe um arquivo com o mesmo nome q o usuário informou
        * se n existir criar o arquivo excel
        * se existir mostrar um aviso e n criar*/
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
