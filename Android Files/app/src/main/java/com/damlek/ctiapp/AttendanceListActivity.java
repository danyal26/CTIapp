package com.damlek.ctiapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;



public class AttendanceListActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    String base_url, modName, modID, lecturerID, date, firstName;

    Button btnScan;
    TextView txtModuleName;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Attendance List
    AttendanceListAdapter listAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        Date dt = new Date();
        date = new SimpleDateFormat("yyyy-MM-dd").format(dt);

        btnScan = (Button) findViewById(R.id.button_scan);
        txtModuleName = (TextView) findViewById(R.id.text_module_name);

        base_url = getIntent().getExtras().getString("base_url");
        modName = getIntent().getExtras().getString("mod_name");
        lecturerID = getIntent().getExtras().getString("lecturer_id");
        modID = getIntent().getExtras().getString("mod_id");
        firstName = getIntent().getExtras().getString("first_name");

        txtModuleName.setText(modName);

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Attendance List");
        data.putString("lecturer_id", lecturerID);
        data.putString("first_name", firstName);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "LecturerModulesActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        // Module List
        listAdapter = new AttendanceListAdapter(this, R.layout.list_row_attendance);
        listView = (ListView) findViewById(R.id.attendance_list);
        listView.setAdapter(listAdapter);

        new AsyncGetAttendanceList().execute(modID, date);

        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                String studentID = result.getContents();
//                Toast.makeText(this, "Mod: " + modID + " stud_id: " + studentID + " date: " + date,Toast.LENGTH_LONG).show();
                new AsyncScanStudent().execute(modID, studentID, date);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private class AsyncGetAttendanceList extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(base_url + "/attendance_list.php");

            } catch (MalformedURLException e) {

                Toast.makeText(AttendanceListActivity.this, "Exception 1", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return "exception";

            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                try {
                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("module_id", params[0])
                            .appendQueryParameter("date", params[1]);
                    String query = builder.build().getEncodedQuery();

                    // Open connection for sending data
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                }
                catch (Exception e) {
                    Toast.makeText(AttendanceListActivity.this, "Exception 2", Toast.LENGTH_SHORT).show();
                }


                conn.connect();

            } catch (IOException e1) {

                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            // PARSE JSON

//            Toast.makeText(LecturerModulesActivity.this, result, Toast.LENGTH_SHORT).show();

            try {

                try {
                    jsonObject = new JSONObject(result);
                }
                catch (Exception e){
                    Toast.makeText(AttendanceListActivity.this, "Object Exception", Toast.LENGTH_SHORT).show();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("attendance_list");
                }
                catch (Exception e){
                    Toast.makeText(AttendanceListActivity.this, "Array Exception", Toast.LENGTH_SHORT).show();
                }

                int count = 0;

                try{
                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        int id;
                        String studentID, firstName, lastName;

                        id = Integer.parseInt(currentObject.getString("id"));
                        studentID = currentObject.getString("student_id");
                        firstName = currentObject.getString("first_name");
                        lastName = currentObject.getString("last_name");

                        AttendanceList list = new AttendanceList(id, studentID, firstName, lastName);
                        listAdapter.add(list);

                        count++;

                    }
                }
                catch (Exception e){
                    Toast.makeText(AttendanceListActivity.this, "Exception 1", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(AttendanceListActivity.this, "Exception 2", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class AsyncScanStudent extends AsyncTask<String, String, String>
    {
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(base_url + "/scan_student.php");

            } catch (MalformedURLException e) {
                Toast.makeText(AttendanceListActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);


                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("module_id", params[0])
                        .appendQueryParameter("student_id", params[1])
                        .appendQueryParameter("date", params[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                Toast.makeText(AttendanceListActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
                e1.printStackTrace();
                return "exception";
            }


            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {


                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    try{
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(AttendanceListActivity.this,"Exception",Toast.LENGTH_LONG).show();
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                Toast.makeText(AttendanceListActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {
            // PARSE JSON


            try {

                if (result.equals("true")) {
//                    new AsyncGetAttendanceList().execute(modID, date);
                    Intent intent = new Intent("com.damlek.ctiapp.AttendanceListActivity");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("base_url", base_url);
                    intent.putExtra("lecturer_id", lecturerID);
                    intent.putExtra("mod_name", modName);
                    intent.putExtra("mod_id", modID);
                    intent.putExtra("first_name", firstName);
                    startActivity(intent);
                } else if (result.equals("false")) {
                    Toast.makeText(AttendanceListActivity.this,"Database Error. Please contact Administrator.",Toast.LENGTH_LONG).show();
                } else if (result.equals("scanned")) {
                    Toast.makeText(AttendanceListActivity.this,"This student has already been scanned",Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(AttendanceListActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
