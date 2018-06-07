package com.damlek.ctiapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by DAMLEK GROUP.
 */

public class ActionBarComposeFragment extends Fragment {

    ImageButton btnBack, btnCompose;
    TextView txtTitle;

    String title, base_url, student_id, firstName, lastName, programID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.action_bar_compose_fragment, container, false);

        base_url = getArguments().getString("base_url");
        student_id = getArguments().getString("student_id");
        title = getArguments().getString("title");

        firstName = getArguments().getString("first_name");
        lastName = getArguments().getString("last_name");
        programID = getArguments().getString("program_id");

        btnBack = (ImageButton) view.findViewById(R.id.button_back);
        txtTitle = (TextView) view.findViewById(R.id.text_title);
        btnCompose = (ImageButton) view.findViewById(R.id.button_compose);

        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        getActivity().finish();

                        Intent intent = new Intent("com.damlek.ctiapp.StudentMenuActivity");
                        intent.putExtra("student_id", student_id);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id",programID);
                        intent.putExtra("base_url", base_url);
                        startActivity(intent);
                    }
                }
        );

        btnCompose.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent("com.damlek.ctiapp.SupportActivity");
                        intent.putExtra("student_id", student_id);
                        intent.putExtra("base_url", base_url);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id",programID);
                        intent.putExtra("base_url", base_url);
                        startActivity(intent);
                    }
                }
        );


        txtTitle.setText(title);

        return view;
    }

    public ActionBarComposeFragment() {

    }
}
