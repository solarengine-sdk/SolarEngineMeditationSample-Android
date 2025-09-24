package com.solarengine.solarengine_meditation_sample.wrappers.admob;

import static com.reyun.solar.engine.AdType.Banner;
import static com.reyun.solar.engine.AdType.InterstitialVideo;
import static com.reyun.solar.engine.AdType.Native;
import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.reyun.solar.engine.AdType.Splash;

import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.AdapterResponseInfo;
import com.google.android.gms.ads.OnPaidEventListener;

/**
 * Consolidated utility class for AdMob ad callback handling
 */
public class AdMobAdWrapper {

    /**
     * Build OnPaidEventListener for Rewarded Ad
     */
    @SuppressWarnings("unused")
    public static OnPaidEventListener buildRewardedAdOnPaidEventListener(Context context,
                                                                         String adUnitId,
                                                                        AdapterResponseInfo adInfo,
                                                                        OnPaidEventListener userCallback) {
        LogUtils.i("AdMobAdWrapper.buildRewardedAdOnPaidEventListener() called");

        return new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                LogUtils.i("AdMob Rewarded onPaidEvent");

                AdMobSolarEngineTracker.trackAdImpression(context,adUnitId, adInfo, adValue, RewardVideo);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onPaidEvent(adValue);
                }
            }
        };
    }

    /**
     * Build OnPaidEventListener for Interstitial Ad
     */
    @SuppressWarnings("unused")
    public static OnPaidEventListener buildInterstitialAdOnPaidEventListener(Context context,
                                                                             String adUnitId,
                                                                            AdapterResponseInfo adInfo,
                                                                            OnPaidEventListener userCallback) {
        LogUtils.i("AdMobAdWrapper.buildInterstitialAdOnPaidEventListener() called");

        return new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                LogUtils.i("AdMob Interstitial onPaidEvent");

                AdMobSolarEngineTracker.trackAdImpression(context,adUnitId, adInfo, adValue, InterstitialVideo);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onPaidEvent(adValue);
                }
            }
        };
    }

    /**
     * Build OnPaidEventListener for Banner Ad
     */
    @SuppressWarnings("unused")
    public static OnPaidEventListener buildBannerAdOnPaidEventListener(Context context,
                                                                       String adUnitId,
                                                                      AdapterResponseInfo adInfo,
                                                                      OnPaidEventListener userCallback) {
        LogUtils.i("AdMobAdWrapper.buildBannerAdOnPaidEventListener() called");

        return new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                LogUtils.i("AdMob Banner onPaidEvent");

                AdMobSolarEngineTracker.trackAdImpression(context,adUnitId, adInfo, adValue, Banner);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onPaidEvent(adValue);
                }
            }
        };
    }

    /**
     * Build OnPaidEventListener for Native Ad
     */
    @SuppressWarnings("unused")
    public static OnPaidEventListener buildNativeAdOnPaidEventListener(Context context,
                                                                       String adUnitId,
                                                                      AdapterResponseInfo adInfo,
                                                                      OnPaidEventListener userCallback) {
        LogUtils.i("AdMobAdWrapper.buildNativeAdOnPaidEventListener() called");

        return new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                LogUtils.i("AdMob Native onPaidEvent");

                AdMobSolarEngineTracker.trackAdImpression(context,adUnitId, adInfo, adValue, Native);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onPaidEvent(adValue);
                }
            }
        };
    }

    /**
     * Build OnPaidEventListener for App Open Ad
     */
    @SuppressWarnings("unused")
    public static OnPaidEventListener buildAppOpenAdOnPaidEventListener(Context context,
                                                                        String adUnitId,
                                                                       AdapterResponseInfo adInfo,
                                                                       OnPaidEventListener userCallback) {
        LogUtils.i("AdMobAdWrapper.buildAppOpenAdOnPaidEventListener() called");

        return new OnPaidEventListener() {
            @Override
            public void onPaidEvent(@NonNull AdValue adValue) {
                LogUtils.i("AdMob App Open onPaidEvent");

                AdMobSolarEngineTracker.trackAdImpression(context,adUnitId, adInfo, adValue,
                        Splash);
                // 调用SolarEngine埋点上报广告展示事件
                if (userCallback != null) {
                    userCallback.onPaidEvent(adValue);
                }
            }
        };
    }
}
