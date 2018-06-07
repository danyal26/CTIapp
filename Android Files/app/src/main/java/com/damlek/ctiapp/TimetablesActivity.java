package com.damlek.ctiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.support.v4.app.FragmentManager;
import android.widget.ListView;
import android.widget.Toast;

public class TimetablesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;
    String base_url, student_no, firstName, lastName, programID;

    ListView listView;
    String[] timetableArray = {"Class Timetable", "Test Timetable", "Exam Timetable"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetables);

        // Get values from previous activity
        base_url = getIntent().getExtras().getString("base_url");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Select Timetable");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "StudentMenuActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        TimetableAdapter timetableAdapter = new TimetableAdapter(this, timetableArray);
        listView = (ListView) findViewById(R.id.timetables_list_view);
        listView.setAdapter(timetableAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String timetable = String.valueOf(parent.getItemAtPosition(position));

                        Intent intent;

                        switch (timetable) {
                            case "Class Timetable":
                                intent = new Intent("com.damlek.ctiapp.ClassTimetableActivity");
                                intent.putExtra("base_url", base_url);
                                intent.putExtra("student_no", student_no);
                                intent.putExtra("first_name", firstName);
                                intent.putExtra("last_name", lastName);
                                intent.putExtra("program_id",programID);
                                startActivity(intent);
                                break;
                            case "Test Timetable":
                                intent = new Intent("com.damlek.ctiapp.TestTimetableActivity");
                                intent.putExtra("student_no", student_no);
                                intent.putExtra("base_url", base_url);
                                intent.putExtra("first_name", firstName);
                                intent.putExtra("last_name", lastName);
                                intent.putExtra("program_id",programID);
                                startActivity(intent);
                                break;
                            case "Exam Timetable":
                                intent = new Intent("com.damlek.ctiapp.ExamTimetableActivity");
                                intent.putExtra("base_url", base_url);
                                intent.putExtra("student_no", student_no);
                                intent.putExtra("first_name", firstName);
                                intent.putExtra("last_name", lastName);
                                intent.putExtra("program_id",programID);
                                startActivity(intent);
                                break;
                            default:
                                Toast.makeText(TimetablesActivity.this, "Error opening timetable", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );

    }
}
