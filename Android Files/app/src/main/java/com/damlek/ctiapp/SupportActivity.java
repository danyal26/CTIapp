package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class SupportActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    static String student_no, firstName, lastName, programID;

    // BASE URL
    String base_url;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Department List
    DepartmentAdapter departmentAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        base_url = getIntent().getExtras().getString("base_url");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Select department to contact");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "SupportMainActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();


        // Department List
        departmentAdapter = new DepartmentAdapter(this, R.layout.list_row_single);
        listView = (ListView) findViewById(R.id.departments_list_view);
        listView.setAdapter(departmentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Department clickItem = (Department) parent.getItemAtPosition(position);

                String department = clickItem.getId();
                String departmentName = clickItem.getDeptName();


                Intent intent = new Intent("com.damlek.ctiapp.SupportDetailActivity");
                intent.putExtra("student_id", student_no);
                intent.putExtra("base_url", base_url);
                intent.putExtra("department_id", department);
                intent.putExtra("department_name", departmentName);

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
                url = new URL(base_url + "/departments.php");

            } catch (MalformedURLException e) {
                Toast.makeText(SupportActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SupportActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SupportActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                jsonArray = jsonObject.getJSONArray("departments");



                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String departmentID, deptName, coordinator, type;

                    departmentID = currentObject.getString("department_id");
                    deptName = currentObject.getString("department_name");
                    coordinator = currentObject.getString("coordinator");
                    type = currentObject.getString("department_type");

                    Department department = new Department(departmentID, deptName, coordinator, type);
                    departmentAdapter.add(department);

                    count++;

                }


            } catch (JSONException e) {
                Toast.makeText(SupportActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }


}
