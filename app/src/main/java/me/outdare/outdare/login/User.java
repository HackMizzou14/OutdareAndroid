package me.outdare.outdare.login;

public class User {
    private String _id;
    private String user;
    private String email;
    private String phoneNumber;

    public User(String id, String user, String email, String phoneNumber) {
        this._id = id;
        this.user = user;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
