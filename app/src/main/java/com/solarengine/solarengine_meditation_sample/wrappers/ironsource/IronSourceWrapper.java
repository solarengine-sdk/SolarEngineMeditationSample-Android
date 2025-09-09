package com.solarengine.solarengine_meditation_sample.wrappers.ironsource;

import android.content.Context;
import androidx.annotation.NonNull;

import com.unity3d.mediation.LevelPlay;
import com.unity3d.mediation.impression.LevelPlayImpressionData;
import com.unity3d.mediation.impression.LevelPlayImpressionDataListener;

/**
 * Main IronSource wrapper for impression data event interception and SolarEngine tracking
 * Provides simple listener management for impression data events
 */
public class IronSourceWrapper {

    private static boolean listenerHeld = false;
    private static LevelPlayImpressionDataListener mListener = null;
    private static LevelPlayImpressionDataListener innerListener = null;

    /**
     * Add impression data listener
     * 
     * @param context The context for SolarEngine tracking
     * @param listener The impression data listener to add
     */
    public static void addImpressionDataListener(@NonNull Context context, @NonNull LevelPlayImpressionDataListener listener) {
        LogUtils.i("IronSourceWrapper.addImpressionDataListener() called");
        
        if (!listenerHeld){
            innerListener = new LevelPlayImpressionDataListener() {
                @Override
                public void onImpressionSuccess(@NonNull LevelPlayImpressionData levelPlayImpressionData) {
                    processImpressionData(context,levelPlayImpressionData);
                }
            };
            LevelPlay.addImpressionDataListener(innerListener);
            listenerHeld = true;
        }
        mListener = listener;
    }

    /**
     * Remove impression data listener
     * 
     * @param listener The impression data listener to remove
     */
    public static void removeImpressionDataListener(@NonNull LevelPlayImpressionDataListener listener) {
        LogUtils.i("IronSourceWrapper.removeImpressionDataListener() called");
        if (innerListener != null){
            LevelPlay.removeImpressionDataListener(innerListener);
            innerListener = null;
        }
        mListener = null;
        listenerHeld = false;
    }

    /**
     * Process impression data from IronSource ads
     * This method should be called when impression data is available
     * 
     * @param adInfo The LevelPlayAdInfo containing impression data
     */
    public static void processImpressionData(@NonNull Context context,
                                             @NonNull LevelPlayImpressionData adInfo) {
        LogUtils.i("IronSourceWrapper.processImpressionData() called with data: " + adInfo);
        
        // Track ad impression for SolarEngine
        trackAdImpression(context,adInfo);
        
        // Forward event to all registered listener
        notifyListener(adInfo);
    }

    /**
     * Notify all registered listeners with impression data
     * 
     * @param adInfo The LevelPlayAdInfo containing impression data
     */
    private static void notifyListener(@NonNull LevelPlayImpressionData adInfo) {
        if (mListener != null){
            mListener.onImpressionSuccess(adInfo);
        }
    }

    /**
     * Track ad impression for SolarEngine
     * 
     * @param adInfo The LevelPlayAdInfo containing impression data
     */
    private static void trackAdImpression(@NonNull Context context,
                                          @NonNull LevelPlayImpressionData adInfo) {
        LogUtils.i("IronSourceWrapper: Tracking ad impression for SolarEngine");

        LogUtils.i("IronSourceWrapper: LevelPlayAdInfo available for SolarEngine tracking");
        IronSourceSolarEngineTracker.trackAdImpression(context,adInfo);
    }

}
