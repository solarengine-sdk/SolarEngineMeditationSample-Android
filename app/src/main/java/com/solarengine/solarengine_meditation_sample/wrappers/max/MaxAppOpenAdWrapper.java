package com.solarengine.solarengine_meditation_sample.wrappers.max;

import static com.reyun.solar.engine.AdType.Splash;
import static com.solarengine.solarengine_meditation_sample.wrappers.max.MaxSolarEngineTracker.trackAdImpression;

import android.content.Context;

import androidx.annotation.NonNull;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;

/**
 * Utility class for MAX App Open Ad callback handling
 */
@SuppressWarnings("unused")
public class MaxAppOpenAdWrapper {

    /**
     * Build MaxAdRevenueListener with callback interception
     */
    @SuppressWarnings("unused")
    public static MaxAdRevenueListener buildAdRevenueListener(Context context,
                                                             MaxAdRevenueListener userCallback) {
        LogUtils.i("MaxAppOpenAdWrapper.buildAdRevenueListener() called");

        return new MaxAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull MaxAd maxAd) {
                LogUtils.i("Max App Open onAdRevenuePaid");

                MaxSolarEngineTracker.trackAdImpression(context, maxAd, Splash, false);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onAdRevenuePaid(maxAd);
                }
            }
        };
    }
}

