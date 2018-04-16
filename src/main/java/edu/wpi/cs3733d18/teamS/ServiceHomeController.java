package edu.wpi.cs3733d18.teamS;

import com.gluonhq.charm.glisten.control.ExpansionPanel;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.binding.Bindings;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;

public class ServiceHomeController {

    private static boolean include_stairs = true;
    private static String KIOSK_DEFAULT_LOCATION = "BINFO00102";

    @FXML
    JFXButton pathfind;

    @FXML
    JFXButton login_btn;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    Label wrong_credentials;

    @FXML
    Label time, time2;

    @FXML
    BorderPane main_pane;

//    @FXML
//    JFXComboBox<edu.wpi.cs3733d18.teamS.data.Node> combobox_start;
//
//    @FXML
//    JFXComboBox<edu.wpi.cs3733d18.teamS.data.Node> combobox_end;

    @FXML
    JFXComboBox language_selector;

    @FXML
    StackPane stack_pane;

//    @FXML
//    ExpansionPanel exp_panel;
    @FXML
    JFXButton REST;
    @FXML
    JFXButton DEPT;
    @FXML
    JFXButton INFO;
    @FXML
    JFXToggleButton stairs_toggle;
    //private ObservableList<edu.wpi.cs3733d18.teamS.data.Node> locations = FXCollections.observableArrayList();

    public static boolean includeStairs() {
        return include_stairs;
    }

    public static void setKioskDefaultLocation(String kioskDefaultLocation) {
        KIOSK_DEFAULT_LOCATION = kioskDefaultLocation;
    }

    /**
     * Performs this function during creation of Controller; sets up the ComboBoxes
     * by pulling all nodes from the edu.wpi.cs3733d18.teamS.database
     *
     * @author Will Lucca
     */
    public void initialize() {

    }

    public void onLanguageChange() {
        if (language_selector.getSelectionModel().isEmpty()) {
            return;
        }
        AllText.changeLanguage(AllText.getLanguages()[language_selector.getSelectionModel().getSelectedIndex()]);
        Main.switchScenes("Brigham and Women's", "/HomePage.fxml");

    }

    /**
     * Sets start_loc and end_loc to the values selected in the combobox, then switches view to
     * PathfindPage, initializing PathfindController
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onPathfindClick(ActionEvent event) throws IOException {

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

    //THIS IS A TEST TO TRY OUT DIFFERENT USERS
    public void onLoginClick(ActionEvent event) throws IOException {

    }

//    //PART OF THE USER TEST
//    public void openUser(ActionEvent event, String page, User user) throws IOException {
//        UserController user_controller = (UserController) Main.switchScenes("User", page);
//        user_controller.setUp(user, page);
//
//    } //END OF TEST
}
