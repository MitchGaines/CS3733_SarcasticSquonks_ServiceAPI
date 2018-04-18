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

/**
 * The main class
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: April 16, 2018
 */
public class Main extends Application {

    private static Stage primary_stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Switches the scene.
     * @param title the title.
     * @param fxml_name the name of the fxml file.
     * @return the controller object.
     */
    public static Object switchScenes(String title, String fxml_name) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml_name), AllText.getBundle());
            Parent user_parent = loader.load();
            Object controller = loader.getController();
            Scene new_scene = new Scene(user_parent, primary_stage.getWidth() - 13, primary_stage.getHeight() - 35.5);
            primary_stage.setTitle(title);
            primary_stage.setScene(new_scene);
            primary_stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Starts the scene.
     * @param primary_stage the initial stage.
     * @throws Exception
     */
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
        primary_stage.setScene(primary_scene);
        primary_stage.show();

        // before system shutdown
        primary_stage.setOnCloseRequest(windowEvent -> {
            Storage.getInstance().getDatabase().dropTable("DEVICES");
            Storage.getInstance().getDatabase().dropTable("TICKETS");
        });
    }
}
