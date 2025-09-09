package com.solarengine.solarengine_meditation_sample.ads

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import com.solarengine.solarengine_meditation_sample.config.IronSourceConfig
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.ironsource.*
import com.unity3d.mediation.*
import com.unity3d.mediation.banner.LevelPlayBannerAdView
import com.unity3d.mediation.impression.LevelPlayImpressionData
import com.unity3d.mediation.impression.LevelPlayImpressionDataListener
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAd
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd

/**
 * IronSource广告管理器 - 使用新的Listener模式
 * 提供静态方法来Create各种广告的Listener
 */
object IronSourceSampleAdManager {
    
    private var isInitialized = false
    
    /**
     * InitializeIronSource SDK
     */
    fun initialize(context: Context) {
        if (isInitialized) {
            LogUtils.w("IronSourceSampleAdManager already initialized")
            return
        }
        
        LogUtils.i("IronSourceSampleAdManager initializing...")
        
        // InitializeIronSource SDK
        val initRequest = LevelPlayInitRequest.Builder(IronSourceConfig.APP_KEY)
            .withUserId("UserID")
            .build()
        
        LevelPlay.init(context, initRequest, object : LevelPlayInitListener {
            override fun onInitSuccess(configuration: LevelPlayConfiguration) {
                LogUtils.i("IronSource SDK initialized successfully")
                isInitialized = true
            }
            
            override fun onInitFailed(error: LevelPlayInitError) {
                LogUtils.e("IronSource SDK initialization failed: ${error.toString()}")
            }
        })
//      instead of using  LevelPlay.addImpressionDataListener(), please using:
        IronSourceWrapper.addImpressionDataListener(context,object:LevelPlayImpressionDataListener{
            override fun onImpressionSuccess(impressionData: LevelPlayImpressionData) {
                LogUtils.e("IronSource SDK onImpressionSuccess: ${impressionData.toString()}")
            }

        })
// when you don`t need listener
//        IronSourceWrapper.removeImpressionDataListener(listener);

        LogUtils.i("IronSourceSampleAdManager initialization started")
    }
    
    /**
     * CreateInterstitial广告
     */
    fun createInterstitialAd(adUnitId: String): LevelPlayInterstitialAd {
        return LevelPlayInterstitialAd(adUnitId)
    }
    
    /**
     * CreateRewarded Video广告
     */
    fun createRewardedAd(adUnitId: String): LevelPlayRewardedAd {
        return LevelPlayRewardedAd(adUnitId)
    }
    
    /**
     * CreateBanner广告
     */
    fun createBannerAd(context: Context, adUnitId: String): LevelPlayBannerAdView {
        return LevelPlayBannerAdView(context, adUnitId)
    }
    
    /**
     * ShowBanner广告到Container中
     */
    fun showBannerAd(container: ViewGroup, bannerAd: LevelPlayBannerAdView) {
        try {
            // 移除Container中现有的广告
            container.removeAllViews()
            
            // 添加新的广告视图
            container.addView(bannerAd)
            LogUtils.i("IronSource Banner ad shown successfully")
        } catch (e: Exception) {
            LogUtils.e("Error showing IronSource Banner ad: ${e.message}")
        }
    }
    
    /**
     * DestroyBanner广告
     */
    fun destroyBannerAd(bannerAd: LevelPlayBannerAdView?) {
        try {
            if (bannerAd == null) {
                return
            }
            
            if (bannerAd.parent != null) {
                (bannerAd.parent as ViewGroup).removeView(bannerAd)
            }
            bannerAd.destroy()
            LogUtils.i("IronSource Banner ad destroyed successfully")
        } catch (e: Exception) {
            LogUtils.e("Error destroying IronSource Banner ad: ${e.message}")
        }
    }
}
