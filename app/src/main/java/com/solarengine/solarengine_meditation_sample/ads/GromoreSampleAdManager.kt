package com.solarengine.solarengine_meditation_sample.ads

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bytedance.sdk.openadsdk.*
import com.bytedance.sdk.openadsdk.mediation.MediationConstant
import com.bytedance.sdk.openadsdk.mediation.ad.MediationAdSlot
import com.solarengine.solarengine_meditation_sample.BuildConfig
import com.solarengine.solarengine_meditation_sample.config.GromoreConfig
import com.solarengine.solarengine_meditation_sample.config.GromoreConfig.AdType
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.gromore.*

/**
 * Gromore广告管理器 - 使用新的Listener模式
 * 提供静态方法来Create各种广告的Listener
 */
object GromoreSampleAdManager {
    
    private var isInitialized = false
    
    /**
     * InitializeGromore SDK
     */
    fun initialize(context: Context) {
        if (isInitialized) {
            LogUtils.w("GromoreSampleAdManager already initialized")
            return
        }
        
        LogUtils.i("GromoreSampleAdManager initializing...")
        
        // InitializeGromore SDK
        TTAdSdk.init(context, TTAdConfig.Builder()
            .appId(GromoreConfig.APP_ID)
            .appName("SolarEngine Meditation Sample")
            .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
            .allowShowNotify(true)
            .debug(BuildConfig.DEBUG)
            .supportMultiProcess(false)
            .build())
        
        // 启动SDK
        TTAdSdk.start(object : TTAdSdk.Callback {
            override fun success() {
                LogUtils.i("Gromore SDK start success")
            }
            
            override fun fail(code: Int, msg: String) {
                LogUtils.e("Gromore SDK start failed: code=$code, message=$msg")
            }
        })
        
        isInitialized = true
        LogUtils.i("GromoreSampleAdManager initialized successfully")
    }
    
    /**
     * CreateInterstitial广告的AdSlot
     */
    fun createInterstitialAdSlot(): AdSlot {
        val adUnitID = GromoreConfig.getAdUnitId(AdType.INTERSTITIAL)
        return AdSlot.Builder()
            .setCodeId(adUnitID)
            .setOrientation(TTAdConstant.VERTICAL)
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setExtraObject(MediationConstant.ADN_PANGLE, "pangleInterstitialCustomData")
                    .setExtraObject(MediationConstant.ADN_GDT, "gdtInterstitialCustomData")
                    .setExtraObject(MediationConstant.ADN_BAIDU, "baiduInterstitialCustomData")
                    .build()
            )
            .build()
    }
    
    /**
     * CreateRewarded Video广告的AdSlot
     */
    fun createRewardedAdSlot(): AdSlot {
        val adUnitID = GromoreConfig.getAdUnitId(AdType.REWARDED)
        return AdSlot.Builder()
            .setCodeId(adUnitID)
            .setOrientation(TTAdConstant.VERTICAL)
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setExtraObject(MediationConstant.ADN_PANGLE, "pangleRewardCustomData")
                    .setExtraObject(MediationConstant.ADN_GDT, "gdtRewardCustomData")
                    .setExtraObject(MediationConstant.ADN_BAIDU, "baiduRewardCustomData")
                    .build()
            )
            .build()
    }
    
    /**
     * CreateBanner广告的AdSlot
     */
    fun createBannerAdSlot(): AdSlot {
        val adUnitID = GromoreConfig.getAdUnitId(AdType.BANNER)
        return AdSlot.Builder()
            .setCodeId(adUnitID)
            .setAdCount(1)
            .setExpressViewAcceptedSize(350f, 0f)
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setExtraObject(MediationConstant.ADN_PANGLE, "pangleBannerCustomData")
                    .setExtraObject(MediationConstant.ADN_GDT, "gdtBannerCustomData")
                    .setExtraObject(MediationConstant.ADN_BAIDU, "baiduBannerCustomData")
                    .build()
            )
            .build()
    }
    
    /**
     * CreateSplash广告的AdSlot
     */
    fun createSplashAdSlot(): AdSlot {
        val adUnitID = GromoreConfig.getAdUnitId(AdType.SPLASH)
        return AdSlot.Builder()
            .setCodeId(adUnitID)
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setExtraObject(MediationConstant.ADN_PANGLE, "pangleSplashCustomData")
                    .setExtraObject(MediationConstant.ADN_GDT, "gdtSplashCustomData")
                    .setExtraObject(MediationConstant.ADN_BAIDU, "baiduSplashCustomData")
                    .build()
            )
            .build()
    }
    
    /**
     * CreateFeed广告的AdSlot
     */
    fun createFeedAdSlot(): AdSlot {
        val adUnitID = GromoreConfig.getAdUnitId(AdType.FEED)
        return AdSlot.Builder()
            .setCodeId(adUnitID)
            .setAdCount(1)
            .setMediationAdSlot(
                MediationAdSlot.Builder()
                    .setExtraObject(MediationConstant.ADN_PANGLE, "pangleFeedCustomData")
                    .setExtraObject(MediationConstant.ADN_GDT, "gdtFeedCustomData")
                    .setExtraObject(MediationConstant.ADN_BAIDU, "baiduFeedCustomData")
                    .build()
            )
            .build()
    }
    
    /**
     * GetTTAdNative实例
     */
    fun getAdNative(context: Context): TTAdNative {
        return TTAdSdk.getAdManager().createAdNative(context)
    }
    
    /**
     * ShowBanner广告到Container中
     */
    fun showBannerAd(context: Context, container: ViewGroup, bannerAd: TTNativeExpressAd) {
        try {
            if (bannerAd == null) {
                LogUtils.w("Gromore Banner ad is null, cannot show")
                return
            }
            
            val expressAdView = bannerAd.expressAdView
            if (expressAdView != null) {
                // 移除Container中现有的广告
                container.removeAllViews()
                
                // 添加新的广告视图
                container.addView(expressAdView)
                LogUtils.i("Gromore Banner ad shown successfully")
            } else {
                LogUtils.w("Gromore Banner ad view is null")
            }
        } catch (e: Exception) {
            LogUtils.e("Error showing Gromore Banner ad: ${e.message}")
        }
    }
    
    /**
     * DestroyBanner广告
     */
    fun destroyBannerAd(bannerAd: TTNativeExpressAd?) {
        try {
            if (bannerAd == null) {
                return
            }
            
            val expressAdView = bannerAd.expressAdView
            if (expressAdView != null && expressAdView.parent != null) {
                (expressAdView.parent as ViewGroup).removeView(expressAdView)
            }
            bannerAd.destroy()
            LogUtils.i("Gromore Banner ad destroyed successfully")
        } catch (e: Exception) {
            LogUtils.e("Error destroying Gromore Banner ad: ${e.message}")
        }
    }
}
