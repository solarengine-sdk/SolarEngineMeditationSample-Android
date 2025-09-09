package com.solarengine.solarengine_meditation_sample.ads

import android.app.Activity
import android.content.Context
import android.view.View
import com.solarengine.solarengine_meditation_sample.config.TakuConfig
import com.solarengine.solarengine_meditation_sample.config.AdType
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.taku.TakuInterstitialAdWrapper
import com.solarengine.solarengine_meditation_sample.wrappers.taku.TakuRewardedAdWrapper
import com.solarengine.solarengine_meditation_sample.wrappers.taku.TakuBannerAdWrapper
import com.anythink.core.api.ATSDK
import com.anythink.core.api.ATAdInfo
import com.anythink.core.api.AdError
import com.anythink.core.api.ATAdRevenueListener
import com.anythink.interstitial.api.ATInterstitial
import com.anythink.interstitial.api.ATInterstitialListener
import com.anythink.rewardvideo.api.ATRewardVideoAd
import com.anythink.rewardvideo.api.ATRewardVideoListener
import com.anythink.banner.api.ATBannerView
import com.anythink.banner.api.ATBannerListener

/**
 * TakuSampleAdManager class to handle Taku ads
 * This is a sample implementation showing how to load and display ads using Taku SDK directly
 * with wrapper callback interceptors for logging
 */
class TakuSampleAdManager(private val context: Context) {

    private var interstitialAd: ATInterstitial? = null
    private var rewardedAd: ATRewardVideoAd? = null
    private var bannerAd: ATBannerView? = null
    private var isInitialized = false
    
    /**
     * Initialize Taku SDK with callback
     */
    fun initializeSdk(context:Context , takuAppID:String , takuAppKey:String ) {
        if (isInitialized) {
            LogUtils.i("Taku SDK already initialized")
            return
        }
        
        LogUtils.i("Initializing Taku SDK...")
        ATSDK.init(context, takuAppID, takuAppKey);//InitializeSDK
        ATSDK.start();    //v6.2.95+，针对国内SDK，调用start启动SDK。
        isInitialized = true
        LogUtils.i("Taku SDK initialization completed")

    }
    
