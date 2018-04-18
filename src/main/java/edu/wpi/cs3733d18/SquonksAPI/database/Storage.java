package edu.wpi.cs3733d18.SquonksAPI.database;

import edu.wpi.cs3733d18.SquonksAPI.data.Device;
import edu.wpi.cs3733d18.SquonksAPI.data.Ticket;
import edu.wpi.cs3733d18.SquonksAPI.data.User;

import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Storage.java
 * Class that stores information for the the Apache Database and manages methods related to it.
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: March 29, 2018
 * Modified: April 6, 2018
 */
public class Storage {

    // to exclude auto-generated IDs from tables
    private final String USER_VALUES = String.format("( %s, %s, %s, %s, %s, %s )",
            "username", "password", "first_name", "last_name", "user_type", "can_mod_map");
    private final String TICKET_VALUES = String.format(" ( %s, %s, %s, %s )",
            "requester_name", "fulfiller_name", "description", "location");
    /**
     * Stores the database.
     */
    private IDatabase database;

    /**
     * Empty constructor for storage.
     */
    private Storage() {
    }

    /**
     * Get instance of Storage object.
     */
    public static Storage getInstance() {
        return StorageHolder.instance;
    }

    // ---------------- USER METHODS ----------------- //

    /**
     * Inserts the fields of a new edu.wpi.cs3733d18.SquonksAPI.user object into the users table.
     *
     * @param user the User object to store in the table.
     */
    public void saveUser(User user) {
        database.insert("USERS" + USER_VALUES, new String[]{
                database.addQuotes(user.getUsername()),
                database.addQuotes(new String(user.getEncodedPassword(), Charset.forName("UTF-8"))),
                database.addQuotes(user.getFirstName()),
                database.addQuotes(user.getLastName()),
                database.addQuotes(user.getType().toString()),
                String.valueOf(user.canModMap())
        });
    }

    /**
     * Deletes the given edu.wpi.cs3733d18.SquonksAPI.user from the users table.
     *
     * @param user the edu.wpi.cs3733d18.SquonksAPI.user to delete from the users table.
     */
    public void deleteUser(User user) {
        database.delete("USERS", "user_id = " + user.getUserID(), null);
    }

    /**
     * Updates a edu.wpi.cs3733d18.SquonksAPI.user in the users table with new values.
     *
     * @param user the edu.wpi.cs3733d18.SquonksAPI.user to update in the edu.wpi.cs3733d18.SquonksAPI.database, with the new values.
     */
    public void updateUser(User user) {
        String[] values = new String[]{
                String.format("%s = '%s'", "username", user.getUsername().replaceAll("'", "''")),
                String.format("%s = '%s'", "password", new String(user.getEncodedPassword(), Charset.forName("UTF-8")).replaceAll("'", "''")),
                String.format("%s = '%s'", "first_name", user.getFirstName().replaceAll("'", "''")),
                String.format("%s = '%s'", "last_name", user.getLastName().replaceAll("'", "''")),
                String.format("%s = '%s'", "user_type", user.getType().toString()),
                String.format("%s = %b", "can_mod_map", user.canModMap())
        };

        database.update("USERS", values, "user_id = " + user.getUserID(), null);
    }

    /**
     * Gets a edu.wpi.cs3733d18.SquonksAPI.user from the users table by unique id.
     *
     * @param id the edu.wpi.cs3733d18.SquonksAPI.user id of the edu.wpi.cs3733d18.SquonksAPI.user in the table.
     * @return the edu.wpi.cs3733d18.SquonksAPI.user with the given edu.wpi.cs3733d18.SquonksAPI.user id.
     */
    public User getUserByID(long id) {
        ResultSet r_set = database.query("USERS", null,
                "user_id = " + id, null, null);
        return getUser(r_set);
    }

    /**
     * Gets a edu.wpi.cs3733d18.SquonksAPI.user from the table by username only.
     *
     * @param username the username of the edu.wpi.cs3733d18.SquonksAPI.user to retrieve.
     * @return a edu.wpi.cs3733d18.SquonksAPI.user with the given username.
     */
    public User getUserByName(String username) {
        ResultSet r_set = database.query("USERS", null,
                "username = '" + username + "'", null, null);
        return getUser(r_set);
    }

    /**
     * Gets a edu.wpi.cs3733d18.SquonksAPI.user from the users table by username and password.
     *
     * @param username username of the edu.wpi.cs3733d18.SquonksAPI.user to retrieve.
     * @param password password of the edu.wpi.cs3733d18.SquonksAPI.user to retrieve.
     * @return a edu.wpi.cs3733d18.SquonksAPI.user with the given name and password.
     */
    public User getUserByCredentials(String username, String password) {
        ResultSet r_set = database.query(
                "USERS",
                null,
                "username = ? AND password = ?",
                new String[]{username, password},
                null
        );

        return getUser(r_set);
    }

