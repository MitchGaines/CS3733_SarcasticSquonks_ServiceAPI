package edu.wpi.cs3733d18.teamS.controller;

import edu.wpi.cs3733d18.teamS.data.Device;
import edu.wpi.cs3733d18.teamS.database.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.LinkedList;
import java.util.List;

public class RegisterDeviceController {

    @FXML
    private ComboBox<String> names_selector;

    @FXML
    private ComboBox<String> types_selector;

    @FXML
    private TextField device_name;

    @FXML
    private Button create_device_btn;

    @FXML
    private Button back_btn;

    public void populateNamesBox() {

        List<Device> devices = Storage.getInstance().getAllDevices();
        List<String> owners = new LinkedList<>();
        int length = devices.size();
        for (int i = 0; i < length; i++) {
            owners.add(devices.get(i).getOwner());
        }

        names_selector.valueProperty().set(null);
        names_selector.getItems().removeAll(names_selector.getItems());
        names_selector.getItems().addAll(owners);
    }

    public void populateTypesBox() {
        List<Device> devices = Storage.getInstance().getAllDevices();
        List<String> device_types = new LinkedList<>();
        int length = devices.size();
        for (int i = 0; i < length; i++) {
            device_types.add(devices.get(i).getDeviceType());
        }

        types_selector.valueProperty().set(null);
        types_selector.getItems().removeAll(types_selector.getItems());
        types_selector.getItems().addAll(device_types);
    }

    public void createDevice() {
        Device d = new Device(
                device_name.getText(),
                names_selector.getSelectionModel().getSelectedItem(),
                types_selector.getSelectionModel().getSelectedItem());
        Storage.getInstance().saveDevice(d);

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
