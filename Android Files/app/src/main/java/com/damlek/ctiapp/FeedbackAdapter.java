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
 * Created by danyaldharani on 25/09/2016.
 */

public class FeedbackAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public FeedbackAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Feedback object) {
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

        FeedbackHolder feedbackHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row,parent,false);
            feedbackHolder = new FeedbackHolder();

            feedbackHolder.txt_department_name = (TextView) row.findViewById(R.id.text_main);
            feedbackHolder.txt_date = (TextView) row.findViewById(R.id.text_date);
            feedbackHolder.txt_message = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(feedbackHolder);

        }
        else {
            feedbackHolder = (FeedbackHolder) row.getTag();
        }

        Feedback feedback = (Feedback) this.getItem(position);

        feedbackHolder.txt_department_name.setText(feedback.getDeptName());
        feedbackHolder.txt_message.setText(feedback.getMessage());
        feedbackHolder.txt_date.setText("");

        return row;
    }

    static class FeedbackHolder {

        TextView txt_department_name, txt_message, txt_date;

    }

}
