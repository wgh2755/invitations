package com.hiby.btcontrol;

import android.content.Context;

/**
 * Created by Administrator on 2019/1/11.
 */

public class Util {
    public static final int ARCVP_ID = 221;
    public static final int CODEC_ID = 222;
    public static final int SAMPLE_ID = 223;
    public static final int BITS_ID = 224;
    public static final int CHANNEL_ID = 225;
    public static final int LDACQUALITY_ID = 226;
    private static String[] arcvp_list;
    private static String[] arcvp_value_list;
    private static String[] codec_list;
    private static String[] codec_value_list;
    private static String[] sample_list;
    private static String[] sample_value_list;
    private static String[] bits_list;
    private static String[] bits_value_list;
    private static String[] channel_list;
    private static String[] channel_value_list;
    private static String[] ldac_list;
    private static String[] ldac_value_list;
    private static String arcvp_str;
    private static String codec_str;
    private static String sample_str;
    private static String bits_str;
    private static String channel_str;
    private static String ldac_str;


    public static String[] getValueList(Context context, int id) {
        switch (id) {
            case ARCVP_ID:
                if (arcvp_value_list == null)
                    arcvp_value_list = context.getResources().getStringArray(R.array.bluetooth_avrcp_version_values);
                return arcvp_value_list;
            case CODEC_ID:
                if (codec_value_list == null)
                    codec_value_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_values);
                return codec_value_list;
            case SAMPLE_ID:
                if (sample_value_list == null)
                    sample_value_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_sample_rate_values);
                return sample_value_list;
            case BITS_ID:
                if (bits_value_list == null)
                    bits_value_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_bits_per_sample_values);
                return bits_value_list;
            case CHANNEL_ID:
                if (channel_value_list == null)
                    channel_value_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_channel_mode_values);
                return channel_value_list;
            case LDACQUALITY_ID:
                if (ldac_value_list == null)
                    ldac_value_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_ldac_playback_quality_values);
                return ldac_value_list;
        }
        return new String[]{""};
    }

    static String[] getStringList(Context context, int id) {
        switch (id) {
            case ARCVP_ID:
                if (arcvp_list == null)
                    arcvp_list = context.getResources().getStringArray(R.array.bluetooth_avrcp_versions);
                return arcvp_list;
            case CODEC_ID:
                if (codec_list == null)
                    codec_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_titles);
                return codec_list;
            case SAMPLE_ID:
                if (sample_list == null)
                    sample_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_sample_rate_titles);
                return sample_list;
            case BITS_ID:
                if (bits_list == null)
                    bits_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_bits_per_sample_titles);
                return bits_list;
            case CHANNEL_ID:
                if (channel_list == null)
                    channel_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_channel_mode_titles);
                return channel_list;
            case LDACQUALITY_ID:
                if (ldac_list == null)
                    ldac_list = context.getResources().getStringArray(R.array.bluetooth_a2dp_codec_ldac_playback_quality_titles);
                return ldac_list;
        }
        return new String[]{""};
    }

    static String getTitle(Context context,int id){
        switch (id) {
            case ARCVP_ID:
                if (arcvp_str==null)
                arcvp_str = context.getResources().getString(R.string.arvcp_setting);
                return arcvp_str;
            case CODEC_ID:
                if (codec_str == null)
                    codec_str = context.getResources().getString(R.string.codec_setting);
                return codec_str;
            case SAMPLE_ID:
                if (sample_str == null)
                    sample_str = context.getResources().getString(R.string.codec_setting);
                return sample_str;
            case BITS_ID:
                if (bits_str == null)
                    bits_str = context.getResources().getString(R.string.codec_setting);
                return bits_str;
            case CHANNEL_ID:
                if (channel_str == null)
                    channel_str = context.getResources().getString(R.string.codec_setting);
                return channel_str;
            case LDACQUALITY_ID:
                if (ldac_str == null)
                    ldac_str = context.getResources().getString(R.string.codec_setting);
                return ldac_str;
        }
        return "";
    }

    static void setValue(Context context,int setId,int postion){
        String s = Util.getValueList(context, setId)[postion];
        switch (setId) {
            case ARCVP_ID:
                A2dpSettingsHelper.getInstance().setAVRCPVersion(s);
                break;
            case CODEC_ID:
                int value = Integer.valueOf(s);
                A2dpSettingsHelper.getInstance().setCodecType(value);
                break;
            case SAMPLE_ID:
                value = Integer.valueOf(s);
                A2dpSettingsHelper.getInstance().setSampleRate(value);
                break;
            case BITS_ID:
                value = Integer.valueOf(s);
                A2dpSettingsHelper.getInstance().setBitsPerSample(value);
                break;
            case CHANNEL_ID:
                value = Integer.valueOf(s);
                A2dpSettingsHelper.getInstance().setChannelMode(value);
                break;
            case LDACQUALITY_ID:
                long val = Long.valueOf(s);
                A2dpSettingsHelper.getInstance().setCodecSpecific1(val);
                break;
            default:
                break;
        }
    }

    static int getValuePostion(Context context,int setId){
        int post = 0;
        switch (setId){
            case ARCVP_ID:
                String s = A2dpSettingsHelper.getInstance().getAVRCPVersion();
                post = valueToPostion(context,setId,s);
                break;
            case CODEC_ID:
                int value = A2dpSettingsHelper.getInstance().getCodecType();
                post = valueToPostion(context,setId,String.valueOf(value));
                break;
            case SAMPLE_ID:
                value = A2dpSettingsHelper.getInstance().getSampleRate();
                post = valueToPostion(context,setId,String.valueOf(value));
                break;
            case BITS_ID:
                value = A2dpSettingsHelper.getInstance().getBitsPerSample();
                post = valueToPostion(context,setId,String.valueOf(value));
                break;
            case CHANNEL_ID:
                value = A2dpSettingsHelper.getInstance().getBitsPerSample();
                post = valueToPostion(context,setId,String.valueOf(value));
                break;
            case LDACQUALITY_ID:
                long val = A2dpSettingsHelper.getInstance().getCodecSpecific1();
                post = valueToPostion(context,setId,String.valueOf(val));
                break;
            default:
                break;
        }
        return post;
    }

     static int valueToPostion(Context context, int settingId, Object value){
        String[] values = getValueList(context,settingId);
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i])){
                return i;
            }
        }
        return settingId==LDACQUALITY_ID?values.length-1:0;
    }

    static String valueToSummary(Context context,int settingId,String value) {
        String[] values = getValueList(context,settingId);
        String[] summarys = getStringList(context,settingId);
        for (int i = 0; i <values.length; i++) {
            if (value.equals(values[i])){
                return summarys[i];
            }
        }
        return settingId==LDACQUALITY_ID?summarys[summarys.length-1]:summarys[0];
    }
}
