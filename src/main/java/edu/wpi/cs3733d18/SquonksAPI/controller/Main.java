package edu.wpi.cs3733d18.SquonksAPI.controller;

import edu.wpi.cs3733d18.SquonksAPI.internationalization.AllText;
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

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the scene.
     * @param new_primary_stage the initial stage.
     * @throws Exception
     */
    @Override
    public void start(Stage new_primary_stage) throws Exception {
        SquonksAPI squonksAPI = new SquonksAPI();
        squonksAPI.run(100, 30, 900, 600, null, null, new_primary_stage);
    }
}
