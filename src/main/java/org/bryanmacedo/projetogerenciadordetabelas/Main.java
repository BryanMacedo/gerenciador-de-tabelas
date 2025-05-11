package org.bryanmacedo.projetogerenciadordetabelas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/org/bryanmacedo/gui/InitialDir/InitialView.fxml"));
        Scene scene = new Scene(root, 1445, 833);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("G-Tables");
        primaryStage.setScene(scene);
        primaryStage.show();

        Image icon = new Image(getClass().getResourceAsStream("/imgs/imgAppIcon/ic_app_icon.png"));
        primaryStage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch();
    }
}