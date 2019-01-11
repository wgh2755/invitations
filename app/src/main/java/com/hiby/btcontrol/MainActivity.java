package com.hiby.btcontrol;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private BlueAudioSettingItem avrcp_setting;
    private BlueAudioSettingItem codec_setting;
    private BlueAudioSettingItem sample_setting;
    private BlueAudioSettingItem bit_setting;
    private BlueAudioSettingItem channel_setting;
    private BlueAudioSettingItem ldacQuality_setting;
    private BlueAudioSettingListener audioSettingListener;
    private SimpleListDialog simpleListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        A2dpSettingsHelper.getInstance().initBluetoothA2dp(this);
        avrcp_setting = findViewById(R.id.avrcp_set);
        codec_setting = findViewById(R.id.codec_set);
        sample_setting = findViewById(R.id.sample_set);
        bit_setting = findViewById(R.id.bit_set);
        channel_setting = findViewById(R.id.channel_set);
        ldacQuality_setting = findViewById(R.id.ldac_quality_set);
        audioSettingListener = new BlueAudioSettingListener();
        avrcp_setting.setOnClickListener(audioSettingListener);
        codec_setting.setOnClickListener(audioSettingListener);
        sample_setting.setOnClickListener(audioSettingListener);
        bit_setting.setOnClickListener(audioSettingListener);
        channel_setting.setOnClickListener(audioSettingListener);
        ldacQuality_setting.setOnClickListener(audioSettingListener);
        avrcp_setting.setTitle(R.string.arvcp_setting);
        avrcp_setting.setTag(Util.ARCVP_ID);
        codec_setting.setTitle(R.string.codec_setting);
        codec_setting.setTag(Util.CODEC_ID);
        sample_setting.setTitle(R.string.sample_setting);
        sample_setting.setTag(Util.SAMPLE_ID);
        bit_setting.setTitle(R.string.bits_setting);
        bit_setting.setTag(Util.BITS_ID);
        channel_setting.setTitle(R.string.channel_setting);
        channel_setting.setTag(Util.CHANNEL_ID);
        ldacQuality_setting.setTitle(R.string.ldac_setting);
        ldacQuality_setting.setTag(Util.LDACQUALITY_ID);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateSetting();
            }
        },500);
    }

    private void updateSetting(){
        String avrcp = A2dpSettingsHelper.getInstance().getAVRCPVersion();
        avrcp_setting.setSummary(Util.valueToSummary(this,Util.ARCVP_ID,avrcp));
        int codec = A2dpSettingsHelper.getInstance().getCodecType();
        codec_setting.setSummary(Util.valueToSummary(this,Util.CODEC_ID,String.valueOf(codec)));
        int sample = A2dpSettingsHelper.getInstance().getSampleRate();
        sample_setting.setSummary(Util.valueToSummary(this,Util.SAMPLE_ID,String.valueOf(sample)));
        int bits = A2dpSettingsHelper.getInstance().getBitsPerSample();
        bit_setting.setSummary(Util.valueToSummary(this,Util.BITS_ID,String.valueOf(bits)));
        int channel = A2dpSettingsHelper.getInstance().getChannelMode();
        channel_setting.setSummary(Util.valueToSummary(this,Util.CHANNEL_ID,String.valueOf(channel)));
        long ldac = A2dpSettingsHelper.getInstance().getCodecSpecific1();
        ldacQuality_setting.setSummary(Util.valueToSummary(this,Util.LDACQUALITY_ID,String.valueOf(ldac)));
    }

    class BlueAudioSettingListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int id = (int) v.getTag();
//            showDialog(MainActivity.this,id);
            showAlertDialog(MainActivity.this,id);
        }
    }

    private void showAlertDialog(final Context context, final int id){
        int pos = Util.getValuePostion(context, id);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Util.getTitle(this,id));
        builder.setSingleChoiceItems(Util.getStringList(context, id), pos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Util.setValue(context,id,which);
                handler.sendEmptyMessageDelayed(0,500);
            }
        });
        builder.show();
    }



    private void showDialog(final Context context,int id){
        final int setId = id;
        simpleListDialog = new SimpleListDialog<>(MainActivity.this, id);
        simpleListDialog.setTitle(Util.getTitle(MainActivity.this,id));
        simpleListDialog.setOnItemClickListener(new SimpleListDialog.OnItemClickListener<String>() {
            @Override
            public void onItemClick(View v, String item, int position) {
                Util.setValue(context,setId,position);
                handler.sendEmptyMessageDelayed(0,500);
            }
        });
        simpleListDialog.show();
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            updateSetting();
            return true;
        }
    });
}
