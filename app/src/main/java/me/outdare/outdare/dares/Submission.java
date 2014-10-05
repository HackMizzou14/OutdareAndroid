package me.outdare.outdare.dares;

import android.graphics.Bitmap;

public class Submission {
    private String _id;
    private String userId;
    private Bitmap image;

    public Submission(String id, String userId) {
        this._id = id;
        this.userId = userId;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
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
