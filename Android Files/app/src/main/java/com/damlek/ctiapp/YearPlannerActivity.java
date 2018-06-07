package com.damlek.ctiapp;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Calendar;

public class YearPlannerActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, month, year, monthYear, firstName, lastName, programID, base_url;


    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // News List
    YearPlannerAdapter plannerAdapter;
    ListView listView;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_planner);

        // Pull data from previous activity
        base_url = getIntent().getExtras().getString("base_url");
        month = getIntent().getExtras().getString("month");
        student_no = getIntent().getExtras().getString("student_id");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        String monthFull;

        switch (month) {
            case "01":
                monthFull = "January";
                break;
            case "02":
                monthFull = "February";
                break;
            case "03":
                monthFull = "March";
                break;
            case "04":
                monthFull = "April";
                break;
            case "05":
                monthFull = "May";
                break;
            case "06":
                monthFull = "June";
                break;
            case "07":
                monthFull = "July";
                break;
            case "08":
                monthFull = "August";
                break;
            case "09":
                monthFull = "September";
                break;
            case "10":
                monthFull = "October";
                break;
            case "11":
                monthFull = "November";
                break;
            case "12":
                monthFull = "December";
                break;
            default:
                monthFull = "";
        }

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", monthFull);

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "YearPlannerMonthActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        year = calendar.get(Calendar.YEAR) + "";

        monthYear = year + "-" + month + "%";

        // News List
        plannerAdapter = new YearPlannerAdapter(this, R.layout.list_row_planner);
        listView = (ListView) findViewById(R.id.year_planner_list_view);
        listView.setAdapter(plannerAdapter);


        new AsyncLogin().execute(monthYear);
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
                url = new URL(base_url + "/year_planner.php");

            } catch (MalformedURLException e) {
                Toast.makeText(YearPlannerActivity.this,"Exception",Toast.LENGTH_LONG).show();
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
                            .appendQueryParameter("monthYear", params[0]);
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(YearPlannerActivity.this,"Exception",Toast.LENGTH_LONG).show();
                }



                conn.connect();

            } catch (IOException e1) {
                Toast.makeText(YearPlannerActivity.this,"Exception",Toast.LENGTH_LONG).show();
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
                Toast.makeText(YearPlannerActivity.this,"Exception",Toast.LENGTH_LONG).show();
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
                jsonArray = jsonObject.getJSONArray("year_planner");

                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String id, type, date, details;

                    id = currentObject.getString("year_event_id");
                    type = currentObject.getString("year_event_type");
                    date = currentObject.getString("date");
                    date = date.substring(8,10);
                    details = currentObject.getString("details");

                    YearEvent yearEvent = new YearEvent(id, type, date, details);
                    plannerAdapter.add(yearEvent);

                    count++;

                }


            } catch (JSONException e) {
                Toast.makeText(YearPlannerActivity.this,"Exception",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

}
