package com.damlek.ctiapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
import android.support.v4.app.FragmentManager;


public class MarksModulesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, programLevel, programID, firstName, lastName;

    String base_url;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Level List
    ModuleAdapter moduleAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_modules);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        programLevel = getIntent().getExtras().getString("program_level");

        base_url = getIntent().getExtras().getString("base_url");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Level " + programLevel);
        data.putString("previous_activity", "MarksLevelsActivity");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("program_level", programLevel);
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        base_url = getIntent().getExtras().getString("base_url");

        // Module List
        moduleAdapter = new ModuleAdapter(this, R.layout.list_row_double);
        listView = (ListView) findViewById(R.id.modules_list_view);
        listView.setAdapter(moduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Module clickItem = (Module)parent.getItemAtPosition(position);

                String mod = clickItem.getModuleID();
                String modName = clickItem.getModuleName();;

                Intent intent = new Intent("com.damlek.ctiapp.MarksActivity");
                intent.putExtra("student_id", student_no);
                intent.putExtra("mod", mod);
                intent.putExtra("mod_name", modName);
                intent.putExtra("base_url", base_url);

                intent.putExtra("first_name", firstName);
                intent.putExtra("last_name", lastName);
                intent.putExtra("program_id", programID);
                intent.putExtra("program_level", programLevel);
                startActivity(intent);

            }
        });

        new AsyncLogin().execute(student_no, programLevel);

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
                url = new URL(base_url + "/module_marks.php");

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
                        .appendQueryParameter("student_no", params[0])
                        .appendQueryParameter("program_level", params[1]);
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
                jsonArray = jsonObject.getJSONArray("modules");

                int count = 0;

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


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
