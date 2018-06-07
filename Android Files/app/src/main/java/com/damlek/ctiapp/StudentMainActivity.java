package com.damlek.ctiapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StudentMainActivity extends AppCompatActivity {

    ImageButton btn_menu, btn_logout;
    Button btn_show_hidden;
    static String student_no;
    TextView welcome_text;

    String studentId, firstName, lastName, programID, base_url;

    int hiddenAnnouncementCount = 0;

    // JASON PARSING VARIABLES
    static JSONObject jsonObject;
    static JSONArray jsonArray;

    // News List
    NewsAdapter newsAdapter;
    ListView listView;

    //Swiping
    private boolean mSwiping = false; // detects if user is swiping on ACTION_UP
    private boolean mItemPressed = false; // Detects if user is currently holding down a view
    private static final int SWIPE_DURATION = 250; // needed for velocity implementation
    private static final int MOVE_DURATION = 150;
    HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
    Map hiddenEvenMap;
    Iterator valueIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        btn_menu = (ImageButton) findViewById(R.id.button_menu);
        btn_logout = (ImageButton) findViewById(R.id.button_logout);
        btn_show_hidden = (Button) findViewById(R.id.button_show_hidden);

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("hiddenEvents", Context.MODE_PRIVATE);
            hiddenEvenMap = sharedPreferences.getAll();
            hiddenAnnouncementCount = hiddenEvenMap.size();

            if (hiddenAnnouncementCount < 1){
                btn_show_hidden.setVisibility(View.INVISIBLE);
            }
//            Toast.makeText(this, hiddenEvenMap.toString(), Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(this, "no file found", Toast.LENGTH_SHORT).show();
        }

        btn_show_hidden.setText("Show hidden announcements (" + hiddenAnnouncementCount + ")");


        // Pull data from previous activity
        base_url = getIntent().getExtras().getString("base_url");

        studentId = getIntent().getExtras().getString("student_id");
        firstName = getIntent().getExtras().getString("first_name");
        lastName = getIntent().getExtras().getString("last_name");
        programID = getIntent().getExtras().getString("program_id");



        // News List
        newsAdapter = new NewsAdapter(this, R.layout.list_row, mTouchListener);
        listView = (ListView) findViewById(R.id.news_list_view);
        listView.setAdapter(newsAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                News clickItem = (News)parent.getItemAtPosition(position);
//
//                String sender = clickItem.getDepartmentName();
//                String date = clickItem.getDate();
//                String time = clickItem.getTime();
//                String message = clickItem.getMessage();
//
//                Intent intent = new Intent("com.damlek.ctiapp.NewsDetailActivity");
//                intent.putExtra("sender", sender);
//                intent.putExtra("date", date);
//                intent.putExtra("time", time);
//                intent.putExtra("details", message);
//                intent.putExtra("base_url", base_url);
//
//                intent.putExtra("student_id", studentId);
//                intent.putExtra("first_name", firstName);
//                intent.putExtra("last_name", lastName);
//                intent.putExtra("program_id", programID);
//
//                startActivity(intent);
//
//            }
//        });




        welcome_text = (TextView) findViewById(R.id.welcome_text);

        new AsyncLogin().execute(student_no);

        welcome_text.setText("Welcome, " + firstName);

        ButtonListener();


    }

    // Menu Button Listener
    public void ButtonListener() {

        btn_menu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent("com.damlek.ctiapp.StudentMenuActivity");
                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id", programID);

                        intent.putExtra("base_url", base_url);
                        startActivity(intent);
                    }
                }
        );

        btn_logout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(StudentMainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );

        btn_show_hidden.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        SharedPreferences preferences = getSharedPreferences("hiddenEvents", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        startActivity(getIntent());
                    }
                }
        );
    }


    private View.OnTouchListener mTouchListener = new View.OnTouchListener()
    {
        float mDownX;
        private int mSwipeSlop = -1;
        boolean swiped;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (mSwipeSlop < 0)
            {
                mSwipeSlop = ViewConfiguration.get(StudentMainActivity.this).getScaledTouchSlop();
            }
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (mItemPressed)
                    {
                        // Doesn't allow swiping two items at same time
                        return false;
                    }
                    mItemPressed = true;
                    mDownX = event.getX();
                    swiped = false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setTranslationX(0);
                    mItemPressed = false;
                    TextView textView = (TextView) findViewById(R.id.text_hide);
                    textView.setText("");
                    break;
                case MotionEvent.ACTION_MOVE:
                {
                    float x = event.getX() + v.getTranslationX();
                    float deltaX = x - mDownX;
                    float deltaXAbs = Math.abs(deltaX);

                    if (deltaX < -1 * (v.getWidth() / 6))
                    {
                        TextView tv = (TextView) v.findViewById(R.id.text_hide);
                        TextView dt = (TextView) v.findViewById(R.id.text_date);
                        tv.setText("Hide");
                        dt.setVisibility(View.INVISIBLE);


                    }
                    else {
                        TextView tv = (TextView) v.findViewById(R.id.text_hide);
                        TextView dt = (TextView) v.findViewById(R.id.text_date);
                        tv.setText("");
                        dt.setVisibility(View.VISIBLE);

                    }

                    if (!mSwiping)
                    {
                        if (deltaXAbs > mSwipeSlop) // tells if user is actually swiping or just touching in sloppy manner
                        {
                            mSwiping = true;
                            listView.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                    if (mSwiping && !swiped) // Need to make sure the user is both swiping and has not already completed a swipe action (hence mSwiping and swiped)
                    {
                        v.setTranslationX((x - mDownX)); // moves the view as long as the user is swiping and has not already swiped

//                        }
                        if (deltaX < -1 * (v.getWidth() / 6))
                        {
                            TextView tv = (TextView) v.findViewById(R.id.text_hide);
                            tv.setText("Hide");
                        }
                        else {
                            TextView tv = (TextView) v.findViewById(R.id.text_hide);
                            tv.setText("");
                        }

                        if (deltaX < -1 * (v.getWidth() / 3)) // swipe to left
                        {

                            v.setEnabled(false); // need to disable the view for the animation to run

                            // stacked the animations to have the pause before the views flings off screen
                            v.animate().setDuration(300).translationX(-v.getWidth()/3).withEndAction(new Runnable() {
                                @Override
                                public void run()
                                {
                                    v.animate().setDuration(300).alpha(0).translationX(-v.getWidth()).withEndAction(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            mSwiping = false;
                                            mItemPressed = false;
//                                            animateRemoval(listView, v);
                                            int pos = listView.getPositionForView(v);

                                            News swipeItem = (News)listView.getItemAtPosition(pos);
//                                            Toast.makeText(StudentMainActivity.this, swipeItem.getId(), Toast.LENGTH_SHORT).show();

                                            // Add to shared preferences
                                            SharedPreferences sharedPreferences = getSharedPreferences("hiddenEvents", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(swipeItem.getId(), swipeItem.getId());
                                            editor.apply();

                                            // Refresh activity

                                            finish();
                                            startActivity(getIntent());

                                        }
                                    });
                                }
                            });
                            mDownX = x;
                            swiped = true;
                            return true;
                        }
                    }

                }
                break;
                case MotionEvent.ACTION_UP:
                {
                    if (mSwiping) // if the user was swiping, don't go to the and just animate the view back into position
                    {
                        v.animate().setDuration(300).translationX(0).withEndAction(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                TextView tv = (TextView) v.findViewById(R.id.text_hide);
                                tv.setText("");
                                mSwiping = false;
                                mItemPressed = false;
                                listView.setEnabled(true);
                            }
                        });
                    }
                    else // user was not swiping; registers as a click
                    {
                        mItemPressed = false;
                        listView.setEnabled(true);

                        int position = listView.getPositionForView(v);

                        News clickItem = (News) listView.getItemAtPosition(position);

                        String sender = clickItem.getDepartmentName();
                        String date = clickItem.getDate();
                        String time = clickItem.getTime();
                        String message = clickItem.getMessage();

                        Intent intent = new Intent("com.damlek.ctiapp.NewsDetailActivity");
                        intent.putExtra("sender", sender);
                        intent.putExtra("date", date);
                        intent.putExtra("time", time);
                        intent.putExtra("details", message);
                        intent.putExtra("base_url", base_url);

                        intent.putExtra("student_id", studentId);
                        intent.putExtra("first_name", firstName);
                        intent.putExtra("last_name", lastName);
                        intent.putExtra("program_id", programID);

                        startActivity(intent);

                        return false;
                    }
                }
                default:
                    return false;
            }
            return true;
        }
    };

    // animates the removal of the view, also animates the rest of the view into position
    private void animateRemoval(final ListView listView, View viewToRemove)
    {
        int firstVisiblePosition = listView.getFirstVisiblePosition();

        for (int i = 0; i < listView.getChildCount(); ++i)
        {
            View child = listView.getChildAt(i);
            if (child != viewToRemove)
            {
                int position = firstVisiblePosition + i;
                long itemId = listView.getAdapter().getItemId(position);
                mItemIdTopMap.put(itemId, child.getTop());
            }
        }

        int pos = -1;

        try {


            pos = listView.getPositionForView(viewToRemove);

            News swipeItem = (News)listView.getItemAtPosition(pos);
            Toast.makeText(this, swipeItem.getId(), Toast.LENGTH_SHORT).show();

//            newsAdapter.remove(newsAdapter.getItem(pos));
            newsAdapter.list.remove(pos);
//            newsAdapter.notifyDataSetChanged();

            Toast.makeText(this, newsAdapter.getCount() + "", Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }



//        final ViewTreeObserver observer = listView.getViewTreeObserver();
//        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw()
//            {
//                observer.removeOnPreDrawListener(this);
//                boolean firstAnimation = true;
//                int firstVisiblePosition = listView.getFirstVisiblePosition();
//                for (int i = 0; i < listView.getChildCount(); ++i)
//                {
//                    final View child = listView.getChildAt(i);
//                    int position = firstVisiblePosition + i;
//                    long itemId = newsAdapter.getItemId(position);
//                    Integer startTop = mItemIdTopMap.get(itemId);
//                    int top = child.getTop();
//                    if (startTop != null)
//                    {
//                        if (startTop != top)
//                        {
//                            int delta = startTop - top;
//                            child.setTranslationY(delta);
//                            child.animate().setDuration(MOVE_DURATION).translationY(0);
//                            if (firstAnimation) {
//                                child.animate().withEndAction(new Runnable()
//                                {
//                                    public void run()
//                                    {
//                                        mSwiping = false;
//                                        listView.setEnabled(true);
//                                    }
//                                });
//                                firstAnimation = false;
//                            }
//                        }
//                    }
//                    else {
//                        // Animate new views along with the others. The catch is that they did not
//                        // exist in the start state, so we must calculate their starting position
//                        // based on neighboring views.
//                        int childHeight = child.getHeight() + listView.getDividerHeight();
//                        startTop = top + (i > 0 ? childHeight : -childHeight);
//                        int delta = startTop - top;
//                        child.setTranslationY(delta);
//                        child.animate().setDuration(MOVE_DURATION).translationY(0);
//                        if (firstAnimation) {
//                            child.animate().withEndAction(new Runnable()
//                            {
//                                public void run()
//                                {
//                                    mSwiping = false;
//                                    listView.setEnabled(true);
//                                }
//                            });
//                            firstAnimation = false;
//                        }
//                    }
//                }
//                mItemIdTopMap.clear();
//                return true;
//            }
//        });
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
                url = new URL(base_url + "/news.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
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
                // TODO Auto-generated catch block
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
                jsonArray = jsonObject.getJSONArray("news");

                int count = 0;

                while (count < jsonArray.length()){
                    JSONObject currentObject = jsonArray.getJSONObject(count);

                    String id, departmentName, message, date, time;

                    id = currentObject.getString("id");
                    departmentName = currentObject.getString("department_name");
                    message = currentObject.getString("message");
                    date = currentObject.getString("date_posted");
                    time = currentObject.getString("time_posted");

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

                    time = time.substring(0, time.length() - 3);

                    valueIterator = hiddenEvenMap.keySet().iterator();
                    boolean exists = false;

                    while(valueIterator.hasNext()) {
                        String key = (String) valueIterator.next();

                        if(id.equals(key)){
                            exists = true;
                        }

                    }

                    if (exists){
//                        Toast.makeText(StudentMainActivity.this, "exists: " + id, Toast.LENGTH_SHORT).show();
                    } else {
                        News news = new News(id, departmentName, message, day + " " + month, time);
                        newsAdapter.add(news);
                    }

                    count++;


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
