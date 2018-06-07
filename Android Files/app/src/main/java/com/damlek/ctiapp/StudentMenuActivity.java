package com.damlek.ctiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class StudentMenuActivity extends AppCompatActivity {

    static ImageButton btn_student_id;
    static ImageButton btn_timetables;
    static ImageButton btn_deadlines;
    static ImageButton btn_events;
    static ImageButton btn_finances;
    static ImageButton btn_marks;
    static ImageButton btn_year_planner;
    static ImageButton btn_support;
    static ImageButton btn_back;

    String studentId, firstName, lastName, programID, base_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        base_url = getIntent().getExtras().getString("base_url");

        studentId = getIntent().getExtras().getString("student_id");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");


        ButtonListener();

    }

    public void ButtonListener() {

        btn_back = (ImageButton) findViewById(R.id.button_back);
        btn_back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.StudentMainActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id",programID);
                        intent.putExtra("base_url", base_url);
                        startActivity(intent);
                    }
                }
        );

        btn_student_id = (ImageButton) findViewById(R.id.button_student_id);
        btn_student_id.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.StudentIDActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_timetables = (ImageButton) findViewById(R.id.button_timetables);
        btn_timetables.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.TimetablesActivity");
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("student_id",studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_deadlines = (ImageButton) findViewById(R.id.button_deadlines);
        btn_deadlines.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.DeadlinesActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_events = (ImageButton) findViewById(R.id.button_events);
        btn_events.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.EventsActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_finances = (ImageButton) findViewById(R.id.button_finances);
        btn_finances.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.FinancesActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_marks = (ImageButton) findViewById(R.id.button_marks);
        btn_marks.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.MarksLevelsActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_year_planner = (ImageButton) findViewById(R.id.button_year_planner);
        btn_year_planner.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.YearPlannerMonthActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );

        btn_support = (ImageButton) findViewById(R.id.button_support);
        btn_support.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.SupportMainActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("program_id",programID);
                        startActivity(intent);
                    }
                }
        );


    }
}
