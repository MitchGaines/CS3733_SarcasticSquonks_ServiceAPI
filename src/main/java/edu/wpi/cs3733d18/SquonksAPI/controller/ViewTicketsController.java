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
import org.joda.time.DateTime;

import java.util.Stack;

public class ViewTicketsController {

    @FXML
    private TableView<Ticket> log_table;

    @FXML
    private Button pathfind_btn;

    @FXML
    private Button resolve_ticket_btn;

    private Ticket selected_ticket;
    private static Stack<Ticket> most_recent_tickets = new Stack<>();
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
            p.set(e.getValue().getLocation().getLongName());
            return p;
        });

        log_table.setItems(FXCollections.observableArrayList(Storage.getInstance().getAllUnfulfilledTickets()));
    }

    @FXML
    void onTicketChoose() {
        selected_ticket = log_table.getSelectionModel().getSelectedItem();
    }

    @FXML
    void onResolveClick() {
        if (selected_ticket != null) {
            most_recent_tickets.push(selected_ticket);
            selected_ticket.setIsFulfilled(true);
            selected_ticket.setFulfillDate(DateTime.now());
            Storage.getInstance().updateTicket(selected_ticket);
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
    void onPathfindClick() {
        if (selected_ticket != null) {
            SquonksAPI.dest_node_id = selected_ticket.getLocation().getNodeID();
            SquonksAPI.string_func.accept(SquonksAPI.dest_node_id);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("No Ticket Selected");
            alert.setHeaderText("No Ticket Selected");
            alert.setContentText("No ticket was selected.");
            alert.showAndWait();
        }
    }

    @FXML
    void onUndoClick() {
        if (!most_recent_tickets.empty()) {
            Ticket t = most_recent_tickets.pop();
            t.setIsFulfilled(false);
            Storage.getInstance().updateTicket(t);
            populateTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Nothing to Undo");
            alert.setHeaderText("Nothing to Undo");
            alert.setContentText("There is no action to undo.");
            alert.showAndWait();
        }
    }

    public void setParent(ServiceHomeController home_controller) {
        parent = home_controller;
    }
}
