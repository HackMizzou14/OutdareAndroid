package me.outdare.outdare.dares;

import android.graphics.Bitmap;

public class Submission {
    private int id;
    private String submitter;
    private Bitmap image;

    public Submission(int id, String submitter) {
        this.id = id;
        this.submitter = submitter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
