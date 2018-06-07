package com.damlek.ctiapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danyaldharani on 09/10/2016.
 */

public class AttendanceListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public AttendanceListAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(AttendanceList object) {
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

        AttendanceListHolder attendanceListHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_attendance,parent,false);
            attendanceListHolder = new AttendanceListHolder();

            attendanceListHolder.txtName = (TextView) row.findViewById(R.id.text_main);
            attendanceListHolder.txtStudentID = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(attendanceListHolder);

        }
        else {
            attendanceListHolder = (AttendanceListHolder) row.getTag();
        }

        AttendanceList attendanceList = (AttendanceList) this.getItem(position);

        attendanceListHolder.txtName.setText(attendanceList.getFirstName() + " " + attendanceList.getLastName());
        attendanceListHolder.txtStudentID.setText(attendanceList.getStudentID());

        return row;
    }

    static class AttendanceListHolder {

        TextView txtName, txtStudentID;

    }

}
