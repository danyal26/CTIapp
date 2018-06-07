package com.damlek.ctiapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import android.support.v4.app.FragmentManager;
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


public class SupportDetailActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, message, deptNo, deptName, firstName, lastName, programID;

    // BASE URL
    String base_url;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    EditText txtMessage;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_detail);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        deptNo = getIntent().getExtras().getString("department_id");
        base_url = getIntent().getExtras().getString("base_url");
        deptName = getIntent().getExtras().getString("department_name");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        txtMessage = (EditText) findViewById(R.id.text_message);
        btnSend = (Button) findViewById(R.id.button_send);

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Contact " + deptName);

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "SupportActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

    }

    public void sendMessage(View arg0) {

        message = txtMessage.getText().toString();

//        Toast.makeText(SupportDetailActivity.this,student_no + " " + deptNo + " " + message,Toast.LENGTH_LONG).show();

        new AsyncLogin().execute(student_no,deptNo,message);

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
                url = new URL(base_url + "/add_query.php");

            } catch (MalformedURLException e) {
                Toast.makeText(SupportDetailActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                        .appendQueryParameter("department_id", params[1])
                        .appendQueryParameter("message", params[2]);
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
                Toast.makeText(SupportDetailActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(SupportDetailActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(SupportDetailActivity.this,"Message Sent!",Toast.LENGTH_LONG).show();
                    txtMessage.setText("");
                } else {
                    Toast.makeText(SupportDetailActivity.this,"Error: Message sending failed! \n Contact the Administrator",Toast.LENGTH_LONG).show();
                }

                Toast.makeText(SupportDetailActivity.this,result,Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(SupportDetailActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }
}
