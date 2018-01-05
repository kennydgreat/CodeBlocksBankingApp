package com.example.kenny.codeblocksbankingapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kenny on 2018-01-04.
 * This Adapter helps with the display of the transaction data of each account
 */

public class TransactionListViewAdapter extends ArrayAdapter<String> {
    private Context context;
    private int resource;

    public TransactionListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);

        }
        //Date Text View for the transaction date
        TextView date_of_transaction_textView = convertView.findViewById(R.id.date_of_transaction_textView);
        date_of_transaction_textView.setText("Today");
        //Info
        TextView info_of_transaction_textview = convertView.findViewById(R.id.info_of_transaction_textview);
        info_of_transaction_textview.setText(getItem(position));
        //Amount
        TextView amout_of_transaction_textview = convertView.findViewById(R.id.amout_of_transaction_textview);
        amout_of_transaction_textview.setText("500");

        return convertView;
    }
}
