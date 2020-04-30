package dbbg2.data.users;

import javax.persistence.*;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

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
