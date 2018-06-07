package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.text.DecimalFormat;

public class MarksActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String base_url, student_no, mod, modName, firstName, lastName, programID, program_level;

    Double dp, finalMark, examMarkDouble, projectMarkDouble;

    DecimalFormat markFormat = new DecimalFormat("##");

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;


    TextView txtTitle, txtTest1, txtTest2, txtAssignment, txtProject, txtExam, txtDP, txtFinalMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        // Pull data from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        mod = getIntent().getExtras().getString("mod");
        modName = getIntent().getExtras().getString("mod_name");
        base_url = getIntent().getExtras().getString("base_url");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");
        program_level = getIntent().getExtras().getString("program_level");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", modName);
        data.putString("previous_activity", "MarksModulesActivity");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("program_level", program_level);
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();


        txtTitle = (TextView) findViewById(R.id.text_title);
        txtTest1 = (TextView) findViewById(R.id.text_test1_mark);
        txtTest2 = (TextView) findViewById(R.id.text_test2_mark);
        txtAssignment = (TextView) findViewById(R.id.text_assignment_mark);
        txtProject = (TextView) findViewById(R.id.text_project_mark);
        txtExam = (TextView) findViewById(R.id.text_exam_mark);
        txtDP = (TextView) findViewById(R.id.text_dp);
        txtFinalMark = (TextView) findViewById(R.id.text_final_mark);

        new AsyncLogin().execute(student_no, mod);
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
                url = new URL(base_url + "/marks.php");

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
                        .appendQueryParameter("mod", params[1]);
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

//            Toast.makeText(MarksActivity.this, result, Toast.LENGTH_SHORT).show();

            try {

                // PARSE JSON

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("marks");

                JSONObject currentObject = jsonArray.getJSONObject(0);

                // Get information from JSON
                String test1Mark = currentObject.getString("test1_mark");
                String test2Mark = currentObject.getString("test2_mark");
                String assignmentMark = currentObject.getString("assignment_mark");
                String projectMark = currentObject.getString("project_mark");
                String examMark = currentObject.getString("exam_mark");

                int markCounter = 3;

                if(!test1Mark.equals("null")){
                    txtTest1.setText(test1Mark + "%");
                    markCounter--;
                }

                if(!test2Mark.equals("null")){
                    txtTest2.setText(test2Mark + "%");
                    markCounter--;
                }

                if(!assignmentMark.equals("null")){
                    txtAssignment.setText(assignmentMark + "%");
                    markCounter--;
                }

                if(!projectMark.equals("null")){
                    txtProject.setText(projectMark + "%");
                    projectMarkDouble = Double.parseDouble(projectMark);
                }

                if(!examMark.equals("null")){
                    txtExam.setText(examMark + "%");
                    examMarkDouble = Double.parseDouble(examMark);
                }

                if (markCounter == 0) {
                    double test1perc, test2perc, assignmentPerc;

                    test1perc = Double.parseDouble(test1Mark) * 0.1;
                    test2perc = Double.parseDouble(test2Mark) * 0.2;
                    assignmentPerc = Double.parseDouble(assignmentMark) * 0.2;

                    dp = (test1perc + test2perc + assignmentPerc) * 2;

                    txtDP.setText(markFormat.format(dp) + "%");
                }

                getFinalMark();

            }

            catch (JSONException e) {

                e.printStackTrace();
            }
        }

        public void getFinalMark(){

            if(examMarkDouble != null) {

                if(examMarkDouble != 0){

                    if(dp != null) {

                        if (dp != 0) {
                            finalMark = (dp / 2) + (examMarkDouble / 2);
                            txtFinalMark.setText(markFormat.format(finalMark) + "%");
                        }

                    } else {
                        txtFinalMark.setText(markFormat.format(examMarkDouble) + "%");
                    }

                }


            } else {

                if (projectMarkDouble != null) {

                    if (projectMarkDouble != 0){
                        txtFinalMark.setText(markFormat.format(projectMarkDouble) + "%");
                    }

                }

            }

        }

    }


}
