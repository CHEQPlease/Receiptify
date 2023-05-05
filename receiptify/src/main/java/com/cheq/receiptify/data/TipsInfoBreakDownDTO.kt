package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class TipsInfoBreakdown(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)