package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
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

public class SupportMainActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;
    String student_no, base_url, firstName, lastName, programID;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Feedback List
    FeedbackAdapter feedbackAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_main);

        // Pull data from previous activity
        base_url = getIntent().getExtras().getString("base_url");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarComposeFragment();
        Bundle data = new Bundle();
        data.putString("title", "Messages");
        data.putString("base_url", base_url);
        data.putString("student_id", student_no);

        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("previous_activity", "StudentMenuActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



        // Feedback List
        feedbackAdapter = new FeedbackAdapter(this, R.layout.list_row);
        listView = (ListView) findViewById(R.id.feedback_list_view);
        listView.setAdapter(feedbackAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Feedback clickItem = (Feedback) parent.getItemAtPosition(position);

                String department = clickItem.getDeptName();
                String message = clickItem.getMessage();

                Intent intent = new Intent("com.damlek.ctiapp.FeedbackDetailActivity");
                intent.putExtra("department", department);
                intent.putExtra("message", message);
                intent.putExtra("base_url", base_url);

                intent.putExtra("student_id", student_no);
                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id",programID);

                startActivity(intent);

            }
        });

        new AsyncLogin().execute(student_no);

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
                url = new URL(base_url + "/query_feedback.php");

            } catch (MalformedURLException e) {
                Toast.makeText(SupportMainActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SupportMainActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SupportMainActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SupportMainActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("feedback");
                }
                catch (Exception e) {
                    Toast.makeText(SupportMainActivity.this,"Exception 5",Toast.LENGTH_LONG).show();
                }

                int count = 0;

                try {
                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        int id;
                        String deptID, studentID, message, deptName;

                        id = Integer.parseInt(currentObject.getString("feedback_id"));
                        deptID = currentObject.getString("department_id");
                        studentID = currentObject.getString("student_id");
                        message = currentObject.getString("message");
                        deptName = currentObject.getString("department_name");

                        try {
                            Feedback feedback = new Feedback(id, deptID, studentID, message, deptName);
                            feedbackAdapter.add(feedback);

                            count++;
                        }
                        catch (Exception e) {
                            Toast.makeText(SupportMainActivity.this,"Exception 6",Toast.LENGTH_LONG).show();
                        }



                    }
                }
                catch (Exception e) {
                    Toast.makeText(SupportMainActivity.this,"Exception 7",Toast.LENGTH_LONG).show();
                }


            } catch (Exception e) {
                Toast.makeText(SupportMainActivity.this,"Exception 8",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
