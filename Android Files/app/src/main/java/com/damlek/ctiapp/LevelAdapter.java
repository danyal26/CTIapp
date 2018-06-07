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
public class LevelAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public LevelAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Level object) {
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

        LevelHolder levelHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_single,parent,false);
            levelHolder = new LevelHolder();

            levelHolder.txt_level = (TextView) row.findViewById(R.id.text_main);

            row.setTag(levelHolder);

        }
        else {
            levelHolder = (LevelHolder) row.getTag();
        }

        Level lvl = (Level) this.getItem(position);

        levelHolder.txt_level.setText("Level " + lvl.getLevelItem());

        return row;
    }

    static class LevelHolder {

        TextView txt_level;

    }

}
