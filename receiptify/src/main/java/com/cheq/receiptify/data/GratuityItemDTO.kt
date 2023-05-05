package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class GratuityItem(
    @SerializedName("tip")
    val tip: String?,
    @SerializedName("total")
    val total: String?,
)