package me.outdare.outdare.dare;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import me.outdare.outdare.ODConstants;
import me.outdare.outdare.R;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DareActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks {

    private View view;
    private EditText etTitle;
    private EditText etDetails;

    private Button btnSubmit;
    private LocationClient mLocationClient;

    private String userId;

    private boolean isConnected = false;
    private OutdareService outdareService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint(ODConstants.SERVER).build();
        outdareService = loginAdapter.create(OutdareService.class);

        Bundle inBundle = getIntent().getExtras().getBundle(ODConstants.BUNDLE_KEY);
        userId = inBundle.getString(ODConstants.USER_KEY);

        view = getLayoutInflater().inflate(R.layout.activity_dare, null);
        etTitle = (EditText) view.findViewById(R.id.dare_et_title);
        etDetails = (EditText) view.findViewById(R.id.dare_et_details);
        btnSubmit = (Button) view.findViewById(R.id.dare_btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDare();
            }
        });


        mLocationClient = new LocationClient(this, this, null);
        mLocationClient.connect();

        setContentView(view);
    }

    private void submitDare() {
        if (!isConnected) {
            Toast.makeText(this, "Unable to detect your location.", Toast.LENGTH_LONG).show();
            return;
        }

        Location location = mLocationClient.getLastLocation();
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String title = etTitle.getText().toString();
        String details = etDetails.getText().toString();

        outdareService.createDare(title, details, userId, lat, lon, new Callback<Dare>() {
            @Override
            public void success(Dare dare, Response response) {
                etTitle.setText("");
                etDetails.setText("");
                Toast.makeText(getApplicationContext(), "Dare submitted successfully.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("DareActivity", error.toString());
            }
        });
    }

    @Override
    public void onConnected(Bundle bundle) {
        isConnected = true;
    }

    @Override
    public void onDisconnected() {
        isConnected = false;
    }
}
