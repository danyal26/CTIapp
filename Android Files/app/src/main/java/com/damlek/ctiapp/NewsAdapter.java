package com.damlek.ctiapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danyaldharani on 29/08/2016.
 */
public class NewsAdapter extends ArrayAdapter {

    List list = new ArrayList();

    View.OnTouchListener mTouchListener;

    public NewsAdapter(Context context, int resource, View.OnTouchListener listener) {
        super(context, resource);
        mTouchListener = listener;

    }

    public void add(News object) {
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

        NewsHolder newsHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row,parent,false);
            newsHolder = new NewsHolder();

            newsHolder.txt_department_name = (TextView) row.findViewById(R.id.text_main);
            newsHolder.txt_date = (TextView) row.findViewById(R.id.text_date);
            newsHolder.txt_message = (TextView) row.findViewById(R.id.text_subtext);

            row.setTag(newsHolder);

        }
        else {
            newsHolder = (NewsHolder) row.getTag();
        }

        News news = (News) this.getItem(position);

        newsHolder.txt_department_name.setText(news.getDepartmentName());
        newsHolder.txt_message.setText(news.getMessage());
        newsHolder.txt_date.setText(news.getDate() + "\n" + news.getTime());

        row.setOnTouchListener(mTouchListener);

        return row;
    }

    static class NewsHolder {

        TextView txt_department_name, txt_message, txt_date;

    }
}
