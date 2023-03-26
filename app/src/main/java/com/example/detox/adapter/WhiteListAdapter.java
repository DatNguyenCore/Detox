package com.example.detox.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.detox.R;
import com.example.detox.models.WhiteListModal;

import java.util.ArrayList;

public class WhiteListAdapter extends BaseAdapter {
    final ArrayList<WhiteListModal> mWhiteList;
    private Context mContext;

    public WhiteListAdapter(Context mContext, ArrayList<WhiteListModal> mWhiteList) {
        this.mWhiteList = mWhiteList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return this.mWhiteList.size();
    }

    @Override
    public Object getItem(int i) {
        return mWhiteList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mWhiteList.get(i).getPosition();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater mInflater = (LayoutInflater)  mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.fragment_white_list_item, null);

            TextView textView = view.findViewById(R.id.text_name_white_list_item);
            textView.setText(mWhiteList.get(i).getName());
        }

        return view;
    }
}
