package edu.wpi.cs3733d18.SquonksAPI.controller;

import javafx.application.Application;
import javafx.stage.Stage;

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
        squonksAPI.run( null);
    }
}
