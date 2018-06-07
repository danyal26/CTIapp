package com.damlek.ctiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;


public class FeedbackDetailActivity extends AppCompatActivity {

    String base_url, department, message, student_no, firstName, lastName, programID;
    TextView txtDept, txtMessage;

    static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);

        base_url = getIntent().getExtras().getString("base_url");
        department = getIntent().getExtras().getString("department");
        message = getIntent().getExtras().getString("message");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        txtDept = (TextView) findViewById(R.id.text_sender);
        txtMessage = (TextView) findViewById(R.id.text_message);

        txtDept.setText("From: " + department);
        txtMessage.setText(message);

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Message");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "SupportMainActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

    }
}
