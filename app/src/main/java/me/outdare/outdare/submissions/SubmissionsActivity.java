package me.outdare.outdare.submissions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import me.outdare.outdare.ODConstants;
import me.outdare.outdare.R;
import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.dares.Submission;
import me.outdare.outdare.services.DownloadImageTask;
import me.outdare.outdare.services.OutdareService;
import me.outdare.outdare.services.SubmissionTask;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SubmissionsActivity extends Activity implements DownloadImageTask.ImageListener {

    private OutdareService outdareService;

    private DownloadImageTask.ImageListener _this = this;

    private View view;
    private TextView tvTitle;
    private TextView tvDetails;
    private LinearLayout llImages;

    private Dare dare;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle inBundle = getIntent().getExtras().getBundle(ODConstants.BUNDLE_KEY);
        dare = (Dare) inBundle.getParcelable(ODConstants.DARE_KEY);
        currentUser = inBundle.getString(ODConstants.USER_KEY);


        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint(ODConstants.SERVER).build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_submissions, null);

        tvTitle = (TextView) view.findViewById(R.id.submissions_tv_title);
        tvDetails = (TextView) view.findViewById(R.id.submissions_tv_details);
        llImages = (LinearLayout) view.findViewById(R.id.submissions_ll_images);

        tvTitle.setText(dare.getTitle());
        tvDetails.setText(dare.getDetails());

        outdareService.getSubmissions(dare.getId(), new Callback<List<Submission>>() {
            @Override
            public void success(List<Submission> submissions, Response response) {
                for (Submission submission : submissions) {
                    String imageUrl = ODConstants.SERVER + "/images/" + submission.getId();
                    new DownloadImageTask(_this).execute(new String[]{imageUrl});
                }
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
        llImages.addView(createImageView(bitmap));
    }

    private View createImageView(Bitmap bitmap) {
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(bitmap);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(500, 600);
        params.setMargins(5, 5, 5, 5);
        iv.setLayoutParams(params);
        return iv;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.submission_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_submission_new_submission) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, 111);

            return true;
        }

        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            new SubmissionTask(this, currentUser, dare.getId()).execute(photo);
        }
    }
}
