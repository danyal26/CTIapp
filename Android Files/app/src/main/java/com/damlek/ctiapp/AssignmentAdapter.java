package com.damlek.ctiapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.app.NotificationManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssignmentAdapter extends ArrayAdapter {

    List list = new ArrayList();
    private int daysInAdvance;
    AlarmManager alarmManager;

    public AssignmentAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Assignment object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Nullable
    @Override
    public View getView(int position, View row, ViewGroup parent) {

        final String deadline, assignmentID, assignmentNo, moduleName;
        final AssignmentHolder assignmentHolder;
        alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_deadline,parent,false);
            assignmentHolder = new AssignmentHolder();

            assignmentHolder.txtDate = (TextView) row.findViewById(R.id.text_date);
            assignmentHolder.txtModID = (TextView) row.findViewById(R.id.text_subtext);
            assignmentHolder.txtModName = (TextView) row.findViewById(R.id.text_main);
            assignmentHolder.btnNotify = (ImageButton) row.findViewById(R.id.button_notify_individual);

            row.setTag(assignmentHolder);

        }
        else {
            assignmentHolder = (AssignmentHolder) row.getTag();
        }


        // GET ASSIGNMENT ITEM AND ITEM PROPERTIES
        Assignment assignment = (Assignment) this.getItem(position);

        deadline = assignment.getEndDate();
        assignmentID = assignment.getAssID();
        assignmentNo = assignment.getAssNo();
        moduleName = assignment.getModName();

        // SET ASSIGNMENT VALUES IN ROW
        assignmentHolder.txtDate.setText(deadlineDateDisplay(deadline));
        assignmentHolder.txtModName.setText(assignment.getModName());
        assignmentHolder.txtModID.setText(assignment.getModID() + " - Assignment " + assignment.getAssNo());

        // CHECK IF ALARM EXISTS AND SET ICON ACCORDINGLY
        if(alarmExists(getAlarmID(deadline, assignmentID))){

            assignmentHolder.btnNotify.setBackgroundResource(R.drawable.notified_button);
            assignmentHolder.btnNotify.setTag(R.drawable.notified_button);

        } else {

            assignmentHolder.btnNotify.setBackgroundResource(R.drawable.notify_button);
            assignmentHolder.btnNotify.setTag(R.drawable.notify_button);

        }

        // ALARM BUTTON CLICK
        assignmentHolder.btnNotify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (assignmentHolder.btnNotify.getTag().equals(R.drawable.notify_button)) {

                    numberPickerDialog(deadline, assignmentID, moduleName, assignmentNo);
                    assignmentHolder.btnNotify.setBackgroundResource(R.drawable.notified_button);
                    assignmentHolder.btnNotify.setTag(R.drawable.notified_button);

                }
                else   if (assignmentHolder.btnNotify.getTag().equals(R.drawable.notified_button)) {

                    cancelAlarm(getAlarmID(deadline, assignmentID));
                    assignmentHolder.btnNotify.setBackgroundResource(R.drawable.notify_button);
                    assignmentHolder.btnNotify.setTag(R.drawable.notify_button);
                }

            }
        });


        return row;
    }

    public void numberPickerDialog(final String deadline, final String assignmentID, final String moduleName, final String assignmentNo) {

        final NumberPicker picker = new NumberPicker(getContext());

        picker.setMinValue(1);
        picker.setMaxValue(7);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        NumberPicker.OnValueChangeListener valueChangeListener = new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                daysInAdvance = newVal;
            }
        };

        picker.setOnValueChangedListener(valueChangeListener);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setView(picker);
        builder.setTitle("How many days in advance you you be like to be reminded?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                daysInAdvance = picker.getValue();
                setAlarm(daysInAdvance, deadline, assignmentID, moduleName, assignmentNo);
            }
        });

        builder.show();

    }

    private void setAlarm(int daysInAdvance, String deadline, String assignmentID, String moduleName, String assignmentNo) {

        Calendar calendar = Calendar.getInstance();
        String stringYear, stringMonth, stringDay;

        stringYear = deadline.substring(0, 4);
        stringMonth = deadline.substring(5, 7);
        stringDay = deadline.substring(8, 10);

        int alarmID = getAlarmID(deadline, assignmentID);

        int intYear = Integer.parseInt(stringYear);
        int intMonth = Integer.parseInt(stringMonth);
        int intDay = Integer.parseInt(stringDay);

        calendar.set(Calendar.YEAR, intYear);
        calendar.set(Calendar.MONTH, intMonth - 1);
        calendar.set(Calendar.DAY_OF_MONTH, intDay);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);

        int daysToSubtract = daysInAdvance * -1;

        calendar.add(Calendar.DATE, daysToSubtract);

        Intent alertIntent;
//        NotificationManager notificationManager;

        alertIntent = new Intent(getContext(), AlertReceiver.class);


//        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        alertIntent.putExtra("title", moduleName + " Deadline.");
        alertIntent.putExtra("alert", "Assignment due");
        alertIntent.putExtra("message_content", "The deadline for Assignment " + assignmentNo + " is on " +  deadline);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(getContext(), alarmID, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    private String deadlineDateDisplay(String fullDate){

        String day = fullDate.substring(8,10);
        String month = fullDate.substring(5,7);

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

        return day + " " + month;

    }

    private int getAlarmID(String deadline, String assignmentID) {

        String stringYear, stringMonth, stringDay, stringAlarmID;

        stringYear = deadline.substring(0, 4);
        stringMonth = deadline.substring(5, 7);
        stringDay = deadline.substring(8, 10);

        stringAlarmID = stringYear + stringMonth + stringDay + assignmentID.substring(4);
        int alarmID = Integer.parseInt(stringAlarmID);

        return alarmID;
    }

    private void cancelAlarm(int alarmID) {

        Intent intent = new Intent(getContext(), AlertReceiver.class);

        alarmManager.cancel(PendingIntent.getBroadcast(getContext().getApplicationContext(), alarmID, intent, PendingIntent.FLAG_NO_CREATE));

        Toast.makeText(getContext(), "Alarm cancelled!", Toast.LENGTH_SHORT).show();

    }

    private boolean alarmExists(int alarmID){

        Intent intent = new Intent(getContext(), AlertReceiver.class);
        return PendingIntent.getBroadcast(getContext().getApplicationContext(), alarmID, intent, PendingIntent.FLAG_NO_CREATE) != null;

    }

    static class AssignmentHolder {

        TextView txtDate, txtModName, txtModID;
        ImageButton btnNotify;

    }
}
