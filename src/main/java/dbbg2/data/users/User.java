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
        this.userId = "U" + Integer.toString(nextUserId);
        nextUserId++;
    }

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


}
