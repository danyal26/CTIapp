package com.damlek.ctiapp;

import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FinancesActivity extends AppCompatActivity {

    static FragmentManager fragmentManager;

    String base_url, student_no, firstName, lastName, programID;

    String totalCost;
    Double totalPaid = 0.0;
    ArrayList<String> paymentList;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;
    static JSONObject jsonObject2;
    static JSONArray jsonArray2;

    // Payments List
    PaymentAdapter paymentAdapter;
    ListView listView;

    TextView txtDue, txtPaid, txtBalanceString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finances);

        // Initialize
        paymentList = new ArrayList<>();

        // Get extras from previous activity
        student_no = getIntent().getExtras().getString("student_id");
        base_url = getIntent().getExtras().getString("base_url");

        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");

        fragmentManager = getSupportFragmentManager();

        android.support.v4.app.Fragment argumentFragment = new ActionBarFragment();
        Bundle data = new Bundle();
        data.putString("title", "Payments made");

        data.putString("student_id", student_no);
        data.putString("first_name", firstName);
        data.putString("last_name", lastName);
        data.putString("program_id", programID);
        data.putString("base_url", base_url);
        data.putString("previous_activity", "StudentMenuActivity");
        argumentFragment.setArguments(data);

        fragmentManager.beginTransaction().replace(R.id.action_bar, argumentFragment).commit();



        txtDue = (TextView) findViewById(R.id.text_due);
        txtPaid = (TextView) findViewById(R.id.text_paid);
        txtBalanceString = (TextView) findViewById(R.id.text_paid_string);

        // Payments List
        paymentAdapter = new PaymentAdapter(this, R.layout.list_row_planner);
        listView = (ListView) findViewById(R.id.finances_list_view);
        listView.setAdapter(paymentAdapter);


        new AsyncGetPayments().execute(student_no);
        new AsyncGetTotalCost().execute(student_no);
    }

    private class AsyncGetPayments extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/payments.php");

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

                try {
                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();

                        try {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(FinancesActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
                        }


                        // Pass data to onPostExecute method
                        return(result.toString());

                    }else{

                        return("unsuccessful");
                    }
                }
                catch (Exception e) {
                    Toast.makeText(FinancesActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
                }


            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

            return("unsuccessful 1");

        }

        @Override
        protected void onPostExecute(String result) {

            // PARSE JSON

            try {

                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("payments");



                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String id, amount, date;

                    id = currentObject.getString("payment_id");
                    amount = currentObject.getString("payment_amount");
                    date = currentObject.getString("payment_date");

                    int paymentID = Integer.parseInt(id);

                    totalPaid += Double.parseDouble(amount);

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

                    Payment payment = new Payment(paymentID, amount, day + " " + month);
                    paymentAdapter.add(payment);

                    count++;

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    //

    private class AsyncGetTotalCost extends AsyncTask<String, String, String>
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
                url = new URL(base_url + "/get_cost.php");

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

                try {
                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();

                        try {
                            String line;

                            while ((line = reader.readLine()) != null) {
                                result.append(line);
                            }
                        }
                        catch (Exception e){
                            Toast.makeText(FinancesActivity.this,"Exception 1",Toast.LENGTH_LONG).show();
                        }


                        // Pass data to onPostExecute method
                        return(result.toString());

                    }else{

                        return("unsuccessful");
                    }
                }
                catch (Exception e) {
                    Toast.makeText(FinancesActivity.this,"Exception 2",Toast.LENGTH_LONG).show();
                }


            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

            return("unsuccessful 1");

        }

        @Override
        protected void onPostExecute(String result) {

            try {

                // PARSE JSON

                jsonObject2 = new JSONObject(result);
                jsonArray2 = jsonObject2.getJSONArray("cost");

                JSONObject currentObject = jsonArray2.getJSONObject(0);

                // Get information from JSON
                totalCost = currentObject.getString("cost");
                Double totalModuleCost = Double.parseDouble(totalCost);

                Double balance = totalModuleCost -totalPaid;

                DecimalFormat currencyFormat = new DecimalFormat("#,###,###,###.##");

                String paidText;

                if (totalPaid >= totalModuleCost) {
                    paidText = "Overpaid: ";
                    balance = balance * -1;
                }
                else {
                    paidText = "Balance Due: ";
                }



                String amountPaid = currencyFormat.format(Double.parseDouble(totalPaid + ""));
                String balanceAmount = currencyFormat.format(Double.parseDouble(balance + ""));

                txtDue.setText("R " + amountPaid);
                txtBalanceString.setText(paidText);
                txtPaid.setText("R " + balanceAmount);

            }

            // Show error if database cannot be reached
            catch (JSONException e) {

                Toast.makeText(FinancesActivity.this, "Cannot connect to database 2", Toast.LENGTH_LONG).show();
            }

        }

    }
}
