package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class ServerTipInfo(
    @SerializedName("serverName")
    val serverName: String?,
    @SerializedName("serverId")
    val serverId: String?,
    @SerializedName("totalTip")
    val totalTip: String?,
    @SerializedName("tipInfoBreakdown")
    val tipsInfoBreakdown: List<TipsInfoBreakdown> = listOf(),
    @SerializedName("tipPerRevenueCenter")
    val tipPerRevenueCenter: List<TipsPerRevenueCenter> = listOf()
)