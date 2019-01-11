package com.hiby.btcontrol;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Copyright (C) 2019$ Unicorn, Inc.
 * Description :
 * Created by Administrator$ on 2019/1/10$ 15:27$.
 */

public class A2dpSettingsHelper {

    private final String TAG = "A2dpSettingsHelper";
    private final Object mBluetoothA2dpLock = new Object();
    private BluetoothA2dp mBluetoothA2dp;
    private BluetoothProfile.ServiceListener mBluetoothA2dpServiceListener;
    private Context mContext;
    private static A2dpSettingsHelper mInstance;

    private static final String BLUETOOTH_AVRCP_VERSION_PROPERTY =
            "persist.bluetooth.avrcpversion";
    public static final String AVRCP_VERSION_14 = "avrcp14";
    public static final String AVRCP_VERSION_13 = "avrcp13";
    public static final String AVRCP_VERSION_15 = "avrcp15";
    public static final String AVRCP_VERSION_16 = "avrcp16";


    public static A2dpSettingsHelper getInstance() {
        if (mInstance == null) {
            mInstance = new A2dpSettingsHelper();
        }
        return mInstance;
    }

    Handler mHandler = new Handler();

    public void test(Context context) {
        System.out.println("tag-n debug 1-10 test start");
        initBluetoothA2dp(context);

        System.out.println("tag-n debug 1-10 mBluetoothA2dp != null " + (mBluetoothA2dp != null));
        if (mBluetoothA2dp == null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    System.out.println("tag-n debug 1-10 Delay mBluetoothA2dp != null " + (mBluetoothA2dp != null));
                    if (mBluetoothA2dp != null) {
                        getBluetoothCodecConfig(mBluetoothA2dp).printfString();

                        int value = getCodecType();
                        if (value != BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_APTX) {
                            value = BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_APTX;
                        } else {
                            value = BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_INVALID;
                        }
                        setCodecType(value);

                        value = getSampleRate();
                        if (value != BluetoothCodecConfigReflect.SAMPLE_RATE_48000) {
                            value = BluetoothCodecConfigReflect.SAMPLE_RATE_48000;
                        } else {
                            value = BluetoothCodecConfigReflect.SAMPLE_RATE_88200;
                        }
                        setSampleRate(value);

                        value = getBitsPerSample();
                        if (value != BluetoothCodecConfigReflect.BITS_PER_SAMPLE_24) {
                            value = BluetoothCodecConfigReflect.BITS_PER_SAMPLE_24;
                        } else {
                            value = BluetoothCodecConfigReflect.BITS_PER_SAMPLE_32;
                        }
                        setBitsPerSample(value);

                        value = getChannelMode();
                        if (value != BluetoothCodecConfigReflect.CHANNEL_MODE_STEREO) {
                            value = BluetoothCodecConfigReflect.CHANNEL_MODE_STEREO;
                        } else {
                            value = BluetoothCodecConfigReflect.CHANNEL_MODE_MONO;
                        }
                        setChannelMode(value);

                        long value6 = getCodecSpecific1();
                        if (value != BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY) {
                            value = BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY;
                        } else {
                            value = BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_CONNECT_QUALITY;
                        }
                        setCodecSpecific1(value);

                        getBluetoothCodecConfig(mBluetoothA2dp).printfString();
                    }
                }
            }, 2000);
        }
        if (mBluetoothA2dp != null) {
            getBluetoothCodecConfig(mBluetoothA2dp).printfString();
            long value = getCodecSpecific1();
            if (value != BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY) {
                value = BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY;
            } else {
                value = BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_CONNECT_QUALITY;
            }
            setCodecSpecific1(value);
            getBluetoothCodecConfig(mBluetoothA2dp).printfString();
        }
    }

    public void initBluetoothA2dp(Context context) {
        if (mBluetoothA2dpServiceListener != null) {
            return;
        }
        if (context == null) {
            Log.e("A2dpSettingsHelper", "init BluetoothA2dp error, context is null");
            return;
        }
        mContext = context;
        mBluetoothA2dpServiceListener = new BluetoothProfile.ServiceListener() {

            @Override
            public void onServiceConnected(int profile, BluetoothProfile bluetoothProfile) {
                Log.e("A2dpSettingsHelper", "BluetoothProfile ServiceListener onServiceConnected ");
                mBluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
            }

            @Override
            public void onServiceDisconnected(int i) {
                mBluetoothA2dp = null;
            }
        };

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null) {
            adapter.getProfileProxy(mContext,
                    mBluetoothA2dpServiceListener,
                    BluetoothProfile.A2DP);
        }
    }

    public void closeProfileProxy() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null && mBluetoothA2dp != null) {
            adapter.closeProfileProxy(BluetoothProfile.A2DP, mBluetoothA2dp);
            mBluetoothA2dp = null;
            mBluetoothA2dpServiceListener = null;
            mContext = null;
        } else {
            Log.e("A2dpSettingsHelper", "Close ProfileProxy error, BluetoothAdapter or BluetoothA2dp is null");
        }
    }

    /**********************************************************************
     *    Set Value
     **********************************************************************/

    public void setCodecType(int value) {
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return;
        }
        if (value == BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_ENABLE_CODEC) {
            synchronized (mBluetoothA2dpLock) {
                if (mBluetoothA2dp != null) {
                    enableOptionalCodecs(mBluetoothA2dp);
                }
            }
            return;
        } else if (value == BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_DISABLE_CODEC) {
            synchronized (mBluetoothA2dpLock) {
                if (mBluetoothA2dp != null) {
                    disableOptionalCodecs(mBluetoothA2dp);
                }
            }
            return;
        }
        Object codecConfig = codecConfigReflect.setCodecType(value);
        setBluetoothCodecConfig(mBluetoothA2dp, codecConfig);
    }

    public void setSampleRate(int value) {
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return;
        }
        Object codecConfig = codecConfigReflect.setSampleRate(value);
        setBluetoothCodecConfig(mBluetoothA2dp, codecConfig);
    }

    public void setBitsPerSample(int value) {
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return;
        }
        Object codecConfig = codecConfigReflect.setBitsPerSample(value);
        setBluetoothCodecConfig(mBluetoothA2dp, codecConfig);
    }

    public void setChannelMode(int value) {
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return;
        }
        Object codecConfig = codecConfigReflect.setChannelMode(value);
        setBluetoothCodecConfig(mBluetoothA2dp, codecConfig);
    }

    public void setCodecSpecific1(long value) {
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return;
        }
        Object codecConfig = codecConfigReflect.setCodecSpecific1(value);
        setBluetoothCodecConfig(mBluetoothA2dp, codecConfig);
    }

    public void setAVRCPVersion(String value) {
//        SystemProperties.set(BLUETOOTH_AVRCP_VERSION_PROPERTY, newValue.toString());
        switch (value) {
            case AVRCP_VERSION_14:
                break;
            case AVRCP_VERSION_13:
                break;
            case AVRCP_VERSION_15:
                break;
            case AVRCP_VERSION_16:
                break;
            default:
                value = AVRCP_VERSION_14;
                break;
        }
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, BLUETOOTH_AVRCP_VERSION_PROPERTY, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void enableOptionalCodecs(BluetoothA2dp bluetoothA2dp) {
        if (bluetoothA2dp == null) {
            return;
        }
        try {
            Method method = bluetoothA2dp.getClass().getMethod("enableOptionalCodecs");
            method.invoke(bluetoothA2dp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void disableOptionalCodecs(BluetoothA2dp bluetoothA2dp) {
        if (bluetoothA2dp == null) {
            return;
        }
        try {
            Method method = bluetoothA2dp.getClass().getMethod("disableOptionalCodecs");
            method.invoke(bluetoothA2dp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**********************************************************************
     *    Get Value
     **********************************************************************/

    public int getCodecType() {
        int value = BluetoothCodecConfigReflect.SOURCE_CODEC_TYPE_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getCodecType();
        return value;
    }

    public int getCodecPriority() {
        int value = BluetoothCodecConfigReflect.CODEC_PRIORITY_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getCodecPriority();
        return value;
    }

    public int getSampleRate() {
        int value = BluetoothCodecConfigReflect.SAMPLE_RATE_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getSampleRate();
        return value;
    }

    public int getBitsPerSample() {
        int value = BluetoothCodecConfigReflect.BITS_PER_SAMPLE_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getBitsPerSample();
        return value;
    }

    public int getChannelMode() {
        int value = BluetoothCodecConfigReflect.CHANNEL_MODE_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getChannelMode();
        return value;
    }

    public long getCodecSpecific1() {
        long value = BluetoothCodecConfigReflect.CODEC_SPECIFIC_1_DEFAULT;
        BluetoothCodecConfigReflect codecConfigReflect = getBluetoothCodecConfig(mBluetoothA2dp);
        if (codecConfigReflect == null) {
            return value;
        }
        value = codecConfigReflect.getCodecSpecific1();
        return value;
    }

    public String getAVRCPVersion() {
//        String value = SystemProperties.get(BLUETOOTH_AVRCP_VERSION_PROPERTY, values[0]);
        String value = AVRCP_VERSION_14;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, BLUETOOTH_AVRCP_VERSION_PROPERTY, "unknown"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    private void setBluetoothCodecConfig(BluetoothA2dp bluetoothA2dp, Object codecConfig) {
        if (bluetoothA2dp == null) {
            return;
        }
        try {
            Class<?> class_CodecConfig = Class.forName("android.bluetooth.BluetoothCodecConfig");
            Method method_getCodecStatus = bluetoothA2dp.getClass().getMethod("setCodecConfigPreference", class_CodecConfig);
            method_getCodecStatus.invoke(bluetoothA2dp, codecConfig);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private BluetoothCodecConfigReflect getBluetoothCodecConfig(BluetoothA2dp bluetoothA2dp) {
        Object codecStatus = getBluetoothCodecStatus(bluetoothA2dp);
        if (codecStatus == null) {
            return null;
        }
        Object codecConfig = getBluetoothCodecConfig(codecStatus);
        if (codecConfig == null) {
            return null;
        }
        return new BluetoothCodecConfigReflect(codecConfig);
    }

    private Object getBluetoothCodecConfig(Object bluetoothCodecStatus) {
        if (bluetoothCodecStatus == null) {
            return null;
        }
        Object bluetoothCodecConfig = null;
        try {
            Method method_getCodecConfig = bluetoothCodecStatus.getClass().getMethod("getCodecConfig");
            bluetoothCodecConfig = method_getCodecConfig.invoke(bluetoothCodecStatus);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bluetoothCodecConfig;
    }

    private Object getBluetoothCodecStatus(BluetoothA2dp bluetoothA2dp) {
        if (bluetoothA2dp == null) {
            return null;
        }
        Object bluetoothCodecStatus = null;
        try {
            Method method_getCodecStatus = bluetoothA2dp.getClass().getMethod("getCodecStatus");
            bluetoothCodecStatus = method_getCodecStatus.invoke(bluetoothA2dp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bluetoothCodecStatus;
    }


}
