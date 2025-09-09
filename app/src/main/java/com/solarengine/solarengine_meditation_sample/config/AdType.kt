package com.solarengine.solarengine_meditation_sample.config

/**
 * Ad type enumeration for different ad formats
 */
enum class AdType(val value: String) {
    INTERSTITIAL("interstitial"),
    REWARDED("rewarded"),
    BANNER("banner"),
    MREC("mrec"),
    NATIVE("native"),
    APP_OPEN("app_open"),
    SPLASH("splash")
}
