package me.outdare.outdare.dare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import me.outdare.outdare.R;


public class DareActivity extends Activity {

    private View view;
    private EditText etDare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = getLayoutInflater().inflate(R.layout.dare, null);
        etDare = (EditText) view.findViewById(R.id.dare_et_dare);

        setContentView(view);
    }
}
