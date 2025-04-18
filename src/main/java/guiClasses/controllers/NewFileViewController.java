package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class NewFileViewController implements Initializable {
    @FXML
    private Button btCreate;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private TextField tfFileName;

    @FXML
    private void onImgvCloseClick() {
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick() {
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void onBtCreateAction() {
        Path path = Paths.get("C://tabelas-GT");
        String fileName = null;

        if (!Files.exists(path) && !Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Pasta não criada." + e.getMessage());
            }
        }

        if (!tfFileName.equals("")) {
            fileName = tfFileName.getText() + ".xlsx";
        } else {
            System.out.println("Por favor digíte um nome para o arquivo!");
        }
        System.out.println("O usuário digítou: " + fileName);

        String fullPath = path + "\\" + fileName;

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
