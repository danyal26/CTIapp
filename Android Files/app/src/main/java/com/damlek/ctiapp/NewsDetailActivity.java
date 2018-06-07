package com.damlek.ctiapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

public class NewsDetailActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    TextView txtFrom, txtTime, txtDate, txtDetails;
    String from, time, date, details;

    static String studentId, firstName, lastName, programID, base_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        from = getIntent().getExtras().getString("sender");
        time = getIntent().getExtras().getString("time");
        date = getIntent().getExtras().getString("date");
        details = getIntent().getExtras().getString("details");

        studentId = getIntent().getExtras().getString("student_id");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Announcement");
        data.putString("previous_activity","StudentMainActivity");

        data.putString("student_id", studentId);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        txtFrom = (TextView) findViewById(R.id.text_sender);
        txtTime = (TextView) findViewById(R.id.text_time);
        txtDate = (TextView) findViewById(R.id.text_date);
        txtDetails = (TextView) findViewById(R.id.text_details);


        txtFrom.setText(from);
        txtTime.setText("Time: " + time);
        txtDate.setText("Date: " + date);
        txtDetails.setText(details);

    }
}
