package me.outdare.outdare.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import me.outdare.outdare.ODConstants;

public class SubmissionTask extends AsyncTask<Bitmap, Void, Void> {
    private Context context;
    private String user;
    private String dareId;

    public SubmissionTask(Context context, String user, String dareId) {
        this.context = context;
        this.user = user;
        this.dareId = dareId;
    }

    @Override
    protected Void doInBackground(Bitmap... images) {
        uploadImage(images[0]);
        return null;
    }


    private void uploadImage(Bitmap bitmap) {
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);
        byte[] ba = bao.toByteArray();

        entity.addPart("image", new ByteArrayBody(ba, "temp" + System.currentTimeMillis() + ".png"));

        try {
            entity.addPart("dare_id", new StringBody(dareId));
            Log.e("TAG", "" + dareId);
            entity.addPart("user", new StringBody(user));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ODConstants.SERVER + "/submissions/create");

        httpPost.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
