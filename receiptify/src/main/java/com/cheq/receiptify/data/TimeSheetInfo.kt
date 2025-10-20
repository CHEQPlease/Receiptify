package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class TimeSheetInfo(
    @SerializedName("employeeName")
    val employeeName: String? = null,
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("totalWorkTime")
    val totalWorkTime: String? = null,
    @SerializedName("breakTime")
    val breakTime: String? = null,
    @SerializedName("totalTips")
    val totalTips: String? = null,
)


