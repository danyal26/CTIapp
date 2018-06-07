package com.damlek.ctiapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.rgb;

/**
 * Created by danyaldharani on 23/09/2016.
 */

public class YearPlannerAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public YearPlannerAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(YearEvent object) {
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

        YearPlannerHolder plannerHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_planner,parent,false);
            plannerHolder = new YearPlannerHolder();

            plannerHolder.txtDetails = (TextView) row.findViewById(R.id.text_main);
            plannerHolder.txtDate = (TextView) row.findViewById(R.id.text_date);
            plannerHolder.rowLayout = (RelativeLayout) row.findViewById(R.id.row_layout);

            row.setTag(plannerHolder);

        }
        else {
            plannerHolder = (YearPlannerHolder) row.getTag();
        }

        YearEvent event = (YearEvent) this.getItem(position);

        plannerHolder.txtDetails.setText(event.getDetails());
        plannerHolder.txtDate.setText(event.getDate());

        String type = event.getType().trim().toLowerCase();


        switch (type){
            case "normal":
                plannerHolder.txtDetails.setTextColor(Color.BLACK);
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "yellow":
                plannerHolder.txtDetails.setTextColor(Color.WHITE);
                plannerHolder.rowLayout.setBackgroundColor(rgb(119,119,119));
                plannerHolder.txtDate.setTextColor(Color.WHITE);
                break;
            case "red":
                plannerHolder.txtDetails.setTextColor(Color.RED);
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "green":
                plannerHolder.txtDetails.setTextColor(Color.GREEN);
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "light green":
                plannerHolder.txtDetails.setTextColor(rgb(0,204,102));
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "purple":
                plannerHolder.txtDetails.setTextColor(rgb(128,0,255));
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "pink":
                plannerHolder.txtDetails.setTextColor(rgb(255,0,191));
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "orange":
                plannerHolder.txtDetails.setTextColor(rgb(255,291,0));
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "grey":
                plannerHolder.txtDetails.setTextColor(Color.GRAY);
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            case "pale red":
                plannerHolder.txtDetails.setTextColor(rgb(179,77,77));
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
                break;
            default:
                plannerHolder.txtDetails.setTextColor(Color.BLUE);
                plannerHolder.rowLayout.setBackgroundColor(Color.WHITE);
        }

        return row;
    }

    static class YearPlannerHolder {

        RelativeLayout rowLayout;
        TextView txtDetails, txtDate;

    }

}
