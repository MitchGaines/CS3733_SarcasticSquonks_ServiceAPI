package edu.wpi.cs3733d18.teamS.controller;

import edu.wpi.cs3733d18.teamS.database.ApacheDatabase;
import edu.wpi.cs3733d18.teamS.database.Storage;
import edu.wpi.cs3733d18.teamS.internationalization.AllText;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private static Stage primary_stage;

    public static void main(String[] args) {
        launch(args);
    }

    public static Object switchScenes(String title, String fxml_name) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml_name), AllText.getBundle());
            Parent user_parent = loader.load();
            Object controller = loader.getController();
            Scene new_scene = new Scene(user_parent, primary_stage.getWidth(), primary_stage.getHeight());
            primary_stage.setTitle(title);

            //Timeout.addListenersToScene(new_scene);

            primary_stage.setScene(new_scene);
            primary_stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void start(Stage primary_stage) throws Exception {
        AllText.changeLanguage("en");

        // set database and storage class
        Storage storage = Storage.getInstance();
        storage.setDatabase(new ApacheDatabase("serviceDB"));

        this.primary_stage = primary_stage;

        Parent root = FXMLLoader.load(getClass().getResource("/ServiceHomePage.fxml"), AllText.getBundle());
        primary_stage.setTitle("Brigham and Women's");

        Scene primary_scene = new Scene(root, 1200, 800);

//        Timeout.addListenersToScene(primary_scene);
//
//        Timeout.start();

        primary_stage.setScene(primary_scene);
        primary_stage.show();

        // before system shutdown
//        primary_stage.setOnCloseRequest(windowEvent -> {
//        });
    }
}
