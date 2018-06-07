package com.damlek.ctiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by danyaldharani on 14/09/2016.
 */
public class TimetableAdapter extends ArrayAdapter<String> {

    public TimetableAdapter(Context context, String[] timetables) {
        super(context, R.layout.list_row_single, timetables);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View view = layoutInflater.inflate(R.layout.list_row_single, parent, false);

        String timetableItem = getItem(position);
        TextView mainText = (TextView) view.findViewById(R.id.text_main);

        mainText.setText(timetableItem);

        return view;

    }
}
