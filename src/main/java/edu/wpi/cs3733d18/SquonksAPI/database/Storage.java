package edu.wpi.cs3733d18.SquonksAPI.database;

import edu.wpi.cs3733d18.SquonksAPI.data.Device;
import edu.wpi.cs3733d18.SquonksAPI.data.Node;
import edu.wpi.cs3733d18.SquonksAPI.data.Ticket;
import edu.wpi.cs3733d18.SquonksAPI.data.User;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
    private final String USER_VALUES = String.format("( %s, %s, %s, %s, %s, %s, %s )",
            "username", "password", "first_name", "last_name", "full_name", "user_type", "can_mod_map");
    private final String TICKET_VALUES = String.format(" ( %s, %s, %s, %s, %s, %s, %s )",
            "requester_name", "fulfiller_name", "description", "location", "request_time", "fulfill_time", "is_fulfilled");
    /**
     * Stores the database.
     */
    private IDatabase database;

    // to parse dates
    private DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

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

    // ----------- NODE METHODS ------------- //

    /**
     * Inserts the fields of a new node into the nodes table.
     *
     * @param node the Node object to insert into the nodes table.
     */
    public void saveNode(Node node) {
        database.insert("NODES", new String[]{
                database.addQuotes(node.getNodeID()),
                String.valueOf(node.getXCoord()),
                String.valueOf(node.getYCoord()),
                database.addQuotes(node.getNodeFloor()),
                database.addQuotes(node.getNodeBuilding()),
                database.addQuotes(node.getNodeType()),
                database.addQuotes(node.getLongName()),
                database.addQuotes(node.getShortName()),
                database.addQuotes(node.getTeamAssigned()),
                String.valueOf(node.getXCoord3D()),
                String.valueOf(node.getYCoord3D()),
                String.valueOf(node.isDisabled())
        });
    }

    /**
     * Removes a node entry from the nodes table.
     *
     * @param node the Node object to delete from the nodes table.
     */
    public void deleteNode(Node node) {
        database.delete("NODES", "node_id = '" + node.getNodeID() + "'", null);
    }

    /**
     * Updates the nodes table entry for a given node.
     *
     * @param node the Node object to update in the nodes table.
     */
    public void updateNode(Node node) {
        String[] values = new String[]{
                String.format("%s = '%s'", "node_id", node.getNodeID()),
                String.format("%s = %d", "x_coord", node.getXCoord()),
                String.format("%s = %d", "y_coord", node.getYCoord()),
                String.format("%s = '%s'", "floor", node.getNodeFloor()),
                String.format("%s = '%s'", "building", node.getNodeBuilding()),
                String.format("%s = '%s'", "node_type", node.getNodeType()),
                String.format("%s = '%s'", "long_name", node.getLongName()),
                String.format("%s = '%s'", "short_name", node.getShortName()),
                String.format("%s = '%s'", "team_assigned", node.getTeamAssigned()),
                String.format("%s = %d", "x_coord_3d", node.getXCoord3D()),
                String.format("%s = %d", "y_coord_3d", node.getYCoord3D()),
                String.format("%s = %b", "disabled", node.isDisabled())
        };

        database.update("NODES", values, "node_id = '" + node.getNodeID() + "'", null);
    }

    /**
     * Retrieves a specific node from the nodes table based on its id.
     *
     * @param node_id the id of the node to extract from the nodes table.
     * @return a Node object corresponding to the node requested.
     */
    public Node getNodeByID(String node_id) {
        ResultSet result_set = database.query("NODES", null,
                "node_id = '" + node_id + "'", null, null);
        return getNode(result_set);
    }

    /**
     * Retrieves all nodes from the nodes table.
     *
     * @return a List of all of the nodes in the nodes table.
     */
    public List<Node> getAllNodes() {
        ResultSet result_set = database.query("NODES", null,
                null, null, null);
        return getNodes(result_set);
    }

    /**
     * Parses the nodes table and adds nodes to a list.
     *
     * @param result_set the table of node entries.
     * @return a List of the node entries in the nodes table.
     */
    private List<Node> getNodes(ResultSet result_set) {
        List<Node> nodes = new LinkedList<>();

        // if there were no results from the query, return null
        if (result_set == null) {
            return nodes;
        }

        // loop through result set and add to list
        Node node;
        while ((node = getNode(result_set)) != null) {
            nodes.add(node);
        }

        return nodes;
    }

    /**
     * Retrieves one node from the nodes table.
     *
     * @param result_set a single entry from the nodes table.
     * @return a node corresponding to the nodes table entry.
     */
    private Node getNode(ResultSet result_set) {

        // if there is no node to get, return null
        try {
            if (result_set == null || !result_set.next()) {
                return null;
            }

            // extract fields from table and put into Node object
            String node_id = result_set.getString("node_id");
            int x_coord = result_set.getInt("x_coord");
            int y_coord = result_set.getInt("y_coord");
            String floor = result_set.getString("floor");
            String building = result_set.getString("building");
            String node_type = result_set.getString("node_type");
            String long_name = result_set.getString("long_name");
            String short_name = result_set.getString("short_name");
            String team_assigned = result_set.getString("team_assigned");
            int x_coord_3d = result_set.getInt("x_coord_3d");
            int y_coord_3d = result_set.getInt("y_coord_3d");
            boolean disabled = result_set.getBoolean("disabled");

            Node n = new Node(node_id, x_coord, y_coord, floor, building, node_type,
                    long_name, short_name, team_assigned, x_coord_3d, y_coord_3d, disabled);

            return n;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
                database.addQuotes(user.getFullName()),
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
                String.format("%s = '%s'", "last_name", user.getFullName().replaceAll("'", "''")),
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
     * Gets a user from the table by username only.
     *
     * @param full_name the full name of the user to retrieve.
     * @return a user with the given full name.
     */
    public User getUserByName(String full_name) {
        ResultSet r_set = database.query("USERS", null,
                "full_name = '" + full_name + "'", null, null);
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
            String full_name = r_set.getString("full_name");
            User.user_type user_type = User.user_type.valueOf(r_set.getString("user_type"));
            boolean can_mod_map = r_set.getBoolean("can_mod_map");

            User user = new User(username, password, first_name, last_name, full_name, user_type, can_mod_map);
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
                database.addQuotes(device.getOwner().getFullName()),
                database.addQuotes(device.getDeviceType().toString())
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
                String.format("%s = '%s'", "owner", device.getOwner().getFullName().replaceAll("'", "''")),
                String.format("%s = '%s'", "device_type", device.getDeviceType().toString())
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
        List<String> owners = new LinkedList<>();

        // return an empty list if query didn't return anything
        if (r_set == null) {
            return devices;
        }

        Device device;
        while ((device = getDevice(r_set)) != null) {
            try {
                String owner = r_set.getString("owner");
                owners.add(owner);
                devices.add(device);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // second for loop to iterate over usernames
        int length = devices.size();
        for (int i = 0; i < length; i++) {
            User owner = Storage.getInstance().getUserByName(owners.get(i));
            devices.get(i).setOwner(owner);
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
            Device.device_type device_type = Device.device_type.valueOf(r_set.getString("device_type"));

            // set some device fields to null, then fill them in after
            Device device = new Device(device_name, null, device_type);

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
                database.addQuotes(ticket.getLocation().getNodeID()),
                database.addQuotes(dtf.print(ticket.getRequestDate())),
                database.addQuotes(dtf.print(ticket.getFulfillDate())),
                String.valueOf(ticket.isFulfilled())
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
                String.format("%s = '%s'", "location", ticket.getLocation().getNodeID().replaceAll("'", "''")),
                String.format("%s = '%s'", "request_time", dtf.print(ticket.getRequestDate())),
                String.format("%s = '%s'", "fulfill_time", dtf.print(ticket.getFulfillDate())),
                String.format("%s = %b", "is_fulfilled", ticket.isFulfilled())
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

        return getTickets(r_set);
    }

    /**
     * Gets a list of all unfulfilled tickets
     * @return A list of all unfulfilled tickets
     */
    public List<Ticket> getAllUnfulfilledTickets() {
        ResultSet r_set = database.query(
                "TICKETS",
                null,
                "is_fulfilled = false",
                null,
                null
        );

        return getTickets(r_set);
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

        return getTickets(r_set);
    }

    /**
     * private method for parsing result set.
     *
     * @param r_set the result set containing tickets.
     * @return a List of the Ticket objects from the table.
     */
    private List<Ticket> getTickets(ResultSet r_set) {
        List<Ticket> tickets = new LinkedList<>();
        List<DateTime> requestDateTimes = new LinkedList<>();
        List<DateTime> fulfillDateTimes = new LinkedList<>();
        List<String> locations = new LinkedList<>();
        List<Long> ids = new LinkedList<>();

        // return an empty list if query didn't return anything
        if (r_set == null) {
            return tickets;
        }

        Ticket ticket;
        while ((ticket = getTicket(r_set)) != null) {
            try {
                requestDateTimes.add(new DateTime(r_set.getTimestamp("request_time")));
                fulfillDateTimes.add(new DateTime(r_set.getTimestamp("fulfill_time")));
                ids.add(r_set.getLong("ticket_id"));
                locations.add(r_set.getString("location"));
                tickets.add(ticket);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // second for loop to create the edu.wpi.cs3733d18.teamS.service requests
        int length = ids.size();
        for (int i = 0; i < length; i++) {
            DateTime requestDateTime = requestDateTimes.get(i);
            DateTime fulfillDateTime = fulfillDateTimes.get(i);
            Long id = ids.get(i);
            Node location = Storage.getInstance().getNodeByID(locations.get(i));

            tickets.get(i).setID(id);
            tickets.get(i).setRequestDate(requestDateTime);
            tickets.get(i).setFulfillDate(fulfillDateTime);
            tickets.get(i).setLocation(location);
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
            String requester_name = r_set.getString("requester_name");
            String fulfiller_name = r_set.getString("fulfiller_name");
            String description = r_set.getString("description");
            boolean is_fulfilled = r_set.getBoolean("is_fulfilled");

            // set some request fields to null, then fill them in after
            Ticket ticket = new Ticket(requester_name, fulfiller_name, description, null, is_fulfilled);

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

        if (!database.doesTableExist("NODES")) {
            database.createTable("NODES", new String[]{
                    String.format("%s VARCHAR (100) PRIMARY KEY", "node_id"),
                    String.format("%s INT", "x_coord"),
                    String.format("%s INT", "y_coord"),
                    String.format("%s VARCHAR (100)", "floor"),
                    String.format("%s VARCHAR (100)", "building"),
                    String.format("%s VARCHAR (100)", "node_type"),
                    String.format("%s VARCHAR (100)", "long_name"),
                    String.format("%s VARCHAR (100)", "short_name"),
                    String.format("%s VARCHAR (100)", "team_assigned"),
                    String.format("%s INT", "x_coord_3d"),
                    String.format("%s INT", "y_coord_3d"),
                    String.format("%s BOOLEAN", "disabled")
            });

            // read from CSV file
            CSVReader csv_reader = new CSVReader(Storage.getInstance().getDatabase());
            csv_reader.readCSVFile("csv/SquonksNodes.csv", "NODES");
        }

        if (!database.doesTableExist("USERS")) {
            database.createTable("USERS", new String[]{
                    String.format("%s BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)", "user_id"),
                    String.format("%s VARCHAR (100)", "username"),
                    String.format("%s VARCHAR (100)", "password"),
                    String.format("%s VARCHAR (100)", "first_name"),
                    String.format("%s VARCHAR (100)", "last_name"),
                    String.format("%s VARCHAR (100)", "full_name"),
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
                    String.format("%s VARCHAR (100)", "location"),
                    String.format("%s TIMESTAMP", "request_time"),
                    String.format("%s TIMESTAMP", "fulfill_time"),
                    String.format("%s BOOLEAN", "is_fulfilled")
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


