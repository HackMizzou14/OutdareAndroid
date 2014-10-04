package me.outdare.outdare.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.outdare.outdare.ODConstants;
import me.outdare.outdare.R;
import me.outdare.outdare.dares.DaresActivity;
import me.outdare.outdare.register.RegisterActivity;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends Activity {

    private View view;
    private EditText etUser;
    private EditText etPassword;
    private Button btnSubmit;
    private Button btnRegister;

    private OutdareService outdareService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint(ODConstants.SERVER).build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_login, null);

        etUser = (EditText) view.findViewById(R.id.login_et_user);
        etPassword = (EditText) view.findViewById(R.id.login_et_user);
        btnSubmit = (Button) view.findViewById(R.id.login_btn_submit);
        btnRegister = (Button) view.findViewById(R.id.login_btn_register);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitCredentials();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });

        setContentView(view);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void submitCredentials() {
        String user = etUser.getText().toString();

        outdareService.login(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Intent dareIntent = new Intent(getApplicationContext(), DaresActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString(ODConstants.USER_KEY, user.getUser());
                dareIntent.putExtra(ODConstants.BUNDLE_KEY, bundle);

                startActivity(dareIntent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("LoginActivity", error.toString());
            }
        });
    }
}