package com.solarengine.solarengine_meditation_sample.ads

import android.app.Activity
import android.content.Context
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxError
import com.applovin.mediation.MaxReward
import com.applovin.mediation.MaxAdRevenueListener
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.ads.MaxRewardedAd
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.sdk.AppLovinMediationProvider
import com.applovin.sdk.AppLovinSdkInitializationConfiguration
import com.applovin.sdk.AppLovinSdk
import com.solarengine.solarengine_meditation_sample.wrappers.max.MaxInterstitialAdWrapper
import com.solarengine.solarengine_meditation_sample.wrappers.max.MaxRewardedAdWrapper
import com.solarengine.solarengine_meditation_sample.wrappers.max.MaxAdViewWrapper
import com.solarengine.solarengine_meditation_sample.wrappers.max.MaxNativeAdWrapper
import com.solarengine.solarengine_meditation_sample.config.AdType
import com.solarengine.solarengine_meditation_sample.config.MaxConfig
import com.solarengine.solarengine_meditation_sample.utils.LogUtils


/**
 * MaxSampleAdManager class to handle MAX ads
 * This is a sample implementation showing how to load and display ads using MAX SDK directly
 * with wrapper callback interceptors for logging
 */
class MaxSampleAdManager(private val context: Context) {

    private var interstitialAd: MaxInterstitialAd? = null
    private var rewardedAd: MaxRewardedAd? = null
    private var bannerAd: MaxAdView? = null
    private var nativeAdLoader: MaxNativeAdLoader? = null
    private var nativeAd: MaxAd? = null
    private var isInitialized = false
    
    companion object {
        // Ad unit IDs are now managed in MaxConfig
    }
    
