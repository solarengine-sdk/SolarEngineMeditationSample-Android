package com.solarengine.solarengine_meditation_sample.wrappers.ironsource;

import android.util.Log;
import com.solarengine.solarengine_meditation_sample.BuildConfig;

/**
 * Log utility class for IronSource Wrapper
 */
public class LogUtils {
    
    private static final String TAG = "SEWrapper.IronSource";
    
    /**
     * Log debug message
     */
    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
    
    /**
     * Log info message
     */
    public static void i(String message) {
        Log.i(TAG, message);
    }
    
    /**
     * Log warning message
     */
    public static void w(String message) {
        Log.w(TAG, message);
    }
    
    /**
     * Log error message
     */
    public static void e(String message) {
        Log.e(TAG, message);
    }
    
    /**
     * Log error message with throwable
     */
    public static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
}
