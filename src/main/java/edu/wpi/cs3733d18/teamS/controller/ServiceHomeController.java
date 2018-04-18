package edu.wpi.cs3733d18.teamS.controller;

import edu.wpi.cs3733d18.teamS.data.Ticket;
import edu.wpi.cs3733d18.teamS.database.Storage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

/**
 * Service API home controller
 *
 * @author Joseph Turcotte
 * @version 1.1, April 16, 2018
 */
public class ServiceHomeController {

    @FXML
    private TableView log_table;

    @FXML
    private Button create_request_btn;

    @FXML
    private Button register_device_btn;

    /**
     * Initialize the home page controller for the service API.
     */
    public void initialize() {

        log_table.getColumns().removeAll(log_table.getColumns());

        TableColumn<Ticket, String> numberCol = new TableColumn("Ticket Number");
        TableColumn<Ticket, String> requesterCol = new TableColumn("Requester");
        TableColumn<Ticket, String> fulfillerCol = new TableColumn("Fulfiller");
        TableColumn<Ticket, String> descriptionCol = new TableColumn("Description");
        TableColumn<Ticket, String> locationCol = new TableColumn("Location");

        numberCol.prefWidthProperty().bind(log_table.widthProperty().multiply(0.2));
        requesterCol.prefWidthProperty().bind(log_table.widthProperty().multiply(0.2));
        fulfillerCol.prefWidthProperty().bind(log_table.widthProperty().multiply(0.2));
        descriptionCol.prefWidthProperty().bind(log_table.widthProperty().multiply(0.2));
        locationCol.prefWidthProperty().bind(log_table.widthProperty().multiply(0.2));

        log_table.getColumns().addAll(numberCol, requesterCol, fulfillerCol, descriptionCol, locationCol);

        numberCol.setCellValueFactory(e -> {
            SimpleStringProperty p = new SimpleStringProperty();
            p.set(String.valueOf(e.getValue().getID()));
            return p;
        });

        requesterCol.setCellValueFactory(e -> {
            SimpleStringProperty p = new SimpleStringProperty();
            p.set(e.getValue().getRequesterName());
            return p;
        });

        fulfillerCol.setCellValueFactory(e -> {
            SimpleStringProperty p = new SimpleStringProperty();
            p.set(e.getValue().getFulfillerName());
            return p;
        });

        descriptionCol.setCellValueFactory(e -> {
            SimpleStringProperty p = new SimpleStringProperty();
            p.set(e.getValue().getDescription());
            return p;
        });

        locationCol.setCellValueFactory(e -> {
            SimpleStringProperty p = new SimpleStringProperty();
            p.set(e.getValue().getLocation());
            return p;
        });

        log_table.setItems(FXCollections.observableArrayList(Storage.getInstance().getAllTickets()));
    }

//    public void onLanguageChange() {
//
//    }

    // TODO resolve a ticket

    @FXML
    void onRequestClick() {
        Main.switchScenes("Service request", "/TicketPage.fxml");
    }

    @FXML
    void onRegisterClick() {
        Main.switchScenes("Register device", "/RegisterDevicePage.fxml");
    }

    @FXML
    void onQuickClick(ActionEvent event) throws IOException {

    }

    /**
     * Autocomplete algorithm which sets the displayed items of a ComboBox to be only the ones that include the text
     * in the edit field as a substring.
     *
     * @param e KeyEvent representing the key that was typed.
     */
    @FXML
    void onKeyReleasedComboBox(KeyEvent e) {

    }
}
