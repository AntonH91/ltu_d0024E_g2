package dbbg2.data.users;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.persistence.annotations.Index;

import javax.persistence.*;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uid;

    @Index(unique = true)
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


    @Basic(optional = false)
    private String password = "";

    @Transient
    private final boolean authenticated = false;

    public User() {
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

    public void setPassword(String password) {
        this.password = DigestUtils.md5Hex(password);
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

    private void createUserId() {
        String temp = firstName.substring(0, Math.min(1, firstName.length())) + lastName.substring(0, Math.min(3, lastName.length())) +
                this.uid;
        this.userId = temp.toLowerCase();
    }

    /**
     * Triggers the creation of a new User ID, if necessary
     * @return True if a new User ID was created
     */
    public boolean triggerUserIdCreation() {
        boolean userIdCreationNeeded = false;
        if (this.userId.isEmpty() && this.uid != 0) {
            createUserId();
            userIdCreationNeeded = true;
        }

        return userIdCreationNeeded;
    }

}
