package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class TipsPerRevenueCenter(
    @SerializedName("name")
    val name: String?,
    @SerializedName("tip")
    val tip: String?,
    @SerializedName("deviceList")
    val deviceList: List<String> = listOf()
)