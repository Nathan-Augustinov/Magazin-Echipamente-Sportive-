package org.loose.fis.proiect;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.loose.fis.proiect.services.FileSystemService;
import org.loose.fis.proiect.services.UserService;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main extends Application {

    @Override
    public void start (Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StartPage.fxml"));
        primaryStage.setTitle("Magazin de Echipamente Sportive");
        primaryStage.setScene(new Scene(root, 350, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}