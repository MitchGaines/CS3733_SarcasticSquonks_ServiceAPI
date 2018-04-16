package edu.wpi.cs3733d18.teamS;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.List;

public class ServiceRequestController {

    /**
     * Ticket number id
     */
    public static int ticket_id = 1;

    /**
     * Holds a list of all computer service requests
     */
    public static List<ServiceRequest> requests = new ArrayList<>();

    @FXML
    private ComboBox devices_list;

    @FXML
    private ComboBox problem_list;

    @FXML
    private TextField additional_info;

    @FXML
    private Button submit_btn;

    @FXML
    private Button back_btn;

    public void populateDevicesBox() {

        // loop through devices list and add to names
        List<String> device_names = new ArrayList<>();
        for (Device d : RegisterDeviceController.devices) {
            device_names.add(d.getDeviceName());
        }

        devices_list.valueProperty().set(null);
        devices_list.getItems().removeAll(devices_list.getItems());
        devices_list.getItems().addAll(device_names);
    }

    public void populateProblemsBox() {
        List<String> problems = new ArrayList<>();
        problems.add("Computer won't turn on.");
        problems.add("Computer froze.");
        problems.add("Software issue.");

        problem_list.valueProperty().set(null);
        problem_list.getItems().removeAll(problem_list.getItems());
        problem_list.getItems().addAll(problems);
    }

    public void submitRequest() {
        // TODO how do we populate this service request? Would need to know who is requesting and fulfilling it
        ServiceRequest sr = new ServiceRequest(ticket_id++, "Joe", "Joe", additional_info.getText(), "3rd Floor");
        requests.add(sr);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Service Request Created");
        alert.setHeaderText("Service Request Created");
        alert.setContentText("Your service request was created successfully.");
        alert.showAndWait();
    }

    public void initialize() {
        populateDevicesBox();
        populateProblemsBox();
    }

    @FXML
    void onBackClick() {
        Main.switchScenes("Brigham and Women's", "/ServiceHomePage.fxml");
    }

    @FXML
    void onKeyReleasedComboBox(KeyEvent e) {

    }

}
