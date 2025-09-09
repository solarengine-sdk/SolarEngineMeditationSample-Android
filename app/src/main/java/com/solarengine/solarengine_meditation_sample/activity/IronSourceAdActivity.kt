package com.solarengine.solarengine_meditation_sample.activity

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.IronSourceSampleAdManager
import com.solarengine.solarengine_meditation_sample.config.IronSourceConfig
import com.solarengine.solarengine_meditation_sample.config.AdType
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.ironsource.IronSourceWrapper
import com.unity3d.mediation.*
import com.unity3d.mediation.banner.LevelPlayBannerAdView
import com.unity3d.mediation.banner.LevelPlayBannerAdViewListener
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAd
import com.unity3d.mediation.interstitial.LevelPlayInterstitialAdListener
import com.unity3d.mediation.rewarded.LevelPlayReward
import com.unity3d.mediation.rewarded.LevelPlayRewardedAd
import com.unity3d.mediation.rewarded.LevelPlayRewardedAdListener


/**
 * IronSource广告演示Activity
 */
class IronSourceAdActivity : AppCompatActivity() {
    
    private lateinit var bannerContainer: FrameLayout
    private var interstitialAd: LevelPlayInterstitialAd? = null
    private var rewardedAd: LevelPlayRewardedAd? = null
    private var bannerAd: LevelPlayBannerAdView? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)
        
        // SetTitle
        findViewById<TextView>(R.id.tvTitle).text = "IronSource Ad Demo"
        
        // GetBannerContainer
        bannerContainer = findViewById(R.id.bannerContainer)
        
        // InitializeSDK并Load广告（在后台线程中执行）
        initializeAdsInBackground()
        
        // SetButton点击事件
        setupButtons()
    }
    
    private fun initializeAdsInBackground() {
        // InitializeSDK on main thread (required by most SDKs)
        LogUtils.i("Starting IronSource SDK initialization on main thread")
        IronSourceSampleAdManager.initialize(this)
        
        // Load广告 in background thread
        Thread {
            try {
                LogUtils.i("Starting IronSource ads loading in background thread")
                
                // Load广告
                loadAds()
                
                LogUtils.i("IronSource ads loading completed in background thread")
            } catch (e: Exception) {
                LogUtils.e("Error during IronSource ads loading: ${e.message}")
            }
        }.start()
    }
    
    private fun loadAds() {
        LogUtils.i("Loading IronSource ads...")
        loadInterstitialAd()
        loadRewardedAd()
        loadBannerAd()
        LogUtils.i("IronSource ads loading started")
    }
    
    private fun loadInterstitialAd() {

        val adUnitId = IronSourceConfig.getAdUnitId(AdType.INTERSTITIAL)
        interstitialAd = IronSourceSampleAdManager.createInterstitialAd(adUnitId)
        
        val listener = object : LevelPlayInterstitialAdListener {

                override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Interstitial ad loaded")
                }
                
                override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                    LogUtils.e("IronSource Interstitial ad load failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Interstitial ad displayed")
                }
                
                override fun onAdDisplayFailed(levelPlayAdError: LevelPlayAdError, levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.e("IronSource Interstitial ad display failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Interstitial ad clicked")
                }
                
                override fun onAdClosed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Interstitial ad closed")
                    // 重新Load广告
                    loadInterstitialAd()
                }
                
                override fun onAdInfoChanged(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Interstitial ad info changed")
                }
            }

        
        interstitialAd?.setListener(listener)
        interstitialAd?.loadAd()
    }
    
    private fun loadRewardedAd() {
        val adUnitId = IronSourceConfig.getAdUnitId(AdType.REWARDED)
        rewardedAd = IronSourceSampleAdManager.createRewardedAd(adUnitId)
        
        val listener = object : LevelPlayRewardedAdListener {
                override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded ad loaded")
                }
                
                override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                    LogUtils.e("IronSource Rewarded ad load failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded ad displayed")
                }
                
                override fun onAdDisplayFailed(levelPlayAdError: LevelPlayAdError, levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.e("IronSource Rewarded ad display failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded ad clicked")
                }
                
                override fun onAdClosed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded ad closed")
                    // 重新Load广告
                    loadRewardedAd()
                }
                
                override fun onAdInfoChanged(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded ad info changed")
                }
                
                override fun onAdRewarded(levelPlayReward: LevelPlayReward, levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Rewarded user earned reward: ${levelPlayReward.toString()}")
                }
            }

        rewardedAd?.setListener(listener)
        rewardedAd?.loadAd()
    }
    
    private fun loadBannerAd() {
        val adUnitId = IronSourceConfig.getAdUnitId(AdType.BANNER)
        bannerAd = IronSourceSampleAdManager.createBannerAd(this, adUnitId)
        
        val listener = object : LevelPlayBannerAdViewListener {
                override fun onAdLoaded(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad loaded")
                }
                
                override fun onAdLoadFailed(levelPlayAdError: LevelPlayAdError) {
                    LogUtils.e("IronSource Banner ad load failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdDisplayed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad displayed")
                }
                
                override fun onAdDisplayFailed(levelPlayAdInfo: LevelPlayAdInfo, levelPlayAdError: LevelPlayAdError) {
                    LogUtils.e("IronSource Banner ad display failed: ${levelPlayAdError.toString()}")
                }
                
                override fun onAdClicked(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad clicked")
                }
                
                override fun onAdExpanded(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad expanded")
                }
                
                override fun onAdCollapsed(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad collapsed")
                }
                
                override fun onAdLeftApplication(levelPlayAdInfo: LevelPlayAdInfo) {
                    LogUtils.i("IronSource Banner ad left application")
                }
            }

        bannerAd?.setBannerListener(listener)
        bannerAd?.loadAd()
    }
    
    private fun setupButtons() {
        // Interstitial广告Button
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("IronSource Interstitial button clicked")
            interstitialAd?.let { ad ->
                if (ad.isAdReady()) {
                    ad.showAd(this)
                } else {
                    LogUtils.w("IronSource Interstitial not ready to show")
                }
            } ?: run {
                LogUtils.w("IronSource Interstitial ad is null")
            }
        }
        
        // Rewarded Video广告Button
        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("IronSource Rewarded button clicked")
            rewardedAd?.let { ad ->
                if (ad.isAdReady()) {
                    ad.showAd(this)
                } else {
                    LogUtils.w("IronSource Rewarded not ready to show")
                }
            } ?: run {
                LogUtils.w("IronSource Rewarded ad is null")
            }
        }
        
        // Banner广告Button
        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("IronSource Banner button clicked")
            bannerAd?.let { ad ->
                IronSourceSampleAdManager.showBannerAd(bannerContainer, ad)
            } ?: run {
                LogUtils.w("IronSource Banner ad is null")
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // DestroyBanner广告
        bannerAd?.let { ad ->
            IronSourceSampleAdManager.destroyBannerAd(ad)
        }
    }
}
