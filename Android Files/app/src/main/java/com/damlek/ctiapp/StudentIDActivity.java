package com.damlek.ctiapp;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class StudentIDActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    ImageView qr_image;
    TextView txtStudentID, txtStudentName;
    static String studentID, firstName, lastName, programID, base_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_id);

        studentID = getIntent().getExtras().getString("student_id");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");


        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Student ID");

        data.putString("student_id", studentID);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "StudentMenuActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



        txtStudentID = (TextView) findViewById(R.id.text_qr_student_no);
        txtStudentName = (TextView) findViewById(R.id.text_qr_name);
        qr_image = (ImageView) findViewById(R.id.qr_code_image);

        String fullName = firstName + " " + lastName;

        txtStudentID.setText("Student No: " + studentID);
        txtStudentName.setText("Student Name: " + fullName);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(studentID, BarcodeFormat.QR_CODE,600,600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

            qr_image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

    }
}
