package com.damlek.ctiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import java.util.Calendar;


public class LecturerModulesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String lecturerID, currentDay, base_url, firstName;

    Calendar calendar = Calendar.getInstance();

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Level List
    ModuleAdapter moduleAdapter;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_modules);

        // Pull data from previous activity
        lecturerID = getIntent().getExtras().getString("lecturer_id");
        base_url = getIntent().getExtras().getString("base_url");
        firstName = getIntent().getExtras().getString("first_name");

        int day = calendar.get(Calendar.DAY_OF_WEEK);


        switch (day) {
            case Calendar.SUNDAY:
                currentDay = "Sunday";
                break;
            case Calendar.MONDAY:
                currentDay = "Monday";
                break;
            case Calendar.TUESDAY:
                currentDay = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                currentDay = "Wednesday";
                break;
            case Calendar.THURSDAY:
                currentDay = "Thursday";
                break;
            case Calendar.FRIDAY:
                currentDay = "Friday";
                break;
            case Calendar.SATURDAY:
                currentDay = "Saturday";
        }


        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Select Module");
        data.putString("lecturer_id", lecturerID);
        data.putString("first_name", firstName);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "LecturerMainActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



        // Module List
        moduleAdapter = new ModuleAdapter(this, R.layout.list_row_double);
        listView = (ListView) findViewById(R.id.lecturer_modules_list_view);
        listView.setAdapter(moduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Module clickItem = (Module)parent.getItemAtPosition(position);

                String mod = clickItem.getModuleID();
                String modName = clickItem.getModuleName();

                Intent intent = new Intent("com.damlek.ctiapp.AttendanceListActivity");
                intent.putExtra("base_url", base_url);
                intent.putExtra("lecturer_id", lecturerID);
                intent.putExtra("mod_name", modName);
                intent.putExtra("mod_id", mod);
                intent.putExtra("first_name", firstName);
                startActivity(intent);

            }
        });

        new AsyncGetModules().execute(lecturerID,currentDay);
    }

    private class AsyncGetModules extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/lecturer_modules.php");

            } catch (MalformedURLException e) {

                Toast.makeText(LecturerModulesActivity.this, "Exception 1", Toast.LENGTH_SHORT).show();
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
                            .appendQueryParameter("lecturer_id", params[0])
                            .appendQueryParameter("day", params[1]);
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
                    Toast.makeText(LecturerModulesActivity.this, "Exception 2", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LecturerModulesActivity.this, "Object Exception", Toast.LENGTH_SHORT).show();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("lecturer_modules");
                }
                catch (Exception e){
                    Toast.makeText(LecturerModulesActivity.this, "Array Exception", Toast.LENGTH_SHORT).show();
                }

                int count = 0;

                try{
                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        String modName, modID, modLength, progID, lectID, nqfLvl, cost, programLvl;

                        modName = currentObject.getString("module_name");
                        modID = currentObject.getString("module_id");
                        modLength = currentObject.getString("module_length");
                        progID = currentObject.getString("program_id");
                        lectID = currentObject.getString("lecturer_id");
                        nqfLvl = currentObject.getString("nqf_level");
                        cost = currentObject.getString("cost");
                        programLvl = currentObject.getString("program_level");

                        Module mod = new Module(modID, modName, modLength, progID, lectID, nqfLvl, cost, programLvl);
                        moduleAdapter.add(mod);

                        count++;

                    }
                }
                catch (Exception e){
                    Toast.makeText(LecturerModulesActivity.this, "Exception 1", Toast.LENGTH_SHORT).show();
                }




            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(LecturerModulesActivity.this, "Exception 2", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
