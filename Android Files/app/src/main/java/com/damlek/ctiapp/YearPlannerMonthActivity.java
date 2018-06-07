package com.damlek.ctiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.support.v4.app.FragmentManager;


public class YearPlannerMonthActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    static String base_url;
    String firstName, lastName, programID, student_no;

    Button btnJan, btnFeb, btnMar, btnApr, btnMay, btnJun, btnJul, btnAug, btnSep, btnOct, btnNov, btnDec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_planner_month);

        base_url = getIntent().getExtras().getString("base_url");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Select Month");
        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "StudentMenuActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        btnJan = (Button) findViewById(R.id.button_jan);
        btnFeb = (Button) findViewById(R.id.button_feb);
        btnMar = (Button) findViewById(R.id.button_mar);
        btnApr = (Button) findViewById(R.id.button_apr);
        btnMay = (Button) findViewById(R.id.button_may);
        btnJun = (Button) findViewById(R.id.button_jun);
        btnJul = (Button) findViewById(R.id.button_jul);
        btnAug = (Button) findViewById(R.id.button_aug);
        btnSep = (Button) findViewById(R.id.button_sep);
        btnOct = (Button) findViewById(R.id.button_oct);
        btnNov = (Button) findViewById(R.id.button_nov);
        btnDec = (Button) findViewById(R.id.button_dec);


    }

    public void monthEvents( View v ) {

        Intent intent = new Intent("com.damlek.ctiapp.YearPlannerActivity");
        String month;

        switch (v.getId()) {
            case (R.id.button_jan):
                month = "01";
                break;
            case (R.id.button_feb):
                month = "02";
                break;
            case (R.id.button_mar):
                month = "03";
                break;
            case (R.id.button_apr):
                month = "04";
                break;
            case (R.id.button_may):
                month = "05";
                break;
            case (R.id.button_jun):
                month = "06";
                break;
            case (R.id.button_jul):
                month = "07";
                break;
            case (R.id.button_aug):
                month = "08";
                break;
            case (R.id.button_sep):
                month = "09";
                break;
            case (R.id.button_oct):
                month = "10";
                break;
            case (R.id.button_nov):
                month = "11";
                break;
            case (R.id.button_dec):
                month = "12";
                break;
            default:
                month = "null";
        }

        intent.putExtra("month", month);
        intent.putExtra("base_url", base_url);

        intent.putExtra("student_id", student_no);
        intent.putExtra("first_name", firstName);
        intent.putExtra("last_name", lastName);
        intent.putExtra("program_id",programID);
        startActivity(intent);
    }
}
