package com.damlek.ctiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danyaldharani on 12/09/2016.
 */
public class PaymentAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public PaymentAdapter(Context context, int resource) {
        super(context, resource);

    }

    public void add(Payment object) {
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

        PaymentHolder paymentHolder;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_row_planner,parent,false);
            paymentHolder = new PaymentHolder();

            paymentHolder.txt_amount = (TextView) row.findViewById(R.id.text_main);
            paymentHolder.txt_date = (TextView) row.findViewById(R.id.text_date);

            row.setTag(paymentHolder);

        }
        else {
            paymentHolder = (PaymentHolder) row.getTag();
        }

        Payment payment = (Payment) this.getItem(position);

        DecimalFormat currencyFormat = new DecimalFormat("#,###,###,###.##");

        String paymentAmount = currencyFormat.format(Double.parseDouble(payment.getAmount()));

        paymentHolder.txt_amount.setText("R " + paymentAmount);
        paymentHolder.txt_date.setText(payment.getDate());

        return row;
    }

    static class PaymentHolder {

        TextView txt_amount, txt_date;

    }

}
