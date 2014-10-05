package me.outdare.outdare.dares;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import me.outdare.outdare.ODConstants;
import me.outdare.outdare.R;
import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.dare.DareActivity;
import me.outdare.outdare.services.OutdareService;
import me.outdare.outdare.submissions.SubmissionsActivity;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DaresActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks {

    private View view;
    private GoogleMap map;
    public List<Dare> dareList;

    private OutdareService outdareService;
    private String currentUser;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle inBundle = getIntent().getExtras().getBundle(ODConstants.BUNDLE_KEY);
        currentUser = inBundle.getString(ODConstants.USER_KEY);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint(ODConstants.SERVER).build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_dares, null);

        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                openDare(marker.getTitle());
            }
        });

        mLocationClient = new LocationClient(this, this, null);
        mLocationClient.connect();

        setContentView(view);
    }

    private void openDare(String title) {
        // TODO not this
        Dare selectedDare = null;
        for (Dare dare : dareList) {
            if (dare.getTitle().equals(title)) {
                selectedDare = dare;
                break;
            }
        }

        Intent intent = new Intent(getApplicationContext(), SubmissionsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ODConstants.DARE_KEY, selectedDare);
        bundle.putString(ODConstants.USER_KEY, currentUser);

        intent.putExtra(ODConstants.BUNDLE_KEY, bundle);

        startActivity(intent);
    }

    @Override
    public void onConnected(Bundle bundle) {
        final Location currentLocation = mLocationClient.getLastLocation();
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();

        outdareService.getDares(latitude, longitude, new Callback<List<Dare>>() {

            @Override
            public void success(List<Dare> dares, Response response) {
                dareList = dares;

                for (Dare dare : dares) {
                    Log.e("TAG", "" + dare.getLon());
                    createMarker(dare);
                }

                LatLng currLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(currLoc, 10));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("DaresActivity", error.toString());
            }
        });
    }

    private void createMarker(Dare dare) {
        LatLng location = new LatLng(dare.getLat(), dare.getLon());
        map.addMarker(new MarkerOptions()
                .title(dare.getTitle())
                .snippet(dare.getDetails())
                .position(location));
    }

    @Override
    public void onDisconnected() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dares_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_dares_new_dare) {
            Intent intent = new Intent(this, DareActivity.class);

            Bundle bundle = new Bundle();
            bundle.putString(currentUser, ODConstants.USER_KEY);

            intent.putExtra(ODConstants.BUNDLE_KEY, bundle);

            startActivity(intent);
            return true;
        }

        return false;
    }
}
