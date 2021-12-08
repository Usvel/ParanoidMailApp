package com.paranoid.vpn.app.vpn.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.paranoid.vpn.app.R
import com.paranoid.vpn.app.common.utils.ConfigurationClickHandlers
import com.paranoid.vpn.app.common.vpn_configuration.domain.model.VPNConfigItem


class VPNConfigAdapter(
    private val mList: List<VPNConfigItem>,
    private val onItemClicked: (Long, ConfigurationClickHandlers) -> Unit,
) : RecyclerView.Adapter<VPNConfigAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.config_item_view, parent, false)

        return ViewHolder(view) {
            onItemClicked(
                mList[it].id, ConfigurationClickHandlers.GetConfiguration
            )
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val configItem = mList[position]

        holder.configName.text = configItem.name

        if(configItem.favorite)
            holder.imFavIcon.setImageResource(R.drawable.ic_favorite_full)

        holder.itemView.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.SetConfiguration)
        }
        holder.cvSettingsIcon.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.GetConfiguration)
        }
        holder.imQRIcon.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.QRCode)
        }
        holder.imEditIcon.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.Edit)
        }
        holder.imShareIcon.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.Share)
        }
        holder.imFavIcon.setOnClickListener {
            onItemClicked(configItem.id, ConfigurationClickHandlers.Like)
        }

        setAnimation(holder.itemView, position)
    }

    private var lastPosition = -1

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(
                viewToAnimate.context,
                R.anim.scale_fade_anim
            )
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(
        ItemView: View,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(ItemView) {
        val configName: TextView = itemView.findViewById(R.id.tvConfigurationName)
        val imQRIcon: ImageView  = itemView.findViewById(R.id.imQRIcon)
        val imEditIcon: ImageView = itemView.findViewById(R.id.imEditIcon)
        val imFavIcon: ImageView = itemView.findViewById(R.id.imFavIcon)
        val imShareIcon: ImageView = itemView.findViewById(R.id.imShareIcon)
        val cvSettingsIcon: CardView = itemView.findViewById(R.id.cvSettingsIcon)

        init {
            itemView.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
            cvSettingsIcon.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
            imQRIcon.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
            imEditIcon.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
            imFavIcon.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
            imShareIcon.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }
    }
}