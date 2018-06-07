package com.damlek.ctiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LecturerMainActivity extends AppCompatActivity {

    TextView txtWelcome;

    String base_url, lecturerID, firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_main);

        // Pull data from previous intent
        base_url = getIntent().getExtras().getString("base_url");
        lecturerID = getIntent().getExtras().getString("lecturer_id");
        firstName = getIntent().getExtras().getString("first_name");

        txtWelcome = (TextView) findViewById(R.id.welcome_text);
        txtWelcome.setText("Welcome, " + firstName);

    }

    public void selectModule(View args0){

        Intent intent = new Intent("com.damlek.ctiapp.LecturerModulesActivity");
        intent.putExtra("base_url", base_url);
        intent.putExtra("lecturer_id", lecturerID);
        intent.putExtra("first_name", firstName);

        startActivity(intent);
    }

    public void logout(View args0){
        Intent intent = new Intent(LecturerMainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
