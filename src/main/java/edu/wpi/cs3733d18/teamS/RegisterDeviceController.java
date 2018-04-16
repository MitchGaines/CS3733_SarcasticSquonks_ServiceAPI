package edu.wpi.cs3733d18.teamS;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class RegisterDeviceController {

    @FXML
    private ComboBox<Device> computer_list;

    @FXML
    private ComboBox problem_list;

    @FXML
    private TextField additional_info;

    @FXML
    private Button submit_btn;

    @FXML
    private Button back_btn;

    public void populateNamesBox() {

    }

    public void populateTypesBox() {

    }

    public void submitRequest() {
        System.out.println("request submitted!");
    }

    public void initialize() {
        populateNamesBox();
        populateTypesBox();
    }

    @FXML
    void onBackClick() {
        Main.switchScenes("Brigham and Women's", "/ServiceHomePage.fxml");
    }

    @FXML
    void onKeyReleasedComboBox(KeyEvent e) {

    }
}
