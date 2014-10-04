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
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import me.outdare.outdare.ODConstants;

public class SubmissionTask extends AsyncTask<Bitmap, Void, Void> {
    private Context context;
    private String user;
    private int dareId;

    public SubmissionTask(Context context, String user, int dareId) {
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
        File f = new File(context.getCacheDir(), "temp" + System.currentTimeMillis());

        try {
            f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] bitmapdata = bos.toByteArray();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);

            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

        entity.addPart("image", new FileBody(f));


        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(ODConstants.SERVER + "/submissions/create");

        httpPost.setEntity(entity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());
            Log.e("TAG", responseString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
