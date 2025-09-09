package com.solarengine.solarengine_meditation_sample.activity

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.TopOnSampleAdManager
import com.solarengine.solarengine_meditation_sample.utils.LogUtils

/**
 * TopOn广告演示Activity
 */
class TopOnAdActivity : AppCompatActivity() {
    
    private lateinit var topOnAdManager: TopOnSampleAdManager
    private lateinit var bannerContainer: FrameLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)
        
        // InitializeTopOn广告管理器
        topOnAdManager = TopOnSampleAdManager(this)
        
        // SetTitle
        findViewById<TextView>(R.id.tvTitle).text = "TopOn Ad Demo"
        
        // GetBannerContainer
        bannerContainer = findViewById(R.id.bannerContainer)
        
        // InitializeSDK并Load广告（在后台线程中执行）
        initializeAdsInBackground()
        
        // SetButton点击事件
        setupButtons()
    }
    
    /**
     * ShowBanner广告
     */
    private fun displayBannerAd() {
        // 清除现有Banner
        bannerContainer.removeAllViews()
        
        // GetBanner广告视图并添加到Container
        topOnAdManager.getBannerAdView()?.let { bannerView ->
            bannerContainer.addView(bannerView)
            LogUtils.i("TopOn Banner ad displayed")
        } ?: run {
            LogUtils.w("TopOn Banner ad view is null, loading new banner")
            topOnAdManager.loadBannerAd()
        }
    }
    
    private fun initializeAdsInBackground() {
        // InitializeSDK on main thread (required by most SDKs)
        LogUtils.i("Starting TopOn SDK initialization on main thread")
        // Note: You need to provide actual TopOn App ID and App Key
        topOnAdManager.initializeSdk(this, "your_topon_app_id", "your_topon_app_key")
        LogUtils.i("TopOn SDK initialization completed, starting ads loading")
        
        // Load广告 in background thread
        Thread {
            try {
                LogUtils.i("Starting TopOn ads loading in background thread")
                
                // Load广告
                loadAds()
                
                LogUtils.i("TopOn ads loading completed in background thread")
            } catch (e: Exception) {
                LogUtils.e("Error during TopOn ads loading: ${e.message}")
            }
        }.start()
    }
    
    private fun loadAds() {
        LogUtils.i("Loading TopOn ads...")
        topOnAdManager.loadInterstitialAd()
        topOnAdManager.loadRewardedAd()
        topOnAdManager.loadBannerAd()
        LogUtils.i("TopOn ads loading started")
    }
    
    private fun setupButtons() {
        // Interstitial广告Button
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("TopOn Interstitial button clicked")
            topOnAdManager.showInterstitialAd()
        }
        
        // Rewarded Video广告Button
        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("TopOn Rewarded button clicked")
            topOnAdManager.showRewardedAd()
        }
        
        // Banner广告Button
        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("TopOn Banner button clicked")
            displayBannerAd()
        }
        
        // Splash广告Button（TopOn可能不支持，暂时隐藏或禁用）
        findViewById<Button>(R.id.btnSplash).apply {
            text = "Splash (N/A)"
            isEnabled = false
            setOnClickListener {
                LogUtils.w("TopOn Splash ad is not supported in this demo")
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // 清理Banner广告
        topOnAdManager.destroyBannerAd()
    }
}
