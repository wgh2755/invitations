package com.hiby.btcontrol;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Copyright (C) 2019$ Unicorn, Inc.
 * Description :
 * Created by Administrator$ on 2019/1/10$ 15:57$.
 */

public class BluetoothCodecConfigReflect {
    public static final int SOURCE_CODEC_TYPE_DEFAULT = -1;
    public static final int SOURCE_CODEC_TYPE_SBC = 0;
    public static final int SOURCE_CODEC_TYPE_AAC = 1;
    public static final int SOURCE_CODEC_TYPE_APTX = 2;
    public static final int SOURCE_CODEC_TYPE_APTX_HD = 3;
    public static final int SOURCE_CODEC_TYPE_LDAC = 4;
    public static final int SOURCE_CODEC_TYPE_ENABLE_CODEC = 5;
    public static final int SOURCE_CODEC_TYPE_DISABLE_CODEC = 6;

    public static final int SOURCE_CODEC_TYPE_INVALID = 1000 * 1000;

    public static final int CODEC_PRIORITY_DISABLED = -1;
    public static final int CODEC_PRIORITY_DEFAULT = 0;
    public static final int CODEC_PRIORITY_HIGHEST = 1000 * 1000;

    public static final int SAMPLE_RATE_DEFAULT = 0;
    public static final int SAMPLE_RATE_NONE = 0;
    public static final int SAMPLE_RATE_44100 = 0x1 << 0;
    public static final int SAMPLE_RATE_48000 = 0x1 << 1;
    public static final int SAMPLE_RATE_88200 = 0x1 << 2;
    public static final int SAMPLE_RATE_96000 = 0x1 << 3;
    public static final int SAMPLE_RATE_176400 = 0x1 << 4; // not support
    public static final int SAMPLE_RATE_192000 = 0x1 << 5; // not support

    public static final int BITS_PER_SAMPLE_DEFAULT = 0;
    public static final int BITS_PER_SAMPLE_NONE = 0;
    public static final int BITS_PER_SAMPLE_16 = 0x1 << 0;
    public static final int BITS_PER_SAMPLE_24 = 0x1 << 1;
    public static final int BITS_PER_SAMPLE_32 = 0x1 << 2;

    public static final int CHANNEL_MODE_DEFAULT = 0;
    public static final int CHANNEL_MODE_NONE = 0;
    public static final int CHANNEL_MODE_MONO = 0x1 << 0;
    public static final int CHANNEL_MODE_STEREO = 0x1 << 1;

    public static final int CODEC_SPECIFIC_1_DEFAULT = 0;
    public static final int CODEC_SPECIFIC_1_NONE = 0;
    public static final int CODEC_SPECIFIC_1_AUDIO_QUALITY = 1000;
    public static final int CODEC_SPECIFIC_1_AUDIO_AND_CONNECT_QUALITY = 1001;
    public static final int CODEC_SPECIFIC_1_CONNECT_QUALITY = 1002;
    public static final int CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY = 1003;

    private Object mBluetoothCodecConfig;
    private int mCodecTypeValue = SOURCE_CODEC_TYPE_INVALID;
    private int mCodecPriorityValue = CODEC_PRIORITY_DEFAULT;
    private int mSampleRateValue = SAMPLE_RATE_NONE;
    private int mBitsPerSampleValue = BITS_PER_SAMPLE_NONE;
    private int mChannelModeValue = CHANNEL_MODE_NONE;
    private long mCodecSpecific1Value = 0;
//    long codecSpecific2Value = 0;
//    long codecSpecific3Value = 0;
//    long codecSpecific4Value = 0;

    public BluetoothCodecConfigReflect(Object bluetoothCodecConfig) {
        mBluetoothCodecConfig = bluetoothCodecConfig;
        mCodecTypeValue = getCodecType();
        mCodecPriorityValue = getCodecPriority();
        mSampleRateValue = getSampleRate();
        mBitsPerSampleValue = getBitsPerSample();
        mChannelModeValue = getChannelMode();
        mCodecSpecific1Value = getCodecSpecific1();
    }

