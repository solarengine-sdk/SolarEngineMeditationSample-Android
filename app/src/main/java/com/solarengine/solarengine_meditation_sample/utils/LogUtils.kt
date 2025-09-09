package com.solarengine.solarengine_meditation_sample.utils

import android.util.Log
import com.solarengine.solarengine_meditation_sample.BuildConfig

/**
 * Log utility class for Sample module
 */
object LogUtils {
    
    private const val TAG = "SEWrapper.Sample"
    
    /**
     * Log debug message
     */
    fun d(message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
        }
    }
    
    /**
     * Log info message
     */
    fun i(message: String) {
        Log.i(TAG, message)
    }
    
    /**
     * Log warning message
     */
    fun w(message: String) {
        Log.w(TAG, message)
    }
    
    /**
     * Log error message
     */
    fun e(message: String) {
        Log.e(TAG, message)
    }
    
    /**
     * Log error message with throwable
     */
    fun e(message: String, throwable: Throwable) {
        Log.e(TAG, message, throwable)
    }
}
