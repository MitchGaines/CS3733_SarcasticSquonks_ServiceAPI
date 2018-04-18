package edu.wpi.cs3733d18.teamS.data;

import edu.wpi.cs3733d18.teamS.database.Storage;

/**
 * ServiceRequest class for computer service requests.
 * @author Joseph Turcotte
 * @version 1.3, April 16, 2018
 */
public class Ticket {

    /**
     * id of ticket.
     */
    private long id;

    /**
     * Names of requester and fulfiller.
     */
    private String requester_name, fulfiller_name;

    /**
     * Description of the service request.
     */
    private String description;

    /**
     * Location of the service request.
     */
    private String location;

    /**
     * Constructor for making a Ticket, this takes in the requester's name, the fulfiller's name, a description and a location.
     * @param requester_name name of the requester.
     * @param fulfiller_name name of the fulfiller.
     * @param description    description of the request.
     * @param location       where the request needs to be fulfilled.
     */
    public Ticket(String requester_name, String fulfiller_name, String description, String location) {
        this.requester_name = requester_name;
        this.fulfiller_name = fulfiller_name;
        this.description = description;
        this.location = location;
    }

    /**
     * Generates a dummy list of requests to populate the log.
     */
    public static void generateInitialTickets() {
        Storage storage = Storage.getInstance();
        storage.saveTicket(new Ticket("Admin Adam", "Techie Tom",
                "My computer broke!", "3rd Floor"));
        storage.saveTicket(new Ticket("Stella Staff", "Techie Tom",
                "My computer broke again!", "Ground Floor"));
        storage.saveTicket(new Ticket("Doctor Danny", "Techie Tom",
                "Can't connect to Wifi!", "Front Desk"));
    }

    public long getID() {
        return id;
    }

    public void setID(long new_id) {
        id = new_id;
    }

    public String getRequesterName() {
        return requester_name;
    }

    public void setRequesterName(String requester_name) {
        this.requester_name = requester_name;
    }

    public String getFulfillerName() {
        return fulfiller_name;
    }

    public void setFulfillerName(String fulfiller_name) {
        this.fulfiller_name = fulfiller_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

