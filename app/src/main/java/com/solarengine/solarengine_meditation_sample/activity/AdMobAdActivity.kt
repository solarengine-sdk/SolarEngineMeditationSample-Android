package com.solarengine.solarengine_meditation_sample.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.initialization.InitializationStatus
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.AdMobSampleAdManager
import com.solarengine.solarengine_meditation_sample.utils.LogUtils

/**
 * AdMob Ad Demo Activity
 */
class AdMobAdActivity : AppCompatActivity() {

    private lateinit var admobAdManager: AdMobSampleAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)

        // Initialize AdMob Ad Manager
        admobAdManager = AdMobSampleAdManager(this)

        // Set title
        findViewById<TextView>(R.id.tvTitle).text = "AdMob Ad Demo"

        // Initialize SDK and load ads in background thread
        initializeSdkAndLoadAds()

        // Set button click events
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("AdMob Interstitial button clicked")
            admobAdManager.showInterstitialAd()
        }

        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("AdMob Rewarded button clicked")
            admobAdManager.showRewardedAd()
        }

        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("AdMob Banner button clicked")
            showBannerAd()
        }

        findViewById<Button>(R.id.btnMrec).setOnClickListener {
            LogUtils.i("AdMob MREC button clicked")
            showMrecAd()
        }
    }
    
    /**
     * Initialize SDK on main thread and load ads in background thread
     */
    private fun initializeSdkAndLoadAds() {
        // Initialize SDK on main thread (required by AdMob) with callback
        LogUtils.i("Starting AdMob SDK initialization on main thread")
        admobAdManager.initializeSdk {
            LogUtils.i("AdMob SDK initialization completed, starting ads loading")
            loadAds()
        }
    }
    
    /**
     * Load all ad types after SDK initialization
     */
    private fun loadAds() {
        LogUtils.i("Loading AdMob ads...")
        
        // Load interstitial ad
        admobAdManager.loadInterstitialAd()
        
        // Load rewarded ad
        admobAdManager.loadRewardedAd()
        
        // Load banner ad
        admobAdManager.loadBannerAd()
        
        // Load MREC ad
        admobAdManager.loadMrecAd()
        
        LogUtils.i("AdMob ads loading started")
    }
    
    /**
     * Show banner ad
     */
    private fun showBannerAd() {
        LogUtils.i("Showing AdMob Banner ad")
        
        val bannerAdView = admobAdManager.getBannerAdView()
        if (bannerAdView != null) {
            // Find the banner container in layout
            val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
            if (bannerContainer != null) {
                // Remove any existing views
                bannerContainer.removeAllViews()
                
                // Add banner ad view to container
                bannerContainer.addView(bannerAdView)
                
                LogUtils.i("AdMob Banner ad added to container")
            } else {
                LogUtils.w("Banner container not found in layout")
            }
        } else {
            LogUtils.w("AdMob Banner ad not ready to show")
        }
    }
    
    /**
     * Show MREC ad
     */
    private fun showMrecAd() {
        LogUtils.i("Showing AdMob MREC ad")
        
        val mrecAdView = admobAdManager.getMrecAdView()
        if (mrecAdView != null) {
            // Find the banner container in layout (reuse for MREC)
            val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
            if (bannerContainer != null) {
                // Remove any existing views
                bannerContainer.removeAllViews()
                
                // Add MREC ad view to container
                bannerContainer.addView(mrecAdView)
                
                LogUtils.i("AdMob MREC ad added to container")
            } else {
                LogUtils.w("Banner container not found in layout")
            }
        } else {
            LogUtils.w("AdMob MREC ad not ready to show")
        }
    }
    
    /**
     * Clean up resources when activity is destroyed
     */
    override fun onDestroy() {
        super.onDestroy()
        
        LogUtils.i("AdMobAdActivity onDestroy - cleaning up ads")
        
        // Remove ads from container
        val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
        if (bannerContainer != null) {
            bannerContainer.removeAllViews()
            LogUtils.i("AdMob ads removed from container")
        }
        
        // Destroy banner ad view if needed
        val bannerAdView = admobAdManager.getBannerAdView()
        if (bannerAdView != null) {
            bannerAdView.destroy()
            LogUtils.i("AdMob Banner ad view destroyed")
        }
        
        // Destroy MREC ad view if needed
        val mrecAdView = admobAdManager.getMrecAdView()
        if (mrecAdView != null) {
            mrecAdView.destroy()
            LogUtils.i("AdMob MREC ad view destroyed")
        }
    }
}
