package edu.wpi.cs3733d18.teamS.controller;


import edu.wpi.cs3733d18.teamS.data.Device;
import edu.wpi.cs3733d18.teamS.data.Ticket;
import edu.wpi.cs3733d18.teamS.database.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for the tickets.
 * @author Joseph Turcotte
 * @version 1.1, April 16, 2018
 */
public class TicketController {

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

    /**
     * Populates the Device box with all of the devices in the database.
     */
    public void populateDevicesBox() {

        List<Device> devices = Storage.getInstance().getAllDevices();
        List<String> device_names = new LinkedList<>();
        int length = devices.size();
        for (int i = 0; i < length; i++) {
            device_names.add(devices.get(i).getDeviceName());
        }

        devices_list.valueProperty().set(null);
        devices_list.getItems().removeAll(devices_list.getItems());
        devices_list.getItems().addAll(device_names);
    }

    /**
     * Populates the problem box with all the problems with the devices in the database
     */
    public void populateProblemsBox() {
        List<String> problems = new ArrayList<>();
        problems.add("Computer won't turn on.");
        problems.add("Computer froze.");
        problems.add("Software issue.");
        problems.add("Eclipse is terrible!");

        problem_list.valueProperty().set(null);
        problem_list.getItems().removeAll(problem_list.getItems());
        problem_list.getItems().addAll(problems);
    }

    /**
     * Submits a request.
     */
    public void submitRequest() {
        // TODO need to populate service request better (ask where incident took place, etc.)
        Ticket t = new Ticket("Joe", "Joe", additional_info.getText(), "3rd Floor");
        Storage.getInstance().saveTicket(t);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Service Request Created");
        alert.setHeaderText("Service Request Created");
        alert.setContentText("Your service request was created successfully.");
        alert.showAndWait();
    }

    /**
     * Initializes the scene by populating the device box and problem box.
     */
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
