package com.solarengine.solarengine_meditation_sample.wrappers.max;

import static com.reyun.solar.engine.AdType.Banner;

import android.content.Context;

import androidx.annotation.NonNull;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;

/**
 * Utility class for MAX Banner Ad callback handling
 */
public class MaxAdViewWrapper {
    @SuppressWarnings("unused")
    public static MaxAdRevenueListener buildAdRevenueListener(Context context,
                                                              MaxAdRevenueListener userCallback) {
        LogUtils.i("MaxAdViewWrapper.buildAdRevenueListener() called");

        return new MaxAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull MaxAd maxAd) {
                LogUtils.i("Max Banner onAdRevenuePaid");

                MaxSolarEngineTracker.trackAdImpression(context,maxAd,Banner,false);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onAdRevenuePaid(maxAd);
                }
            }

        };
    }

}

