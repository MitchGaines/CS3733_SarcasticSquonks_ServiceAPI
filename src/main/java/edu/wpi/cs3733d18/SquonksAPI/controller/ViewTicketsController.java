package edu.wpi.cs3733d18.SquonksAPI.controller;

import edu.wpi.cs3733d18.SquonksAPI.data.Ticket;
import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ViewTicketsController {

    @FXML
    private TableView<Ticket> log_table;

    @FXML
    private Button create_request_btn;

    @FXML
    private Button resolve_ticket_btn;

    @FXML
    private Button register_device_btn;

    private Ticket selected_ticket;
    private ServiceHomeController parent;

    /**
     * Initialize the home page controller for the service API.
     */
    public void initialize() {
        populateTable();
    }

    public void populateTable() {
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

    @FXML
    void onTicketChoose() {
        selected_ticket = log_table.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onResolveClick() {
        if (selected_ticket != null) {
            Storage.getInstance().deleteTicket(selected_ticket);
            populateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Ticket Selected");
            alert.setHeaderText("No Ticket Selected");
            alert.setContentText("No ticket was selected.");
            alert.showAndWait();
        }
    }

    @FXML
    void onRequestClick() {
        SquonksAPI.switchScenes("Service request", "/TicketPage.fxml");
    }

    @FXML
    void onRegisterClick() {
        SquonksAPI.switchScenes("Register device", "/RegisterDevicePage.fxml");
    }

    public void setParent(ServiceHomeController home_controller) {
        parent = home_controller;
    }
}