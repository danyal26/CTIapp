package com.damlek.ctiapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    // BASE URL
    public static final String BASE_URL = "http://10.0.0.9/Android/CTIapp";

    EditText txtStudentID;
    EditText txtPassword;

    static String student_no;
    static String password;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    static String studentId, firstName, lastName, programID, currentLevel, lecturerID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtStudentID = (EditText) findViewById(R.id.text_student_no);
        txtPassword = (EditText) findViewById(R.id.text_password);


    }

    public void checkLogin(View arg0) {

        // Get text from student id and password field
        student_no = txtStudentID.getText().toString().trim();
        password = txtPassword.getText().toString().trim();

        String studLect = student_no.substring(0,3);

        if (studLect.equals("LEC")) {
            new AsyncLecturerLogin().execute(student_no,password);
        }
        else {
            new AsyncStudentLogin().execute(student_no,password);
        }

    }

    private class AsyncStudentLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(BASE_URL + "/student_login.php");

            } catch (MalformedURLException e) {
                Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
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

                Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
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

//            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();

            try {

                // PARSE JSON

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("student_info");

                JSONObject currentObject = jsonArray.getJSONObject(0);

                // Get information from JSON
                studentId = currentObject.getString("student_id");
                firstName = currentObject.getString("first_name");
                lastName = currentObject.getString("last_name");
                programID = currentObject.getString("program_id");
                currentLevel = currentObject.getString("current_program_level");



                // Result is false if username and password do not match
                // If result is false, show error message else show main activity and pass student data
                if(result.equalsIgnoreCase("false")) {

                    Toast.makeText(LoginActivity.this, "ID and Password do not match", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent intent = new Intent(LoginActivity.this,StudentMainActivity.class);

                    intent.putExtra("student_id",studentId);
                    intent.putExtra("first_name",firstName);
                    intent.putExtra("last_name",lastName);
                    intent.putExtra("program_id",programID);
                    intent.putExtra("base_url", BASE_URL);
                    intent.putExtra("current_level", currentLevel);
                    startActivity(intent);
                    LoginActivity.this.finish();

                }

            }

            // Show error if database cannot be reached
            catch (JSONException e) {

                Toast.makeText(LoginActivity.this, "Username and Password do not match!", Toast.LENGTH_LONG).show();
            }

            pdLoading.dismiss();

        }

    }

    private class AsyncLecturerLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL(BASE_URL + "/lecturer_login.php");

            } catch (MalformedURLException e) {
                Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
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
                Toast.makeText(LoginActivity.this, "Exception", Toast.LENGTH_SHORT).show();
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

//            Toast.makeText(LoginActivity.this, result, Toast.LENGTH_SHORT).show();

            try {

                // PARSE JSON

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("lecturer_info");

                JSONObject currentObject = jsonArray.getJSONObject(0);

                // Get information from JSON
                lecturerID = currentObject.getString("lecturer_id");
                firstName = currentObject.getString("first_name");

                if(result.equalsIgnoreCase("false")) {

                    Toast.makeText(LoginActivity.this, "ID and Password do not match", Toast.LENGTH_LONG).show();

                }
                else {

                    Intent intent = new Intent(LoginActivity.this,LecturerMainActivity.class);
                    intent.putExtra("lecturer_id",lecturerID);
                    intent.putExtra("first_name",firstName);
                    intent.putExtra("base_url", BASE_URL);
                    startActivity(intent);
                    LoginActivity.this.finish();

                }

            }

            // Show error if database cannot be reached
            catch (JSONException e) {

                Toast.makeText(LoginActivity.this, "Username and Password do not match!", Toast.LENGTH_LONG).show();
            }

            pdLoading.dismiss();

        }

    }

}