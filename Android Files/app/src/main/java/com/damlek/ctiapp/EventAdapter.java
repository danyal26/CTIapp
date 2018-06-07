package com.damlek.ctiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danyaldharani on 12/09/2016.
 */
public class EventAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public EventAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Event object) {
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

    @Override
    public View getView(int position, View row, ViewGroup parent) {

        EventHolder eventHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row,parent,false);
            eventHolder = new EventHolder();

            eventHolder.txtEventName = (TextView) row.findViewById(R.id.text_main);
            eventHolder.txtDate = (TextView) row.findViewById(R.id.text_date);
            eventHolder.txtDepartment = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(eventHolder);

        }
        else {
            eventHolder = (EventHolder) row.getTag();
        }

        Event event = (Event) this.getItem(position);

        String day = event.getDate().substring(8,10);
        String month = event.getDate().substring(5,7);

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

        eventHolder.txtEventName.setText(event.getEventName());
        eventHolder.txtDepartment.setText(event.getDepartmentName());
        eventHolder.txtDate.setText(day + " " + month + "\n" + event.getTime());

        return row;
    }

    static class EventHolder {

        TextView txtEventName, txtDepartment, txtDate;

    }
}
