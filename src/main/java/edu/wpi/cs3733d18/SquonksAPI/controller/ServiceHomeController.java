package edu.wpi.cs3733d18.SquonksAPI.controller;

import edu.wpi.cs3733d18.SquonksAPI.internationalization.AllText;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service API home controller
 *
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: April 16, 2018
 */
public class ServiceHomeController {

    @FXML
    BorderPane main_pane;
    @FXML
    Label time;
    @FXML
    protected TicketSidebarController ticketSidebarController;
    @FXML
    protected ViewTicketsController viewTicketsController;

    public void viewTickets() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewTickets.fxml"), AllText.getBundle());
        Parent root = loader.load();
        viewTicketsController = loader.getController();
        main_pane.setCenter(root);
    }

    public void newTicket() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TicketPage.fxml"), AllText.getBundle());
        Parent root = loader.load();
        main_pane.setCenter(root);
    }

    public void newDevice() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterDevicePage.fxml"), AllText.getBundle());
        Parent root = loader.load();
        main_pane.setCenter(root);
    }

    public void showUsers() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewUsers.fxml"), AllText.getBundle());
        Parent root = loader.load();
        main_pane.setCenter(root);
    }

    public void showReports() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewUserReports.fxml"), AllText.getBundle());
        Parent root = loader.load();
        main_pane.setCenter(root);
    }

    public void initialize() throws IOException {
        ticketSidebarController.setParent(this);
        viewTicketsController.setParent(this);
        viewTickets();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
//        time.setText(dtf.format(now));
        //dismissEmergency();
    }
}
