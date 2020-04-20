package dbbg2.data.users;

public abstract class User {

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
}
