package dbbg2.data.users;

import dbbg2.persistence.Database;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long uid;

    private String userId = "";
    private String personNr = "";

    private String firstName = "";
    private String lastName = "";

    private String streetAddress = "";
    private String postCode = "";
    private String postArea = "";

    private String phoneNr = "";
    private String email = "";

    private boolean authenticated = false;

    private static int nextUserId = 10001;

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

    public String getLastName() {
        return lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPostArea() {
        return postArea;
    }

    public String getPhoneNr() {
        return phoneNr;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setPostArea(String postArea) {
        this.postArea = postArea;
    }

    public void setPhoneNr(String phoneNr) {
        this.phoneNr = phoneNr;
    }

    public void setEmail(String email) {
        this.email = email;
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
            pst.setString(1,this.firstName);
            pst.setString(2,this.lastName);
            pst.setString(3,this.streetAddress);
            pst.setString(4,this.postCode);
            pst.setString(5,this.postArea);
            pst.setString(6,this.personNr);
            pst.setString(7,this.email);
            pst.setString(8,this.phoneNr);
            pst.execute();

            // TODO Get userId from the database
            saveSpecificDetails(userId);

        }
        finally {
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
            pst.setString(1,this.firstName);
            pst.setString(2,this.lastName);
            pst.setString(3,this.streetAddress);
            pst.setString(4,this.postCode);
            pst.setString(5,this.postArea);
            pst.setString(6,this.personNr);
            pst.setString(7,this.email);
            pst.setString(8,this.phoneNr);
            pst.execute();

            ResultSet rsNewId = pst.getGeneratedKeys();




            // TODO Get userId from the database
            saveSpecificDetails(userId);

        }
        finally {
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

    // TODO remove this
    public static void main(String[] args) {
        final String PERSISTENCE_UNIT_NAME = "lddb_jpa";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        // Read existing
        Query q = em.createQuery("SELECT u FROM User u");
        List<User> userList = q.getResultList();

        for (User u : userList) {
            System.out.println(u.toString());
        }
        System.out.println("Size: " + userList.size());

        // New user

        em.getTransaction().begin();
        User u = new User();

        u.setFirstName("Anton");
        u.setLastName("Högelin");

        em.persist(u);

        em.getTransaction().commit();

        em.close();


    }

}
