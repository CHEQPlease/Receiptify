package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

enum class ItemType {
    @SerializedName("FOOD")
    FOOD,
    @SerializedName("BEVERAGE")
    BEVERAGE,
    @SerializedName("NON_FOOD_BEVERAGE")
    NON_FOOD_BEVERAGE
}

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
    val strikethrough: Boolean = false,
    @SerializedName("itemType")
    val itemType: ItemType
)