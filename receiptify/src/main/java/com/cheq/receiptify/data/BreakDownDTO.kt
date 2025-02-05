package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class Breakdown(
    @SerializedName("important")
    val important: Boolean = false,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?,
    @SerializedName("subscriptKey")
    val subscriptKey: String?,
    @SerializedName("subscriptValue")
    val subScriptValue: String?,
    @SerializedName("gap")
    val gap: Boolean = false,

)