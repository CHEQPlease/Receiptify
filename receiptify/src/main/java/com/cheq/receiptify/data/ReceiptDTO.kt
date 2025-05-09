package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class ReceiptDTO(
    @SerializedName("companyName")
    val companyName: String?,
    @SerializedName("brandName")
    val brandName: String?,
    @SerializedName("vatAddress")
    val vatAddress: String?,
    @SerializedName("vatId")
    val vatId: String?,
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
    @SerializedName("alcoholItemWarning")
    val alcoholItemWarning: String?,
    @SerializedName("guestName")
    val guestName: Pair?,
    @SerializedName("customerName")
    val customerName: String?,
    @SerializedName("placedAt")
    val placedAt: Pair?,
    @SerializedName("timeOfOrder")
    val timeOfOrder: String?,
    @SerializedName("serverName")
    val serverName: String?,
    @SerializedName("deviceName")
    val deviceName: String?,
    @SerializedName("deviceLabel")
    val deviceLabel: String?,
    @SerializedName("totalItems")
    val totalItems: String?,
    @SerializedName("isRefunded")
    val isRefunded: Boolean = false,
    @SerializedName("isReprinted")
    val isReprinted: Boolean = false,
    @SerializedName("printStatusText")
    val printStatusText: String?,
    @SerializedName("isUpdatedFullVoid")
    val isUpdatedFullVoid: Boolean = false,
    @SerializedName("serverTipInfo")
    val serverTipInfo: ServerTipInfo,
    @SerializedName("deviceSalesReport")
    val deviceSalesReport: DeviceSalesReport,
    @SerializedName("gratuityInfo")
    val gratuityInfo: GratuityInfo?,
    @SerializedName("paymentQR")
    val paymentQR: String?,
    @SerializedName("footerQR")
    val footerQR: String?,
    @SerializedName("offlineHeaderMsg")
    val offlineHeaderMsg: String?,
    @SerializedName("supportInfo")
    val supportInfo: String?,
    @SerializedName("splitCount")
    val splitCount: String?,
    @SerializedName("splits")
    val splits: List<List<Breakdown>> = listOf(),
    @SerializedName("suiteLocation")
    val suiteLocation: String?,
    @SerializedName("excludeCompanyNameWatermark")
    val excludeCompanyNameWatermark: Boolean = false,
)