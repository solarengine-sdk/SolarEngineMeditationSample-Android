package com.solarengine.solarengine_meditation_sample

import android.app.Application
import com.reyun.solar.engine.SolarEngineConfig
import com.reyun.solar.engine.SolarEngineManager
import com.solarengine.solarengine_meditation_sample.config.AppConfig
import com.solarengine.solarengine_meditation_sample.utils.LogUtils


class MeditationApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize SolarEngine SDK
        initializeSolarEngineSDK()
    }
    
    private fun initializeSolarEngineSDK() {
        // Initialize SolarEngine SDK
        LogUtils.i("initializeSolarEngineSDK, will call preInit (请确保User同意《隐私政策》后再使用其他方法)")
        // Note: 请确保User同意《隐私政策》后再使用其他方法
        SolarEngineManager.getInstance().preInit(this, AppConfig.SOLAR_ENGINE_APP_KEY)
//        val config:SolarEngineConfig= SolarEngineConfig.Builder().logEnabled().build()
        val config:SolarEngineConfig= SolarEngineConfig.Builder().build()
        SolarEngineManager.getInstance().initialize(
            this, AppConfig.SOLAR_ENGINE_APP_KEY, config
        ) { code ->
            if (code == 0) {
                LogUtils.i("initializeSolarEngineSDK, Initialize成功")
            } else {
                LogUtils.i("initializeSolarEngineSDK, Initialize失败, code: $code")
            }
        }
    }
}
