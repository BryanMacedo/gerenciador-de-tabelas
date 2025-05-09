package guiClasses.controllers.StatisticsDir;

import guiClasses.controllers.listFilesDir.ListFilesViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
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
import java.lang.reflect.Array;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class StatisticsViewController implements Initializable {
    private List<Game> gamesToStatistics = new ArrayList<>();
    private AudioClip clickSound;

    @FXML
    private ImageView imgvClose;

    @FXML
    private ImageView imgvMinimize;

    @FXML
    private HBox hbNewFile;

    @FXML
    private Label lbFinishedGames;

    @FXML
    private Label lbUnfinishedGames;

    @FXML
    private Label lbMaxRating;

    @FXML
    private void onHbNewFileClick() {
        clickSound.play();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        // pega os nomes das tabelas
        File path = new File("C:\\tabelas-GT");
        File[] files = path.listFiles((dir, name) -> name.endsWith(".xlsx"));

        for (File file : files) {
            try (FileInputStream fis = new FileInputStream("C:\\tabelas-GT\\" + file.getName());
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);

                    if (row != null) {
                        int rating = (int) row.getCell(3).getNumericCellValue();
                        TypeDLC dlc = TypeDLC.valueOf(row.getCell(4).toString());
                        Game game;

                        if (row.getCell(2).toString().equals("Jogo não finalizado")) {
                            String textDate = row.getCell(2).toString();
                            game = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                    rating, dlc, row.getCell(5).toString(), textDate);
                        } else {
                            LocalDate date = LocalDate.parse(row.getCell(2).toString());
                            game = new Game(row.getCell(0).toString(), row.getCell(1).toString(),
                                    rating, dlc, row.getCell(5).toString(), date);
                        }

                        gamesToStatistics.add(game);
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int countGamesFinished = 0;
        int countGamesUnfinished = 0;
        int countMaxRating = 0;



        for (Game game : gamesToStatistics) {
            // quantidades de jogos zerados / n zerdos
            if (game.getTextDate() != null) {
                countGamesUnfinished++;
            } else {
                countGamesFinished++;
            }

            // qnt d jogos com nota maxima
            if (game.getRating() == 10) {
                countMaxRating++;
            }
        }

        //plataforma mais jogada

//        Map<String, Integer> counter = new HashMap<>();
//
//        for (Game gamePlatform : gamesToStatistics) {
//            String plataforma = gamePlatform.getPlatform();
//            counter.put(plataforma, counter.getOrDefault(plataforma, 0) + 1);
//        }
//
//        System.out.println(counter);


        lbFinishedGames.setText(String.valueOf(countGamesFinished));
        lbUnfinishedGames.setText(String.valueOf(countGamesUnfinished));
        lbMaxRating.setText(String.valueOf(countMaxRating));
    }

    @FXML
    private void onImgvCloseClick() {
        clickSound.play();
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick() {
        clickSound.play();
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
