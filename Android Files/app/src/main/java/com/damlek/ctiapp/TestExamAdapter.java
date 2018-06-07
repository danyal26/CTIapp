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
 * Created by danyaldharani on 20/09/2016.
 */

public class TestExamAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public TestExamAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(TestExam object) {
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

        TestExamAdapter.TestExamHolder testExamHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_test,parent,false);
            testExamHolder = new TestExamAdapter.TestExamHolder();

            testExamHolder.txtDate = (TextView) row.findViewById(R.id.text_date);
            testExamHolder.txtTime = (TextView) row.findViewById(R.id.text_time);
            testExamHolder.txtVenue = (TextView) row.findViewById(R.id.text_venue);
            testExamHolder.txtModuleName = (TextView) row.findViewById(R.id.text_main);
            testExamHolder.txtModuleID = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(testExamHolder);

        }
        else {
            testExamHolder = (TestExamAdapter.TestExamHolder) row.getTag();
        }

        TestExam testExam = (TestExam) this.getItem(position);

        testExamHolder.txtDate.setText(testExam.getDate());
        testExamHolder.txtTime.setText(testExam.getStart_time() + "\n-\n" + testExam.getEnd_time());
        testExamHolder.txtVenue.setText(testExam.getVenue());
        testExamHolder.txtModuleName.setText(testExam.getModule_name());
        testExamHolder.txtModuleID.setText(testExam.getModule_id());

        return row;
    }

    static class TestExamHolder {

        TextView txtDate, txtTime, txtVenue, txtModuleName, txtModuleID;

    }

}
