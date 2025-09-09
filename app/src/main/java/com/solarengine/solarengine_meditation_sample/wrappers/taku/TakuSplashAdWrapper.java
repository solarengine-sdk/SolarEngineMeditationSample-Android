package com.solarengine.solarengine_meditation_sample.wrappers.taku;

import static com.reyun.solar.engine.AdType.Splash;
import android.content.Context;
import androidx.annotation.NonNull;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATAdRevenueListener;

/**
 * Taku Splash Ad Wrapper
 * Provides callback interception methods for Taku Splash Ad listeners
 */
public class TakuSplashAdWrapper {

    /**
     * Build ATAdRevenueListener that intercepts revenue callbacks and forwards them to the user
     * This method returns an ATAdRevenueListener object that can be passed to ATSplashAd
     *
     * @param context 上下文，用于SolarEngine埋点
     * @param userListener The user's ATAdRevenueListener that will receive the forwarded callbacks
     * @return A wrapped ATAdRevenueListener that intercepts and logs callbacks before forwarding them
     */
    @SuppressWarnings("unused")
    public static ATAdRevenueListener buildATAdRevenueListener(Context context,
                                                               ATAdRevenueListener userListener) {
        LogUtils.i("TakuSplashAdWrapper.buildATAdRevenueListener() called");

        return new ATAdRevenueListener() {
            @Override
            public void onAdRevenuePaid(@NonNull ATAdInfo atAdInfo) {
                LogUtils.i("Taku Splash Ad onAdRevenuePaid() callback received");
                
                // 调用SolarEngine埋点上报广告展示事件
                TakuSolarEngineTracker.trackAdImpression(context, atAdInfo, Splash);

                if (userListener != null) {
                    userListener.onAdRevenuePaid(atAdInfo);
                }
            }
        };
    }
}
