package edu.wpi.cs3733d18.SquonksAPI.data;

import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import org.joda.time.DateTime;

/**
 * ServiceRequest class for computer service requests.
 *
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: April 16, 2018
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
    private Node location;

    /**
     * Date-times that the ticket was requested and fulfilled
     */
    private DateTime request_date, fulfill_date;

    /**
     * Indicates whether the ticket has been fulfilled
     */
    private boolean is_fulfilled;

    /**
     * Constructor for making a Ticket, this takes in the requester's name, the fulfiller's name, a description and a location.
     *
     * @param requester_name name of the requester.
     * @param fulfiller_name name of the fulfiller.
     * @param description    description of the request.
     * @param location       where the request needs to be fulfilled.
     */
    public Ticket(String requester_name, String fulfiller_name, String description, Node location, boolean is_fulfilled) {
        this.requester_name = requester_name;
        this.fulfiller_name = fulfiller_name;
        this.description = description;
        this.location = location;
        this.is_fulfilled = is_fulfilled;
    }

    /**
     * Generates a dummy list of requests to populate the log.
     */
    public static void generateInitialTickets() {
        Storage storage = Storage.getInstance();

        Ticket t1 = new Ticket("Admin Adam", "Techie Tom",
                "My computer broke!", Storage.getInstance().getNodeByID("ADEPT00201"), false);
        t1.setRequestDate(new DateTime());
        storage.saveTicket(t1);

        Ticket t2 = new Ticket("Stella Staff", "Techie Tom",
                "My computer broke again!", Storage.getInstance().getNodeByID("ALABS001L2"), false);
        t2.setRequestDate(new DateTime());
        storage.saveTicket(t2);

        Ticket t3 = new Ticket("Doctor Danny", "Techie Tom",
                "Can't connect to Wifi!", Storage.getInstance().getNodeByID("BSERV00102"), false);
        t3.setRequestDate(new DateTime());
        storage.saveTicket(t3);
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

    public Node getLocation() {
        return location;
    }

    public void setLocation(Node location) {
        this.location = location;
    }

    public DateTime getRequestDate() {
        return request_date;
    }

    public void setRequestDate(DateTime request_date) {
        this.request_date = request_date;
    }

    public DateTime getFulfillDate() {
        return fulfill_date;
    }

    public void setFulfillDate(DateTime fulfill_date) {
        this.fulfill_date = fulfill_date;
    }

    public boolean isFulfilled() {
        return is_fulfilled;
    }

    public void setIsFulfilled(boolean is_fulfilled) {
        this.is_fulfilled = is_fulfilled;
    }
}

