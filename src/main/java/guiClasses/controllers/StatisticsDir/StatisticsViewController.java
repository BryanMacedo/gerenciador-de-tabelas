package guiClasses.controllers.StatisticsDir;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class StatisticsViewController implements Initializable {
    private List<Game> gamesToStatistics = new ArrayList<>();

    private AudioClip clickSound;
    private MediaPlayer hover;
    private MediaPlayer ps5;
    private MediaPlayer ps4;
    private MediaPlayer ps3;
    private MediaPlayer ps2;
    private MediaPlayer ps1;
    private MediaPlayer psp;
    private MediaPlayer psVita;
    private MediaPlayer pc;
    private MediaPlayer xboxS;
    private MediaPlayer xboxOne;
    private MediaPlayer xbox360;
    private MediaPlayer xbox;
    private MediaPlayer deck;
    private MediaPlayer nSwitch;
    private Media mediaHover;


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
    private Label lbPlatform;

    @FXML
    private Label lbAverageMonth;

    @FXML
    private Label lbAverageYear;

    @FXML
    private HBox hbLogo;

    @FXML
    private HBox hbListFiles;

    @FXML
    private HBox hbStatistics;

    @FXML
    private void onHbListFilesClick() {
        List<MediaPlayer> sounds = new ArrayList<>(Arrays.asList(ps5, ps4, ps3, ps2, ps1, psp, psVita, pc, xbox, xbox360, xboxOne, xboxS, deck, nSwitch));

        clickSound.play();

        for (MediaPlayer sound : sounds) {
            sound.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/listFilesDir/ListFilesView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onHbLogoClick() {
        // tocar um audio conforme a plataforma mais jogada
        clickSound.play();
        hbLogo.setMouseTransparent(true);
        switch (lbPlatform.getText()) {
            case "PS5" -> {
                ps5.seek(javafx.util.Duration.ZERO);
                ps5.play();
                ps5.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PS4" -> {
                ps4.seek(javafx.util.Duration.ZERO);
                ps4.play();
                ps4.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PS3" -> {
                ps3.seek(javafx.util.Duration.ZERO);
                ps3.play();
                ps3.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PS2" -> {
                ps2.seek(javafx.util.Duration.ZERO);
                ps2.play();
                ps2.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PS1" -> {
                ps1.seek(javafx.util.Duration.ZERO);
                ps1.play();
                ps1.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PSP" -> {
                psp.seek(javafx.util.Duration.ZERO);
                psp.play();
                psp.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PSVITA" -> {
                psVita.seek(javafx.util.Duration.ZERO);
                psVita.play();
                psVita.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "PC" -> {
                pc.seek(javafx.util.Duration.ZERO);
                pc.play();
                pc.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "XBOX SX", "XBOX SS" -> {
                xboxS.seek(javafx.util.Duration.ZERO);
                xboxS.play();
                xboxS.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }

            case "XBOX ONE" -> {
                xboxOne.seek(javafx.util.Duration.ZERO);
                xboxOne.play();
                xboxOne.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }

            case "XBOX 360" -> {
                xbox360.seek(javafx.util.Duration.ZERO);
                xbox360.play();
                xbox360.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }
            case "XBOX" -> {
                xbox.seek(javafx.util.Duration.ZERO);
                xbox.play();
                xbox.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }

            case "STEAM DECK" -> {
                deck.seek(javafx.util.Duration.ZERO);
                deck.play();
                deck.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }

            case "SWITCH", "SWITCH 2" -> {
                nSwitch.seek(javafx.util.Duration.ZERO);
                nSwitch.play();
                nSwitch.setOnEndOfMedia(() -> {
                    hbLogo.setMouseTransparent(false);
                });
            }

        }
    }

    @FXML
    private void onHbNewFileClick() {
        List<MediaPlayer> sounds = new ArrayList<>(Arrays.asList(ps5, ps4, ps3, ps2, ps1, psp, psVita, pc, xbox, xbox360, xboxOne, xboxS, deck, nSwitch));

        clickSound.play();

        for (MediaPlayer sound : sounds) {
            sound.stop();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/bryanmacedo/gui/newFileDir/NewFileView.fxml"));
            Parent root = loader.load();
            Scene scene = imgvClose.getScene();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSounds();

        setInitialsSounds();

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

        Map<String, Integer> counterPlatforms = new HashMap<>();

        for (Game gamePlatform : gamesToStatistics) {
            String platform = gamePlatform.getPlatform();
            counterPlatforms.put(platform, counterPlatforms.getOrDefault(platform, 0) + 1);
        }

        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : counterPlatforms.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        //Média mensal

        Map<String, Integer> counterMonthYear = new HashMap<>();

        DecimalFormat df = new DecimalFormat("#.0");

        for (Game game : gamesToStatistics) {
            if (game.getFinishDate() != null) {
                String monthYear =
                        String.format("%02d-%d",
                                game.getFinishDate().getMonthValue(),
                                game.getFinishDate().getYear()
                        );
                counterMonthYear.put(monthYear, counterMonthYear.getOrDefault(monthYear, 0) + 1);
            }
        }


        int sum = 0;
        for (int quantity : counterMonthYear.values()) {
            sum += quantity;
        }

        double average =  (double) sum / counterMonthYear.size();
        String formattedAverage = df.format(average);

        //Média anual

        Map<String, Integer> counterYear = new HashMap<>();

        for (Game game : gamesToStatistics) {
            if (game.getFinishDate() != null) {
                String year = String.valueOf(game.getFinishDate().getYear());
                counterYear.put(year, counterYear.getOrDefault(year, 0) + 1);
            }
        }

        sum = 0;
        for (int quantity : counterYear.values()) {
            sum += quantity;
        }


        double averageYear = (double) sum / counterYear.size();
        String formattedAverageYear = df.format(averageYear);


        lbFinishedGames.setText(String.valueOf(countGamesFinished));
        lbUnfinishedGames.setText(String.valueOf(countGamesUnfinished));

        if (Double.isNaN(average)){
            lbAverageMonth.setText("0");
        }else{
            lbAverageMonth.setText(String.valueOf(formattedAverage));
        }
        if (Double.isNaN(averageYear)){
            lbAverageYear.setText("0");
        }else{
            lbAverageYear.setText(String.valueOf(formattedAverageYear));
        }

        if (maxEntry != null) {
            lbPlatform.setText(maxEntry.getKey());
        } else {
            lbPlatform.setText("Dados insulficientes");
            hbLogo.setMouseTransparent(true);
        }
        lbMaxRating.setText(String.valueOf(countMaxRating));

    }

    private void setInitialsSounds() {
        List<ImageView> imageViews = new ArrayList<>(Arrays.asList(imgvMinimize, imgvClose));
        List<HBox> hBoxViews = new ArrayList<>(Arrays.asList(hbListFiles, hbNewFile, hbStatistics));

        for (ImageView imgv : imageViews) {
            imgv.setOnMouseEntered(event -> {
                MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
                newHoverPlayer.setVolume(0.1);
                newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
                newHoverPlayer.play();
            });
        }

        for (HBox hbv : hBoxViews) {
            hbv.setOnMouseEntered(event -> {
                MediaPlayer newHoverPlayer = new MediaPlayer(mediaHover);
                newHoverPlayer.setVolume(0.1);
                newHoverPlayer.setOnEndOfMedia(() -> newHoverPlayer.dispose());
                newHoverPlayer.play();
            });
        }
    }

    private void loadSounds() {
        String ClickPath = getClass().getResource("/sounds/click_on_UI_01.mp3").toString();
        this.clickSound = new AudioClip(ClickPath);
        this.clickSound.setVolume(0.1);
        clickSound.setPriority(1);

        String audioFilePs5 = getClass().getResource("/sounds/easter_egg_sounds/ps5.mp3").toString();
        Media mediaPs5 = new Media(audioFilePs5);
        ps5 = new MediaPlayer(mediaPs5);
        ps5.setVolume(0.1);

        String audioFilePs4 = getClass().getResource("/sounds/easter_egg_sounds/ps4.mp3").toString();
        Media mediaPs4 = new Media(audioFilePs4);
        ps4 = new MediaPlayer(mediaPs4);
        ps4.setVolume(0.1);

        String audioFilePs3 = getClass().getResource("/sounds/easter_egg_sounds/ps3.mp3").toString();
        Media mediaPs3 = new Media(audioFilePs3);
        ps3 = new MediaPlayer(mediaPs3);
        ps3.setVolume(0.1);

        String audioFilePs2 = getClass().getResource("/sounds/easter_egg_sounds/ps2.mp3").toString();
        Media mediaPs2 = new Media(audioFilePs2);
        ps2 = new MediaPlayer(mediaPs2);
        ps2.setVolume(0.1);

        String audioFilePs1 = getClass().getResource("/sounds/easter_egg_sounds/ps1.mp3").toString();
        Media mediaPs1 = new Media(audioFilePs1);
        ps1 = new MediaPlayer(mediaPs1);
        ps1.setVolume(0.1);

        String audioFilePsp = getClass().getResource("/sounds/easter_egg_sounds/psp.mp3").toString();
        Media mediaPsp = new Media(audioFilePsp);
        psp = new MediaPlayer(mediaPsp);
        psp.setVolume(0.1);

        String audioFilePsVita = getClass().getResource("/sounds/easter_egg_sounds/psvita.mp3").toString();
        Media mediaPsVita = new Media(audioFilePsVita);
        psVita = new MediaPlayer(mediaPsVita);
        psVita.setVolume(0.1);

        String audioPc = getClass().getResource("/sounds/easter_egg_sounds/pc.mp3").toString();
        Media mediaPc = new Media(audioPc);
        pc = new MediaPlayer(mediaPc);
        pc.setVolume(0.1);

        String audioXboxS = getClass().getResource("/sounds/easter_egg_sounds/xboxS.mp3").toString();
        Media mediaXboxS = new Media(audioXboxS);
        xboxS = new MediaPlayer(mediaXboxS);
        xboxS.setVolume(0.1);

        String audioXboxOne = getClass().getResource("/sounds/easter_egg_sounds/xboxOne.mp3").toString();
        Media mediaXboxOne = new Media(audioXboxOne);
        xboxOne = new MediaPlayer(mediaXboxOne);
        xboxOne.setVolume(0.1);

        String audioXbox360 = getClass().getResource("/sounds/easter_egg_sounds/xbox360.mp3").toString();
        Media mediaXbox360 = new Media(audioXbox360);
        xbox360 = new MediaPlayer(mediaXbox360);
        xbox360.setVolume(0.1);

        String audioXbox = getClass().getResource("/sounds/easter_egg_sounds/xbox.mp3").toString();
        Media mediaXbox = new Media(audioXbox);
        xbox = new MediaPlayer(mediaXbox);
        xbox.setVolume(0.1);

        String audioDeck = getClass().getResource("/sounds/easter_egg_sounds/deck.mp3").toString();
        Media mediaDeck = new Media(audioDeck);
        deck = new MediaPlayer(mediaDeck);
        deck.setVolume(0.1);

        String audioSwitch = getClass().getResource("/sounds/easter_egg_sounds/switch.mp3").toString();
        Media mediaSwitch = new Media(audioSwitch);
        nSwitch = new MediaPlayer(mediaSwitch);
        nSwitch.setVolume(0.1);

        String audioHover = getClass().getResource("/sounds/hover_sound_01.mp3").toString();
        this.mediaHover = new Media(audioHover);
        hover = new MediaPlayer(mediaHover);
        hover.setVolume(0.1);
    }

    @FXML
    private void onImgvCloseClick() {
        clickSound.play();
        Platform.exit();
    }

    @FXML
    private void onImgvMinimizeClick() {
        List<MediaPlayer> sounds = new ArrayList<>(Arrays.asList(ps5, ps4, ps3, ps2, ps1, psp, psVita, pc, xbox, xbox360, xboxOne, xboxS, deck, nSwitch));

        clickSound.play();

        for (MediaPlayer sound : sounds) {
            sound.seek(javafx.util.Duration.ZERO);
            sound.stop();
            hbLogo.setMouseTransparent(false);
        }
        Stage stage = (Stage) imgvMinimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
