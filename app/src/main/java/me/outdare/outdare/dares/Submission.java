package me.outdare.outdare.dares;

import android.graphics.Bitmap;

public class Submission {
    private int id;
    private String userId;
    private Bitmap image;

    public Submission(int id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
