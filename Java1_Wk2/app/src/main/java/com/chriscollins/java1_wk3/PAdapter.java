package com.chriscollins.java1_wk3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chriscollins.java1_wk2.R;

import java.util.HashMap;

/**
 * Created by chriscollins on 11/12/15.
 */
class PAdapter extends BaseAdapter{
    private static final int ID_Constant = 0x01010101;
    private Context mContxt;
    private int itemIndex;
    private HashMap<Integer, PlayerData> mHash;

    public PAdapter(Context mContext, HashMap<Integer, PlayerData> mMap) {
        mContxt = mContext;
        mHash = mMap;
        this.itemIndex = itemIndex;
    }

    @Override
    public int getCount() {
        if(mHash == null) {
            return 0;
        }
        return mHash.size();
    }

    @Override
    public Object getItem(int position) {
        if(mHash != null && position < mHash.size() && position >= 0) {
            return mHash.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return ID_Constant + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if no recycle view
        if(convertView == null) {
            convertView = LayoutInflater.from(mContxt).inflate(R.layout.activity_main, parent, false);
        }

        PlayerData playerData = (PlayerData)getItem(position);

        if(playerData != null) {
            TextView thisName = (TextView) convertView.findViewById(R.id.textViewName);
            TextView thisNumber = (TextView) convertView.findViewById(R.id.textViewNumber);
            TextView thisTeam = (TextView) convertView.findViewById(R.id.textViewTeam);
            //Spinner spinner = (Spinner)convertView.findViewById(R.id.team_dropdown);
            //spinner.setAdapter(playerData.getPlayer());
            thisName.setText(playerData.getPlayer());
            thisNumber.setText(playerData.getNumber());
            thisTeam.setText(playerData.getTeam());

        }

        return convertView;
    }
}
