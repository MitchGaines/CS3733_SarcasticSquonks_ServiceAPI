package edu.wpi.cs3733d18.SquonksAPI.data;

import edu.wpi.cs3733d18.SquonksAPI.database.Storage;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * User.java
 * Class for all possible users
 * Author: Danny Sullivan
 * Date: March 31, 2018
 * Modified by: Joseph Turcotte
 * Date: April 6, 2018
 */

public class User {
    user_type type;
    private long user_id;
    private boolean can_mod_map;
    private String username;
    private String first_name;
    private String last_name;
    private String full_name;
    private byte[] password_salt;
    private byte[] enc_password;

    public User(String username, String password, String first_name, String last_name, String full_name, user_type type, boolean can_mod_map) {
        password_salt = new byte[16];
        //new SecureRandom().nextBytes(password_salt);

        this.username = username;

        byte[] password_unsalted = password.getBytes();
        byte[] password_salted = new byte[password_unsalted.length + password_salt.length];
        System.arraycopy(password_unsalted, 0, password_salted, 0, password_unsalted.length);
        System.arraycopy(password_salt, 0, password_salted, password_unsalted.length, password_salt.length);

        enc_password = Base64.getEncoder().encode(password_salted);

        this.first_name = first_name;
        this.last_name = last_name;
        this.full_name = full_name;
        this.type = type;
        this.can_mod_map = can_mod_map;
    }

    /**
     * Encodes a password
     *
     * @param password the password as a string
     * @return the password, hashed and salted (as a String)
     */
    public static String encodePassword(String password) {
        byte[] password_salt = new byte[16];
        //new SecureRandom().nextBytes(password_salt);

        byte[] password_unsalted = password.getBytes();
        byte[] password_salted = new byte[password_unsalted.length + password_salt.length];
        System.arraycopy(password_unsalted, 0, password_salted, 0, password_unsalted.length);
        System.arraycopy(password_salt, 0, password_salted, password_unsalted.length, password_salt.length);

        return new String(Base64.getEncoder().encode(password_salted));
    }

    /**
     * Generates an initial list of users
     */
    public static void generateInitialUsers() {
        Storage storage = Storage.getInstance();

        // generate initial user objects to be stored in the database
        User u1 = new User("doctor", "doctor", "Doctor", "Danny", "Doctor Danny", User.user_type.DOCTOR, false);
        User u2 = new User("admin", "admin", "Admin", "Adam", "Admin Adam", User.user_type.ADMIN_STAFF, true);
        User u3 = new User("staff", "staff", "Stella", "Staff", "Stella Staff", User.user_type.REGULAR_STAFF, false);
        User u4 = new User("techtom", "tech", "Techie", "Tom", "Techie Tom", user_type.REGULAR_STAFF, false);

        // save users to database
        storage.saveUser(u1);
        storage.saveUser(u2);
        storage.saveUser(u3);
        storage.saveUser(u4);
    }

    /**
     * Takes a Boolean as a parameter and sets the permission
     * for the User if they are able to modify the map or not.
     *
     * @param if_can_mod_map is a boolean value, true if map can be
     *                       modified by the edu.wpi.cs3733d18.SquonksAPI.user, false otherwise
     */
    public void setCanModMap(boolean if_can_mod_map) {
        can_mod_map = if_can_mod_map;
    }

    /**
     * Returns whether or not the User has the ability to modify the map
     *
     * @return true if User can modify map, false otherwise
     */
    public boolean canModMap() {
        return can_mod_map;
    }

    /**
     * Returns the username of the current User
     *
     * @return username of the User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Takes a String as a parameter and changes the username of the current
     * User to the String in the parameter
     *
     * @param new_username the desired new username
     */
    public void setUsername(String new_username) {
        username = new_username;
    }

    public void setPassword(String new_password) {
        password_salt = new byte[16];
        byte[] password_unsalted = new_password.getBytes();
        byte[] password_salted = new byte[password_unsalted.length + password_salt.length];
        System.arraycopy(password_unsalted, 0, password_salted, 0, password_unsalted.length);
        System.arraycopy(password_salt, 0, password_salted, password_unsalted.length, password_salt.length);
        enc_password = Base64.getEncoder().encode(password_salted);
    }

    public byte[] getEncodedPassword() {
        return enc_password;
    }

    public byte[] getPasswordSalt() {
        return password_salt;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public user_type getType() {
        return type;
    }

    public void setType(user_type ut) {
        type = ut;
    }

    public long getUserID() {
        return user_id;
    }

    public void setUserID(long new_id) {
        user_id = new_id;
    }

    @Override
    public String toString() {
        return String.format("%s %s", first_name, last_name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id &&
                can_mod_map == user.can_mod_map &&
                Objects.equals(username, user.username) &&
                type == user.type;
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(user_id, can_mod_map, username, type);
        result = 31 * result + Arrays.hashCode(password_salt);
        result = 31 * result + Arrays.hashCode(enc_password);
        return result;
    }

    public enum user_type {DOCTOR, ADMIN_STAFF, REGULAR_STAFF}

    ///////////////////// Fancy Reports ///////////////////////

    private Stream<Ticket> requestedInRange(DateTime start, DateTime end) {
        return Storage.getInstance().getAllTickets().stream()
                .filter(e -> e.getRequestDate().toDateTime().isBefore(end.toDateTime().toInstant()) && e.getRequestDate().toDateTime().isAfter(start.toDateTime()) /*&& canFulfill(e)*/);
    }

    public long getNumFulfillableRequests(DateTime start, DateTime end) {
        return requestedInRange(start, end).count();
    }

    public long getNumFulfilledRequests(DateTime start, DateTime end) {
        return Storage.getInstance().getAllTickets().stream()
                .filter(e -> /*e.isFulfilled() &&*/ e.getFulfillDate().toDateTime().isBefore(end.toDateTime().toInstant()) && e.getFulfillDate().toDateTime().isAfter(start.toDateTime()) /*&& e.getFulfiller().getUserID() == user_id*/)
                .count();
    }

    public double getAverageFulfillmentTimeInHours(DateTime start, DateTime end) {
        return requestedInRange(start, end)
                /*.filter(Ticket::isFulfilled)*/
                .mapToDouble(e -> ((double) (e.getFulfillDate().getMillis() - e.getRequestDate().getMillis())) / DateTimeConstants.MILLIS_PER_HOUR)
                .average()
                .orElse(0);
    }
}

