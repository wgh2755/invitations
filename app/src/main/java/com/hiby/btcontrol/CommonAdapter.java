package com.hiby.btcontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/1/10.
 */

public class CommonAdapter<T> extends BaseAdapter {

    private Context mContext;
    private String[] strings;
    private int selPostion;
    public CommonAdapter(Context context,String[] list,int postion) {
        mContext = context;
        strings = list;
        selPostion = postion;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_simple_list, parent, false);
        RadioButton radioButton = view.findViewById(R.id.bt_check);
        TextView tv = view.findViewById(R.id.select_tv);
        radioButton.setChecked(selPostion==position);
        radioButton.setEnabled(selPostion==position);
        tv.setText(strings[position]);
        return view;
    }
}
