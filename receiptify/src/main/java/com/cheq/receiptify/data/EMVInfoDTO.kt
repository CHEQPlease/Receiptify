package com.cheq.receiptify.data

import com.google.gson.annotations.SerializedName

data class EMVInfoDTO(
    @SerializedName("mid")
    val mid: String?,
    @SerializedName("tid")
    val tid: String?,
    @SerializedName("cardBrand")
    val cardBrand: String?,
    @SerializedName("cardLastFour")
    val cardLastFour: String?,
    @SerializedName("entryMode")
    val entryMode: String?,
    @SerializedName("traceNumber")
    val traceNumber: String?,
    @SerializedName("stan")
    val stan: String?,
    @SerializedName("responseCode")
    val responseCode: String?,
    @SerializedName("authCode")
    val authCode: String?,
    @SerializedName("authMode")
    val authMode: String?,
    @SerializedName("transactionStatus")
    val transactionStatus: String?,
    @SerializedName("iad")
    val iad: String?,
    @SerializedName("tsi")
    val tsi: String?,
    @SerializedName("aid")
    val aid: String?,
    @SerializedName("arc")
    val arc: String?,
    @SerializedName("tvr")
    val tvr: String?,
    @SerializedName("transactionType")
    val transactionType: String?,
    @SerializedName("isPinVerified")
    val isPinVerified: Boolean?
) 