package com.damlek.ctiapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

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
import java.util.ArrayList;
import java.util.Calendar;

public class ClassTimetableActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, firstName, lastName, programID, base_url;

    Calendar calendar = Calendar.getInstance();

    static ArrayList<ClassTimetable> ttList = new ArrayList<>();

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    TextView m1, m2, m3, m4, m5, m6, m7, m8, t1, t2, t3, t4, t5, t6, t7, t8, w1, w2, w3, w4, w5, w6, w7, w8;
    TextView th1, th2, th3, th4, th5, th6, th7, th8, f1, f2, f3, f4, f5, f6, f7, f8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_timetable);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_no");

        base_url = getIntent().getExtras().getString("base_url");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        base_url = getIntent().getExtras().getString("base_url");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Class Timetable");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "TimetablesActivity");

        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();

        assignTextViews();



        new AsyncClassTimetable().execute(student_no);

    }

    public void assignTextViews(){
        m1 = (TextView) findViewById(R.id.m1);
        m2 = (TextView) findViewById(R.id.m2);
        m3 = (TextView) findViewById(R.id.m3);
        m4 = (TextView) findViewById(R.id.m4);
        m5 = (TextView) findViewById(R.id.m5);
        m6 = (TextView) findViewById(R.id.m6);
        m7 = (TextView) findViewById(R.id.m7);
        m8 = (TextView) findViewById(R.id.m8);

        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t5 = (TextView) findViewById(R.id.t5);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);

        w1 = (TextView) findViewById(R.id.w1);
        w2 = (TextView) findViewById(R.id.w2);
        w3 = (TextView) findViewById(R.id.w3);
        w4 = (TextView) findViewById(R.id.w4);
        w5 = (TextView) findViewById(R.id.w5);
        w6 = (TextView) findViewById(R.id.w6);
        w7 = (TextView) findViewById(R.id.w7);
        w8 = (TextView) findViewById(R.id.w8);

        th1 = (TextView) findViewById(R.id.th1);
        th2 = (TextView) findViewById(R.id.th2);
        th3 = (TextView) findViewById(R.id.th3);
        th4 = (TextView) findViewById(R.id.th4);
        th5 = (TextView) findViewById(R.id.th5);
        th6 = (TextView) findViewById(R.id.th6);
        th7 = (TextView) findViewById(R.id.th7);
        th8 = (TextView) findViewById(R.id.th8);

        f1 = (TextView) findViewById(R.id.f1);
        f2 = (TextView) findViewById(R.id.f2);
        f3 = (TextView) findViewById(R.id.f3);
        f4 = (TextView) findViewById(R.id.f4);
        f5 = (TextView) findViewById(R.id.f5);
        f6 = (TextView) findViewById(R.id.f6);
        f7 = (TextView) findViewById(R.id.f7);
        f8 = (TextView) findViewById(R.id.f8);

    }

    // Called on session item click
    public void showSessionInfo(View v){

        TextView txtModule = (TextView) v;
        String sessionID = v.getTag().toString();

        String moduleID = txtModule.getText().toString();

//        Toast.makeText(this, moduleName + " " + sessionID, Toast.LENGTH_SHORT).show();

        new AsyncShowInfo().execute(moduleID, sessionID);

    }

    private class AsyncClassTimetable extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/class_timetable.php");

            } catch (MalformedURLException e) {
                Toast.makeText(ClassTimetableActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                Toast.makeText(ClassTimetableActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(ClassTimetableActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
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
                    Toast.makeText(ClassTimetableActivity.this,"Exception object",Toast.LENGTH_LONG).show();
                }

                try {
                    jsonArray = jsonObject.getJSONArray("timetable");
                }
                catch (Exception e) {
                    Toast.makeText(ClassTimetableActivity.this,"Exception array",Toast.LENGTH_LONG).show();
                }


                int count = 0;

                try {
                    while (count < jsonArray.length()){
                        JSONObject currentObject = jsonArray.getJSONObject(count);

                        String id, modID, roomNo, sessionID, moduleName, fName, lName;

                        id = currentObject.getString("c_timetable_id");
                        modID = currentObject.getString("module_id");
                        roomNo = currentObject.getString("room_no");
                        sessionID = currentObject.getString("session_id");
                        moduleName = currentObject.getString("module_name");
                        fName = currentObject.getString("first_name");
                        lName = currentObject.getString("last_name");

                        try {

                            switch (sessionID){
                                case "1":
                                    m1.setText(modID);
                                    break;
                                case "2":
                                    m2.setText(modID);
                                    break;
                                case "3":
                                    m3.setText(modID);
                                    break;
                                case "4":
                                    m4.setText(modID);
                                    break;
                                case "5":
                                    m5.setText(modID);
                                    break;
                                case "6":
                                    m6.setText(modID);
                                    break;
                                case "7":
                                    m7.setText(modID);
                                    break;
                                case "8":
                                    m8.setText(modID);
                                    break;

                                case "9":
                                    t1.setText(modID);
                                    break;
                                case "10":
                                    t2.setText(modID);
                                    break;
                                case "11":
                                    t3.setText(modID);
                                    break;
                                case "12":
                                    t4.setText(modID);
                                    break;
                                case "13":
                                    t5.setText(modID);
                                    break;
                                case "14":
                                    t6.setText(modID);
                                    break;
                                case "15":
                                    t7.setText(modID);
                                    break;
                                case "16":
                                    t8.setText(modID);
                                    break;

                                case "17":
                                    w1.setText(modID);
                                    break;
                                case "18":
                                    w2.setText(modID);
                                    break;
                                case "19":
                                    w3.setText(modID);
                                    break;
                                case "20":
                                    w4.setText(modID);
                                    break;
                                case "21":
                                    w5.setText(modID);
                                    break;
                                case "22":
                                    w6.setText(modID);
                                    break;
                                case "23":
                                    w7.setText(modID);
                                    break;
                                case "24":
                                    w8.setText(modID);
                                    break;

                                case "25":
                                    th1.setText(modID);
                                    break;
                                case "26":
                                    th2.setText(modID);
                                    break;
                                case "27":
                                    th3.setText(modID);
                                    break;
                                case "28":
                                    th4.setText(modID);
                                    break;
                                case "29":
                                    th5.setText(modID);
                                    break;
                                case "30":
                                    th6.setText(modID);
                                    break;
                                case "31":
                                    th7.setText(modID);
                                    break;
                                case "32":
                                    th8.setText(modID);
                                    break;

                                case "33":
                                    f1.setText(modID);
                                    break;
                                case "34":
                                    f2.setText(modID);
                                    break;
                                case "35":
                                    f3.setText(modID);
                                    break;
                                case "36":
                                    f4.setText(modID);
                                    break;
                                case "37":
                                    f5.setText(modID);
                                    break;
                                case "38":
                                    f6.setText(modID);
                                    break;
                                case "39":
                                    f7.setText(modID);
                                    break;
                                case "40":
                                    f8.setText(modID);
                                    break;
                            }

                            ClassTimetable classTimetable = new ClassTimetable(id, modID, roomNo, sessionID, moduleName, fName, lName);

                            ttList.add(classTimetable);

                            count++;
                        }
                        catch (Exception e) {
                            Toast.makeText(ClassTimetableActivity.this,"Exception 4",Toast.LENGTH_LONG).show();
                        }



                    }
                }
                catch (Exception e) {
                    Toast.makeText(ClassTimetableActivity.this,"Exception 5",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }




            } catch (Exception e) {
                Toast.makeText(ClassTimetableActivity.this,"Exception 6",Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

    }

    private class AsyncShowInfo extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/class_session_info.php");

            } catch (MalformedURLException e) {
                Toast.makeText(ClassTimetableActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
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
                        .appendQueryParameter("module_id", params[0])
                        .appendQueryParameter("session_id", params[1]);
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
                Toast.makeText(ClassTimetableActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
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
                Toast.makeText(ClassTimetableActivity.this,"Exception 3",Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            // PARSE JSON

            String modName, firstName, lastName, venue;

            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("session_info");
                JSONObject currentObject = jsonArray.getJSONObject(0);

                modName = currentObject.getString("module_name");
                firstName = currentObject.getString("first_name");
                lastName = currentObject.getString("last_name");
                venue = currentObject.getString("room_no");

                String fullInfo = "Module Name: " + modName + "\nLecturer: " + firstName + " " + lastName + "\nVenue: " + venue;

                Toast.makeText(ClassTimetableActivity.this, fullInfo, Toast.LENGTH_SHORT).show();

            }
            catch (Exception e) {
//                Toast.makeText(ClassTimetableActivity.this,"Exception 5",Toast.LENGTH_LONG).show();
//                e.printStackTrace();
            }
        }

    }
}
