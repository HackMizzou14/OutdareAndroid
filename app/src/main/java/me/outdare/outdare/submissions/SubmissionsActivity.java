package me.outdare.outdare.submissions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.outdare.outdare.ODConstants;
import me.outdare.outdare.R;
import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.services.DownloadImageTask;
import me.outdare.outdare.services.OutdareService;
import me.outdare.outdare.services.SubmissionTask;
import retrofit.RestAdapter;

public class SubmissionsActivity extends Activity implements DownloadImageTask.ImageListener {

    private OutdareService outdareService;

    private DownloadImageTask.ImageListener _this = this;

    private View view;
    private TextView tvTitle;
    private TextView tvDetails;

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

        tvTitle.setText(dare.getTitle());
        tvDetails.setText(dare.getDetails());

//        outdareService.getSubmissions(dare.getId(), new Callback<List<Submission>>() {
//            @Override
//            public void success(List<Submission> submissions, Response response) {
//
//
//                Submission submission = submissions.get(0);
//                String imageUrl = "http://outdare.me/images/" + submission.getId() + ".png";
//                new DownloadImageTask(_this).execute(new String[]{imageUrl});
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("TAG", error.toString());
//                Log.e("SubmissionsActivity", error.getUrl());
//            }
//        });

        setContentView(view);
    }

    @Override
    public void imageRetrieved(Bitmap bitmap) {

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
            // TODO push bitmap to server

            new SubmissionTask(this, currentUser, dare.getId()).execute(photo);
        }
    }
}
