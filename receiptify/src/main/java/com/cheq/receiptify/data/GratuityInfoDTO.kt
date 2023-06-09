package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class GratuityInfo(
    @SerializedName("gratuityItems")
    val gratuityItems: List<GratuityItem> = listOf(),
    @SerializedName("isSignatureNeeded")
    var isSignatureNeeded: Boolean = false,
    @SerializedName("isCustomTip")
    var isCustomTip: Boolean = true,
)