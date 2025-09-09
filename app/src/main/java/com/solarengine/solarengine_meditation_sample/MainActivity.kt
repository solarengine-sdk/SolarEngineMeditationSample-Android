package com.solarengine.solarengine_meditation_sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.solarengine.solarengine_meditation_sample.activity.AdMobAdActivity
import com.solarengine.solarengine_meditation_sample.activity.MaxAdActivity
import com.solarengine.solarengine_meditation_sample.activity.TakuAdActivity
import com.solarengine.solarengine_meditation_sample.activity.IronSourceAdActivity
import com.solarengine.solarengine_meditation_sample.activity.GromoreAdActivity
import com.solarengine.solarengine_meditation_sample.activity.TopOnAdActivity
import com.solarengine.solarengine_meditation_sample.adapter.AdPlatformAdapter
import com.solarengine.solarengine_meditation_sample.model.AdPlatform

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create ad platform list data
        val platforms = listOf(
            AdPlatform(
                id = "max",
                name = "MAX",
                description = "AppLovin MAX Ad Platform",
                iconResId = R.drawable.ic_launcher_foreground,
                activityClass = MaxAdActivity::class.java
            ),
            AdPlatform(
                id = "admob",
                name = "AdMob",
                description = "Google AdMob Ad Platform",
                iconResId = R.drawable.ic_launcher_foreground,
                activityClass = AdMobAdActivity::class.java
            ),
            AdPlatform(
                id = "ironsource",
                name = "IronSource",
                description = "IronSource Ad Platform",
                iconResId = R.drawable.ic_launcher_foreground,
                activityClass = IronSourceAdActivity::class.java
            ),
            AdPlatform(
                id = "gromore",
                name = "Gromore",
                description = "Gromore Ad Platform",
                iconResId = R.drawable.ic_launcher_foreground,
                activityClass = GromoreAdActivity::class.java
            ),
            // TopOn SDK dependencies are not available, temporarily commented out
            // AdPlatform(
            //     id = "topon",
            //     name = "TopOn",
            //     description = "TopOn Aggregated Ad Platform",
            //     iconResId = R.drawable.ic_launcher_foreground,
            //     activityClass = TopOnAdActivity::class.java
            // ),
            AdPlatform(
                id = "taku",
                name = "Taku",
                description = "Taku Aggregated Ad Platform",
                iconResId = R.drawable.ic_launcher_foreground,
                activityClass = TakuAdActivity::class.java
            )
        )

        // Create adapter
        val adapter = AdPlatformAdapter(platforms) { platform ->
            // Click to open the Activity
            val intent = Intent(this, platform.activityClass)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }
}
