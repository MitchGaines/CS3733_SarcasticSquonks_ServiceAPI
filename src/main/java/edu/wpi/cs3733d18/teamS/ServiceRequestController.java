package edu.wpi.cs3733d18.teamS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.util.Comparator;
import java.util.stream.Stream;

public class ServiceRequestController {

//    @FXML
//    ComboBox<ServiceType> request_type_selector;
//
//    @FXML
//    ComboBox<ServiceRequest> active_requests_box;
//
//    @FXML
//    ComboBox<edu.wpi.cs3733d18.teamS.data.Node> service_location;
//    @FXML
//    Button request_service_button;
//    @FXML
//    Button mark_completed_btn;
//    private User user;
//    private ObservableList<Node> locations = FXCollections.observableArrayList();
//    @FXML
//    private Text title_text, location_text;
//    @FXML
//    private TextField service_title;
//    @FXML
//    private TextArea description_field, description_text;
//    private UserController parent;

    @FXML
    private Button back_btn;

    public void populateRequestTypes() {

    }

    public void populateRequestsBox() {

    }

    public void markComplete() {

    }

    public void submitRequest() {

    }

    public void loadRequestInfo() {

    }

    public void initialize() {

    }

    @FXML
    void onBackClick() {
        Main.switchScenes("Brigham and Women's", "/ServiceHomePage.fxml");
    }

    @FXML
    void onKeyReleasedComboBox(KeyEvent e) {

    }

}