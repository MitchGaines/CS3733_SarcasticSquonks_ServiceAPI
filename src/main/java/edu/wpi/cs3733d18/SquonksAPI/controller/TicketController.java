package edu.wpi.cs3733d18.SquonksAPI.controller;


import edu.wpi.cs3733d18.SquonksAPI.data.Device;
import edu.wpi.cs3733d18.SquonksAPI.data.Node;
import edu.wpi.cs3733d18.SquonksAPI.data.Ticket;
import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller for the tickets.
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: April 16, 2018
 */
public class TicketController {

    @FXML
    private ComboBox<Device> devices_list;

    @FXML
    private ComboBox problem_list;

    @FXML
    private ComboBox<Node> location_list;

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
        devices_list.valueProperty().set(null);
        devices_list.getItems().removeAll(devices_list.getItems());
        devices_list.getItems().addAll(devices);
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
        problems.add("Other");

        problem_list.valueProperty().set(null);
        problem_list.getItems().removeAll(problem_list.getItems());
        problem_list.getItems().addAll(problems);
    }

    /**
     * Populates the location box with all of the locations in the database
     */
    @FXML
    public void populateLocationsBox() {
        location_list.valueProperty().set(null);
        location_list.getItems().removeAll(location_list.getItems());
        location_list.getItems().addAll(Storage.getInstance().getAllNodes());
    }

    /**
     * Submits a request.
     */
    public void submitRequest() {
        Ticket t = new Ticket(
                devices_list.getSelectionModel().getSelectedItem().getOwner().getFullName(),
                "Techie Tom",
                additional_info.getText(),
                location_list.getSelectionModel().getSelectedItem(),
                false
        );
        Storage.getInstance().saveTicket(t);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ticket Created");
        alert.setHeaderText("Ticket Created");
        alert.setContentText("Your ticket was created successfully.");
        alert.showAndWait();
    }

    /**
     * Initializes the scene by populating the device box and problem box.
     */
    public void initialize() {
        populateDevicesBox();
        populateProblemsBox();
        populateLocationsBox();
    }

}
