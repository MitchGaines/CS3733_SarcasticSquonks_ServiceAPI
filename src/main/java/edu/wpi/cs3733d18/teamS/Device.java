package edu.wpi.cs3733d18.teamS;

import java.util.List;

public class Device {

    /**
     * Name of the device
     */
    private String device_name;

    /**
     * Owner of the device
     */
    private String owner;

    /**
     * Type of the device
     */
    private String device_type;

    /**
     * Constructor
     * @param device_name name of the device
     * @param owner owner of the device
     * @param device_type type of the device
     */
    public Device(String device_name, String owner, String device_type) {
        this.device_name = device_name;
        this.owner = owner;
        this.device_type = device_type;
    }

    /**
     * Generates a dummy list of devices
     */
    public static void generateDummyDevices() {
        List<Device> devices = RegisterDeviceController.devices;
        List<String> usernames = RegisterDeviceController.usernames;
        List<String> device_types = RegisterDeviceController.device_types;

        devices.add(new Device("Galaxy S6", "Joseph Turcotte", "Smartphone"));
        usernames.add("Joseph Turcotte");
        device_types.add("Smartphone");

        devices.add(new Device("Pixel 2", "Danny Sullivan", "Desktop"));
        usernames.add("Danny Sullivan");
        device_types.add("Desktop");

        devices.add(new Device("Broken Apple", "Cormac Lynch-Collier", "Laptop"));
        usernames.add("Cormac Lynch-Collier");
        device_types.add("Laptop");
    }

    public String getDeviceName() {
        return device_name;
    }

    public void setDeviceName(String device_name) {
        this.device_name = device_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDeviceType() {
        return device_type;
    }

    public void setDeviceType(String device_type) {
        this.device_type = device_type;
    }
}
