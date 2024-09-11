package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class DeviceSalesReport(
    @SerializedName("header")
    val header: String?,
    @SerializedName("headerMeta")
    val headerMeta: List<MetaData>?,
    @SerializedName("paymentBreakdown")
    val breakdown: List<PaymentBreakdown>?
)

data class MetaData(
    @SerializedName("isBold")
    val isBold: Boolean = false,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)

data class PaymentBreakdown(
    @SerializedName("isBold")
    val isBold: Boolean = false,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)
