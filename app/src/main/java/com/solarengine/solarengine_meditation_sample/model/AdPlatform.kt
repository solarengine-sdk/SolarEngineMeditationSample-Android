package com.solarengine.solarengine_meditation_sample.model

/**
 * 广告平台数据模型
 */
data class AdPlatform(
    val id: String,
    val name: String,
    val description: String,
    val iconResId: Int,
    val activityClass: Class<*>
)
