package edu.wpi.cs3733d18.teamS;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class RegisterDeviceController {

    /**
     * List of devices to be accessed anywhere in code
     */
    public static List<Device> devices = new ArrayList<>();

    /**
     * List of usernames to be accessed anywhere in code
     */
    public static List<String> usernames = new ArrayList<>();

    /**
     * List of device types to be accessed anywhere in code
     */
    public static List<String> device_types = new ArrayList<>();

    @FXML
    private ComboBox names_selector;

    @FXML
    private ComboBox types_selector;

    @FXML
    private TextField device_name;

    @FXML
    private Button create_device_btn;

    @FXML
    private Button back_btn;

    public void populateNamesBox() {
        names_selector.valueProperty().set(null);
        names_selector.getItems().removeAll(names_selector.getItems());
        names_selector.getItems().addAll(usernames);
    }

    public void populateTypesBox() {
        types_selector.valueProperty().set(null);
        types_selector.getItems().removeAll(types_selector.getItems());
        types_selector.getItems().addAll(device_types);
    }

    public void createDevice() {
        Device d = new Device(device_name.getText(), usernames.get(0), device_types.get(0));
        devices.add(d);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Device Registered");
        alert.setHeaderText("Device Registered");
        alert.setContentText("Your device was registered successfully.");
        alert.showAndWait();
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
