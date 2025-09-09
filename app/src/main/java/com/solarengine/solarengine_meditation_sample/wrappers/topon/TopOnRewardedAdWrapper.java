package com.solarengine.solarengine_meditation_sample.wrappers.topon;

import static com.reyun.solar.engine.AdType.RewardVideo;

import android.content.Context;

import androidx.annotation.NonNull;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdRevenueListener;

/**
 * Utility class for TopOn Rewarded Ad callback handling
 */
public class TopOnRewardedAdWrapper {

    /**
     * Build ATAdRevenueListener that intercepts revenue callbacks and forwards them to the user
     * This method returns an ATAdRevenueListener object that can be passed to ATRewardVideoAd
     *
     * @param context 上下文，用于SolarEngine埋点
     * @param userListener The user's ATAdRevenueListener that will receive the forwarded callbacks
     * @return A wrapped ATAdRevenueListener that intercepts and logs callbacks before forwarding them
     */
    @SuppressWarnings("unused")
    public static com.anythink.core.api.ATAdRevenueListener buildATAdRevenueListener(Context context, com.anythink.core.api.ATAdRevenueListener userListener) {
        LogUtils.i("TopOnRewardedAdWrapper.buildATAdRevenueListener() called");

        return new com.anythink.core.api.ATAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull ATAdInfo atAdInfo) {
                LogUtils.i("TopOn Rewarded Ad onAdRevenuePaid() callback received");
                
                // 调用SolarEngine埋点上报广告展示事件
                TopOnSolarEngineTracker.trackAdImpression(context, atAdInfo, RewardVideo);

                if (userListener != null) {
                    userListener.onAdRevenuePaid(atAdInfo);
                }
            }
        };
    }
}
