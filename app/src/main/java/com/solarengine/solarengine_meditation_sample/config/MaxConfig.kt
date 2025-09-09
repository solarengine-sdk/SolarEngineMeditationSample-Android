package com.solarengine.solarengine_meditation_sample.config

/**
 * Configuration for AppLovin MAX SDK
 * Replace the placeholder values with your actual configuration from AppLovin MAX dashboard
 */
object MaxConfig {
    
    /**
     * AppLovin MAX SDK Key
     * Find this in your AppLovin MAX dashboard under Account > General > Keys
     */
    const val SDK_KEY = "72M7erINuKBzEhfgApnfIeBkxHO6CyWKL5G_0ndbFqZf2cErSCaQHFJHhR7tlsQFXBw_4X07L4IBVw3nbTKenM"
    
    /**
     * AppLovin Ad Review Key
     * Find this in your AppLovin MAX dashboard under Account > General > Keys
     */
    const val AD_REVIEW_KEY = "YOUR_AD_REVIEW_KEY_HERE"
    
    /**
     * AppLovin MAX Ad Unit IDs
     * Create these in your AppLovin MAX dashboard under Monetize > Manage Ad Units
     */
    object AdUnits {
        const val INTERSTITIAL = "d6df2d96fdb2ddf3"
        const val REWARDED = "e1e7c0c62cc5567e"
        const val BANNER = "a8107612cddb2394"
        const val MREC = "YOUR_MAX_MREC_AD_UNIT_ID"
        const val NATIVE = "3f9b8ea80940bd64"
        const val APP_OPEN = "16a2ecd917a68e56"
    }
    
    /**
     * Get MAX ad unit ID by ad type
     */
    fun getAdUnitId(adType: AdType): String {
        return when (adType) {
            AdType.INTERSTITIAL -> AdUnits.INTERSTITIAL
            AdType.REWARDED -> AdUnits.REWARDED
            AdType.BANNER -> AdUnits.BANNER
            AdType.SPLASH -> AdUnits.APP_OPEN // MAX使用APP_OPEN作为Splash广告
            AdType.MREC -> AdUnits.MREC
            AdType.NATIVE -> AdUnits.NATIVE
            AdType.APP_OPEN -> AdUnits.APP_OPEN
        }
    }
}
