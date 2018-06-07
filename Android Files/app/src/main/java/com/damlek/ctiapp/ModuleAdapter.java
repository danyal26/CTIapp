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
 * Created by danyaldharani on 10/09/2016.
 */
public class ModuleAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ModuleAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Module object) {
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

        ModuleHolder moduleHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_double,parent,false);
            moduleHolder = new ModuleHolder();

            moduleHolder.txtModName = (TextView) row.findViewById(R.id.text_main);
            moduleHolder.txtModID = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(moduleHolder);

        }
        else {
            moduleHolder = (ModuleHolder) row.getTag();
        }

        Module module = (Module) this.getItem(position);

        moduleHolder.txtModName.setText(module.getModuleName());
        moduleHolder.txtModID.setText(module.getModuleID());

        return row;
    }

    static class ModuleHolder {

        TextView txtModName, txtModID;

    }

}
