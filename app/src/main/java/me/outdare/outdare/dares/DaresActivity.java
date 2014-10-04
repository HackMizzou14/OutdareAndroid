package me.outdare.outdare.dares;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;

import java.util.List;

import me.outdare.outdare.R;
import me.outdare.outdare.dare.Dare;
import me.outdare.outdare.dare.DareActivity;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DaresActivity extends Activity implements GooglePlayServicesClient.ConnectionCallbacks {

    private View view;
    private ListView lvDares;
    private DaresAdapter adapter;

    private OutdareService outdareService;
    private String currentUser;
    private LocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle inBundle = getIntent().getExtras().getBundle(DareActivity.USER_KEY);
        currentUser = inBundle.getString(DareActivity.USERNAME_KEY);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint("http://outdare.me").build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_dares, null);
        lvDares = (ListView) view.findViewById(R.id.dares_lv_dares);

        adapter = new DaresAdapter(this, android.R.layout.simple_list_item_1);
        lvDares.setAdapter(adapter);

        mLocationClient = new LocationClient(this, this, null);
        mLocationClient.connect();

        setContentView(view);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location currentLocation = mLocationClient.getLastLocation();
        double latitude = currentLocation.getLatitude();
        double longitude = currentLocation.getLongitude();

        outdareService.getDares(currentUser, latitude, longitude, new Callback<List<Dare>>() {
            @Override
            public void success(List<Dare> dares, Response response) {
                adapter.addAll(dares);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("DaresActivity", error.toString());
            }
        });
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
            bundle.putString(currentUser, DareActivity.USERNAME_KEY);

            intent.putExtra(DareActivity.USER_KEY, bundle);

            startActivity(intent);
            return true;
        }

        return false;
    }
}
