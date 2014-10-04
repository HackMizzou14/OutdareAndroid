package me.outdare.outdare.dare;

public class Dare {
    private String dare;
    private String challenger;
    private double latitude;
    private double longitude;

    public Dare(String dare, String challenger, double latitude, double longitude) {
        this.dare = dare;
        this.challenger = challenger;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDare() {
        return dare;
    }

    public void setDare(String dare) {
        this.dare = dare;
    }

    public String getChallenger() {
        return challenger;
    }

    public void setChallenger(String challenger) {
        this.challenger = challenger;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
