package com.solarengine.solarengine_meditation_sample.wrappers.gromore;

import static com.reyun.solar.engine.AdType.Banner;
import static com.reyun.solar.engine.AdType.InterstitialVideo;
import static com.reyun.solar.engine.AdType.Native;
import static com.reyun.solar.engine.AdType.RewardVideo;
import static com.reyun.solar.engine.AdType.Splash;

import android.content.Context;
import com.bytedance.sdk.openadsdk.CSJSplashAd;
import com.bytedance.sdk.openadsdk.TTFeedAd;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationAdEcpmInfo;
import com.bytedance.sdk.openadsdk.mediation.manager.MediationBaseManager;

/**
 * Consolidated utility class for Gromore ad impression tracking
 */
public class GromoreAdWrapper {

    /**
     * Track Rewarded Ad impression
     */
    public static void trackRewardedAdImpression(Context context, TTRewardVideoAd rewardVideoAd) {
        LogUtils.i("GromoreAdWrapper.trackRewardedAdImpression() called");
        
        MediationBaseManager mediationManager = rewardVideoAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                GromoreSolarEngineTracker.trackAdImpression(context, showEcpm, RewardVideo);
            }
        }
    }

    /**
     * Track Interstitial Ad impression
     */
    public static void trackInterstitialAdImpression(Context context, TTFullScreenVideoAd fullScreenVideoAd) {
        LogUtils.i("GromoreAdWrapper.trackInterstitialAdImpression() called");
        
        MediationBaseManager mediationManager = fullScreenVideoAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                GromoreSolarEngineTracker.trackAdImpression(context, showEcpm, InterstitialVideo);
            }
        }
    }

    /**
     * Track Banner Ad impression
     */
    public static void trackBannerAdImpression(Context context, TTNativeExpressAd nativeExpressAd) {
        LogUtils.i("GromoreAdWrapper.trackBannerAdImpression() called");
        
        MediationBaseManager mediationManager = nativeExpressAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                GromoreSolarEngineTracker.trackAdImpression(context, showEcpm, Banner);
            }
        }
    }

    /**
     * Track Native Ad impression
     */
    public static void trackNativeAdImpression(Context context, TTFeedAd ttFeedAd) {
        LogUtils.i("GromoreAdWrapper.trackNativeAdImpression() called");
        
        MediationBaseManager mediationManager = ttFeedAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                GromoreSolarEngineTracker.trackAdImpression(context, showEcpm, Native);
            }
        }
    }

    /**
     * Track Splash Ad impression
     */
    public static void trackSplashAdImpression(Context context, CSJSplashAd csjSplashAd) {
        LogUtils.i("GromoreAdWrapper.trackSplashAdImpression() called");
        
        MediationBaseManager mediationManager = csjSplashAd.getMediationManager();
        if (mediationManager != null) {
            MediationAdEcpmInfo showEcpm = mediationManager.getShowEcpm();
            if (showEcpm != null) {
                GromoreSolarEngineTracker.trackAdImpression(context, showEcpm, Splash);
            }
        }
    }
}
