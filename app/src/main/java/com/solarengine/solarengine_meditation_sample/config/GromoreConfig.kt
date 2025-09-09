package com.solarengine.solarengine_meditation_sample.config

/**
 * Configuration for Gromore SDK
 * Replace the placeholder values with your actual configuration from Gromore dashboard
 */
object GromoreConfig {
    
    /**
     * Gromore App ID
     * Find this in your Gromore dashboard under Apps > App settings
     */
    const val APP_ID = "5734464"
    
    /**
     * Gromore Ad Unit IDs
     * Create these in your Gromore dashboard under Apps > Ad units
     */
    object AdUnits {
        const val INTERSTITIAL = "103599798"
        const val REWARDED = "YOUR_GROMORE_REWARDED_AD_UNIT_ID"
        const val BANNER = "103601359"
        const val SPLASH = "YOUR_GROMORE_SPLASH_AD_UNIT_ID"
        const val MREC = "YOUR_GROMORE_MREC_AD_UNIT_ID"
        const val NATIVE = "YOUR_GROMORE_NATIVE_AD_UNIT_ID"
        const val APP_OPEN = "YOUR_GROMORE_APP_OPEN_AD_UNIT_ID"
        const val FEED = "YOUR_GROMORE_FEED_AD_UNIT_ID"
    }
    
    /**
     * Ad types for Gromore
     */
    enum class AdType {
        INTERSTITIAL,
        REWARDED,
        BANNER,
        SPLASH,
        MREC,
        NATIVE,
        APP_OPEN,
        FEED
    }
    
    /**
     * Get Gromore ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.SPLASH
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
            AdType.FEED -> AdUnits.FEED
        }
    }
}
