package edu.wpi.cs3733d18.teamS;

import javax.xml.ws.Service;
import java.util.List;

/**
 * ServiceRequest class for computer service requests
 * Author: Joseph Turcotte
 * Date: April 16, 2018
 */
public class ServiceRequest {

    /**
     * id of ticket
     */
    private long id;

    /**
     * Names of requester and fulfiller
     */
    private String requester_name, fulfiller_name;

    /**
     * Description of the service request
     */
    private String description;

    /**
     * Location of the service request
     */
    private String location;

    /**
     * Constructor
     *
     * @param requester_name name of the requester
     * @param fulfiller_name name of the fulfiller
     * @param description    description of the request
     * @param location       where the request needs to be fulfilled
     */
    public ServiceRequest(long id, String requester_name, String fulfiller_name, String description, String location) {
        this.id = id;
        this.requester_name = requester_name;
        this.fulfiller_name = fulfiller_name;
        this.description = description;
        this.location = location;
    }

    /**
     * Generates a dummy list of requests to populate the log
     */
    public static void generateDummyRequests() {
        List<ServiceRequest> requests = ServiceHomeController.requests;

        requests.add(new ServiceRequest(1, "Joe Turcotte", "Matt Puentes",
                "My computer broke!", "3rd Hallway"));
        requests.add(new ServiceRequest(2, "Joe Turcotte", "Mitch Gaines",
                "My computer broke again!", "3rd Hallway"));
        requests.add(new ServiceRequest(3, "Joe Turcotte", "Cormac Lynch-Collier",
                "Can't connect to Wifi!", "3rd Hallway"));
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

