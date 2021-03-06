package me.outdare.outdare.register;

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
import me.outdare.outdare.login.User;
import me.outdare.outdare.services.OutdareService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterActivity extends Activity {

    private View view;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etPhone;
    private Button btnRegister;

    private OutdareService outdareService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter loginAdapter = new RestAdapter.Builder().setEndpoint(ODConstants.SERVER).build();
        outdareService = loginAdapter.create(OutdareService.class);

        view = getLayoutInflater().inflate(R.layout.activity_register, null);
        etUsername = (EditText) view.findViewById(R.id.register_et_username);
        etPassword = (EditText) view.findViewById(R.id.register_et_password);
        etEmail = (EditText) view.findViewById(R.id.register_et_email);
        etPhone = (EditText) view.findViewById(R.id.register_et_phone);
        btnRegister = (Button) view.findViewById(R.id.register_btn_submit);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        setContentView(view);
    }

    private void registerUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();

        outdareService.createUser(username, email, phone, new Callback<User>() {
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
                Log.e("RegisterActivity", error.toString());
            }
        });
    }

}