package com.solarengine.solarengine_meditation_sample.config

import com.solarengine.solarengine_meditation_sample.config.AdType

/**
 * Configuration for IronSource SDK
 * Replace the placeholder values with your actual configuration from IronSource dashboard
 */
object IronSourceConfig {
    
    /**
     * IronSource App Key
     * Find this in your IronSource dashboard under Account > General > Keys
     */
    const val APP_KEY = "2350911ad"
    
    /**
     * IronSource Ad Unit IDs
     * Create these in your IronSource dashboard under Monetization > Ad Units
     */
    object AdUnits {
        const val INTERSTITIAL = "aaohxs995pr6sv2a"
        const val REWARDED = "ewisumcpyg3exjqf"
        const val BANNER = "lf6o75y9bk1680m6"
        const val MREC = "NOT_SUPPORT"
        const val NATIVE = "9nc05ftq8r84njns"
        const val APP_OPEN = "NOT_SUPPORT"
    }
    
    /**
     * Get IronSource ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.APP_OPEN // IronSource使用APP_OPEN作为Splash广告
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
        }
    }
}
