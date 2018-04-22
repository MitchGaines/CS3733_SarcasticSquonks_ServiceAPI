package edu.wpi.cs3733d18.SquonksAPI.data;

import edu.wpi.cs3733d18.SquonksAPI.database.Storage;

/**
 * A class that manages registered devices and deals with the methods related to them.
 *
 * @author Joseph Turcotte
 * @version %I%, %G%
 * Date: April 16, 2018
 */
public class Device {

    /**
     * Name of the device.
     */
    private String device_name;

    /**
     * Owner of the device.
     */
    private User owner;

    /**
     * Type of the device.
     */
    device_type type;

    /**
     * Enum of possible device types
     */
    public enum device_type {SMARTPHONE, LAPTOP, DESKTOP}

    /**
     * Constructs a Device by taking in a name, an owner, and the device type.
     *
     * @param device_name name of the device
     * @param owner       owner of the device
     * @param type        type of the device
     */
    public Device(String device_name, User owner, device_type type) {
        this.device_name = device_name;
        this.owner = owner;
        this.type = type;
    }

    /**
     * Generates a dummy list of devices.
     */
    public static void generateInitialDevices() {
        Storage storage = Storage.getInstance();
        storage.saveDevice(new Device("Adam's Dell",
                Storage.getInstance().getUserByName("Admin Adam"), device_type.DESKTOP));
        storage.saveDevice(new Device("Stella's Pixel",
                Storage.getInstance().getUserByName("Stella Staff"), device_type.SMARTPHONE));
        storage.saveDevice(new Device("Danny's Broken Apple",
                Storage.getInstance().getUserByName("Doctor Danny"), device_type.LAPTOP));
    }

    public String getDeviceName() {
        return device_name;
    }

    public void setDeviceName(String device_name) {
        this.device_name = device_name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public device_type getDeviceType() {
        return type;
    }

    public void setDeviceType(device_type device_type) {
        this.type = device_type;
    }

    @Override
    public String toString() {
        return String.format("%s", device_name);
    }
}
