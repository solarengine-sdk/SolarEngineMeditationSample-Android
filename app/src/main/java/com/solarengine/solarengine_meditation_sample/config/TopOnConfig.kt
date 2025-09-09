package com.solarengine.solarengine_meditation_sample.config

/**
 * Configuration for TopOn SDK
 * Replace the placeholder values with your actual configuration from TopOn dashboard
 */
object TopOnConfig {
    
    /**
     * TopOn App ID
     * Find this in your TopOn dashboard under Apps > App settings
     */
    const val APP_ID = "YOUR_TOPON_APP_ID"
    
    /**
     * TopOn App Key
     * Find this in your TopOn dashboard under Apps > App settings
     */
    const val APP_KEY = "YOUR_TOPON_APP_KEY"
    
    /**
     * TopOn Ad Unit IDs
     * Create these in your TopOn dashboard under Apps > Ad units
     */
    object AdUnits {
        const val INTERSTITIAL = "YOUR_TOPON_INTERSTITIAL_AD_UNIT_ID"
        const val REWARDED = "YOUR_TOPON_REWARDED_AD_UNIT_ID"
        const val BANNER = "YOUR_TOPON_BANNER_AD_UNIT_ID"
        const val MREC = "YOUR_TOPON_MREC_AD_UNIT_ID"
        const val NATIVE = "YOUR_TOPON_NATIVE_AD_UNIT_ID"
        const val APP_OPEN = "YOUR_TOPON_APP_OPEN_AD_UNIT_ID"
    }
    
    /**
     * Get TopOn ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.APP_OPEN // TopOn使用APP_OPEN作为Splash广告
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
        }
    }
}
