package com.solarengine.solarengine_meditation_sample.ads

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.AdapterResponseInfo
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.ResponseInfo
import com.solarengine.solarengine_meditation_sample.config.AdMobConfig
import com.solarengine.solarengine_meditation_sample.config.AdType
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.admob.AdMobAdWrapper;


/**
 * AdMobSampleAdManager class to handle AdMob ads
 * This is a sample implementation showing how to load and display ads using AdMob SDK directly
 * with wrapper callback interceptors for logging
 */
class AdMobSampleAdManager(private val context: Context) {

    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    private var bannerAd: AdView? = null
    private var mrecAd: AdView? = null
    private var isInitialized = false
    
    companion object {
        // Ad unit IDs are managed in AdMobConfig
    }
    
    /**
     * Load interstitial ad
     */
    fun loadInterstitialAd() {
        val adUnitID = AdMobConfig.getAdUnitId(AdType.INTERSTITIAL)
        
        InterstitialAd.load(
            context,
            adUnitID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    LogUtils.i("AdMob Interstitial loaded successfully")
                    interstitialAd = ad

                    val responseInfo: ResponseInfo = ad.responseInfo
                    var info: AdapterResponseInfo? = responseInfo.getLoadedAdapterResponseInfo()

                    ad.onPaidEventListener = AdMobAdWrapper.buildInterstitialAdOnPaidEventListener(context,
                        info, object : OnPaidEventListener {
                            override fun onPaidEvent(adValue: AdValue) {
                                LogUtils.i("AdMob Interstitial onPaidEvent")
                            }
                        })

                    ad.setFullScreenContentCallback(object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            LogUtils.i("AdMob Interstitial onAdDismissedFullScreenContent")
                            interstitialAd = null
                        }
                        
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            LogUtils.e("AdMob Interstitial onAdFailedToShowFullScreenContent: ${adError.message}")
                            interstitialAd = null
                        }
                        
                        override fun onAdShowedFullScreenContent() {
                            LogUtils.i("AdMob Interstitial onAdShowedFullScreenContent")
                        }
                        
                        override fun onAdClicked() {
                            LogUtils.i("AdMob Interstitial onAdClicked")
                        }
                    })
                }
                
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    LogUtils.e("AdMob Interstitial failed to load: ${adError.message}")
                    interstitialAd = null
                }
            }
        )
    }
    
    /**
     * Show interstitial ad if loaded
     */
    fun showInterstitialAd() {
        if (interstitialAd != null) {
            interstitialAd?.show(context as Activity)
        } else {
            LogUtils.w("AdMob Interstitial not ready to show")
        }
    }
    
    /**
     * Load rewarded ad
     */
    fun loadRewardedAd() {
        val adUnitID = AdMobConfig.getAdUnitId(AdType.REWARDED)
        
        RewardedAd.load(
            context,
            adUnitID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    LogUtils.i("AdMob Rewarded loaded successfully")
                    rewardedAd = ad
                    
                    val responseInfo: ResponseInfo = ad.responseInfo
                    var info: AdapterResponseInfo? = responseInfo.getLoadedAdapterResponseInfo()

                    ad.onPaidEventListener = AdMobAdWrapper.buildRewardedAdOnPaidEventListener(context,
                        info, object : OnPaidEventListener {
                            override fun onPaidEvent(adValue: AdValue) {
                                LogUtils.i("AdMob Rewarded onPaidEvent")
                            }
                        })
                    
                    ad.setFullScreenContentCallback(object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            LogUtils.i("AdMob Rewarded onAdDismissedFullScreenContent")
                            rewardedAd = null
                        }
                        
                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            LogUtils.e("AdMob Rewarded onAdFailedToShowFullScreenContent: ${adError.message}")
                            rewardedAd = null
                        }
                        
                        override fun onAdShowedFullScreenContent() {
                            LogUtils.i("AdMob Rewarded onAdShowedFullScreenContent")
                        }
                        
                        override fun onAdClicked() {
                            LogUtils.i("AdMob Rewarded onAdClicked")
                        }
                    })
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    LogUtils.e("AdMob Rewarded failed to load: ${loadAdError.message}")
                    rewardedAd = null
                }
            }
        )
    }
    
    /**
     * Show rewarded ad if loaded
     */
    fun showRewardedAd() {
        if (rewardedAd != null) {
            rewardedAd?.show(
                context as Activity,
                object : OnUserEarnedRewardListener {
                    override fun onUserEarnedReward(rewardItem: RewardItem) {
                        LogUtils.i("AdMob Rewarded user earned reward: ${rewardItem.type} - ${rewardItem.amount}")
                    }
                }
            )
        } else {
            LogUtils.w("AdMob Rewarded not ready to show")
        }
    }
    
    /**
     * Initialize AdMob SDK with callback
     */
    fun initializeSdk(callback: (() -> Unit)?) {
        if (isInitialized) {
            LogUtils.i("AdMob SDK already initialized")
            // Call callback even if already initialized to ensure loadAds() is triggered
            callback?.invoke()
            return
        }
        
        LogUtils.i("Initializing AdMob SDK...")
        MobileAds.initialize(context, object : OnInitializationCompleteListener {
            override fun onInitializationComplete(initializationStatus: InitializationStatus) {
                LogUtils.i("AdMob SDK initialization completed")
                isInitialized = true
                // Forward callback to client if provided
                callback?.invoke()
            }
        })
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
        if (bannerAd == null) {
            val adUnitID = AdMobConfig.getAdUnitId(AdType.BANNER)
            bannerAd = AdView(context)
            bannerAd?.adUnitId = adUnitID
            bannerAd?.setAdSize(AdSize.BANNER)
            
            bannerAd?.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    LogUtils.i("AdMob Banner loaded successfully")
                    
                    // Set OnPaidEventListener for Banner ad
                    val responseInfo: ResponseInfo? = bannerAd?.responseInfo
                    var info: AdapterResponseInfo? = responseInfo?.getLoadedAdapterResponseInfo()
                    
                    bannerAd?.onPaidEventListener = AdMobAdWrapper.buildBannerAdOnPaidEventListener(context,
                        info, object : OnPaidEventListener {
                            override fun onPaidEvent(adValue: AdValue) {
                                LogUtils.i("AdMob Banner onPaidEvent")
                            }
                        })
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    LogUtils.e("AdMob Banner failed to load: ${loadAdError.message}")
                }
                
                override fun onAdClicked() {
                    LogUtils.i("AdMob Banner clicked")
                }
                
                override fun onAdImpression() {
                    LogUtils.i("AdMob Banner impression")
                }
                
                override fun onAdOpened() {
                    LogUtils.i("AdMob Banner opened")
                }
                
                override fun onAdClosed() {
                    LogUtils.i("AdMob Banner closed")
                }
            }
        }
        
        bannerAd?.loadAd(AdRequest.Builder().build())
    }
    
    /**
     * Get banner ad view
     */
    fun getBannerAdView(): AdView? {
        return bannerAd
    }
    
    /**
     * Destroy banner ad
     */
    fun destroyBannerAd() {
        bannerAd?.destroy()
        bannerAd = null
    }
    
    /**
     * Load MREC ad
     */
    fun loadMrecAd() {
        if (mrecAd == null) {
            val adUnitID = AdMobConfig.getAdUnitId(AdType.MREC)
            mrecAd = AdView(context)
            mrecAd?.adUnitId = adUnitID
            mrecAd?.setAdSize(AdSize.MEDIUM_RECTANGLE)
            
            mrecAd?.adListener = object : AdListener() {
                override fun onAdLoaded() {
                    LogUtils.i("AdMob MREC loaded successfully")
                    
                    // Set OnPaidEventListener for MREC ad
                    val responseInfo: ResponseInfo? = mrecAd?.responseInfo
                    var info: AdapterResponseInfo? = responseInfo?.getLoadedAdapterResponseInfo()

                    mrecAd?.onPaidEventListener = AdMobAdWrapper.buildNativeAdOnPaidEventListener(context,
                        info, object : OnPaidEventListener {
                            override fun onPaidEvent(adValue: AdValue) {
                                LogUtils.i("AdMob MREC onPaidEvent")
                            }
                        })
                }
                
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    LogUtils.e("AdMob MREC failed to load: ${loadAdError.message}")
                }
                
                override fun onAdClicked() {
                    LogUtils.i("AdMob MREC clicked")
                }
                
                override fun onAdImpression() {
                    LogUtils.i("AdMob MREC impression")
                }
                
                override fun onAdOpened() {
                    LogUtils.i("AdMob MREC opened")
                }
                
                override fun onAdClosed() {
                    LogUtils.i("AdMob MREC closed")
                }
            }
        }
        
        mrecAd?.loadAd(AdRequest.Builder().build())
    }
    
    /**
     * Get MREC ad view
     */
    fun getMrecAdView(): AdView? {
        return mrecAd
    }
    
    /**
     * Destroy MREC ad
     */
    fun destroyMrecAd() {
        mrecAd?.destroy()
        mrecAd = null
    }
}
