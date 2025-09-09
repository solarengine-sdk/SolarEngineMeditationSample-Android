package com.solarengine.solarengine_meditation_sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.solarengine.solarengine_meditation_sample.R
import com.solarengine.solarengine_meditation_sample.model.AdPlatform

/**
 * 广告平台列表适配器
 */
class AdPlatformAdapter(
    private val platforms: List<AdPlatform>,
    private val onItemClick: (AdPlatform) -> Unit
) : RecyclerView.Adapter<AdPlatformAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivPlatformIcon: ImageView = view.findViewById(R.id.ivPlatformIcon)
        val tvPlatformName: TextView = view.findViewById(R.id.tvPlatformName)
        val tvPlatformDesc: TextView = view.findViewById(R.id.tvPlatformDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ad_platform, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val platform = platforms[position]
        
        holder.ivPlatformIcon.setImageResource(platform.iconResId)
        holder.tvPlatformName.text = platform.name
        holder.tvPlatformDesc.text = platform.description
        
        holder.itemView.setOnClickListener {
            onItemClick(platform)
        }
    }

    override fun getItemCount() = platforms.size
}