    /**
     * Load interstitial ad
     */
    fun loadInterstitialAd() {
        val adUnitID = MaxConfig.getAdUnitId(AdType.INTERSTITIAL)
        interstitialAd = MaxInterstitialAd(adUnitID)
        
        // Set native MAX SDK listener
        interstitialAd?.setListener(object : com.applovin.mediation.MaxAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                LogUtils.i("MAX Interstitial ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                interstitialAd?.setRevenueListener(
                    MaxInterstitialAdWrapper.buildAdRevenueListener(context, object : MaxAdRevenueListener {
                        override fun onAdRevenuePaid(ad: MaxAd) {
                            LogUtils.i("MAX Interstitial onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                LogUtils.e("MAX Interstitial ad load failed: ${error.message}")
            }
            
            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                LogUtils.e("MAX Interstitial ad display failed: ${error.message}")
            }
            
            override fun onAdDisplayed(ad: MaxAd) {
                LogUtils.i("MAX Interstitial ad displayed")
            }
            
            override fun onAdClicked(ad: MaxAd) {
                LogUtils.i("MAX Interstitial ad clicked")
            }
            
            override fun onAdHidden(ad: MaxAd) {
                LogUtils.i("MAX Interstitial ad hidden")
            }
        })
        
        interstitialAd?.loadAd()
    }
    
    /**
     * Show interstitial ad if loaded
     */
    fun showInterstitialAd(activity: Activity) {
        if (interstitialAd?.isReady == true) {
            interstitialAd?.showAd(activity)
        }
    }
    
    /**
     * Load rewarded ad
     */
    fun loadRewardedAd() {
        val adUnitID = MaxConfig.getAdUnitId(AdType.REWARDED)
        rewardedAd = MaxRewardedAd.getInstance(adUnitID, context as Activity)
        
        // Set native MAX SDK listener
        rewardedAd?.setListener(object : com.applovin.mediation.MaxRewardedAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                LogUtils.i("MAX Rewarded ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                rewardedAd?.setRevenueListener(
                    MaxRewardedAdWrapper.buildAdRevenueListener(context, object : com.applovin.mediation.MaxAdRevenueListener {
                        override fun onAdRevenuePaid(ad: MaxAd) {
                            LogUtils.i("MAX Rewarded onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                LogUtils.e("MAX Rewarded ad load failed: ${error.message}")
            }
            
            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                LogUtils.e("MAX Rewarded ad display failed: ${error.message}")
            }
            
            override fun onAdDisplayed(ad: MaxAd) {
                LogUtils.i("MAX Rewarded ad displayed")
            }
            
            override fun onAdClicked(ad: MaxAd) {
                LogUtils.i("MAX Rewarded ad clicked")
            }
            
            override fun onAdHidden(ad: MaxAd) {
                LogUtils.i("MAX Rewarded ad hidden")
            }
            
            override fun onUserRewarded(ad: MaxAd, reward: MaxReward) {
                LogUtils.i("MAX Rewarded user earned reward: ${reward.amount} ${reward.label}")
            }
        })
        
        rewardedAd?.loadAd()
    }
    
    /**
     * Show rewarded ad if loaded
     */
    fun showRewardedAd() {
        if (rewardedAd?.isReady == true) {
            rewardedAd?.showAd()
        }
    }
    
    /**
     * Initialize MAX SDK with callback
     */
    fun initializeSdk(callback: (() -> Unit)?) {
        if (isInitialized) {
            LogUtils.i("MAX SDK already initialized")
            // Call callback even if already initialized to ensure loadAds() is triggered
            callback?.invoke()
            return
        }
        
        LogUtils.i("Initializing MAX SDK...")
        val initConfig = AppLovinSdkInitializationConfiguration.builder(MaxConfig.SDK_KEY)
            .setMediationProvider(AppLovinMediationProvider.MAX)
            .setTestDeviceAdvertisingIds(mutableListOf("b8c3010b-8882-4c83-a23d-850290e1748e"))
            .build()
        
        AppLovinSdk.getInstance(context).initialize(initConfig) {
            LogUtils.i("MAX SDK initialization completed")
            isInitialized = true
            // Forward callback to client if provided
            callback?.invoke()
        }
    }
    
    /**
     * Check if SDK is initialized
     */
    private fun isSdkInitialized(): Boolean {
        return isInitialized
    }
    
    /**
     * Load banner ad
     */
    fun loadBannerAd() {
        val adUnitID = MaxConfig.getAdUnitId(AdType.BANNER)
        bannerAd = MaxAdView(adUnitID)
        
        // Set native MAX SDK listener
        bannerAd?.setListener(object : com.applovin.mediation.MaxAdViewAdListener {
            override fun onAdLoaded(ad: MaxAd) {
                LogUtils.i("MAX Banner ad loaded successfully")
                
                // Set AdRevenueListener using wrapper
                bannerAd?.setRevenueListener(
                    MaxAdViewWrapper.buildAdRevenueListener(context, object : com.applovin.mediation.MaxAdRevenueListener {
                        override fun onAdRevenuePaid(ad: MaxAd) {
                            LogUtils.i("MAX Banner onAdRevenuePaid")
                        }
                    })
                )
            }
            
            override fun onAdLoadFailed(adUnitId: String, error: MaxError) {
                LogUtils.e("MAX Banner ad load failed: ${error.message}")
            }
            
            override fun onAdDisplayFailed(ad: MaxAd, error: MaxError) {
                LogUtils.e("MAX Banner ad display failed: ${error.message}")
            }
            
            override fun onAdDisplayed(ad: MaxAd) {
                LogUtils.i("MAX Banner ad displayed")
            }
            
            override fun onAdClicked(ad: MaxAd) {
                LogUtils.i("MAX Banner ad clicked")
            }
            
            override fun onAdHidden(ad: MaxAd) {
                LogUtils.i("MAX Banner ad hidden")
            }
            
            override fun onAdExpanded(ad: MaxAd) {
                LogUtils.i("MAX Banner ad expanded")
            }
            
            override fun onAdCollapsed(ad: MaxAd) {
                LogUtils.i("MAX Banner ad collapsed")
            }
        })
        
        bannerAd?.loadAd()
    }
    
    /**
     * Get banner ad view
     */
    fun getBannerAdView(): com.applovin.mediation.ads.MaxAdView? {
        return bannerAd
    }
    
    /**
     * Destroy banner ad
     */
    fun destroyBannerAd() {
        bannerAd?.stopAutoRefresh()
        bannerAd = null
    }
    
    /**
     * Load native ad
     */
    fun loadNativeAd() {
        val adUnitID = MaxConfig.getAdUnitId(AdType.NATIVE)
         nativeAdLoader = MaxNativeAdLoader(adUnitID)
        nativeAdLoader?.setRevenueListener(
            MaxNativeAdWrapper.buildAdRevenueListener(context, object : com.applovin.mediation.MaxAdRevenueListener {
                override fun onAdRevenuePaid(ad: MaxAd) {
                    LogUtils.i("MAX Native onAdRevenuePaid")
                }
            })
        )

        nativeAdLoader?.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                LogUtils.i("MAX Native ad loaded successfully")
                nativeAd = ad
            }
            
            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                LogUtils.e("MAX Native ad load failed: ${error.message}")
                nativeAd = null
            }
            
            override fun onNativeAdClicked(ad: MaxAd) {
                LogUtils.i("MAX Native ad clicked")
            }
        })
        
        nativeAdLoader?.loadAd()

    }
    
    /**
     * Get native ad
     */
    fun getNativeAd(): MaxAd? {
        return nativeAd
    }
    
    /**
     * Get native ad loader
     */
    fun getNativeAdLoader(): MaxNativeAdLoader? {
        return nativeAdLoader
    }
    
    /**
     * Destroy native ad
     */
    fun destroyNativeAd() {
        nativeAdLoader?.destroy()
        nativeAdLoader = null
        nativeAd = null
    }
}
