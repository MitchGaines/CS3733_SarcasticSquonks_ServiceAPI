package edu.wpi.cs3733d18.SquonksAPI.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733d18.SquonksAPI.data.User;
import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import edu.wpi.cs3733d18.SquonksAPI.internationalization.AllText;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

public class ViewUsersController {

    /**
     * Stores the table columns for username, type, lastname, and firstname.
     */
    TableColumn user_name, type, last_name, first_name;

    /**
     * Stores the selected user.
     */
    User selected_user;

    /**
     * The boxes for modifying user data.
     */
    @FXML
    VBox add_user_box, delete_user_box, modify_user_box;

    /**
     * The text fields for username, password, firstname, lastname, user to delete, modify firstname and modify lastname.
     */
    @FXML
    JFXTextField username_field, password_field, first_name_field, last_name_field, user_to_delete, modify_firstname, modify_lastname;

    /**
     * The text fields for modifying the username and password.
     */
    @FXML
    JFXTextField modify_username, modify_password, timeout_field;

    /**
     * Buttons for the modify map, delete user, add user, confirm delete and cancel delete.
     */
    @FXML
    Button delete_user_btn, modify_user, add_user_btn, confirm_delete_btn, cancel_delete_btn;

    /**
     * The combo box for modifying users.
     */
    @FXML
    JFXComboBox<User.user_type> type_user, type_user_modify;

    /**
     * The User table.
     */
    @FXML
    TableView<User> user_table;

    /**
     * Adds a user to the database.
     */
    public void onAddUser() {
        if(!first_name_field.getText().equals("") && !last_name_field.getText().equals("") && !username_field.getText().equals("")
                && !password_field.getText().equals("") && !type_user.getSelectionModel().isEmpty()) {
            boolean can_mod = false;
            if (type_user.getValue() == User.user_type.ADMIN_STAFF) {
                can_mod = true;
            }
            User new_user = new User(username_field.getText(), password_field.getText(), first_name_field.getText(), last_name_field.getText(),
                    first_name_field.getText() + last_name_field.getText(), type_user.getValue(), can_mod);
            Storage.getInstance().saveUser(new_user);
            populateUserTable();
            first_name_field.setText("");
            last_name_field.setText("");
            username_field.setText("");
            password_field.setText("");
            type_user.getSelectionModel().clearSelection();
        }
    }

    /**
     * Populates the user table.
     */
    private void populateUserTable() {
        user_table.getColumns().removeAll(user_table.getColumns());
        last_name = new TableColumn("Last Name");
        first_name = new TableColumn("First Name");
        user_name = new TableColumn("User Name");
        type = new TableColumn("Type");

        ObservableList<User> users = FXCollections.observableArrayList(Storage.getInstance().getAllUsers());

        last_name.setCellValueFactory(new PropertyValueFactory<User, String>("LastName"));
        first_name.setCellValueFactory(new PropertyValueFactory<User, String>("FirstName"));
        user_name.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        type.setCellValueFactory(new PropertyValueFactory<User, String>("type"));

        user_table.setItems(users);
        user_table.getColumns().addAll(last_name, first_name, user_name, type);
    }

    /**
     * Deletes a user.
     */
    public void onDeleteUser() {
        if(selected_user != null) {
            add_user_box.setVisible(false);
            delete_user_box.setVisible(true);
            user_to_delete.setText(selected_user.getFirstName() + " " + selected_user.getLastName());
        }
    }

    /**
     * Confirms a user to be deleted.
     */
    public void onConfirmDelete() {
        Storage.getInstance().deleteUser(selected_user);
        populateUserTable();
        user_to_delete.setText("");
        add_user_box.setVisible(true);
        delete_user_box.setVisible(false);
    }

    /**
     * Cancels a user being deleted.
     */
    public void onCancelDelete() {
        add_user_box.setVisible(true);
        delete_user_box.setVisible(false);
        user_to_delete.setText("");
    }

    /**
     * Modifies a users data.
     */
    public void onModifyUser() {
        if(selected_user != null) {
            add_user_box.setVisible(false);
            modify_user_box.setVisible(true);
            modify_firstname.setText(selected_user.getFirstName());
            modify_lastname.setText(selected_user.getLastName());
            modify_username.setText(selected_user.getUsername());
            type_user_modify.getSelectionModel().select(selected_user.getType());
            ObservableList<User.user_type> types_list = FXCollections.observableArrayList();
            types_list.addAll(User.user_type.ADMIN_STAFF, User.user_type.DOCTOR, User.user_type.REGULAR_STAFF);
            type_user_modify.setItems(types_list);
        }
    }

    /**
     * Confirms the changes made to the user.
     */
    public void onConfirmModify() {
        if(!type_user_modify.getSelectionModel().isEmpty() && !modify_firstname.getText().equals("") && !modify_lastname.getText().equals("")
                && !modify_password.getText().equals("") && !modify_username.getText().equals("")) {
            selected_user.setType(type_user_modify.getValue());
            selected_user.setLastName(modify_lastname.getText());
            selected_user.setFirstName(modify_firstname.getText());
            selected_user.setPassword(modify_password.getText());
            selected_user.setUsername(modify_username.getText());
            Storage.getInstance().updateUser(selected_user);
            populateUserTable();
            modify_user_box.setVisible(false);
            add_user_box.setVisible(true);
        }
    }

    /**
     * cancels changes made to the user.
     */
    public void onCancelModify() {
        modify_user_box.setVisible(false);
        add_user_box.setVisible(true);
    }

    /**
     * sets the selected user.
     */
    public void onUserChoose() {
        selected_user = user_table.getSelectionModel().getSelectedItem();
    }

    /**
     * Initializes the scene.
     */
    public void initialize() {
        populateUserTable();
        ObservableList<User.user_type> types_list = FXCollections.observableArrayList();
        types_list.addAll(User.user_type.ADMIN_STAFF, User.user_type.DOCTOR, User.user_type.REGULAR_STAFF, User.user_type.IT_STAFF);
        type_user.setItems(types_list);

        add_user_box.setVisible(true);
        delete_user_box.setVisible(false);
        modify_user_box.setVisible(false);
    }
}
