package guiClasses.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
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
        Path path = Paths.get("C:\\tabelas-GT");
        String fileName = null;
        fileName = tfFileName.getText().trim();

        if (fileName.isEmpty()) {
            System.out.println("Por favor digíte um nome para o arquivo!");
            return;
        }


        //verificando se já existe um arquivo com o nome informado
        String fileNameXlsx = fileName + ".xlsx";

        File checkFile = new File(path.toString(), fileNameXlsx);
        if (checkFile.exists()) {
            System.out.println("Já existe um arquivo com este nome, por favor digite outro.");
            tfFileName.clear();
        } else {
            // criando o arquivo
            Path fullPath = Paths.get(path.toString(), fileName + ".xlsx");

            try (XSSFWorkbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOutputStream = new FileOutputStream(fullPath.toString())) {
                workbook.createSheet(fileName);
                workbook.write(fileOutputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //tirar a criação da pasta daqui quando tiver a tela inicial
        Path path = Paths.get("C://tabelas-GT");

        if (!Files.exists(path) && !Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Pasta não criada." + e.getMessage());
            }
        }
    }
}
