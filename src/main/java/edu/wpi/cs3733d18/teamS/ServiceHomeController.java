package edu.wpi.cs3733d18.teamS;

import com.gluonhq.charm.glisten.control.ExpansionPanel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Service API home controller
 * Author: Joseph Turcotte
 * Date: April 16, 2018
 */

public class ServiceHomeController {

    @FXML
    private TableView log_table;

    @FXML
    private Button create_request_btn;

    @FXML
    private Button register_device_btn;

    /**
     * Initialize the home page controller for the service API
     */
    public void initialize() {

        log_table.getColumns().removeAll(log_table.getColumns());

        TableColumn<ServiceRequest, String> numberCol = new TableColumn("Ticket Number");
        TableColumn<ServiceRequest, String> requesterCol = new TableColumn("Requester");
        TableColumn<ServiceRequest, String> fulfillerCol = new TableColumn("Fulfiller");
        TableColumn<ServiceRequest, String> descriptionCol = new TableColumn("Description");
        TableColumn<ServiceRequest, String> locationCol = new TableColumn("Location");

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

        log_table.setItems(FXCollections.observableArrayList(ServiceRequestController.requests));
    }

//    public void onLanguageChange() {
//
//    }

    @FXML
    void onRequestClick() {
        Main.switchScenes("Service request", "/ServiceRequest.fxml");
    }

    @FXML
    void onRegisterClick() {
        Main.switchScenes("Register device", "/RegisterDevice.fxml");
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
