package com.solarengine.solarengine_meditation_sample.wrappers.max;

import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.solarengine.solarengine_meditation_sample.wrappers.max.MaxSolarEngineTracker.trackAdImpression;

import android.content.Context;

import androidx.annotation.NonNull;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdRevenueListener;

/**
 * Utility class for MAX Rewarded Ad callback handling
 */
public class MaxRewardedAdWrapper {

    /**
     * Build MaxAdRevenueListener with callback interception
     */
    @SuppressWarnings("unused")
    public static MaxAdRevenueListener buildAdRevenueListener(Context context,
                                                             MaxAdRevenueListener userCallback) {
        LogUtils.i("MaxRewardedAdWrapper.buildAdRevenueListener() called");

        return new MaxAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull MaxAd maxAd) {
                LogUtils.i("Max Rewarded onAdRevenuePaid");

                MaxSolarEngineTracker.trackAdImpression(context, maxAd, RewardVideo, false);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onAdRevenuePaid(maxAd);
                }
            }
        };
    }
}

