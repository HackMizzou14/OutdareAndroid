package me.outdare.outdare.dare;

import android.os.Parcel;
import android.os.Parcelable;

public class Dare implements Parcelable {
    private int id;
    private String title;
    private String details;
    private String userId;
    private double lat;
    private double lon;

    public Dare(int id, String title, String details, String userId, double lat, double lon) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(details);
        dest.writeString(userId);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }

    public static final Creator CREATOR = new Creator<Dare>() {
        @Override
        public Dare createFromParcel(Parcel source) {
            int id = source.readInt();
            String title = source.readString();
            String details = source.readString();
            String userId = source.readString();
            double lat = source.readDouble();
            double lon = source.readDouble();

            return new Dare(id, title, details, userId, lat, lon);
        }

        @Override
        public Dare[] newArray(int size) {
            return new Dare[size];
        }
    };
}
