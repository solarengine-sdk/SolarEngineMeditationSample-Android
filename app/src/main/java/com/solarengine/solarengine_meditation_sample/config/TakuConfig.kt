package com.solarengine.solarengine_meditation_sample.config

/**
 * Configuration for Taku SDK
 * Replace the placeholder values with your actual configuration from Taku dashboard
 */
object TakuConfig {
    
    /**
     * Taku App ID
     * Find this in your Taku dashboard under Apps > App settings
     */
    const val APP_ID = "a68a6ba1764ab4"
    
    /**
     * Taku App Key
     * Find this in your Taku dashboard under Apps > App settings
     */
    const val APP_KEY = "a2c1f75130014e6b11565d5245113f410"
    
    /**
     * Taku Ad Unit IDs
     * Create these in your Taku dashboard under Apps > Ad units
     */
    object AdUnits {
        const val INTERSTITIAL = "b68a6ba6257517"
        const val REWARDED = "b68a6ba43578fe"
        const val BANNER = "b68a6ba7177a7d"
        const val MREC = "YOUR_TAKU_MREC_AD_UNIT_ID"
        const val NATIVE = "YOUR_TAKU_NATIVE_AD_UNIT_ID"
        const val APP_OPEN = "b68a6ba90e6325"
    }
    
    /**
     * Get Taku ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.APP_OPEN // Taku使用APP_OPEN作为Splash广告
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
        }
    }
}
