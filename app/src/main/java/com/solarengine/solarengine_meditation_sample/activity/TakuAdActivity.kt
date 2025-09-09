package com.solarengine.solarengine_meditation_sample.activity

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.TakuSampleAdManager
import com.solarengine.solarengine_meditation_sample.config.TakuConfig
import com.solarengine.solarengine_meditation_sample.utils.LogUtils

/**
 * Taku Ad Demo Activity
 */
class TakuAdActivity : AppCompatActivity() {

    private lateinit var takuAdManager: TakuSampleAdManager
    private lateinit var bannerContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)

        // Initialize Taku SDK
        takuAdManager = TakuSampleAdManager(this)

        // Set title
        findViewById<TextView>(R.id.tvTitle).text = "Taku Ad Demo"
        
        // Get banner container
        bannerContainer = findViewById(R.id.bannerContainer)

        // Initialize SDK in background thread
        initializeSdk()

        // Set button click events
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("Taku Interstitial button clicked")
            takuAdManager.showInterstitialAd()
        }

        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("Taku Rewarded button clicked")
            takuAdManager.showRewardedAd()
        }

        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("Taku Banner button clicked")
            displayBannerAd()
        }
    }
    
    /**
     * Display banner ad
     */
    private fun displayBannerAd() {
        // Clear existing banner
        bannerContainer.removeAllViews()
        
        // Get banner ad view and add to container
        takuAdManager.getBannerAdView()?.let { bannerView ->
            bannerContainer.addView(bannerView)
            LogUtils.i("Taku Banner ad displayed")
        } ?: run {
            LogUtils.w("Taku Banner ad view is null, loading new banner")
            takuAdManager.loadBannerAd()
        }
    }
    
    /**
     * Initialize SDK on main thread
     */
    private fun initializeSdk() {
        // Initialize SDK on main thread (required by most SDKs)
        LogUtils.i("Starting Taku SDK initialization on main thread")
        takuAdManager.initializeSdk(applicationContext,
            TakuConfig.APP_ID,
            TakuConfig.APP_KEY
        );
        loadAds();
    }
    
    /**
     * Load all ad types after SDK initialization
     */
    private fun loadAds() {
        LogUtils.i("Loading Taku ads...")
        
        // Load interstitial ad
        takuAdManager.loadInterstitialAd()
        
        // Load rewarded ad
        takuAdManager.loadRewardedAd()
        
        // Load banner ad
        takuAdManager.loadBannerAd()
        
        LogUtils.i("Taku ads loading started")
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // Clean up banner ad
        takuAdManager.destroyBannerAd()
    }
}
