package com.damlek.ctiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.support.v4.app.FragmentManager;


public class EventsActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String base_url, student_no, firstName, lastName, programID;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Events List
    EventAdapter eventAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        base_url = getIntent().getExtras().getString("base_url");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Events");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "StudentMenuActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        // Events List
        eventAdapter = new EventAdapter(this, R.layout.list_row);
        listView = (ListView) findViewById(R.id.events_list_view);
        listView.setAdapter(eventAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event clickItem = (Event) parent.getItemAtPosition(position);

                String eventID = clickItem.getEventID();
                String eventName = clickItem.getEventName();
                String venue = clickItem.getVenue();
                String date = clickItem.getDate();
                String time = clickItem.getTime();
                String department = clickItem.getDepartmentName();
                String details = clickItem.getDetails();

                Intent intent = new Intent("com.damlek.ctiapp.EventDetailsActivity");
                intent.putExtra("event_id", eventID);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("department", department);
                intent.putExtra("details", details);
                intent.putExtra("venue", venue);
                intent.putExtra("event_name", eventName);

                intent.putExtra("base_url", base_url);
                intent.putExtra("student_no", student_no);

                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id",programID);
                startActivity(intent);

            }
        });

        new AsyncLogin().execute();

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
                url = new URL(base_url + "/events.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
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
                Uri.Builder builder = new Uri.Builder();
                String query = builder.build().getEncodedQuery();

                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
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

            try {

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("events");

                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String eventID, eventName, message, date, time, department, details, venue;

                    eventID = currentObject.getString("event_id");
                    eventName = currentObject.getString("event_name");
                    date = currentObject.getString("date");
                    time = currentObject.getString("time");
                    department = currentObject.getString("department_name");
                    details = currentObject.getString("details");
                    venue = currentObject.getString("venue");

                    time = time.substring(0, time.length() - 3);

                    Event event = new Event(eventID, eventName, time, date, department, details, venue);
                    eventAdapter.add(event);

                    count++;

                }






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
