package com.paranoid.vpn.app.common.ad_block_configuration.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.paranoid.vpn.app.common.utils.Utils

@Entity(tableName = "ad_ip_item")
data class AdBlockIpItem(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @Expose()
    var Ip: String? = "",
    @Expose()
    var Domain: String? = "",
    @Expose()
    var IsDomain: Boolean = false,
    @Expose()
    var IsLocal: Boolean = false
)

class AdBlockIpDataGenerator {
    companion object {
        fun getAdBlockIpItems(size: Int): MutableList<AdBlockIpItem> {
            val adBlockItems: MutableList<AdBlockIpItem> = arrayListOf()
            val fileName = "ipsList"
            var id = 1L
            val ipLines = Utils.readLines(fileName)
            while (adBlockItems.size < size) {
                val randomIp = ipLines.random()
                if (adBlockItems.none { it.Ip == randomIp })
                    adBlockItems.add(
                        AdBlockIpItem(
                            Ip = randomIp,
                            IsLocal = true
                        )
                    )
            }
            return adBlockItems
        }
    }

}