package com.solarengine.solarengine_meditation_sample.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.sdk.openadsdk.*
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.ads.GromoreSampleAdManager
import com.solarengine.solarengine_meditation_sample.utils.LogUtils
import com.solarengine.solarengine_meditation_sample.wrappers.gromore.GromoreAdWrapper

/**
 * Gromore广告演示Activity
 */
class GromoreAdActivity : AppCompatActivity() {
    
    private lateinit var bannerContainer: FrameLayout
    private var interstitialAd: TTFullScreenVideoAd? = null
    private var rewardedAd: TTRewardVideoAd? = null
    private var bannerAd: TTNativeExpressAd? = null
    private lateinit var adNative: TTAdNative
    
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_demo)
        
        // SetTitle
        findViewById<TextView>(R.id.tvTitle).text = "Gromore Ad Demo"
        
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
        bannerAd?.let { ad ->
            GromoreSampleAdManager.showBannerAd(this, bannerContainer, ad)
        } ?: run {
            LogUtils.w("Gromore Banner ad is null, loading new banner")
            loadBannerAd()
        }
    }
    
    private fun initializeAdsInBackground() {
        // InitializeSDK on main thread (required by most SDKs)
        LogUtils.i("Starting Gromore SDK initialization on main thread")
        GromoreSampleAdManager.initialize(this)
        
        // GetTTAdNative实例
        adNative = GromoreSampleAdManager.getAdNative(this)
        
        // Load广告 in background thread
        Thread {
            try {
                LogUtils.i("Starting Gromore ads loading in background thread")
                
                // Load广告
                loadAds()
                
                LogUtils.i("Gromore ads loading completed in background thread")
            } catch (e: Exception) {
                LogUtils.e("Error during Gromore ads loading: ${e.message}")
            }
        }.start()
    }
    
    private fun loadAds() {
        LogUtils.i("Loading Gromore ads...")
        loadInterstitialAd()
        loadRewardedAd()
        loadBannerAd()
        LogUtils.i("Gromore ads loading started")
    }
    
    private fun loadInterstitialAd() {
        val adSlot = GromoreSampleAdManager.createInterstitialAdSlot()
        
        adNative.loadFullScreenVideoAd(adSlot, object : TTAdNative.FullScreenVideoAdListener {
            override fun onError(code: Int, message: String) {
                LogUtils.e("Gromore Interstitial ad load failed: code=$code, message=$message")
            }
            
            override fun onFullScreenVideoAdLoad(ttFullScreenVideoAd: TTFullScreenVideoAd) {
                LogUtils.i("Gromore Interstitial ad loaded successfully")
                interstitialAd = ttFullScreenVideoAd
                ttFullScreenVideoAd.setFullScreenVideoAdInteractionListener(object
                    : TTFullScreenVideoAd.FullScreenVideoAdInteractionListener {
                    override fun onAdShow() {
                        GromoreAdWrapper.trackInterstitialAdImpression(this@GromoreAdActivity, interstitialAd)
                    }

                    override fun onAdVideoBarClick() {
                        TODO("Not yet implemented")
                    }

                    override fun onAdClose() {
                        TODO("Not yet implemented")
                    }

                    override fun onVideoComplete() {
                        TODO("Not yet implemented")
                    }

                    override fun onSkippedVideo() {
                        TODO("Not yet implemented")
                    }

                })
            }
            
            @Deprecated("Deprecated in Java", ReplaceWith(
                "LogUtils.i(\"Gromore Interstitial ad cached\")",
                "com.solarengine.solarengine_meditation_sample.utils.LogUtils"
            )
            )
            override fun onFullScreenVideoCached() {
                LogUtils.i("Gromore Interstitial ad cached")
            }
            
            override fun onFullScreenVideoCached(ttFullScreenVideoAd: TTFullScreenVideoAd) {
                LogUtils.i("Gromore Interstitial ad cached with ad object")
            }
        })
    }
    
    private fun loadRewardedAd() {
        val adSlot = GromoreSampleAdManager.createRewardedAdSlot()

        
        adNative.loadRewardVideoAd(adSlot, object : TTAdNative.RewardVideoAdListener {
            override fun onError(code: Int, message: String) {
                LogUtils.e("Gromore Rewarded ad load failed: code=$code, message=$message")
            }
            
            override fun onRewardVideoAdLoad(ttRewardVideoAd: TTRewardVideoAd) {
                LogUtils.i("Gromore Rewarded ad loaded successfully")
                rewardedAd = ttRewardVideoAd

                ttRewardVideoAd.setRewardAdInteractionListener(object : TTRewardVideoAd.RewardAdInteractionListener {
                    override fun onAdShow() {
                        GromoreAdWrapper.trackRewardedAdImpression(this@GromoreAdActivity, rewardedAd)
                    }

                    override fun onAdVideoBarClick() {
                    }

                    override fun onAdClose() {
                    }

                    override fun onVideoComplete() {
                    }

                    override fun onVideoError() {
                    }

                    @Deprecated("Deprecated in Java", ReplaceWith("TODO(\"Not yet implemented\")"))
                    override fun onRewardVerify(
                        p0: Boolean,
                        p1: Int,
                        p2: String?,
                        p3: Int,
                        p4: String?
                    ) {
                        TODO("Not yet implemented")
                    }

                    override fun onRewardArrived(p0: Boolean, p1: Int, p2: Bundle?) {
                        TODO("Not yet implemented")
                    }

                    override fun onSkippedVideo() {
                    }
                })

                ttRewardVideoAd.showRewardVideoAd(this@GromoreAdActivity)
            }
            
            @Deprecated("Deprecated in Java", ReplaceWith(
                "LogUtils.i(\"Gromore Rewarded ad cached\")",
                "com.solarengine.solarengine_meditation_sample.utils.LogUtils"
            )
            )
            override fun onRewardVideoCached() {
                LogUtils.i("Gromore Rewarded ad cached")
            }
            
            override fun onRewardVideoCached(ttRewardVideoAd: TTRewardVideoAd) {
                LogUtils.i("Gromore Rewarded ad cached with ad object")
            }
        })
    }
    
    private fun loadBannerAd() {
        val adSlot = GromoreSampleAdManager.createBannerAdSlot()
        adNative.loadBannerExpressAd(adSlot, object : TTAdNative.NativeExpressAdListener {
            override fun onError(code: Int, message: String) {
                LogUtils.e("Gromore Banner ad load failed: code=$code, message=$message")
            }
            
            override fun onNativeExpressAdLoad(ads: MutableList<TTNativeExpressAd>?) {
                if (!ads.isNullOrEmpty()) {
                    LogUtils.i("Gromore Banner ad loaded successfully")
                    bannerAd = ads[0]
                    bannerAd?.setExpressInteractionListener(object : TTNativeExpressAd
                        .AdInteractionListener{
                        override fun onAdClicked(p0: View?, p1: Int) {
                            TODO("Not yet implemented")
                        }

                        override fun onAdShow(p0: View?, p1: Int) {
                            GromoreAdWrapper.trackBannerAdImpression(this@GromoreAdActivity, bannerAd)
                        }

                        override fun onRenderFail(p0: View?, p1: String?, p2: Int) {
                            TODO("Not yet implemented")
                        }

                        override fun onRenderSuccess(p0: View?, p1: Float, p2: Float) {
                            TODO("Not yet implemented")
                        }

                        override fun onAdDismiss() {
                            TODO("Not yet implemented")
                        }

                    })
                }
            }
        })
    }
    
    private fun setupButtons() {
        // Interstitial广告Button
        findViewById<Button>(R.id.btnInterstitial).setOnClickListener {
            LogUtils.i("Gromore Interstitial button clicked")
            interstitialAd?.showFullScreenVideoAd(this) ?: run {
                LogUtils.w("Gromore Interstitial ad is null")
            }
        }
        
        // Rewarded Video广告Button
        findViewById<Button>(R.id.btnRewarded).setOnClickListener {
            LogUtils.i("Gromore Rewarded button clicked")
            rewardedAd?.showRewardVideoAd(this) ?: run {
                LogUtils.w("Gromore Rewarded ad is null")
            }
        }
        
        // Banner广告Button
        findViewById<Button>(R.id.btnBanner).setOnClickListener {
            LogUtils.i("Gromore Banner button clicked")
            displayBannerAd()
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        // DestroyBanner广告
        bannerAd?.let { ad ->
            GromoreSampleAdManager.destroyBannerAd(ad)
        }
    }
}
