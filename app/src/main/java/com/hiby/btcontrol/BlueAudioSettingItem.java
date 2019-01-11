package com.hiby.btcontrol;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/1/10.
 */

public class BlueAudioSettingItem extends LinearLayout{
    public TextView title,summary;
    private Context mContext;
    public BlueAudioSettingItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    public BlueAudioSettingItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView(context);
    }

    public BlueAudioSettingItem(Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    private void initView(Context context){
        View inflate = LayoutInflater.from(context).inflate(R.layout.setting_item, this, true);
        title = inflate.findViewById(R.id.setting_tile);
        summary = inflate.findViewById(R.id.setting_summary);
    }

    public void setSummary(String s){
        summary.setText(s);
    }

    public void setSummary(int resId){
        summary.setText(resId);
    }

    public void setTitle(int resId){
        if (title!=null)
            title.setText(resId);
    }

}
