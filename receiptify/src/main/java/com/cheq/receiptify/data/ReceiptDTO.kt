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
    @SerializedName("serverTipInfo")
    val serverTipInfo: ServerTipInfo,
)

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

data class Breakdown(
    @SerializedName("important")
    val important: Boolean = false,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)

data class Item(
    @SerializedName("description")
    val description: String?,
    @SerializedName("itemName")
    val itemName: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("quantity")
    val quantity: String?,
    @SerializedName("strikethrough")
    val strikethrough: Boolean = false
)

data class TipsInfoBreakdown(
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)

data class TipsPerRevenueCenter(
    @SerializedName("name")
    val name: String?,
    @SerializedName("tip")
    val tip: String?,
    @SerializedName("deviceList")
    val deviceList: List<String> = listOf()
)

enum class ReceiptType {
    CUSTOMER, MERCHANT, KITCHEN, KIOSK, SERVER_TIPS
}
enum class DeviceType {
    POS, HANDHELD
}