    public BluetoothCodecConfigReflect(int codecTypeValue, int codecPriorityValue, int sampleRateValue,
                                       int bitsPerSampleValue, int channelModeValue, long codecSpecific1Value) {
        mBluetoothCodecConfig = createCodecConfig(codecTypeValue, codecPriorityValue, sampleRateValue,
                bitsPerSampleValue, channelModeValue, codecSpecific1Value);
    }

    private Object createCodecConfig(int codecTypeValue, int codecPriorityValue, int sampleRateValue,
                                     int bitsPerSampleValue, int channelModeValue, long codecSpecific1Value) {
        Object codecConfig = null;
        try {
            Class<?> class_CodecConfig = Class.forName("android.bluetooth.BluetoothCodecConfig");
            Constructor<?>[] constructors = class_CodecConfig.getConstructors();
            System.out.println("tag-n debug 1-9 constructors size: " + constructors.length);
            codecConfig = constructors[0].newInstance(codecTypeValue, codecPriorityValue, sampleRateValue,
                    bitsPerSampleValue, channelModeValue, codecSpecific1Value, 0, 0, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return codecConfig;
    }

    private Object createCodecConfig() {
        return createCodecConfig(mCodecTypeValue, mCodecPriorityValue, mSampleRateValue,
                mBitsPerSampleValue, mChannelModeValue, mCodecSpecific1Value);
    }

    /**********************************************************************
     *    Set Value
     **********************************************************************/

    public Object setCodecType(int value) {
        int oldValue = mCodecTypeValue;
        int newValue = value;
        int codecTypeValue = SOURCE_CODEC_TYPE_INVALID;
        int codecPriorityValue = CODEC_PRIORITY_DEFAULT;
        switch (newValue) {
            case SOURCE_CODEC_TYPE_DEFAULT:
                switch (oldValue) {
                    case SOURCE_CODEC_TYPE_DEFAULT:
                        codecTypeValue = SOURCE_CODEC_TYPE_INVALID;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;      // No current codec
                    case SOURCE_CODEC_TYPE_SBC:
                        codecTypeValue = SOURCE_CODEC_TYPE_SBC;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;
                    case SOURCE_CODEC_TYPE_AAC:
                        codecTypeValue = SOURCE_CODEC_TYPE_AAC;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;
                    case SOURCE_CODEC_TYPE_APTX:
                        codecTypeValue = SOURCE_CODEC_TYPE_APTX;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;
                    case SOURCE_CODEC_TYPE_APTX_HD:
                        codecTypeValue = SOURCE_CODEC_TYPE_APTX_HD;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;
                    case SOURCE_CODEC_TYPE_LDAC:
                        codecTypeValue = SOURCE_CODEC_TYPE_LDAC;
                        codecPriorityValue = CODEC_PRIORITY_DEFAULT;
                        break;
                    default:
                        break;
                }
                break;
            case SOURCE_CODEC_TYPE_SBC:
                codecTypeValue = SOURCE_CODEC_TYPE_SBC;
                codecPriorityValue = CODEC_PRIORITY_HIGHEST;
                break;
            case SOURCE_CODEC_TYPE_AAC:
                codecTypeValue = SOURCE_CODEC_TYPE_AAC;
                codecPriorityValue = CODEC_PRIORITY_HIGHEST;
                break;
            case SOURCE_CODEC_TYPE_APTX:
                codecTypeValue = SOURCE_CODEC_TYPE_APTX;
                codecPriorityValue = CODEC_PRIORITY_HIGHEST;
                break;
            case SOURCE_CODEC_TYPE_APTX_HD:
                codecTypeValue = SOURCE_CODEC_TYPE_APTX_HD;
                codecPriorityValue = CODEC_PRIORITY_HIGHEST;
                break;
            case SOURCE_CODEC_TYPE_LDAC:
                codecTypeValue = SOURCE_CODEC_TYPE_LDAC;
                codecPriorityValue = CODEC_PRIORITY_HIGHEST;
                break;
            case SOURCE_CODEC_TYPE_ENABLE_CODEC:
//                synchronized (mBluetoothA2dpLock) {
//                    if (mBluetoothA2dp != null) {
//                        mBluetoothA2dp.enableOptionalCodecs();
//                    }
//                }
                return SOURCE_CODEC_TYPE_ENABLE_CODEC;
            case SOURCE_CODEC_TYPE_DISABLE_CODEC:
//                synchronized (mBluetoothA2dpLock) {
//                    if (mBluetoothA2dp != null) {
//                        mBluetoothA2dp.disableOptionalCodecs();
//                    }
//                }
                return SOURCE_CODEC_TYPE_DISABLE_CODEC;
            default:
                break;
        }
        mCodecTypeValue = codecTypeValue;
        mCodecPriorityValue = codecPriorityValue;
        return createCodecConfig();
    }

    public Object setSampleRate(int value) {
        switch (value) {
            case SAMPLE_RATE_44100:
                break;
            case SAMPLE_RATE_48000:
                break;
            case SAMPLE_RATE_88200:
                break;
            case SAMPLE_RATE_96000:
                break;
            case SAMPLE_RATE_DEFAULT:
            default:
                // Reset to default
                value = SAMPLE_RATE_NONE;
                break;
        }
        mSampleRateValue = value;
        return createCodecConfig();
    }

    public Object setBitsPerSample(int value) {
        switch (value) {
            case BITS_PER_SAMPLE_16:
                break;
            case BITS_PER_SAMPLE_24:
                break;
            case BITS_PER_SAMPLE_32:
                break;
            case BITS_PER_SAMPLE_DEFAULT:
            default:
                // Reset to default
                value = BITS_PER_SAMPLE_NONE;
                break;
        }
        mBitsPerSampleValue = value;
        return createCodecConfig();
    }

    public Object setChannelMode(int value) {
        switch (value) {
            case CHANNEL_MODE_MONO:
                break;
            case CHANNEL_MODE_STEREO:
                break;
            case CHANNEL_MODE_DEFAULT:
            default:
                // Reset to default
                value = CHANNEL_MODE_NONE;
                break;
        }
        mChannelModeValue = value;
        return createCodecConfig();
    }

    public Object setCodecSpecific1(long value) {
        switch ((int) value) {
            case CODEC_SPECIFIC_1_AUDIO_QUALITY:
                break;
            case CODEC_SPECIFIC_1_AUDIO_AND_CONNECT_QUALITY:
                break;
            case CODEC_SPECIFIC_1_CONNECT_QUALITY:
                break;
            case CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY:
                break;
            case CODEC_SPECIFIC_1_DEFAULT:
            default:
                value = CODEC_SPECIFIC_1_NONE;
                break;
        }
        mCodecSpecific1Value = value;
        return createCodecConfig();
    }

    /**********************************************************************
     *    Get Value
     **********************************************************************/

    public int getCodecType() {
        if (mBluetoothCodecConfig == null) {
            return SOURCE_CODEC_TYPE_SBC;
        }
        int value = getBluetoothCodecConfigIntValue(mBluetoothCodecConfig, "getCodecType");
        switch (value) {
            case SOURCE_CODEC_TYPE_SBC:
            case SOURCE_CODEC_TYPE_AAC:
            case SOURCE_CODEC_TYPE_APTX:
            case SOURCE_CODEC_TYPE_APTX_HD:
            case SOURCE_CODEC_TYPE_LDAC:
                break;
            case SOURCE_CODEC_TYPE_INVALID:
                value = SOURCE_CODEC_TYPE_DEFAULT;
                break;
            default:
                value = SOURCE_CODEC_TYPE_DEFAULT;
                break;
        }
        return value;
    }

    public int getCodecPriority() {
        if (mBluetoothCodecConfig == null) {
            return CODEC_PRIORITY_DEFAULT;
        }
        return getBluetoothCodecConfigIntValue(mBluetoothCodecConfig, "getCodecPriority");
    }

    public int getSampleRate() {
        if (mBluetoothCodecConfig == null) {
            return SAMPLE_RATE_NONE;
        }
        int value = getBluetoothCodecConfigIntValue(mBluetoothCodecConfig, "getSampleRate");
        switch (value) {
            case SAMPLE_RATE_44100:
                break;
            case SAMPLE_RATE_48000:
                break;
            case SAMPLE_RATE_88200:
                break;
            case SAMPLE_RATE_96000:
                break;
            case SAMPLE_RATE_176400:
            case SAMPLE_RATE_192000:
            case SAMPLE_RATE_NONE:
            default:
                value = SAMPLE_RATE_DEFAULT;
                break;
        }
        return value;
    }

    public int getBitsPerSample() {
        if (mBluetoothCodecConfig == null) {
            return BITS_PER_SAMPLE_NONE;
        }
        int value = getBluetoothCodecConfigIntValue(mBluetoothCodecConfig, "getBitsPerSample");
        switch (value) {
            case BITS_PER_SAMPLE_16:
                break;
            case BITS_PER_SAMPLE_24:
                break;
            case BITS_PER_SAMPLE_32:
                break;
            case BITS_PER_SAMPLE_NONE:
            default:
                value = BITS_PER_SAMPLE_DEFAULT;
                break;
        }
        return value;
    }

    public int getChannelMode() {
        if (mBluetoothCodecConfig == null) {
            return CHANNEL_MODE_NONE;
        }
        int value = getBluetoothCodecConfigIntValue(mBluetoothCodecConfig, "getChannelMode");
        switch (value) {
            case CHANNEL_MODE_MONO:
                break;
            case CHANNEL_MODE_STEREO:
                break;
            case CHANNEL_MODE_NONE:
            default:
                value = CHANNEL_MODE_DEFAULT;
                break;
        }
        return value;
    }

    public long getCodecSpecific1() {
        if (mBluetoothCodecConfig == null) {
            return 0;
        }
        long value = getBluetoothCodecConfigLongValue(mBluetoothCodecConfig, "getCodecSpecific1");
        switch ((int) value) {
            case CODEC_SPECIFIC_1_AUDIO_QUALITY:
            case CODEC_SPECIFIC_1_AUDIO_AND_CONNECT_QUALITY:
            case CODEC_SPECIFIC_1_CONNECT_QUALITY:
            case CODEC_SPECIFIC_1_SELF_ADAPTION_QUALITY:
                break;
            case CODEC_SPECIFIC_1_NONE:
            default:
                value = CODEC_SPECIFIC_1_DEFAULT;
                break;
        }
        return value;
    }

    private int getBluetoothCodecConfigIntValue(Object codecConfig, String methodString) {
        int value = -1;
        if (codecConfig == null) {
            return value;
        }
        try {
            Method method = codecConfig.getClass().getMethod(methodString);
            value = (int) method.invoke(codecConfig);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    private long getBluetoothCodecConfigLongValue(Object codecConfig, String methodString) {
        long value = -1;
        if (codecConfig == null) {
            return value;
        }
        try {
            Method method = codecConfig.getClass().getMethod(methodString);
            value = (long) method.invoke(codecConfig);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    public void printfString() {
        Log.i("CodecConfig", "=======================================");
        Log.i("CodecConfig", "mCodecTypeValue: " + mCodecTypeValue);
        Log.i("CodecConfig", "mCodecPriorityValue: " + mCodecPriorityValue);
        Log.i("CodecConfig", "mSampleRateValue: " + mSampleRateValue);
        Log.i("CodecConfig", "mBitsPerSampleValue: " + mBitsPerSampleValue);
        Log.i("CodecConfig", "mChannelModeValue: " + mChannelModeValue);
        Log.i("CodecConfig", "mCodecSpecific1Value: " + mCodecSpecific1Value);
        Log.i("CodecConfig", "=======================================");
    }
}