    /**
     * Load interstitial ad
     */
    fun loadInterstitialAd() {
        val adUnitID = TakuConfig.getAdUnitId(AdType.INTERSTITIAL)
        interstitialAd = ATInterstitial(context, adUnitID)
        
        // Set native AnyThink listener
        interstitialAd?.setAdListener(object : ATInterstitialListener {
            override fun onInterstitialAdLoaded() {
                LogUtils.i("Taku Interstitial ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                interstitialAd?.setAdRevenueListener(
                    TakuInterstitialAdWrapper.buildATAdRevenueListener(context, object : ATAdRevenueListener {
                        override fun onAdRevenuePaid(atAdInfo: ATAdInfo) {
                            LogUtils.i("Taku Interstitial onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onInterstitialAdLoadFail(adError: AdError) {
                LogUtils.e("Taku Interstitial ad load failed: ${adError.getDesc()}")
            }
            
            override fun onInterstitialAdShow(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Interstitial ad shown")
            }
            
            override fun onInterstitialAdClicked(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Interstitial ad clicked")
            }
            
            override fun onInterstitialAdClose(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Interstitial ad closed")
            }
            
            override fun onInterstitialAdVideoStart(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Interstitial ad video started")
            }
            
            override fun onInterstitialAdVideoEnd(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Interstitial ad video ended")
            }
            
            override fun onInterstitialAdVideoError(adError: AdError) {
                LogUtils.e("Taku Interstitial ad video error: ${adError.getDesc()}")
            }
        })
        
        interstitialAd?.load()
    }
    
    /**
     * Show interstitial ad if loaded
     */
    fun showInterstitialAd() {
        if (interstitialAd?.isAdReady() == true) {
            interstitialAd?.show(context as Activity)
        } else {
            LogUtils.w("Taku Interstitial ad not ready")
            // Try to reload
            loadInterstitialAd()
        }
    }
    
    /**
     * Load rewarded ad
     */
    fun loadRewardedAd() {
        val adUnitID = TakuConfig.getAdUnitId(AdType.REWARDED)
        rewardedAd = ATRewardVideoAd(context, adUnitID)
        
        // Set native AnyThink listener
        rewardedAd?.setAdListener(object : ATRewardVideoListener {
            override fun onRewardedVideoAdLoaded() {
                LogUtils.i("Taku Rewarded ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                rewardedAd?.setAdRevenueListener(
                    TakuRewardedAdWrapper.buildATAdRevenueListener(context, object : ATAdRevenueListener {
                        override fun onAdRevenuePaid(atAdInfo: ATAdInfo) {
                            LogUtils.i("Taku Rewarded onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onRewardedVideoAdFailed(adError: AdError) {
                LogUtils.e("Taku Rewarded ad load failed: ${adError.getDesc()}")
            }
            
            override fun onRewardedVideoAdPlayStart(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Rewarded ad video started")
            }
            
            override fun onRewardedVideoAdPlayEnd(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Rewarded ad video completed")
            }
            
            override fun onRewardedVideoAdPlayFailed(adError: AdError, atAdInfo: ATAdInfo) {
                LogUtils.e("Taku Rewarded ad play failed: ${adError.getDesc()}")
            }
            
            override fun onRewardedVideoAdClosed(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Rewarded ad closed")
            }
            
            override fun onRewardedVideoAdPlayClicked(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Rewarded ad clicked")
            }
            
            override fun onReward(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Rewarded user earned reward")
            }
        })
        
        rewardedAd?.load()
    }
    
    /**
     * Show rewarded ad if loaded
     */
    fun showRewardedAd() {
        if (rewardedAd?.isAdReady() == true) {
            rewardedAd?.show(context as Activity)
        } else {
            LogUtils.w("Taku Rewarded ad not ready")
            // Try to reload
            loadRewardedAd()
        }
    }
    
    /**
     * Load banner ad
     */
    fun loadBannerAd() {
        bannerAd = ATBannerView(context)
        val adUnitID = TakuConfig.getAdUnitId(AdType.BANNER)
        bannerAd?.setPlacementId(adUnitID)
        
        // Set native AnyThink listener
        bannerAd?.setBannerAdListener(object : ATBannerListener {
            override fun onBannerLoaded() {
                LogUtils.i("Taku Banner ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                bannerAd?.setAdRevenueListener(
                    TakuBannerAdWrapper.buildATAdRevenueListener(context, object : ATAdRevenueListener {
                        override fun onAdRevenuePaid(atAdInfo: ATAdInfo) {
                            LogUtils.i("Taku Banner onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onBannerFailed(adError: AdError) {
                LogUtils.e("Taku Banner ad load failed: ${adError.getDesc()}")
            }
            
            override fun onBannerClicked(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Banner ad clicked")
            }
            
            override fun onBannerShow(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Banner ad impression")
            }
            
            override fun onBannerClose(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Banner ad closed")
            }
            
            override fun onBannerAutoRefreshed(atAdInfo: ATAdInfo) {
                LogUtils.i("Taku Banner ad auto refreshed")
            }
            
            override fun onBannerAutoRefreshFail(adError: AdError) {
                LogUtils.e("Taku Banner ad auto refresh failed: ${adError.getDesc()}")
            }
        })
        
        bannerAd?.loadAd()
    }
    
    /**
     * Get banner ad view
     */
    fun getBannerAdView(): View? {
        return bannerAd
    }
    
    /**
     * Destroy banner ad
     */
    fun destroyBannerAd() {
        bannerAd?.let { bannerView ->
            if (bannerView.parent != null) {
                (bannerView.parent as android.view.ViewGroup).removeView(bannerView)
            }
        }
        bannerAd?.destroy()
        bannerAd = null
    }
}
