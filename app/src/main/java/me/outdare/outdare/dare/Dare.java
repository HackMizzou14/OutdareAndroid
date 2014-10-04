package me.outdare.outdare.dare;

import android.os.Parcel;
import android.os.Parcelable;

public class Dare implements Parcelable {
    private int id;
    private String title;
    private String challenger;
    private double latitude;
    private double longitude;

    public Dare(int id, String dare, String challenger, double latitude, double longitude) {
        this.id = id;
        this.title = dare;
        this.challenger = challenger;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(challenger);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    public static final Creator CREATOR = new Creator<Dare>() {
        @Override
        public Dare createFromParcel(Parcel source) {
            int id = source.readInt();
            String dare = source.readString();
            String challenger = source.readString();
            double lat = source.readDouble();
            double longitude = source.readDouble();

            return new Dare(id, dare, challenger, lat, longitude);
        }

        @Override
        public Dare[] newArray(int size) {
            return new Dare[size];
        }
    };
}
