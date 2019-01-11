package com.hiby.btcontrol;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by Administrator on 2019/1/10.
 */

public class SimpleListDialog<T> extends Dialog {

    private Context mContext;
    private ListView listView;
    private String[] beans;
    private CommonAdapter<T> adapter;
    private int postion = 0;

    public SimpleListDialog(Context context, int settingId) {
        this(context, R.style.ThemeOverlay_AppCompat_Dialog, settingId);
    }

    public SimpleListDialog(Context context, int theme, int settingId) {
        super(context, theme);
        mContext = context;
        beans = Util.getStringList(context,settingId);
//        postion = Util.getValuePostion(context,settingId);
        init();
    }

    private void init() {
        this.setCanceledOnTouchOutside(true);
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_simple_list);
        initViews();
//        initValues();
    }

    private static final String TAG = "SimpleListDialog";
    private void initViews() {
        if (beans == null) {
            return;
        }
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter = new CommonAdapter<T>(mContext, beans, postion));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long id) {
                Log.e(TAG, "onItemClick: "+position);
                String instance = beans[position];
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, instance, position);
                }
                dismiss();
            }
        });
    }

/*    private void initValues() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }*/

    private OnItemClickListener<String> onItemClickListener;

    public interface OnItemClickListener<String> {
        public void onItemClick(View v, String item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener<String> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
