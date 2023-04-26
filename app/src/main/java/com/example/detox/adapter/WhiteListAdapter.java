package com.example.detox.adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.detox.R;
import com.example.detox.database.AppReaderContract;
import com.example.detox.database.AppReaderDbHelper;
import com.example.detox.fragments.WhiteListFragment;
import com.example.detox.models.WhiteListModal;

import java.util.ArrayList;


public class WhiteListAdapter extends BaseAdapter {
    final ArrayList<WhiteListModal> mWhiteList;
    private Context mContext;


    private static final String TAG = "WhiteListAdapter";

    OnWhiteListClickedListener mCallback;

    public interface OnWhiteListClickedListener {
        public void ItemClicked(WhiteListModal data);
    }

    public WhiteListAdapter(WhiteListFragment context, ArrayList<WhiteListModal> mWhiteList) {
        this.mWhiteList = mWhiteList;
        this.mContext = context.getContext();
        this.mCallback = (OnWhiteListClickedListener) context;
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

            // item be pressed
            view.findViewById(R.id.liner_white_list_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mCallback.ItemClicked(mWhiteList.get(i));
////                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mWhiteList.get(i).getAppPackage());
////                    mContext.startActivity(intent);
//
                }
            });

            TextView textView = view.findViewById(R.id.text_name_white_list_item);
            textView.setText(mWhiteList.get(i).getName());

            ImageView image = view.findViewById(R.id.image_white_list_item);
            image.setImageDrawable(mWhiteList.get(i).getIcon());
        }

        return view;
    }

}
