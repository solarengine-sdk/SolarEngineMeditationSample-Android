package com.solarengine.solarengine_meditation_sample.config

/**
 * Main application configuration
 * This file contains SolarEngine SDK configuration and provides access to all SDK configurations
 */
object AppConfig {
    
    /**
     * SolarEngine SDK AppKey
     * Find this in your SolarEngine dashboard under 资产管理-应用管理-16位 Appkey（即应用 ID）
     */
    const val SOLAR_ENGINE_APP_KEY = "YOUR_SOLAR_ENGINE_APP_KEY_HERE"
    
    // ==================== SDK Configuration Access ====================
    
    /**
     * Get MAX SDK configuration
     */
    fun getMaxConfig() = MaxConfig
    
    /**
     * Get AdMob SDK configuration
     */
    fun getAdMobConfig() = AdMobConfig
    
    /**
     * Get IronSource SDK configuration
     */
    fun getIronSourceConfig() = IronSourceConfig
    
    /**
     * Get Gromore SDK configuration
     */
    fun getGromoreConfig() = GromoreConfig
    
    /**
     * Get TopOn SDK configuration
     */
    fun getTopOnConfig() = TopOnConfig
    
    /**
     * Get Taku SDK configuration
     */
    fun getTakuConfig() = TakuConfig
    
    // ==================== Legacy Methods for Backward Compatibility ====================
    
    /**
     * Get AdMob ad unit ID (legacy method)
     * @deprecated Use AdMobConfig.getAdUnitId() instead
     */
    @Deprecated("Use AdMobConfig.getAdUnitId() instead", ReplaceWith("AdMobConfig.getAdUnitId(adType)"))
    fun getAdMobAdUnitId(adType: AdType): String {
        return AdMobConfig.getAdUnitId(adType)
    }
    
    /**
     * Get AppLovin MAX ad unit ID (legacy method)
     * @deprecated Use MaxConfig.getAdUnitId() instead
     */
    @Deprecated("Use MaxConfig.getAdUnitId() instead", ReplaceWith("MaxConfig.getAdUnitId(adType)"))
    fun getMaxAdUnitId(adType: AdType): String {
        return MaxConfig.getAdUnitId(adType)
    }
    
    /**
     * Get IronSource ad unit ID (legacy method)
     * @deprecated Use IronSourceConfig.getAdUnitId() instead
     */
    @Deprecated("Use IronSourceConfig.getAdUnitId() instead", ReplaceWith("IronSourceConfig.getAdUnitId(adType)"))
    fun getIronSourceAdUnitId(adType: AdType): String {
        return IronSourceConfig.getAdUnitId(adType)
    }
    
    /**
     * Get Gromore ad unit ID (legacy method)
     * @deprecated Use GromoreConfig.getAdUnitId() instead
     */
    @Deprecated("Use GromoreConfig.getAdUnitId() instead", ReplaceWith("GromoreConfig.getAdUnitId(adType)"))
    fun getGromoreAdUnitId(adType: GromoreConfig.AdType): String {
        return GromoreConfig.getAdUnitId(adType)
    }
    
    /**
     * Get TopOn ad unit ID (legacy method)
     * @deprecated Use TopOnConfig.getAdUnitId() instead
     */
    @Deprecated("Use TopOnConfig.getAdUnitId() instead", ReplaceWith("TopOnConfig.getAdUnitId(adType)"))
    fun getTopOnAdUnitId(adType: AdType): String {
        return TopOnConfig.getAdUnitId(adType)
    }
    
    /**
     * Get Taku ad unit ID (legacy method)
     * @deprecated Use TakuConfig.getAdUnitId() instead
     */
    @Deprecated("Use TakuConfig.getAdUnitId() instead", ReplaceWith("TakuConfig.getAdUnitId(adType)"))
    fun getTakuAdUnitId(adType: AdType): String {
        return TakuConfig.getAdUnitId(adType)
    }
}
