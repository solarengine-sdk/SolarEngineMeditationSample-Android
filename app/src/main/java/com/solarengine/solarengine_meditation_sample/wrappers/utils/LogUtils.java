package com.solarengine.solarengine_meditation_sample.wrappers.utils;

import android.util.Log;
import com.solarengine.solarengine_meditation_sample.BuildConfig;

/**
 * Unified Log utility class for all SDK wrappers
 */
public class LogUtils {
    
    // Tag constants for different SDKs
    public static final String TAG_ADMOB = "SEWrapper.AdMob";
    public static final String TAG_MAX = "SEWrapper.MAX";
    public static final String TAG_TAKU = "SEWrapper.Taku";
    public static final String TAG_TOPON = "SEWrapper.TopOn";
    public static final String TAG_IRONSOURCE = "SEWrapper.IronSource";
    public static final String TAG_GROMORE = "SEWrapper.Gromore";
    
    /**
     * Log debug message with specified tag
     */
    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
    
    /**
     * Log info message with specified tag
     */
    public static void i(String tag, String message) {
        Log.i(tag, message);
    }
    
    /**
     * Log warning message with specified tag
     */
    public static void w(String tag, String message) {
        Log.w(tag, message);
    }
    
    /**
     * Log error message with specified tag
     */
    public static void e(String tag, String message) {
        Log.e(tag, message);
    }
    
    /**
     * Log error message with throwable and specified tag
     */
    public static void e(String tag, String message, Throwable throwable) {
        Log.e(tag, message, throwable);
    }
    
    // Single parameter convenience methods for backward compatibility
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d("SEWrapper", message);
        }
    }
    
    public static void i(String message) {
        Log.i("SEWrapper", message);
    }
    
    public static void w(String message) {
        Log.w("SEWrapper", message);
    }
    
    public static void e(String message) {
        Log.e("SEWrapper", message);
    }
    
    // Convenience methods for each SDK
    public static void d_admob(String message) {
        d(TAG_ADMOB, message);
    }
    
    public static void i_admob(String message) {
        i(TAG_ADMOB, message);
    }
    
    public static void w_admob(String message) {
        w(TAG_ADMOB, message);
    }
    
    public static void e_admob(String message) {
        e(TAG_ADMOB, message);
    }
    
    public static void e_admob(String message, Throwable throwable) {
        e(TAG_ADMOB, message, throwable);
    }
    
    public static void d_max(String message) {
        d(TAG_MAX, message);
    }
    
    public static void i_max(String message) {
        i(TAG_MAX, message);
    }
    
    public static void w_max(String message) {
        w(TAG_MAX, message);
    }
    
    public static void e_max(String message) {
        e(TAG_MAX, message);
    }
    
    public static void e_max(String message, Throwable throwable) {
        e(TAG_MAX, message, throwable);
    }
    
    public static void d_taku(String message) {
        d(TAG_TAKU, message);
    }
    
    public static void i_taku(String message) {
        i(TAG_TAKU, message);
    }
    
    public static void w_taku(String message) {
        w(TAG_TAKU, message);
    }
    
    public static void e_taku(String message) {
        e(TAG_TAKU, message);
    }
    
    public static void e_taku(String message, Throwable throwable) {
        e(TAG_TAKU, message, throwable);
    }
    
    public static void d_topon(String message) {
        d(TAG_TOPON, message);
    }
    
    public static void i_topon(String message) {
        i(TAG_TOPON, message);
    }
    
    public static void w_topon(String message) {
        w(TAG_TOPON, message);
    }
    
    public static void e_topon(String message) {
        e(TAG_TOPON, message);
    }
    
    public static void e_topon(String message, Throwable throwable) {
        e(TAG_TOPON, message, throwable);
    }
    
    public static void d_ironsource(String message) {
        d(TAG_IRONSOURCE, message);
    }
    
    public static void i_ironsource(String message) {
        i(TAG_IRONSOURCE, message);
    }
    
    public static void w_ironsource(String message) {
        w(TAG_IRONSOURCE, message);
    }
    
    public static void e_ironsource(String message) {
        e(TAG_IRONSOURCE, message);
    }
    
    public static void e_ironsource(String message, Throwable throwable) {
        e(TAG_IRONSOURCE, message, throwable);
    }
    
    public static void d_gromore(String message) {
        d(TAG_GROMORE, message);
    }
    
    public static void i_gromore(String message) {
        i(TAG_GROMORE, message);
    }
    
    public static void w_gromore(String message) {
        w(TAG_GROMORE, message);
    }
    
    public static void e_gromore(String message) {
        e(TAG_GROMORE, message);
    }
    
    public static void e_gromore(String message, Throwable throwable) {
        e(TAG_GROMORE, message, throwable);
    }
}
