package com.damlek.ctiapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by DAMLEK GROUP.
 */

public class ActionBarFragment extends Fragment {

    ImageButton btnBack;
    TextView txtTitle;

    String title, previousActivity, student_id, firstName, lastName, program_id, base_url, programLevel, lecturer_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.action_bar_fragment, container, false);

        btnBack = (ImageButton) view.findViewById(R.id.button_back);
        txtTitle = (TextView) view.findViewById(R.id.text_title);

        title = getArguments().getString("title");
        previousActivity = getArguments().getString("previous_activity");
        base_url = getArguments().getString("base_url");

        student_id = getArguments().getString("student_id");
        firstName = getArguments().getString("first_name");
        lastName = getArguments().getString("last_name");
        program_id = getArguments().getString("program_id");
        programLevel = getArguments().getString("program_level");
        lecturer_id = getArguments().getString("lecturer_id");

        txtTitle.setText(title);

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        goBack(previousActivity);
                    }
                }
        );

        return view;
    }

    public void goBack(String previousActivity) {

        Activity activity = this.getActivity();
        activity.finish();

        Intent intent;

        switch (previousActivity){
            case "StudentMainActivity":
                intent = new Intent("com.damlek.ctiapp.StudentMainActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "StudentMenuActivity":
                intent = new Intent("com.damlek.ctiapp.StudentMenuActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "TimetablesActivity":
                intent = new Intent("com.damlek.ctiapp.TimetablesActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "MarksLevelsActivity":
                intent = new Intent("com.damlek.ctiapp.MarksLevelsActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                intent.putExtra("program_level", programLevel);
                startActivity(intent);
                break;
            case "MarksModulesActivity":
                intent = new Intent("com.damlek.ctiapp.MarksModulesActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                intent.putExtra("program_level", programLevel);
                startActivity(intent);
                break;
            case "EventsActivity":
                intent = new Intent("com.damlek.ctiapp.EventsActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "SupportActivity":
                intent = new Intent("com.damlek.ctiapp.SupportActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "YearPlannerMonthActivity":
                intent = new Intent("com.damlek.ctiapp.YearPlannerMonthActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "SupportMainActivity":
                intent = new Intent("com.damlek.ctiapp.SupportMainActivity");
                intent.putExtra("student_id", student_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", program_id);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "LecturerMainActivity":
                intent = new Intent("com.damlek.ctiapp.LecturerMainActivity");
                intent.putExtra("lecturer_id", lecturer_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;
            case "LecturerModulesActivity":
                intent = new Intent("com.damlek.ctiapp.LecturerModulesActivity");
                intent.putExtra("lecturer_id", lecturer_id);
                intent.putExtra("first_name", firstName);
                intent.putExtra("base_url", base_url);
                startActivity(intent);
                break;

            default:
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }

    }



}
