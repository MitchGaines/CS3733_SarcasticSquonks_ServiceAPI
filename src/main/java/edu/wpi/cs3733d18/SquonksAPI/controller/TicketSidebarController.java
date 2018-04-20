package edu.wpi.cs3733d18.SquonksAPI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class TicketSidebarController {

    @FXML
    private Label view_tickets, new_ticket, new_device, view_users, view_reports;

    private ServiceHomeController parent;

    @FXML
    GridPane sidebar;

    public void initialize() {
        sidebar.setStyle("-fx-background-color: #4863A0");
    }

    public GridPane getSidebar() {
        return sidebar;
    }

    public void setParent(ServiceHomeController homeController) {
        parent = homeController;
    }

    public void onViewTicketsClick() throws IOException {
        clearColors();
        view_tickets.setStyle("-fx-background-color:  #91a1c6");
        parent.viewTickets();
    }

    public void onNewTicketClick() throws IOException {
        clearColors();
        new_ticket.setStyle("-fx-background-color:  #91a1c6");
        parent.newTicket();
//        parent.populateBoxes();
    }

    public void onNewDeviceClick() throws IOException {
        clearColors();
        new_device.setStyle("-fx-background-color:  #91a1c6");
        parent.newDevice();
    }

    public void onViewUsersClick() throws IOException {
        clearColors();
        view_users.setStyle("-fx-background-color:  #91a1c6");
        parent.showUsers();
    }

    public void onViewReportsClick() throws IOException {
        clearColors();
        view_reports.setStyle("-fx-background-color:  #91a1c6");
        parent.showReports();
    }

    private void clearColors() {
        view_tickets.setStyle("-fx-background-color:  #173e43");
        new_ticket.setStyle("-fx-background-color:  #173e43");
        new_device.setStyle("-fx-background-color:  #173e43");
        view_users.setStyle("-fx-background-color:  #173e43");
        view_reports.setStyle("-fx-background-color:  #173e43");
    }
}