    /**
     * Gets a list of all users in the users table.
     *
     * @return a List of users in the users table.
     */
    public List<User> getAllUsers() {
        ResultSet r_set = database.query("USERS", null, null, null, null);
        return getUsers(r_set);
    }

    /**
     * Private method for parsing result set.
     *
     * @param r_set ResultSet containing table entries.
     * @return a List of users from the table.
     */
    private List<User> getUsers(ResultSet r_set) {
        List<User> users = new LinkedList<>();

        // return an empty list if query didn't return anything
        if (r_set == null) {
            return users;
        }

        User user;
        while ((user = getUser(r_set)) != null) {
            users.add(user);
        }

        return users;
    }

    /**
     * Private method for retrieving a edu.wpi.cs3733d18.SquonksAPI.user from a edu.wpi.cs3733d18.SquonksAPI.database query.
     *
     * @param r_set The ResultSet containing a single table entry.
     * @return a User object corresponding to the single table entry.
     */
    private User getUser(ResultSet r_set) {
        try {
            // if we don't have anything in the result set
            if (r_set == null || !r_set.next()) {
                return null;
            }

            // extract fields from result set and store in edu.wpi.cs3733d18.SquonksAPI.user object
            long id = r_set.getLong("user_id");
            String username = r_set.getString("username");
            String password = r_set.getString("password");
            String first_name = r_set.getString("first_name");
            String last_name = r_set.getString("last_name");
            User.user_type user_type = User.user_type.valueOf(r_set.getString("user_type"));
            boolean can_mod_map = r_set.getBoolean("can_mod_map");

            User user = new User(username, password, first_name, last_name, user_type, can_mod_map);
            user.setUserID(id);

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ---------------- DEVICE METHODS --------------- //

    /**
     * Inserts the fields of a new Device object into the devices table.
     * @param device the Device object to store in the table.
     */
    public void saveDevice(Device device) {
        database.insert("DEVICES", new String[]{
                database.addQuotes(device.getDeviceName()),
                database.addQuotes(device.getOwner()),
                database.addQuotes(device.getDeviceType())
        });
    }

    /**
     * Deletes a device from the table.
     * @param device the device to delete from the table.
     */
    public void deleteDevice(Device device) {
        database.delete("DEVICES", "device_name = '" + device.getDeviceName() + "'", null);
    }

    /**
     * Updates a device in the table.
     * @param device the Device object to update in the table.
     */
    public void updateDevice(Device device) {
        String[] values = new String[]{
                String.format("%s = '%s'", "device_name", device.getDeviceName().replaceAll("'", "''")),
                String.format("%s = '%s'", "owner", device.getOwner().replaceAll("'", "''")),
                String.format("%s = '%s'", "device_type", device.getDeviceType().replaceAll("'", "''"))
        };

        database.update("DEVICES", values, "device_name = '" + device.getDeviceName() + "'", null);
    }

    /**
     * Retrieves a device from the table by name.
     * @param device_name the name of the device.
     * @return a Device object corresponding to the name.
     */
    public Device getDeviceByName(String device_name) {
        ResultSet r_set = database.query(
                "DEVICES",
                null,
                "device_name = '" + device_name + "'",
                null,
                null
        );

        return getDevice(r_set);
    }

    /**
     * Get all devices from the table.
     * @return a List of all devices in the table.
     */
    public List<Device> getAllDevices() {
        ResultSet r_set = database.query(
                "DEVICES",
                null,
                null,
                null,
                null
        );

        return getDevices(r_set);
    }

    /**
     * private method for parsing result set.
     *
     * @param r_set the result set containing devices.
     * @return a List of the Device objects from the table.
     */
    private List<Device> getDevices(ResultSet r_set) {
        List<Device> devices = new LinkedList<>();

        // return an empty list if query didn't return anything
        if (r_set == null) {
            return devices;
        }

        Device device;
        while ((device = getDevice(r_set)) != null) {
            devices.add(device);
        }

        return devices;
    }

    /**
     * private method for parsing result set.
     *
     * @param r_set the result set consisting of a single table entry.
     * @return the Device object corresponding to the query.
     */
    private Device getDevice(ResultSet r_set) {

        try {
            // if we don't have anything in the result set
            if (r_set == null || !r_set.next()) {
                return null;
            }

            // extract fields from result set and store in device object
            String device_name = r_set.getString("device_name");
            String owner = r_set.getString("owner");
            String device_type = r_set.getString("device_type");

            // set some request fields to null, then fill them in after
            Device device = new Device(device_name, owner, device_type);

            return device;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ---------------- TICKET METHODS --------------- //

    /**
     * Inserts the fields of a new Ticket object into the tickets table.
     *
     * @param ticket the Ticket object to store in the table.
     */
    public void saveTicket(Ticket ticket) {

        // check if fulfiller id is null
        String fulfiller_name = ticket.getFulfillerName() == null ? "" : ticket.getFulfillerName();

        database.insert("TICKETS" + TICKET_VALUES, new String[]{
                database.addQuotes(ticket.getRequesterName()),
                database.addQuotes(fulfiller_name),
                database.addQuotes(ticket.getDescription()),
                database.addQuotes(ticket.getLocation())
        });
    }

    /**
     * Removes a ticket from the tickets table.
     *
     * @param ticket the Ticket object to remove from the table.
     */
    public void deleteTicket(Ticket ticket) {
        database.delete("TICKETS", "ticket_id = " + ticket.getID(), null);
    }

    /**
     * Updates a ticket in the tickets table with new values.
     *
     * @param ticket the Ticket object to update in the database, with the new values.
     */
    public void updateTicket(Ticket ticket) {

        String[] values = new String[]{
                String.format("%s = '%s'", "requester_name", ticket.getRequesterName().replaceAll("'", "''")),
                String.format("%s = '%s'", "fulfiller_name", ticket.getFulfillerName().replaceAll("'", "''")),
                String.format("%s = '%s'", "description", ticket.getDescription().replaceAll("'", "''")),
                String.format("%s = '%s'", "location", ticket.getLocation().replaceAll("'", "''"))
        };

        database.update("TICKETS", values, "ticket_id = " + ticket.getID(), null);
    }

    /**
     * Gets a ticket from the table by id.
     *
     * @param id the id of the ticket to retrieve.
     * @return the ticket corresponding to the given id.
     */
    public List<Ticket> getTicketByID(long id) {
        ResultSet r_set = database.query(
                "TICKETS",
                null,
                "ticket_id = " + id,
                null,
                null
        );

        return getRequests(r_set);
    }

    /**
     * Gets all tickets in the tickets table.
     *
     * @return a List containing all of the tickets in the table.
     */
    public List<Ticket> getAllTickets() {
        ResultSet r_set = database.query(
                "TICKETS",
                null,
                null,
                null,
                null
        );

        return getRequests(r_set);
    }

    /**
     * private method for parsing result set.
     *
     * @param r_set the result set containing tickets.
     * @return a List of the Ticket objects from the table.
     */
    private List<Ticket> getRequests(ResultSet r_set) {
        List<Ticket> tickets = new LinkedList<>();

        // return an empty list if query didn't return anything
        if (r_set == null) {
            return tickets;
        }

        Ticket ticket;
        while ((ticket = getTicket(r_set)) != null) {
            tickets.add(ticket);
        }

        return tickets;
    }

    /**
     * private method for parsing result set.
     *
     * @param r_set the result set consisting of a single table entry.
     * @return the Ticket object corresponding to the query.
     */
    private Ticket getTicket(ResultSet r_set) {

        try {
            // if we don't have anything in the result set
            if (r_set == null || !r_set.next()) {
                return null;
            }

            // extract fields from result set and store in ticket object
            long ticket_id = r_set.getLong("ticket_id");
            String requester_name = r_set.getString("requester_name");
            String fulfiller_name = r_set.getString("fulfiller_name");
            String description = r_set.getString("description");
            String location = r_set.getString("location");

            // set some request fields to null, then fill them in after
            Ticket ticket = new Ticket(requester_name, fulfiller_name, description, location);
            ticket.setID(ticket_id);

            return ticket;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get database.
     *
     * @return the database.
     */
    public IDatabase getDatabase() {
        return database;
    }

    /**
     * Sets the database for the controller to interact with.
     *
     * @param database the database to connect to.
     */
    public void setDatabase(IDatabase database) {

        // configure database and connect
        this.database = database;
        database.connect();

        if (!database.doesTableExist("USERS")) {
            database.createTable("USERS", new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", "user_id"),
                    String.format("%s VARCHAR (100)", "username"),
                    String.format("%s VARCHAR (100)", "password"),
                    String.format("%s VARCHAR (100)", "first_name"),
                    String.format("%s VARCHAR (100)", "last_name"),
                    String.format("%s VARCHAR (16)", "user_type"),
                    String.format("%s BOOLEAN", "can_mod_map")
            });

            User.generateInitialUsers();
        }

        if (!database.doesTableExist("DEVICES")) {
            database.createTable("DEVICES", new String[]{
                    String.format("%s VARCHAR (100)", "device_name"),
                    String.format("%s VARCHAR (100)", "owner"),
                    String.format("%s VARCHAR (100)", "device_type")
            });

            // generate initial devices
            Device.generateInitialDevices();
        }

        if (!database.doesTableExist("TICKETS")) {
            database.createTable("TICKETS", new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", "ticket_id"),
                    String.format("%s VARCHAR (100)", "requester_name"),
                    String.format("%s VARCHAR (100)", "fulfiller_name"),
                    String.format("%s VARCHAR (100)", "description"),
                    String.format("%s VARCHAR (100)", "location")
            });

            // generate initial tickets
            Ticket.generateInitialTickets();
        }
    }

    /**
     * Storage holder class (singleton).
     */
    private static class StorageHolder {
        private static final Storage instance = new Storage();
    }
}


