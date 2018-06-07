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
 * Created by danyaldharani on 22/09/2016.
 */

public class DepartmentAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public DepartmentAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Department object) {
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

        DepartmentAdapter.DepartmentHolder departmentHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_single,parent,false);
            departmentHolder = new DepartmentHolder();

            departmentHolder.txt_department = (TextView) row.findViewById(R.id.text_main);

            row.setTag(departmentHolder);

        }
        else {
            departmentHolder = (DepartmentHolder) row.getTag();
        }

        Department department = (Department) this.getItem(position);

        departmentHolder.txt_department.setText(department.getDeptName());

        return row;
    }

    static class DepartmentHolder {

        TextView txt_department;

    }
}
