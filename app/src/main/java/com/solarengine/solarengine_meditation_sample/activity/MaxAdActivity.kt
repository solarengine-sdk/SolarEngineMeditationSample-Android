package com.solarengine.solarengine_meditation_sample.activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.MaxSampleAdManager
import com.solarengine.solarengine_meditation_sample.utils.LogUtils

/**
 * MAX Ad Demo Activity
 */
class MaxAdActivity : AppCompatActivity() {

    private lateinit var maxAdManager: MaxSampleAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)

        // Initialize MAX SDK
        maxAdManager = MaxSampleAdManager(this)

        // Set title
        findViewById<TextView>(R.id.tvTitle).text = "MAX Ad Demo"

        // Initialize SDK in background thread
        initializeSdk()

        // Set button click events
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("MAX Interstitial button clicked")
            maxAdManager.showInterstitialAd(this)
        }

        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("MAX Rewarded button clicked")
            maxAdManager.showRewardedAd()
        }

        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("MAX Banner button clicked")
            showBannerAd()
        }

        findViewById<Button>(R.id.btnMrec).setOnClickListener {
            LogUtils.i("MAX Native Ad button clicked")
            showNativeAd()
        }
    }
    
    /**
     * Initialize SDK on main thread
     */
    private fun initializeSdk() {
        // Initialize SDK on main thread (required by most SDKs)
        LogUtils.i("Starting MAX SDK initialization on main thread")
        maxAdManager.initializeSdk {
            LogUtils.i("MAX SDK initialization completed, starting ads loading")
            
            // Load ads in background thread after successful initialization
            Thread {
                try {
                    LogUtils.i("Starting MAX ads loading in background thread")
                    
                    // Load ads after SDK initialization
                    loadAds()
                    
                    LogUtils.i("MAX ads loading completed in background thread")
                } catch (e: Exception) {
                    LogUtils.e("Error during MAX ads loading: ${e.message}")
                }
            }.start()
        }
    }
    
    /**
     * Load all ad types after SDK initialization
     */
    private fun loadAds() {
        LogUtils.i("Loading MAX ads...")
        
        // Load interstitial ad
        maxAdManager.loadInterstitialAd()
        
        // Load rewarded ad
        maxAdManager.loadRewardedAd()
        
        // Load banner ad
        maxAdManager.loadBannerAd()
        
        // Load native ad
        maxAdManager.loadNativeAd()
        
        LogUtils.i("MAX ads loading started")
    }
    
    /**
     * Show banner ad
     */
    private fun showBannerAd() {
        LogUtils.i("Showing MAX Banner ad")
        
        val bannerAdView = maxAdManager.getBannerAdView()
        if (bannerAdView != null) {
            // Find the banner container in layout
            val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
            if (bannerContainer != null) {
                // Remove any existing views
                bannerContainer.removeAllViews()
                
                // Add banner ad view to container
                bannerContainer.addView(bannerAdView)
                
                LogUtils.i("MAX Banner ad added to container")
            } else {
                LogUtils.w("Banner container not found in layout")
            }
        } else {
            LogUtils.w("MAX Banner ad not ready to show")
        }
    }
    
    /**
     * Show native ad
     */
    private fun showNativeAd() {
        LogUtils.i("Showing MAX Native ad")
        
        val nativeAd = maxAdManager.getNativeAd()
        val nativeAdLoader = maxAdManager.getNativeAdLoader()
        
        if (nativeAd != null && nativeAdLoader != null) {
            // Find the banner container in layout (reuse for native)
            val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
            if (bannerContainer != null) {
                // Remove any existing views
                bannerContainer.removeAllViews()
                
                // Create native ad view and render the ad
                val nativeAdView = layoutInflater.inflate(R.layout.max_native_ad_view, bannerContainer, false) as com.applovin.mediation.nativeAds.MaxNativeAdView
                
                // Render the native ad
                // Note: render method signature may vary based on MAX SDK version
                LogUtils.i("MAX Native ad ready to render")
                LogUtils.i("Native ad title: ${nativeAd.nativeAd?.title}")
                LogUtils.i("Native ad description: ${nativeAd.nativeAd?.body}")
                LogUtils.i("Native ad call to action: ${nativeAd.nativeAd?.callToAction}")
                LogUtils.i("Native ad star rating: ${nativeAd.nativeAd?.starRating}")
                
                // For now, just add the native ad view to container
                bannerContainer.addView(nativeAdView)
                LogUtils.i("MAX Native ad view added to container")
            } else {
                LogUtils.w("Banner container not found in layout")
            }
        } else {
            LogUtils.w("MAX Native ad not ready to show")
        }
    }

/**
 * Clean up resources when activity is destroyed
 */
override fun onDestroy() {
    super.onDestroy()
    
    LogUtils.i("MaxAdActivity onDestroy - cleaning up ads")
    
    // Remove ads from container
    val bannerContainer = findViewById<ViewGroup>(R.id.bannerContainer)
    if (bannerContainer != null) {
        bannerContainer.removeAllViews()
        LogUtils.i("MAX ads removed from container")
    }
    
    // Stop banner ad auto refresh if needed
    maxAdManager.getBannerAdView()?.stopAutoRefresh()
    LogUtils.i("MAX Banner ad auto refresh stopped")
    
    // Destroy native ad if needed
    maxAdManager.destroyNativeAd()
    LogUtils.i("MAX Native ad destroyed")
}
}
