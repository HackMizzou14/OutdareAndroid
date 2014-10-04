package me.outdare.outdare.login;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.outdare.outdare.R;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity {

    private View view;
    private EditText etUser;
    private EditText etPassword;
    private Button btnSubmit;

    private OutdareService outdareService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint("http://outdare.me").build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_main, null);

        etUser = (EditText) view.findViewById(R.id.login_et_user);
        etPassword = (EditText) view.findViewById(R.id.login_et_user);
        btnSubmit = (Button) view.findViewById(R.id.login_btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCredentials();
            }
        });

        setContentView(view);
    }

    private void submitCredentials() {
        String user = etUser.getText().toString();
        String password = etUser.getText().toString();

        outdareService.login(user, password, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                // TODO start DareActivity
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("FAIL", error.toString());
            }
        });
    }
}