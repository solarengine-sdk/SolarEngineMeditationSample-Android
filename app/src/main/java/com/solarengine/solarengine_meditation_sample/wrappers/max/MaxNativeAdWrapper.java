package com.solarengine.solarengine_meditation_sample.wrappers.max;

import static com.reyun.solar.engine.AdType.Native;
import static com.solarengine.solarengine_meditation_sample.wrappers.max.MaxSolarEngineTracker.trackAdImpression;

import android.content.Context;

import androidx.annotation.NonNull;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;

/**
 * Utility class for MAX Native Ad callback handling
 */
@SuppressWarnings("unused")
public class MaxNativeAdWrapper {

    /**
     * Build MaxAdRevenueListener with callback interception
     */
    @SuppressWarnings("unused")
    public static MaxAdRevenueListener buildAdRevenueListener(Context context,
                                                             MaxAdRevenueListener userCallback) {
        LogUtils.i("MaxNativeAdWrapper.buildAdRevenueListener() called");

        return new MaxAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull MaxAd maxAd) {
                LogUtils.i("Max Native onAdRevenuePaid");

                MaxSolarEngineTracker.trackAdImpression(context, maxAd, Native, false);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onAdRevenuePaid(maxAd);
                }
            }
        };
    }
}
