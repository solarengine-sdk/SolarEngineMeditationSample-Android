package com.solarengine.solarengine_meditation_sample.config

/**
 * Configuration for Google AdMob SDK
 * Replace the placeholder values with your actual configuration from AdMob dashboard
 */
object AdMobConfig {
    
    /**
     * AdMob App ID
     * Find this in your AdMob dashboard under Apps > App settings
     */
    const val APP_ID = "ca-app-pub-3940256099942544~3347511713"
    
    /**
     * AdMob Ad Unit IDs
     * Create these in your AdMob dashboard under Apps > Ad units
     */
    object AdUnits {
        const val INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
        const val REWARDED = "ca-app-pub-3940256099942544/5224354917"
        const val BANNER = "ca-app-pub-3940256099942544/6300978111"
        const val MREC = "ca-app-pub-3940256099942544/6300978111"
        const val NATIVE = "ca-app-pub-3940256099942544/2247696110"
        const val APP_OPEN = "ca-app-pub-3940256099942544/3419835294"
    }
    
    /**
     * Get AdMob ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.APP_OPEN // AdMob使用APP_OPEN作为Splash广告
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
        }
    }
}
