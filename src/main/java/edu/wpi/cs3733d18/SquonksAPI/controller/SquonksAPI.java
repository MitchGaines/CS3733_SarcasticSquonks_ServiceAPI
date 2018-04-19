package edu.wpi.cs3733d18.SquonksAPI.controller;

import edu.wpi.cs3733d18.SquonksAPI.database.ApacheDatabase;
import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import edu.wpi.cs3733d18.SquonksAPI.internationalization.AllText;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SquonksAPI {

    public static Stage primary_stage;

    public SquonksAPI() {}

    public void initialize(int x_coord, int y_coord, int window_width, int window_length, Stage new_primary_stage) throws IOException {
        primary_stage = new_primary_stage;
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

    public void run(int x_coord, int y_coord, int window_width, int window_length, String dest_node_id, String origin_node, Stage primary_stage) {
        try {
            this.initialize(x_coord, y_coord, window_width, window_length, primary_stage);
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

