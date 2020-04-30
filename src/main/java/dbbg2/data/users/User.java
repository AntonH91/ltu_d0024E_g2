package dbbg2.data.users;

import dbbg2.persistence.Database;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    private static int nextUserId = 10001;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    private String userId = "";

    @Basic(optional = false)
    private String personNr = "";
    @Basic(optional = false)
    private String firstName = "";
    @Basic(optional = false)
    private String lastName = "";

    private String streetAddress = "";
    private String postCode = "";
    private String postArea = "";
    private String phoneNr = "";

    @Basic(optional = false)
    private String email = "";

    private boolean authenticated = false;

    public User() {
        this.userId = generateUserId();
    }

    /* ---------------------------
        GETTERS / SETTERS
      --------------------------- */

    public String getPersonNr() {
        return personNr;
    }

    public void setPersonNr(String personNr) {
        this.personNr = personNr;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostArea() {
        return postArea;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * Gets a string representation of which kind of user this is.
     *
     * @return A string describing the user type.
     */
    public String getUserType() {
        return "User";
    }

    /**
     * Saves the user to the database.
     */
    public void saveUser() throws SQLException {

        if (userId == null) {
            createNewUser();
        } else {
            updateUser();
        }


    }

    private void updateUser() throws SQLException {
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement(
                "UPDATE users " +
                        "SET first_name=?," +
                        "last_name=?," +
                        "street_address=?," +
                        "post_code=?," +
                        "post_area = ?," +
                        "person_nr = ?," +
                        "email = ?," +
                        "phone_nr = ?" +
                        "WHERE user_id = ?"
        );

        try {
            pst.setString(1, this.firstName);
            pst.setString(2, this.lastName);
            pst.setString(3, this.streetAddress);
            pst.setString(4, this.postCode);
            pst.setString(5, this.postArea);
            pst.setString(6, this.personNr);
            pst.setString(7, this.email);
            pst.setString(8, this.phoneNr);
            pst.execute();

            // TODO Get userId from the database
            saveSpecificDetails(userId);

        } finally {
            pst.close();
        }
    }

    private void createNewUser() throws SQLException {
        PreparedStatement pst = Database.getDefaultInstance().getPreparedStatement(
                "INSERT INTO users(" +
                        "first_name," +
                        "last_name," +
                        "street_address," +
                        "post_code," +
                        "post_area," +
                        "person_nr," +
                        "email," +
                        "phone_nr) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
        try {
            pst.setString(1, this.firstName);
            pst.setString(2, this.lastName);
            pst.setString(3, this.streetAddress);
            pst.setString(4, this.postCode);
            pst.setString(5, this.postArea);
            pst.setString(6, this.personNr);
            pst.setString(7, this.email);
            pst.setString(8, this.phoneNr);
            pst.execute();

            ResultSet rsNewId = pst.getGeneratedKeys();


            // TODO Get userId from the database
            saveSpecificDetails(userId);

        } finally {
            pst.close();
        }

    }

    protected void saveSpecificDetails(String userId) throws SQLException {

    }

    @Override
    public boolean equals(Object obj) {
        boolean equals = false;
        if (obj instanceof User) {
            User otherUser = (User) obj;
            equals = this.getUserId().equals(otherUser.getUserId());
        }
        return equals;
    }


    /* ---------------------------
            Private helpers
        ---------------------------  */

    /**
     * Generates or gets the userId for a User class. This secures UID unicity.
     * In the future this will be the responsibility of the database.
     */
    private String generateUserId() {
        // TODO Change this to query database for User ID.
        String newId = "U" + Integer.toString(nextUserId);
        nextUserId++;

        return newId;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", userId='" + userId + '\'' +
                ", personNr='" + personNr + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", postArea='" + postArea + '\'' +
                ", phoneNr='" + phoneNr + '\'' +
                ", email='" + email + '\'' +
                ", authenticated=" + authenticated +
                '}';
    }

}
