package com.damlek.ctiapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

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
import android.support.v4.app.FragmentManager;


public class EventDetailsActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    TextView txtOrganizer, txtDate, txtTime, txtVenue, txtDetails;
    CheckBox checkBoxAttending;
    String eventID, organizer, date, time, venue, details, programID, firstName, lastName;

    String base_url, student_no, isAttending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        base_url = getIntent().getExtras().getString("base_url");
        student_no = getIntent().getExtras().getString("student_no");
        String eventName = getIntent().getExtras().getString("event_name");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");

        // EVENT DETAILS
        eventID = getIntent().getExtras().getString("event_id");
        organizer = getIntent().getExtras().getString("department");
        date = getIntent().getExtras().getString("date");
        time = getIntent().getExtras().getString("time");
        venue = getIntent().getExtras().getString("venue");
        details = getIntent().getExtras().getString("details");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", eventName);

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "EventsActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        txtOrganizer = (TextView) findViewById(R.id.text_organizer);
        txtDate = (TextView) findViewById(R.id.text_date);
        txtTime = (TextView) findViewById(R.id.text_time);
        txtVenue = (TextView) findViewById(R.id.text_venue);
        txtDetails = (TextView) findViewById(R.id.text_details);
        checkBoxAttending = (CheckBox) findViewById(R.id.checkbox_attending);

        new AsyncIsAttending().execute(student_no, eventID);

        txtOrganizer.setText("Organizer: " + organizer);
        txtDate.setText("Date: " + date);
        txtTime.setText("Time: " + time);
        txtVenue.setText("Venue: " + venue);
        txtDetails.setText(details);


        checkBoxAttending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(checkBoxAttending.isPressed()) {

                    if (isChecked)
                    {
                        isAttending = "true";
                    } else {
                        isAttending = "false";
                    }
                    new AsyncUpdateResponse().execute(student_no, eventID, isAttending);
                }


            }
        });

    }

    private class AsyncIsAttending extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/get_response.php");

            } catch (MalformedURLException e) {
                Toast.makeText(EventDetailsActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                        .appendQueryParameter("student_no", params[0])
                        .appendQueryParameter("event_id", params[1]);
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
                Toast.makeText(EventDetailsActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(EventDetailsActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                    checkBoxAttending.setChecked(true);
                    isAttending = "true";
                } else if (result.equals("false")) {
                    checkBoxAttending.setChecked(false);
                    isAttending = "false";
                }

            } catch (Exception e) {
                Toast.makeText(EventDetailsActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }



    private class AsyncUpdateResponse extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/event_responses.php");

            } catch (MalformedURLException e) {
                Toast.makeText(EventDetailsActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                        .appendQueryParameter("student_no", params[0])
                        .appendQueryParameter("event_id", params[1])
                        .appendQueryParameter("response", params[2]);
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
                Toast.makeText(EventDetailsActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(EventDetailsActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(EventDetailsActivity.this,"Response Updated!",Toast.LENGTH_SHORT).show();
                } else if (result.equals("false")) {
                    Toast.makeText(EventDetailsActivity.this,"Error. Response not update.",Toast.LENGTH_LONG).show();
                }

            } catch (Exception e) {
                Toast.makeText(EventDetailsActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }


}
