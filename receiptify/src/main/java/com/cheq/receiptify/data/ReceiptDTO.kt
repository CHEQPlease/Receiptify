package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class ReceiptDTO(
    @SerializedName("brandName")
    val brandName: String?,
    @SerializedName("breakdown")
    val breakdown: List<Breakdown> = listOf(),
    @SerializedName("items")
    val items: List<Item> = listOf(),
    @SerializedName("orderNo")
    val orderNo: String?,
    @SerializedName("orderType")
    val orderType: String?,
    @SerializedName("orderSubtitle")
    val orderSubtitle: String?,
    @SerializedName("receiptType")
    val receiptType: String = "",
    @SerializedName("deviceType")
    val deviceType: String = "",
    @SerializedName("tableNo")
    val tableNo: String?,
    @SerializedName("timeOfOrder")
    val timeOfOrder: String?,
    @SerializedName("totalItems")
    val totalItems: String?,
    @SerializedName("isRefunded")
    val isRefunded: Boolean = false,
    @SerializedName("isReprinted")
    val isReprinted: Boolean = false,
    @SerializedName("serverTipInfo")
    val serverTipInfo: ServerTipInfo,
    @SerializedName("gratuityInfo")
    val gratuityInfo: GratuityInfo?,
    @SerializedName("paymentQRLink")
    val paymentQRLink: String?
)
