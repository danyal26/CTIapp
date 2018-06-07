package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class MarksLevelsActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, firstName, lastName, programID, base_url;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Level List
    LevelAdapter levelAdapter;
    ListView listView;

    boolean hasPaid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_levels);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        base_url = getIntent().getExtras().getString("base_url");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Select Level");

        data.putString("previous_activity", "StudentMenuActivity");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



        // Level List
        levelAdapter = new LevelAdapter(this, R.layout.list_row_single);
        listView = (ListView) findViewById(R.id.levels_list_view);
        listView.setAdapter(levelAdapter);

        new AsyncCheckPayment().execute(student_no);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Level clickItem = (Level)parent.getItemAtPosition(position);

                String programLevel = clickItem.getLevelItem();

                if (hasPaid) {

                    Intent intent = new Intent("com.damlek.ctiapp.MarksModulesActivity");
                    intent.putExtra("student_id", student_no);
                    intent.putExtra("program_level", programLevel);
                    intent.putExtra("base_url", base_url);
                    intent.putExtra("first_name", firstName);
                    intent.putExtra("last_name", lastName);
                    intent.putExtra("program_id", programID);
                    startActivity(intent);
                } else {
                    Toast.makeText(MarksLevelsActivity.this, "Sorry! You cannot view your Marks. Please complete payment first.", Toast.LENGTH_LONG).show();
                }


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
                url = new URL(base_url + "/program_level.php");

            } catch (MalformedURLException e) {

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
                jsonArray = jsonObject.getJSONArray("levels");


                if(jsonArray != null && jsonArray.length() > 0 ){
                    int count = 0;

                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        String progLevel;

                        progLevel = currentObject.getString("program_level");

                        Level lvl = new Level(progLevel);
                        levelAdapter.add(lvl);

                        count++;

                    }
                }
                else {
                    Toast.makeText(MarksLevelsActivity.this, "Your marks have not been posted yet.", Toast.LENGTH_LONG).show();
                }





            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private class AsyncCheckPayment extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/check_payment.php");

            } catch (MalformedURLException e) {

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

            try {

                if (result.equals("true")) {
                    hasPaid = true;
                } else {
                    hasPaid = false;
                }


            }

            // Show error if database cannot be reached
            catch (Exception e) {

                Toast.makeText(MarksLevelsActivity.this, "Cannot connect to database", Toast.LENGTH_LONG).show();
            }

        }

    }


}
