package me.outdare.outdare.submissions;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.outdare.outdare.R;
import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.dares.Submission;
import me.outdare.outdare.services.DownloadImageTask;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubmissionsActivity extends Activity implements DownloadImageTask.ImageListener {

    public static final String DARE_KEY = "dare_key";

    private OutdareService outdareService;

    private DownloadImageTask.ImageListener _this = this;

    private View view;
    private TextView tvDare;
    private ImageView ivImage;

    private Dare dare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle inBundle = getIntent().getExtras().getBundle(DARE_KEY);
        dare = (Dare) inBundle.getParcelable(DARE_KEY);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint("http://outdare.me").build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_submissions, null);

        tvDare = (TextView) view.findViewById(R.id.submissions_tv_dare);
        ivImage = (ImageView) view.findViewById(R.id.submissions_iv_image);

        outdareService.getSubmissions(dare.getId(), new Callback<List<Submission>>() {
            @Override
            public void success(List<Submission> submissions, Response response) {
                Submission submission = submissions.get(0);
                String imageUrl = "http://outdare.me/images/" + submission.getId() + ".png";
                new DownloadImageTask(_this).execute(new String[]{imageUrl});
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TAG", error.toString());
                Log.e("SubmissionsActivity", error.getUrl());
            }
        });

        setContentView(view);
    }

    @Override
    public void imageRetrieved(Bitmap bitmap) {
        ivImage.setImageBitmap(bitmap);
    }
}
