package org.bryanmacedo.projetogerenciadordetabelas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        //primaryStage.initStyle(StageStyle.UNDECORATED); //tira a barra superior
        Parent root = FXMLLoader.load(getClass().getResource("/org/bryanmacedo/gui/MainView.fxml"));
        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root, 1445, 833));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}