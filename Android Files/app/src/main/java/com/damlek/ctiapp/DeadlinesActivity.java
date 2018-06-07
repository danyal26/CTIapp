package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class DeadlinesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    static String student_no, base_url, firstName, lastName, programID;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Assignment List
    AssignmentAdapter assignmentAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deadlines);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        base_url = getIntent().getExtras().getString("base_url");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        // Assignment List
        assignmentAdapter = new AssignmentAdapter(this, R.layout.list_row_deadline);
        listView = (ListView) findViewById(R.id.deadlines_list_view);
        listView.setAdapter(assignmentAdapter);

        new AsyncLogin().execute(student_no);

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Deadlines");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/assignments.php");

            } catch (MalformedURLException e) {
                Toast.makeText(DeadlinesActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                        .appendQueryParameter("student_no", params[0]);
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
                Toast.makeText(DeadlinesActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(DeadlinesActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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

                try {
                    jsonObject = new JSONObject(result);
                }
                catch (Exception e) {
                    Toast.makeText(DeadlinesActivity.this,"Exception object",Toast.LENGTH_LONG).show();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("assignments");
                }
                catch (Exception e) {
                    Toast.makeText(DeadlinesActivity.this,"Exception array",Toast.LENGTH_LONG).show();
                }


                int count = 0;

                try {
                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        String assID, modID, assNo, startDate, endDate, modName;

                        assID = currentObject.getString("assignment_id");
                        modID = currentObject.getString("module_id");
                        assNo = currentObject.getString("assignment_number");
                        startDate = currentObject.getString("start_date");
                        endDate = currentObject.getString("end_date");
                        modName = currentObject.getString("module_name");

                        try {

                            Assignment assignment = new Assignment(assID, modID, assNo, startDate, endDate, modName);
                            assignmentAdapter.add(assignment);

                            count++;

                        }
                        catch (Exception e) {
                            Toast.makeText(DeadlinesActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                        }


                    }

                    fragmentManager = getSupportFragmentManager();

                    android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
                    Bundle data = new Bundle();
                    data.putString("title", "Deadlines");

                    data.putString("student_id", student_no);
                    data.putString("first_name", firstName);
                    data.putString("last_name", lastName);
                    data.putString("program_id", programID);
                    data.putString("base_url", base_url);
                    data.putString("previous_activity", "StudentMenuActivity");
                    argumentFragment.setArguments(data);

                    fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();
                }
                catch (Exception e) {
                    Toast.makeText(DeadlinesActivity.this,"Exception 5",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




            } catch (Exception e) {
                Toast.makeText(DeadlinesActivity.this,"Exception 6",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
