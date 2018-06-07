package com.damlek.ctiapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

public class ExamTimetableActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String student_no, firstName, lastName, programID, base_url;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // Exam List
    TestExamAdapter testExamAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_timetable);

        try {
            // Pull data from previous activity
            student_no = getIntent().getExtras().getString("student_no");
            base_url = getIntent().getExtras().getString("base_url");

            firstName = getIntent().getExtras().getString("first_name");
            lastName = getIntent().getExtras().getString("last_name");
            programID = getIntent().getExtras().getString("program_id");

            // Exam List
            testExamAdapter = new TestExamAdapter(this, R.layout.list_row_single);
            listView = (ListView) findViewById(R.id.test_list_view);
            listView.setAdapter(testExamAdapter);


        }
        catch (Exception e) {
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
        }

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Exam Timetable");
        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "TimetablesActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



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
                url = new URL(base_url + "/exams.php");

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
                jsonArray = jsonObject.getJSONArray("exams");

                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String modName, modID, date, startTime, endTime, venue;

                    modName = currentObject.getString("module_name");
                    modID = currentObject.getString("module_id");
                    date = currentObject.getString("date");
                    startTime = currentObject.getString("start_time");
                    endTime = currentObject.getString("end_time");
                    venue = currentObject.getString("venue");

                    String day = date.substring(8,10);
                    String month = date.substring(5,7);

                    switch (month) {
                        case "01":
                            month = "Jan";
                            break;
                        case "02":
                            month = "Feb";
                            break;
                        case "03":
                            month = "Mar";
                            break;
                        case "04":
                            month = "Apr";
                            break;
                        case "05":
                            month = "May";
                            break;
                        case "06":
                            month = "Jun";
                            break;
                        case "07":
                            month = "Jul";
                            break;
                        case "08":
                            month = "Aug";
                            break;
                        case "09":
                            month = "Sep";
                            break;
                        case "10":
                            month = "Oct";
                            break;
                        case "11":
                            month = "Nov";
                            break;
                        case "12":
                            month = "Dec";
                            break;
                    }

                    startTime = startTime.substring(0, startTime.length() - 3);
                    endTime = endTime.substring(0, endTime.length() - 3);

                    TestExam testExam = new TestExam(modName, modID, day + " " + month, startTime, endTime, venue);
                    testExamAdapter.add(testExam);

                    count++;

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
