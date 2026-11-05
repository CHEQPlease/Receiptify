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
    val itemType: ItemType?,
    @SerializedName("isGroupHeader")
    val isGroupHeader: Boolean = false,
    /**
     * Marks a contextual annotation row that sits under a group header and is
     * rendered **centered** — e.g. the single "(Reason: ...)" line at the top
     * of a full-void KITCHEN_VOID receipt.
     */
    @SerializedName("isGroupSubHeader")
    val isGroupSubHeader: Boolean = false,
    /**
     * Marks a per-item annotation row that sits directly under its item row
     * and is rendered **left-aligned** across the full row width — e.g. the
     * per-item "(Reason: ...)" line on an item-wise (partial) void
     * KITCHEN_VOID receipt, where each voided line can carry its own reason
     * note.
     */
    @SerializedName("isReasonNote")
    val isReasonNote: Boolean = false,
    /**
     * Item-level savings (e.g. a drinks package) rendered by the customer dish
     * adapters as smaller lines between the item row and its description.
     * Nullable because Gson leaves it null when the key is absent.
     */
    @SerializedName("savings")
    val savings: List<ItemSaving>? = null,
)

/**
 * One item-level saving line: discount name, the quantity it applied to and
 * the negative amount it took off the item (e.g. "CHEERS!", "1", "-$20.00").
 */
data class ItemSaving(
    @SerializedName("name")
    val name: String?,
    @SerializedName("quantity")
    val quantity: String?,
    @SerializedName("price")
    val price: String?,
)