package edu.wpi.cs3733d18.SquonksAPI.controller;

import edu.wpi.cs3733d18.SquonksAPI.database.ApacheDatabase;
import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import edu.wpi.cs3733d18.SquonksAPI.internationalization.AllText;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class SquonksAPI {

    public static Stage primary_stage;
    public static String dest_node_id;
    public static Consumer<String> string_func;

    public SquonksAPI() {}

    public void initialize(Consumer<String> pathfind_func) throws IOException {
        string_func = pathfind_func;
        primary_stage = new Stage();
        AllText.changeLanguage("en");

        // set database and storage class
        Storage storage = Storage.getInstance();
        storage.setDatabase(new ApacheDatabase("serviceDB"));

        Parent root = FXMLLoader.load(getClass().getResource("/ServiceHomePage.fxml"), AllText.getBundle());
        primary_stage.setTitle("Brigham and Women's");

        Scene primary_scene = new Scene(root, 1200, 800);
        primary_stage.setScene(primary_scene);
        primary_stage.show();
    }

    public void run(Consumer<String> pathfind_func) {
        try {
            this.initialize(pathfind_func);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the scene.
     * @param title the title.
     * @param fxml_name the name of the fxml file.
     * @return the controller object.
     */
    public static Object switchScenes(String title, String fxml_name) {
        try {
            double old_width = primary_stage.getWidth();
            double old_height = primary_stage.getHeight();
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml_name), AllText.getBundle());
            Parent user_parent = loader.load();
            Object controller = loader.getController();
            Scene new_scene = new Scene(user_parent, primary_stage.getWidth(), primary_stage.getHeight());
            primary_stage.setTitle(title);
            primary_stage.setScene(new_scene);
            primary_stage.setWidth(old_width);
            primary_stage.setHeight(old_height);
            primary_stage.show();
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

